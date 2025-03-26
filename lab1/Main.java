public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        Thread programThread = new Thread(program);
        Daemon daemon = new Daemon(program);
        program.setProgramThread(programThread);

        Supervisor supervisor = new Supervisor(program);
        Thread daemonThread = new Thread(daemon);
        Thread supervisorThread = new Thread(supervisor);

        daemonThread.setDaemon(true);
        daemonThread.start();
        programThread.start();
        supervisorThread.start();

    }
}
