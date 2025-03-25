public class Supervisor implements Runnable {
    private final Program program;
    private final Object lock = new Object();
    private boolean running = true;


    public Supervisor(Program program) {
        this.program = program;
    }

    public void stop() {
        program.setState(States.STOPPING);
        program.stop();
    }

    public void startProgram() {
        program.setState(States.RUNNING);
    }

    @Override
    public void run() {
        while (running) {
            synchronized (lock) {
                States state = program.getState();
                System.out.println("Supervisor: " + state);

                if (state.equals(States.STOPPING)) {
                    System.out.println("Supervisor: Restart");
                    startProgram();
                }

                else if (state.equals(States.FATAL_ERROR)) {
                    System.out.println("Supervisor: Exit" + state);
                    stop();
                }

                try {
                    program.wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

}
