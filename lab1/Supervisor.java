public class Supervisor implements Runnable {
    private final Program program;

    public Supervisor(Program program) {
        this.program = program;
    }

    public void stopProgram() {
        Program.programThread.interrupt();
        System.out.println("SUPERVISOR: Exit ");
    }

    public void startProgram() {
        Program.programThread = new Thread(new Program());
        Program.state = States.RUNNING;
        Program.programThread.start();
        System.out.println("SUPERVISOR: Restart");
    }

    @Override
    public void run() {
        System.out.println("SUPERVISOR: Start");
        startProgram();
        while (!Program.programThread.isInterrupted()) {
            synchronized (program) {
                States state = Program.state;

                if (state.equals(States.STOPPING)) {
                    startProgram();
                }

                else if (state.equals(States.FATAL_ERROR)) {
                    stopProgram();
                }
                else {
                    System.out.println("SUPERVISOR: Pass");
                }

                program.notify();
            }
        }
    }
}
