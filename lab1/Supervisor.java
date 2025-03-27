public class Supervisor implements Runnable {
    private final Program program;

    public Supervisor(Program program) {
        this.program = program;
    }

    public void stopProgram() {
        System.out.println("SUPERVISOR: Exit ");
        program.stop();
    }

    public void startProgram() {
        System.out.println("SUPERVISOR: Restart");
        Thread newThread = new Thread(program);
        program.setProgramThread(newThread);
        program.setState(States.RUNNING);
        newThread.start();
    }

    @Override
    public void run() {
        System.out.println("SUPERVISOR: Start");
        while (true) {
            synchronized (program) {

                States state = program.getState();


                if (state.equals(States.STOPPING)) {
                    startProgram();
                }

                else if (state.equals(States.FATAL_ERROR)) {
                    stopProgram();
                    break;
                }
                else {
                    System.out.println("SUPERVISOR: Pass");
                }

                program.notify();

                try {
                    program.wait();
                } catch (InterruptedException e) {
                    program.stop();
                    break;
                }

            }
        }
    }
}