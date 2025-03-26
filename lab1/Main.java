public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        Daemon daemon = new Daemon(program);
        Supervisor supervisor = new Supervisor(program);

        Thread daemonThread = new Thread(daemon);
        Thread supervisorThread = new Thread(supervisor);

        daemonThread.setDaemon(true);
        daemonThread.start();
        supervisorThread.start();

    }
}
