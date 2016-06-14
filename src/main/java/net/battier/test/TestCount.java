package net.battier.test;  
  
import net.battier.dao.impl.CountImpl;  
import net.battier.dao.impl.CountProxy;  
  
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