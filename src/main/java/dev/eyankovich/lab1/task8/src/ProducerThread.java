public class ProducerThread extends Thread {

    private boolean state = false;
    private final int delayMillis;
    private boolean running = true;

    public ProducerThread(int delayMillis) {
        this.delayMillis = delayMillis;
    }

    @Override
    public void run() {
        try {
            while (running) {
                synchronized (this) {
                    state = !state;
                    System.out.println("Producer: State changed to " + state);
                    notifyAll();
                }
                Thread.sleep(delayMillis);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean getCurrentState() {
        return state;
    }

    public synchronized void stopThread(){
        running = false;
        notifyAll();
    }
}