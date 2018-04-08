//package study.util;
//
//import com.google.common.collect.Sets;
//import net.rubyeye.xmemcached.MemcachedClient;
//import net.rubyeye.xmemcached.MemcachedClientBuilder;
//import net.rubyeye.xmemcached.XMemcachedClient;
//import net.rubyeye.xmemcached.XMemcachedClientBuilder;
//import net.rubyeye.xmemcached.exception.MemcachedException;
//import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
//import net.rubyeye.xmemcached.utils.AddrUtil;
//import org.junit.Test;
//import study.util.Constant;
//
//import java.io.IOException;
//import java.util.Set;
//import java.util.concurrent.TimeoutException;
//
///**
// * study.util.XMemcachedTest
// *
// * @author Wilson Chen
// * @date 2016/9/29
// */
//public class XMemcachedTest {
//
//    @Test
//    public void testSimple() throws Exception {
//        MemcachedClient client = new XMemcachedClient("172.16.100.58", 11211);
//
//        String key = "testSetObject";
//        Set<String> someObject = Sets.newHashSet();
//        someObject.add("a");
//        someObject.add("b");
//        someObject.add("c");
//        someObject.add("a");
//
//        //同步存储value到memcached，缓存超时为1小时，3600秒。
//        client.set("key", 3600, someObject);
//        //从memcached获取key对应的value
//        Object value = client.get("key");
//        System.out.println(value);
//
//        client.delete("key");
//
//        //从memcached获取key对应的value,操作超时2秒
//         value = client.get("key", 2000);
//        System.out.println(value);
//        //更新缓存的超时时间为10秒。
//        boolean success = client.touch("key", 10);
//
//        //删除value
//        client.delete("key");
//
//    }
//
//    @Test
//    public void testSimpleBuilder() throws Exception {
//        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
//                AddrUtil.getAddresses(Constant.MEMCACHED_HOSTS));
//
//        //设置分布策略：一致性哈希
//        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
////        builder.
//        MemcachedClient memcachedClient = builder.build();
//        try {
//            memcachedClient.set("hello", 0, "Hello,xmemcached");
//            String value = memcachedClient.get("hello");
//            System.out.println("hello=" + value);
//            memcachedClient.delete("hello");
//            value = memcachedClient.get("hello");
//            System.out.println("hello=" + value);
//        } catch (MemcachedException e) {
//            System.err.println("MemcachedClient operation fail");
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            System.err.println("MemcachedClient operation timeout");
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            // ignore
//        }
//        try {
//            //close memcached client
//            memcachedClient.shutdown();
//        } catch (IOException e) {
//            System.err.println("Shutdown MemcachedClient fail");
//            e.printStackTrace();
//        }
//
//    }
//}
