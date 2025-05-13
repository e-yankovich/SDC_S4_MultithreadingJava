import java.util.List;

public class MessagingThread extends Thread {

    private final List<String> messages;

    MessagingThread(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public void run() {
        for (String message : messages) {
            System.out.println(message);
        }
    }
}
