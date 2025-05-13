public class Interrupt {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted");
            }
        });

        t.start();
        t.interrupt();
    }
}
