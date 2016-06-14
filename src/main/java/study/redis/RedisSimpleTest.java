package study.redis;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

/**
 * SimpleTest
 *
 * @author Wilson Chen
 * @date 2016/5/30
 */
public class RedisSimpleTest {

    static {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "172.16.100.58",30001);
    }



    public static void main(String[] args) {
//        Jedis jedis = new Jedis("172.16.100.58", 30002);
//        jedis.set("foo", "bar");
//        for (int i = 1; i <= 10000; i++) {
//            long start = System.currentTimeMillis();
//            jedis.set("k:" + i, "v" + i);
//            System.out.print("set " + i +"th value in " + (System.currentTimeMillis() - start) + " ms");
//            start = System.currentTimeMillis();
//            jedis.get("k:" + i);
//            System.out.println(", get " + i +"th value in " + (System.currentTimeMillis() - start) + " ms");
//        }
    }


    private  void test1(){
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        jedisClusterNodes.add(new HostAndPort("172.16.100.58", 30001));
        jedisClusterNodes.add(new HostAndPort("172.16.100.58", 30002));
        jedisClusterNodes.add(new HostAndPort("172.16.100.58", 30003));
        JedisCluster jc = new JedisCluster(jedisClusterNodes);
        for (int i = 1; i <= 10000; i++) {
            long start = System.currentTimeMillis();
            jc.set("k:" + i, "v" + i);
            System.out.print("set " + i +"th value in " + (System.currentTimeMillis() - start) + " ms");
            start = System.currentTimeMillis();
            jc.get("k:" + i);
            System.out.println(", get " + i +"th value in " + (System.currentTimeMillis() - start) + " ms");
        }
    }
}
