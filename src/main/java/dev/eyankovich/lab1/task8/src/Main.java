public class Main {
    public static void main(String[] args) {

        final int M = 10; // Producer delay to switch state from true to false and vice versa
        final int K = 100; // Consumer countdown time in milliseconds
        final int consumerCheckDelay = M / 10; // Period of time after which Consumer checks Producer's state

        System.out.println("Starting Producer/Consumer application");
        System.out.println("Producer delay (M): " + M + " ms");
        System.out.println("Consumer countdown time (K): " + K + " ms");
        System.out.println("Consumer check delay (M/10): " + consumerCheckDelay + " ms");

        ProducerThread producer = new ProducerThread(M);
        producer.start();

        ConsumerThread consumer = new ConsumerThread(producer, K, consumerCheckDelay);
        consumer.start();

        try {
            consumer.join();
            producer.join();
            System.out.println("Application completed.");
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted");
            Thread.currentThread().interrupt();
        }
    }
}