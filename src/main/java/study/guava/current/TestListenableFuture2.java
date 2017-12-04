package study.guava.current;

import com.google.common.util.concurrent.*;

import java.util.Random;
import java.util.concurrent.*;

public class TestListenableFuture2 {
    // 创建线程池
    final static ListeningExecutorService listeningService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public static void main(String[] args) throws Exception {


        Long t1 = System.currentTimeMillis();
        // 任务1
        ListenableFuture<Boolean> booleanTask = listeningService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        });

        Futures.addCallback(booleanTask, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                System.err.println("BooleanTask: " + result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        // 任务2
        ListenableFuture<String> stringTask = listeningService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello World";
            }
        });

        Futures.addCallback(stringTask, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.err.println("StringTask: " + result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        // 任务3
        ListenableFuture<Integer> integerTask = listeningService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        });

        Futures.addCallback(integerTask, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("IntegerTask: " + result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        // 执行时间
        System.err.println("time: " + (System.currentTimeMillis() - t1));
    }





    // 创建线程池
    final static ExecutorService service = Executors.newCachedThreadPool();

    public static void main2(String[] args) throws InterruptedException, ExecutionException {
        Long t1 = System.currentTimeMillis();

        // 任务1
        Future<Boolean> booleanTask = service.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        });

        while (true) {
            if (booleanTask.isDone() && !booleanTask.isCancelled()) {
                //模拟耗时
                Thread.sleep(500);
                Boolean result = booleanTask.get();
                System.err.println("BooleanTask: " + result);
                break;
            }
        }

        // 任务2
        Future<String> stringTask = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello World";
            }
        });

        while (true) {
            if (stringTask.isDone() && !stringTask.isCancelled()) {
                String result = stringTask.get();
                System.err.println("StringTask: " + result);
                break;
            }
        }



        // 任务3
        Future<Integer> integerTask = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        });

        while (true) {
            if (integerTask.isDone() && !integerTask.isCancelled()) {
                Integer result = integerTask.get();
                System.err.println("IntegerTask: " + result);
                break;
            }
        }

        // 执行时间
        System.err.println("time: " + (System.currentTimeMillis() - t1));
    }

}
