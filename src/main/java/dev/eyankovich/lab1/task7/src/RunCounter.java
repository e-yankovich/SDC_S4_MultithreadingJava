public class RunCounter {
    public static void run(boolean isYield) {

        for (int i = 0; i < 50; i++) {
            if (isYield) {
                Thread.yield();
            }
            System.out.printf("Thread %s message %d\n", Thread.currentThread().getName(), i);
        }
    }
}
