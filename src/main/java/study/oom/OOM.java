package study.oom;

import javassist.ClassPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * https://plumbr.eu/outofmemoryerror
 */
class OOM {
    static class Key {
        Integer id;

        Key(Integer id) {
            this.id = id;
        }

        //        @Override
//        public int hashCode() {
//            return id.hashCode();
//        }
        @Override
        public boolean equals(Object o) {
            boolean response = false;
            if (o instanceof Key) {
                response = (((Key) o).id).equals(this.id);
            }
            return response;
        }

    }

    /**
     * VM params: -Xmx8m
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {


//        heapSpace();
//
//        GCOverheadLimit();
//
//        permgenSpace();
//
//        metaspace();

  //      unableToCreateNewNativeThread();

    }

    /**
     * vm: -Xmx100m -XX:+UseParallelGC
     * GC overhead limit exceeded
     */

    public static void GCOverheadLimit() {
        Map map = System.getProperties();
        Random r = new Random();
        while (true) {
            map.put(r.nextInt(), "value");
        }

    }

    /**
     * java.lang.OutOfMemoryError: Java heap space
     */
    public static void heapSpace() {
        Map m = new HashMap();
        while (true)
            for (int i = 0; i < 100000; i++) {
                System.out.println(i);
                if (!m.containsKey(new Key(i)))
                    m.put(new Key(i), "Number:" + i);
            }
    }

    public static void permgenSpace() throws Exception {
        for (int i = 0; i < 100_000_000; i++) {
            generate("study.oom.Generated" + i);
        }
    }

    public static Class generate(String name) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        return pool.makeClass(name).toClass();
    }

    public static void metaspace() throws Exception {
        javassist.ClassPool cp = javassist.ClassPool.getDefault();
        for (int i = 0; ; i++) {
            Class c = cp.makeClass("study.oom" + i).toClass();
        }
    }

    public static void unableToCreateNewNativeThread() {
        while (true) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(10000000);
                    } catch (InterruptedException e) {
                    }
                }
            }).start();
        }
    }
}