package study.util;

import java.util.concurrent.CountDownLatch;

public class SpyMemcachedPerformanceTest {

    static class TestWriteRunnable implements Runnable {


        private CountDownLatch cd;
        int repeat;
        int start;

        public TestWriteRunnable( int start, CountDownLatch cdl, int repeat) {
            super();
            this.start = start;
            this.cd = cdl;
            this.repeat = repeat;

        }

        public void run() {
            try {

                for (int i = 0; i < repeat; i++) {
                    String key = String.valueOf(start + i);
                    if (!MemcachedUtil.set(key, 0, key)) {
                        System.err.println("set error");
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cd.countDown();
            }
        }

    }

    static class TestReadRunnable implements Runnable {

        private CountDownLatch cd;
        int repeat;
        int start;

        public TestReadRunnable(int start, CountDownLatch cdl, int repeat) {
            super();
            this.start = start;
            this.cd = cdl;
            this.repeat = repeat;

        }

        public void run() {
            try {
                for (int i = 0; i < repeat; i++) {

                    String key = String.valueOf(start + i);
                    String result = (String) MemcachedUtil.get(key);
                    if (!key.equals(result)) {
                        System.out.println(key + " " + result);
                        System.err.println("get error");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cd.countDown();
            }
        }

    }

    static class TestDeleteRunnable implements Runnable {

        private CountDownLatch cd;
        int repeat;
        int start;

        public TestDeleteRunnable(int start, CountDownLatch cdl, int repeat) {
            super();
            this.start = start;
            this.cd = cdl;
            this.repeat = repeat;

        }

        public void run() {
            try {
                for (int i = 0; i < repeat; i++) {
                    String key = String.valueOf(start + i);
                    if (!MemcachedUtil.delete(key))
                        System.err.println("delete error");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cd.countDown();
            }
        }

    }

    // thread num=10, repeat=10000,size=2, all=200000 ,velocity=1057 , using
    // time:189187
    static public void main(String[] args) {
        try {
            String address = Constant.MEMCACHED_HOSTS;

            int size = Runtime.getRuntime().availableProcessors();

            int thread = 100;

            int repeat = 10000;

           
            CountDownLatch cdl = new CountDownLatch(thread);
            long t = System.currentTimeMillis();
            for (int i = 0; i < thread; i++) {
                new Thread(new SpyMemcachedPerformanceTest.TestWriteRunnable( i * 10000, cdl, repeat)).start();
            }
            cdl.await();
            long all = thread * repeat;
            long usingtime = (System.currentTimeMillis() - t);

            System.out.println(String.format(
                    "spymemcached test write,thread num=%d, repeat=%d,size=%d, all=%d ,velocity=%d , using time:%d", thread, repeat,
                    size, all, 1000 * all / usingtime, usingtime));

            cdl = new CountDownLatch(thread);
            t = System.currentTimeMillis();
            for (int i = 0; i < thread; i++) {
                new Thread(new SpyMemcachedPerformanceTest.TestReadRunnable( i * 10000, cdl, repeat)).start();
            }
            cdl.await();
            all = thread * repeat;
            usingtime = (System.currentTimeMillis() - t);
            System.out.println(String.format(
                    "spymemcached test read,thread num=%d, repeat=%d,size=%d, all=%d ,velocity=%d , using time:%d", thread, repeat,
                    size, all, 1000 * all / usingtime, usingtime));
            cdl = new CountDownLatch(thread);
            t = System.currentTimeMillis();
            for (int i = 0; i < thread; i++) {
                new Thread(new SpyMemcachedPerformanceTest.TestDeleteRunnable( i * 10000, cdl, repeat)).start();
            }
            cdl.await();
            all = thread * repeat;
            usingtime = (System.currentTimeMillis() - t);
            System.out.println(String.format(
                    "spymemcached test delete,thread num=%d, repeat=%d,size=%d, all=%d ,velocity=%d , using time:%d", thread,
                    repeat, size, all, 1000 * all / usingtime, usingtime));


            XMemcachedPerformanceTest.main(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}  