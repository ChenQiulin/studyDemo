package study.druid;

import com.alibaba.fastjson.JSON;
import study.util.DruidDbUtil;

/**
 * Simple
 *
 * @author Wilson Chen
 * @date 2016/6/17
 */
public class Simple {
    public static void main(String[] args) throws Exception {
        System.out.println(JSON.toJSONString(DruidDbUtil.executeQuery("test", "select * FROM  sys_user limit 10;", SysUser.class)));
    }
}
