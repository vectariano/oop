import java.util.Random;

public class Daemon implements Runnable {
    private final Random random = new Random();
    private final Program program;

    public Daemon(Program program) {
        this.program = program;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (program) {
                while (Program.state.equals(States.UNKNOWN)) {
                     Program.state = States.values()[random.nextInt(States.values().length)];
                    System.out.println("Daemon: " + Program.state);
                }
//                if (Program.state.equals(States.RUNNING)) {
//                    System.out.println("Daemon: Runs");
//                }
//
//                else {
//                    System.out.println("Daemon: " + Program.state);
//                }

                try {
                    if (random.nextInt(100) > 45) {
                        program.setState(States.RUNNING);
                    }
                    else if (random.nextInt(100) < 45 &&
                            random.nextInt(100) > 10) {
                        program.setState(States.STOPPING);
                    }
                    else {
                        program.setState(States.FATAL_ERROR);
                    }
                    program.wait();
                    program.notifyAll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
