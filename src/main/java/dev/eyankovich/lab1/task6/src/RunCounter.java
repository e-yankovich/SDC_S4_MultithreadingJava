public class RunCounter {
    public static void run() {
        for (int i = 0; i < 50; i++) {
            System.out.printf("Thread %s message %d\n", Thread.currentThread().getName(), i);
        }
    }
}
