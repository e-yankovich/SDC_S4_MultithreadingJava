public class Main {
    public static void main(String[] args) {

        final int M = 10; // Producer delay in milliseconds
        final int K = 100; // Consumer countdown time in milliseconds
        final int consumerCheckDelay = M / 10; // Consumer check delay (M/10 milliseconds)

        System.out.println("Starting Producer/Consumer application");
        System.out.println("Producer delay (M): " + M + " ms");
        System.out.println("Consumer countdown time (K): " + K + " ms");
        System.out.println("Consumer check delay (M/10): " + consumerCheckDelay + " ms");

        // Create and start the Producer thread
        ProducerThread producer = new ProducerThread(M);
        producer.start();

        // Create and start the Consumer thread
        ConsumerThread consumer = new ConsumerThread(producer, K, consumerCheckDelay);
        consumer.start();

        // Wait for threads to complete
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