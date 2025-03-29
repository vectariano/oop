public class Program implements Runnable {
    private volatile States state = States.UNKNOWN;
    protected Thread programThread;
    private volatile boolean running = true;

    public synchronized void setState(States newState) {
        state = newState;
        System.out.println("Daemon: State is changed - " + state);
        notifyAll();
    }

    public synchronized States getState() {
        return state;
    }

    public void restart() {
        running = true;
        state = States.RUNNING;
        System.out.println("Program: Restarted");
        notifyAll();
    }

//    public void setProgramThread(Thread thread) {
//        this.programThread = thread;
//    }

//    public Thread getProgramThread() {
//        return programThread;
//    }

    public void stop() {
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        System.out.println("Program: Start");
        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                stop();
            }
        }
    }

}