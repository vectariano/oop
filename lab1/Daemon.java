import java.util.Random;

public class Daemon implements Runnable {
    private final Program program;
    private final Random random = new Random();

    public Daemon(Program program) {
        this.program = program;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(5000));
                States state = States.values()[random.nextInt(States.values().length)];

                synchronized (program) {
                    if (!state.equals(States.UNKNOWN)) {
                        program.setState(state);
                        System.out.println("Daemon: State is changed - " + state);
                    }
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
