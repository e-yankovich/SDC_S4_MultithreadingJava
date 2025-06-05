public class RunCounter {
    public static void run() {
        long startTime = System.nanoTime();

        for (int i = 0; i < 50; i++) {
            System.out.printf("Thread %s message %d\n", Thread.currentThread().getName(), i);
        }

        long endTime = System.nanoTime();
        long elapsedTime = (endTime - startTime)/1_000_000;
        System.out.printf("Thread %s finished in %d ms\n", Thread.currentThread().getName(), elapsedTime);
    }
}
