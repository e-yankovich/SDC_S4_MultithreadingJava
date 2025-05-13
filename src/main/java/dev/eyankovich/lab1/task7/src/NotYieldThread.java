public class NotYieldThread implements Runnable {
    @Override
    public void run() {
        RunCounter.run(false);
    }
}