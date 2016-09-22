package study.redis;

import com.google.common.base.Stopwatch;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SimpleTest
 *
 * @author Wilson Chen
 * @date 2016/5/30
 */
public class RedisSimpleTest {

    private static BinaryJedisCluster binaryJc;

    private static JedisCluster jc;
    static {
        //只给集群里一个实例就可以
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        jedisClusterNodes.add(new HostAndPort("172.16.100.58", 6380));
//        jedisClusterNodes.add(new HostAndPort("172.16.100.58", 6381));
//        jedisClusterNodes.add(new HostAndPort("172.16.100.58", 6382));
//        jedisClusterNodes.add(new HostAndPort("172.16.100.58", 7380));
//        jedisClusterNodes.add(new HostAndPort("172.16.100.58", 7381));
//        jedisClusterNodes.add(new HostAndPort("172.16.100.58", 7382));
        binaryJc = new BinaryJedisCluster(jedisClusterNodes);
         jc = new JedisCluster(jedisClusterNodes);
    }

    public static  void testBenchRedisSet() throws Exception {
//        final Stopwatch stopwatch = new Stopwatch();
       // List list = buildBlogVideos();
        for (int i = 0; i < 100; i++) {
            String key = "key" + i;
//            stopwatch.start();
           String  bytes1 = "value"+i;
            System.out.println(String.valueOf(binaryJc.get(key.getBytes())));
            System.out.println("");
            System.out.println(binaryJc.get(("k:" + i).getBytes()));
//            binaryJc.setex(key.getBytes(), 60 * 60, bytes1.getBytes());
//            stopwatch.stop();
        }
//        System.out.println("time=" + stopwatch.toString());
    }

    public static void main(String[] args) throws Exception{
//        testBenchRedisSet();
        testKeyValue();
    }


    private  static  void testKeyValue(){

        for (int i = 1; i <= 10000; i++) {
            long start = System.currentTimeMillis();
            jc.set("k:" + i, "v" + i);
            System.out.print("set " + i +"th value in " + (System.currentTimeMillis() - start) + " ms");
            start = System.currentTimeMillis();
            jc.get("k:" + i);
            System.out.println(", get " + i +"th value in " + (System.currentTimeMillis() - start) + " ms");
        }
    }


    private  static  void testPush(){

        for (int i = 1; i <= 10000; i++) {
            long start = System.currentTimeMillis();
            jc.set("k:" + i, "v" + i);
            System.out.print("set " + i +"th value in " + (System.currentTimeMillis() - start) + " ms");
            start = System.currentTimeMillis();
            jc.get("k:" + i);
//            jc.pu
            System.out.println(", get " + i +"th value in " + (System.currentTimeMillis() - start) + " ms");
        }
    }
}
