package study.redis;

import redis.clients.jedis.*;

import java.util.LinkedList;
import java.util.List;

public class JedisDemo {
    private static ShardedJedisPool shardedPool;
    private static JedisPool pool1;
    private static JedisPool pool2;
    static {
        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxActive(1024);
        config.setMaxIdle(200);
//        config.setMaxWait(1000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        /*第一台服务器,阿里云上*/
        JedisShardInfo jedisShardInfo1 = new JedisShardInfo("134.57.81.xxx",6379);
        /*第二台服务器，公司内部测试*/
        JedisShardInfo jedisShardInfo2 = new JedisShardInfo("192.168.1.xxx",6379);
        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
        list.add(jedisShardInfo1);
        list.add(jedisShardInfo2);
        shardedPool = new ShardedJedisPool(config, list);
        /*第一台服务器*/
        pool1=new JedisPool("134.57.81.xxx", 6379);
        /*第二台服务器*/
        pool2=new JedisPool("192.168.1.xxx", 6379);
    }
 
    public static void main(String[] args) {
        ShardedJedis jedis = shardedPool.getResource();
        Jedis jedis1=pool1.getResource();
        Jedis jedis2=pool2.getResource();
        jedis.set("name", "snowo21");
        System.out.println(jedis.get("name"));
        System.out.println("第一台服务器："+jedis1.get("name"));
        System.out.println("第二台服务器："+jedis2.get("name"));
        shardedPool.returnResource(jedis);
        pool1.returnResource(jedis1);
        pool2.returnResource(jedis2);
    }
}