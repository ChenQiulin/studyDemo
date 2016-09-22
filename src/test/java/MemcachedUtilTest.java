import org.junit.Assert;
import org.junit.Test;
import study.util.MemcachedUtil;

/**
 * MemcachedUtilTest
 *
 * @author Wilson Chen
 * @date 2016/9/22
 */
public class MemcachedUtilTest {

    @Test
    public void testSet() throws Exception {
        int expiry = 1000;
        for(int i=0;i<=100;i++){
            MemcachedUtil.set(String.valueOf(i),expiry,i);
        }
    }

    @Test
    public void testGet() throws Exception {
        for(int i=0;i<=100;i++){
            Assert.assertEquals(i,MemcachedUtil.get(String.valueOf(i)));
        }

    }
}
