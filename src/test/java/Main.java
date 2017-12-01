import com.google.api.client.util.Maps;
import study.util.HttpClientUtil;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
public class Main {

    public static void main(String[] args) {
//        Map<String,Object> param = Maps.newHashMap();
//
//        param.put("buyerId",2619364757808125484L);
//        param.put("token","fd070f1ae6a53987dd091c07985bf67f");
////        param.put("",);
////        param.put("buyerId",);
//
//        System.out.println(2+ HttpClientUtil.post("https://www.lizhi.fm/api/user/voice_consumes",param));


        Map<String,Object> param = Maps.newHashMap();

        param.put("id",2609173792745855041L);
        param.put("page",1);
       param.put("count",10);
//        param.put("buyerId",);

        System.out.println(2+ HttpClientUtil.post("http://172.17.10.96:9000//api/playsheet/data",param));

    }
}
