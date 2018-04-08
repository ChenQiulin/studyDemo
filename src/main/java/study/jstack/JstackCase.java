package study.jstack;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JstackCase {
    public static Executor executor = Executors.newFixedThreadPool(5);
    public static Object lock = new Object();

    public static void main(String[] args) {
        Task taskl = new Task();
        Task task2 = new Task();
        executor.execute(taskl);
        executor.execute(task2);
    }

    static class Task implements Runnable {


        @Override
        public void run() {
            synchronized (lock) {
                calculate();
            }




        }

        public void calculate() {
            int i = 0;
            while (true) {
            }
        }
    }
}
