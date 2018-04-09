import com.alibaba.fastjson.JSONObject;
import com.google.api.client.util.Maps;
import org.apache.commons.io.FileUtils;
import study.util.HttpClientUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/9/19.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Map<String,Object> param = Maps.newHashMap();
        String result  = HttpClientUtil.get("http://m.pre.lizhi.fm/vodapi/voice/info/2641262820666727430",param);

        JSONObject object = JSONObject.parseObject(result).getJSONObject("userVoice").getJSONObject("userInfo");
        System.out.println(result);
//        System.out.println(result.split("breadcrumbs")[1]);
//
//        String band = result.split("breadcrumbs")[1].split("</a><a href=\"/user/")[1].split(">")[0];
//        System.out.println(band);


//        ExecutorService executorService =   Executors.newFixedThreadPool(50);
////        Map<String,Object> param = Maps.newHashMap();
////
////        param.put("buyerId",2619364757808125484L);
////        param.put("token","fd070f1ae6a53987dd091c07985bf67f");
//////        param.put("",);
//////        param.put("buyerId",);
////
////        System.out.println(2+ HttpClientUtil.post("https://www.lizhi.fm/api/user/voice_consumes",param));
//
//
////        Map<String,Object> param = Maps.newHashMap();
////
////        param.put("targetId",2609173792745855041L);
////        param.put("eventName","VOICE_PLAY");
////       param.put("targetUserId",2609173792745855041L);
//////        param.put("buyerId",);
////
////        System.out.println(HttpClientUtil.postByJson("http://m.pre.lizhi.fm/vodapi/wechat/event",param));
////        System.out.println(2+ HttpClientUtil.post("http://172.17.10.96:9000//api/playsheet/data",param));
//        List<String> list = FileUtils.readLines(new File("C:\\Users\\Administrator\\Downloads\\播单播放量top1W.txt"));
//        final List<String> resultArr = new ArrayList<>();
//         int i=0;
//
//        for(final  String line:list) {
//
//            i++;
//            final int  j = i;
//
//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    Map<String,Object> param = Maps.newHashMap();
//                    final Long sheetId = Long.valueOf(line.split("\t\t")[0].trim());
//                    param.put("id",sheetId);
//                    param.put("page",1);
//                    param.put("count",1);
////            System.out.print(sheetId);
//                    String result  = HttpClientUtil.post("https://mpre.lizhi.fm/vodapi/playsheet/data",param);
//
//                    JSONObject obj = JSONObject.parseObject(result);
//                  //  System.out.println(result);
//                    if(obj.getJSONObject("playSheetInfo")==null){
//                        System.out.println("error error error error:"+ sheetId);
//                        return;
//                    }
//
//                    String name = obj.getJSONObject("playSheetInfo").getString("name");
//
//                    String tags  = obj.getJSONObject("playSheetInfo").getString("tags");
//                    String rs = line+"\t\t\t\t"+name+"\t\t\t\t"+tags;
//                 //   System.out.println(j+"#"+rs);
//                    resultArr.add(rs);
//
//                }
//            });
//
//
//        }
//        executorService.shutdown();
//        try {//等待直到所有任务完成
//            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
//            FileUtils.writeLines(new File("C:\\Users\\Administrator\\Downloads\\播单播放量top1W222.txt"),resultArr);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }




    }
}
