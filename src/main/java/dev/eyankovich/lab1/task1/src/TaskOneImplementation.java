public class TaskOneImplementation extends Thread
{
    public int numberOfPrintingTimes;

    @Override
    public void run(){
        for (int i = 0; i < numberOfPrintingTimes; i++) {
            System.out.printf("Child printing: %d\n", i+1);
        }
    }
}