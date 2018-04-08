import com.google.api.client.util.Maps;
import study.util.HttpClientUtil;

import java.util.Map;

/**
 * Created by Wilson Chen  on 2018/3/27.
 */

public class HttpWithHeader {

    public static void main(String[] args) {


        String url = "";
        Map<String,Object> param = Maps.newHashMap();

        Map<String,Object> header = Maps.newHashMap();
        header.put("Content-Type","application/json");
        header.put("a","a58a62238f5e129c");
        header.put("t","a58a62238f5e129c");
        System.out.println(HttpClientUtil.post(url,param,header));
    }
}
