public class RunCounter {
    public static void run(boolean isYield) {
        long start = System.nanoTime();

        for (int i = 0; i < 50; i++) {
            if (isYield) {
                // The Thread.yield() method is used to voluntarily give up the CPU by the current thread to allow other threads to execute.
                // It's a signal to the thread scheduler: "I can wait, give others a chance."
                // However, the behavior of yield() is not guaranteed: the JVM and operating system may ignore it.
                Thread.yield();
            }
            System.out.printf("Thread %s message %d\n", Thread.currentThread().getName(), i);
        }

        long end = System.nanoTime();
        long duration = (end - start) / 1_000_000;

        System.out.printf("Thread %s finished in %d ms\n", Thread.currentThread().getName(), duration);
    }
}
