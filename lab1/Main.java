public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        Supervisor supervisor = new Supervisor(program);

        Thread programThread = new Thread(program);
        Thread supervisorThread = new Thread(supervisor);

        programThread.start();
        supervisorThread.start();

    }
}
