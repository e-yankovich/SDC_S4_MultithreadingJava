public class Main {
    public static void main(String[] args) {

        Thread walkMin = new Thread(new WalkThread(), "Min");
        Thread talkMax = new Thread(new TalkThread(), "Max");
        walkMin.setPriority(Thread.MIN_PRIORITY);
        talkMax.setPriority(Thread.MAX_PRIORITY);
        talkMax.start();
        walkMin.start();

        // A thread with MAX_PRIORITY may perform more iterations than one with MIN_PRIORITY,
        // as higher priority can give it more CPU time.
        // This behavior is not guaranteed - the JVM and operating system
        // may choose to ignore thread priorities or handle them differently depending on the platform.

    }
}