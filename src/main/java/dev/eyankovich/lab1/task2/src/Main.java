public class Main {

    public static void main(String[] args) {

        new T1Thread(new R1Runnable()).start();
    }

//  When a Runnable is passed to a subclass of Thread,
//  the thread will normally execute the run() method of the provided Runnable —
//  but only if the subclass does not override its own run() method.

//  If the subclass overrides the run() method,
//  that method takes priority, and the Runnable's run() method
//  is ignored unless the subclass explicitly calls it within its own run() method.

}