package study.util;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class XMemcachedPerformanceTest2 {
    static Map<String, Book> map2 = new HashMap<String, Book>();
    static final int ELEMENT_NUM = 100;
    static {  
        for (int i = 0; i < ELEMENT_NUM; i++)  
            map2.put(String.valueOf(i), new Book(i, String.valueOf(i), String.valueOf(i)));  
    }  
  
    static class TestWriteRunnable implements Runnable {  
  
        private MemcachedClient mc;
        private CountDownLatch cd;
        int repeat;  
        int start;  
  
        public TestWriteRunnable(MemcachedClient mc, int start, CountDownLatch cdl, int repeat) {  
            super();  
            this.mc = mc;  
            this.start = start;  
            this.cd = cdl;  
            this.repeat = repeat;  
  
        }  
  
        public void run() {  
            try {  
  
                for (int i = 0; i < repeat; i++) {  
                    String key = String.valueOf(start + i);  
                    if (!mc.set(key, 0, map2)) {  
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
  
        private MemcachedClient mc;  
        private CountDownLatch cd;  
        int repeat;  
        int start;  
  
        public TestReadRunnable(MemcachedClient mc, int start, CountDownLatch cdl, int repeat) {  
            super();  
            this.mc = mc;  
            this.start = start;  
            this.cd = cdl;  
            this.repeat = repeat;  
  
        }  
  
        @SuppressWarnings("unchecked")  
        public void run() {  
            try {  
                for (int i = 0; i < repeat; i++) {  
  
                    String key = String.valueOf(start + i);  
                    Map<String, Book> result = (Map<String, Book>) mc.get(key);
                    if (result.size() != ELEMENT_NUM) {  
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
  
        private MemcachedClient mc;  
        private CountDownLatch cd;  
        int repeat;  
        int start;  
  
        public TestDeleteRunnable(MemcachedClient mc, int start, CountDownLatch cdl, int repeat) {  
            super();  
            this.mc = mc;  
            this.start = start;  
            this.cd = cdl;  
            this.repeat = repeat;  
  
        }  
  
        public void run() {  
            try {  
                for (int i = 0; i < repeat; i++) {  
                    String key = String.valueOf(start + i);  
                    if (!mc.delete(key))  
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
  
            int thread = 50;  
  
            int repeat = ELEMENT_NUM;
  
            MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(address), new int[] { 1,
                    1, 1 });  
            MemcachedClient mc = null;  
            builder.setConnectionPoolSize(5);  
            try {
                builder.setCommandFactory(new BinaryCommandFactory());
                mc = builder.build();  
            } catch (IOException e) {
                e.printStackTrace();  
            }  
  
            CountDownLatch cdl = new CountDownLatch(thread);  
            long t = System.currentTimeMillis();  
            for (int i = 0; i < thread; i++) {  
                new Thread(new XMemcachedPerformanceTest2.TestWriteRunnable(mc, i * 10000, cdl, repeat)).start();
            }  
            try {  
                cdl.await();  
            } catch (InterruptedException e) {  
  
            }  
            long all = thread * repeat;  
            long usingtime = (System.currentTimeMillis() - t);  
  
            System.out.println(String.format(  
                    "test write,thread num=%d, repeat=%d,size=%d, all=%d ,velocity=%d , using time:%d", thread, repeat,  
                    size, all, 1000 * all / usingtime, usingtime));  
  
            cdl = new CountDownLatch(thread);  
            t = System.currentTimeMillis();  
            for (int i = 0; i < thread; i++) {  
                new Thread(new XMemcachedPerformanceTest2.TestReadRunnable(mc, i * 10000, cdl, repeat)).start();
            }  
            try {  
                cdl.await();  
            } catch (InterruptedException e) {  
  
            }  
            all = thread * repeat;  
            usingtime = (System.currentTimeMillis() - t);  
            System.out.println(String.format(  
                    "test read,thread num=%d, repeat=%d,size=%d, all=%d ,velocity=%d , using time:%d", thread, repeat,  
                    size, all, 1000 * all / usingtime, usingtime));  
            cdl = new CountDownLatch(thread);  
            t = System.currentTimeMillis();  
            for (int i = 0; i < thread; i++) {  
                new Thread(new XMemcachedPerformanceTest2.TestDeleteRunnable(mc, i * 10000, cdl, repeat)).start();
            }  
            try {  
                cdl.await();  
            } catch (InterruptedException e) {  
  
            }  
            all = thread * repeat;  
            usingtime = (System.currentTimeMillis() - t);  
            System.out.println(String.format(  
                    "test delete,thread num=%d, repeat=%d,size=%d, all=%d ,velocity=%d , using time:%d", thread,  
                    repeat, size, all, 1000 * all / usingtime, usingtime));  
  
            mc.shutdown();

            SpyMemcachedPerformanceTest2.main(null);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  