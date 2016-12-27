package study.proxy.test;
  
import study.proxy.dao.impl.CountImpl;
import study.proxy.dao.impl.CountProxy;

/** 
 *测试Count类 
 *  
 * @author Administrator 
 *  
 */  
public class TestCount {  
    public static void main(String[] args) {  
        CountImpl countImpl = new CountImpl();  
        CountProxy countProxy = new CountProxy(countImpl);
        countProxy.updateCount();  
        countProxy.queryCount();  
  
    }  
}  