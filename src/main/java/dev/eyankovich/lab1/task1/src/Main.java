import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the number: ");
        int numberOfPrintingTimes = scanner.nextInt();

        TaskOneImplementation taskOneImplementation = new TaskOneImplementation();
        taskOneImplementation.numberOfPrintingTimes = numberOfPrintingTimes;

        taskOneImplementation.start();

        for (int i = 0; i < numberOfPrintingTimes; i++) {
            System.out.printf("Parent printing: %d\n", i+1);
        }

    }
}