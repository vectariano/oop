import java.util.Random;

public class Program implements Runnable {
    private States state = States.UNKNOWN;
    private final Object lock = new Object();
    private final Random random = new Random();
    private boolean running = true;
    private final Daemon daemon;

    public Program() {
        this.daemon = new Daemon(this);
        Thread daemonThread = new Thread(daemon);
        daemonThread.setDaemon(true);
        daemonThread.start();
    }

    public void setState(States newState) {
        synchronized (lock) {
            state = newState;
            System.out.println("Program: State is changed - " + state);
            lock.notify();
        }
    }

    public States getState() {
        synchronized (lock) {
            return state;
        }
    }

    public void stop() {
        setState(States.STOPPING);
        running = false;
    }

    @Override
    public void run() {
        synchronized (lock) {
            System.out.println("Program: Start");
            while (running) {
                try {
                    while (state.equals(States.UNKNOWN)) {
                        setState(States.values()[random.nextInt(States.values().length)]);
                    }
                    System.out.println("Program: " + state);
                    lock.wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }


}
