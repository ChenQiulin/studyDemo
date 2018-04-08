//package study.util;
//
//import com.google.common.collect.Sets;
//import net.rubyeye.xmemcached.MemcachedClient;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import study.util.MemcachedUtil;
//
//import javax.annotation.Resource;
//import java.util.Set;
//
///**
// * XMemcacheSpringTest
// *
// * @author Wilson Chen
// * @date 2016/9/29
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
//@ContextConfiguration({"/context/app*.xml"}) //加载配置文件
//
////------------如果加入以下代码，所有继承该类的测试类都会遵循该配置，也可以不加，在测试类的方法上///控制事务，参见下一个实例
////这个非常关键，如果不加入这个注解配置，事务控制就会完全失效！
////@Transactional
////这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），同时//指定自动回滚（defaultRollback = true）。这样做操作的数据才不会污染数据库！
////@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
////----
//public class XMemcachedSpringTest {
//
//        static  int expiry = 1000;
//        @Resource
//        MemcachedClient memcachedClient;
//        @Test
//        public void testSet() throws Exception {
//
//                memcachedClient.incr("a",5,1);
//                System.out.println(memcachedClient.get("a"));
//
//                Set<String> set = Sets.newHashSet();
//                set.add("a");
//                set.add("b");
//                set.add("c");
//                set.add("a");
//                memcachedClient.set("b",1000,set);
//                System.out.println(memcachedClient.get("b"));
//
//                for(int i=0;i<=100;i++){
//                        MemcachedUtil.set(String.valueOf(i),expiry,i);
//                }
//                for(int i=0;i<=100;i++){
//                        Assert.assertEquals(i, MemcachedUtil.get(String.valueOf(i)));
//                }
//
//
//        }
//
//        @Test
//        public void testSpyCompareXmemcached() throws Exception {
//                for(int i=0;i<=100;i++){
//                        MemcachedUtil.set(String.valueOf(i), expiry, i);
//                        System.out.println(i+"--->"+memcachedClient.get(String.valueOf(i)));
//                }
//
//
//        }
//}
