public class YieldThread implements Runnable {
    @Override
    public void run() {
        RunCounter.run(true);
    }
}
