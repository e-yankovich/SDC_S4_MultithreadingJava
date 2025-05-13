public class Main {
    public static void main(String[] args) {

        Thread yieldThread = new Thread(new YieldThread(), "YieldThread");
        Thread notYieldThread = new Thread(new NotYieldThread(), "NotYieldThread");

        yieldThread.start();
        notYieldThread.start();

    }
}