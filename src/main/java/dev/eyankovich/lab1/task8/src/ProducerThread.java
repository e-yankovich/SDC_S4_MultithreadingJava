public class ProducerThread extends Thread {
    // The volatile keyword ensures that the variable is always read from and written to main memory,
    // rather than from CPU cache, making it thread-safe for basic operations.
    // This is necessary since 'state' is accessed by both producer and consumer threads
    private volatile boolean state = false;
    private final int delayMillis;
    private boolean running = true;

    public ProducerThread(int delayMillis) {
        this.delayMillis = delayMillis;
    }

    @Override
    public void run() {
        try {
            while (running) {
                state = !state;
                System.out.println("Producer: State changed to " + state);
                Thread.sleep(delayMillis);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean getCurrentState() {
        return state;
    }

    public void stopThread() {
        running = false;
    }
}