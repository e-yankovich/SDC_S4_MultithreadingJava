public class ConsumerThread extends Thread {
    private final ProducerThread producer;
    private final int countdownMillis;
    private final int checkDelayMillis;
    private boolean running = true;

    public ConsumerThread(ProducerThread producer, int countdownMillis, int checkDelayMillis) {
        this.producer = producer;
        this.countdownMillis = countdownMillis;
        this.checkDelayMillis = checkDelayMillis;
    }

    @Override
    public void run() {
        try {
            int remainingTime = countdownMillis;

            while (running && remainingTime > 0) {
                synchronized (producer) {
                    // solution with while is not optimal. will determine why and fix
                    while (!producer.getCurrentState()) {
                        System.out.println("Consumer: Waiting for producer state to become true");
                        producer.wait();
                    }

                    System.out.println("Consumer: Countdown - " + remainingTime + " ms");
                    remainingTime -= checkDelayMillis;

                    if (remainingTime <= 0) {
                        System.out.println("Consumer: Countdown complete!");
                        producer.stopThread();
                        running = false;
                        break;
                    }
                }

                    Thread.sleep(checkDelayMillis);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stopThread() {
        running = false;
    }
}