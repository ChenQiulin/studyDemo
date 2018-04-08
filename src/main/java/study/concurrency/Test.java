package study.concurrency;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService ex = Executors.newFixedThreadPool(1);
        ex.execute(new TestTask());

    }
}
class TestTask implements Runnable {
    @Override
    public void run() {

        synchronized (this) {
            try {
                //等待被唤醒
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
