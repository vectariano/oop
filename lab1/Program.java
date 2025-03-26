
public class Program implements Runnable {
    protected static States state = States.UNKNOWN;
    protected static Thread programThread;

    public void setState(States newState) {
        state = newState;
        System.out.println("Daemon: State is changed - " + state);
        notifyAll();
    }

    public States getState() {
        return state;
    }

    @Override
    public void run() {
        System.out.println("Program: Start");
    }

}
