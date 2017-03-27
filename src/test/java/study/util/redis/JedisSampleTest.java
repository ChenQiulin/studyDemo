package study.util.redis;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;
import study.util.DateUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * JedisSampleTest
 *
 * @author Wilson Chen
 * @date 2016/10/20
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:context/applicationContext.xml"})
public class JedisSampleTest {

    @Autowired
    JedisCluster jedisCluster;


    @Test
    public void testKeyValue() {

        for (int i = 1; i <= 10000; i++) {
            long start = System.currentTimeMillis();
            jedisCluster.set("k" + i, "v" + i);
            System.out.print("set " + i +"th value in " + (System.currentTimeMillis() - start) + " ms");

            start = System.currentTimeMillis();
            String value = jedisCluster.get("k" + i);
            System.out.println(value + ", get " + i + "th value in " + (System.currentTimeMillis() - start) + " ms");

            jedisCluster.del("k" + i);
        }
    }

    @Ignore
    @Test
    public void testListPushAndPop() throws Exception {

        Set<String> keySet = new HashSet();
        for (int i = 0; i <= 1000; i++) {
            String key = DateUtil.convertDateToStr(new Date(), "yyyy-MM-dd-HH:mm:ss");
            jedisCluster.rpush(key, String.valueOf(System.currentTimeMillis()));
            keySet.add(key);
            Thread.sleep(10);
        }

        for (String key : keySet) {
            System.out.println("pop list: " + key + "   " + jedisCluster.rpop(key));
        }

    }
}
