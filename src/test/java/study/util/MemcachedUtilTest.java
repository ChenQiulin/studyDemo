//package study.util;
//
//import com.google.common.collect.Sets;
//import org.junit.Assert;
//import org.junit.Test;
//import study.util.MemcachedUtil;
//
//import java.util.Set;
//
///**
// * study.util.MemcachedUtilTest
// *
// * @author Wilson Chen
// * @date 2016/9/22
// */
//public class MemcachedUtilTest {
//
//    static  int expiry = 1000;
//    @Test
//    public void testSet() throws Exception {
//
//        for(int i=0;i<=100;i++){
//            MemcachedUtil.set(String.valueOf(i),expiry,i);
//        }
//    }
//
//    @Test
//    public void testGet() throws Exception {
//        for(int i=0;i<=100;i++){
//            Assert.assertEquals(i,MemcachedUtil.get(String.valueOf(i)));
//        }
//
//    }
//
//    @Test
//    public void testObjectSet() throws Exception {
//        String key = "testSetObject";
//        Set<String> set = Sets.newHashSet();
//        set.add("a");
//        set.add("b");
//        set.add("c");
//        set.add("a");
//        MemcachedUtil.set(key,expiry,set);
//        Assert.assertEquals(MemcachedUtil.get(key),set);
//
//    }
//}
