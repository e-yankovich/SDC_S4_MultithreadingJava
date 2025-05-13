import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int NUMBER_OF_THREADS = 5;
    private static final int NUMBER_OF_MESSAGES = 10;


    public static void main(String[] args) {

        List<Thread> threads = new ArrayList<>(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            MessagingThread thread = new MessagingThread(createStrings(i));
            threads.add(thread);
            thread.start();
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    private static List<String> createStrings(int threadNumber) {
        List<String> output = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_MESSAGES; i++) {
            output.add(String.format("Thread %d message %d", threadNumber + 1, i));
        }
        return output;
    }
}