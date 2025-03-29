public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        Thread programThread = new Thread(program);
//        program.setProgramThread(programThread);

        Daemon daemon = new Daemon(program);
        Thread daemonThread = new Thread(daemon);

        Supervisor supervisor = new Supervisor(program);
        Thread supervisorThread = new Thread(supervisor);

        programThread.start();
        daemonThread.setDaemon(true);
        daemonThread.start();
        supervisorThread.start();

    }
}
