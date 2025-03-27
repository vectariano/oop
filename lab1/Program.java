public class Program implements Runnable {
    protected volatile States state = States.UNKNOWN;
    protected Thread programThread;

    public synchronized void setState(States newState) {
        state = newState;
        System.out.println("Daemon: State is changed - " + state);
        notifyAll();
    }

    public synchronized States getState() {
        return state;
    }

    public void setProgramThread(Thread thread) {
        this.programThread = thread;
    }

    public Thread getProgramThread() {
        return programThread;
    }

    public void stop() {
        programThread.interrupt();
    }

    @Override
    public void run() {
        System.out.println("Program: Start");

    }

}