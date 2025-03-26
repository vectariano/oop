import java.util.Random;

public class Daemon implements Runnable {
    private final Random random = new Random();
    private final Program program;
    private volatile boolean running = true;

    public Daemon(Program program) {
        this.program = program;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (program) {
                int probability = random.nextInt(100);
                States newState;

                if (probability > 10 && probability < 50) {
                    newState = States.STOPPING;
                }
                else if (probability > 50) {
                    newState = States.RUNNING;
                }
                else {
                    newState = States.FATAL_ERROR;
                }
                program.setState(newState);
                program.notifyAll();

                if (newState.equals(States.FATAL_ERROR)) {
                    stop();
                    break;
                }

            }
        }
        System.out.println("Daemon: Stopped");
    }
}
