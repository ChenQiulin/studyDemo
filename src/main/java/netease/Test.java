package netease;

import com.alibaba.fastjson.JSONObject;
import com.google.api.client.util.Maps;
import fm.lizhi.commons.util.DateUtil;
import fm.lizhi.commons.util.GuidGenerator;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import secret.JSSecret;
import study.util.HttpClientUtil;

import java.io.File;
import java.util.*;

/**
 * Created by Wilson Chen  on 2018/4/8.
 */

public class Test {


    public static void main(String[] args) throws Exception {
        //System.out.println(HttpClientUtil.get("https://music.163.com/discover/playlist/?order=hot&cat=%E6%AC%A7%E7%BE%8E&limit=35&offset=35"));

//        Connection.Response
//                commentResponse = Jsoup.connect("https://music.163.com/discover/playlist/?order=hot&cat=%E6%AC%A7%E7%BE%8E&limit=35&offset=35")
//                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:57.0) Gecko/20100101 Firefox/57.0")
//                .header("Accept", "*/*")
//                .header("Cache-Control", "no-cache")
//                .header("Connection", "keep-alive")
//                .header("Host", "music.163.com")
//                .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
//                .header("DNT", "1")
//                .header("Pragma", "no-cache")
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .method(Connection.Method.GET)
//                .ignoreContentType(true)
//                .timeout(10000)
//                .execute();
//        String commentRspStr = commentResponse.body();
        //   System.out.println(commentRspStr);

        System.out.println(DateUtil.formatStrToDate("2018-04-08 22:08:16",DateUtil.NORMAL_DATE_FORMAT).getTime());
        System.out.println(DateUtil.formatStrToDate("2018-04-09 01:00:00",DateUtil.NORMAL_DATE_FORMAT).getTime());
//        System.out.println(DateUtil.formatStrToDate("2018-04-06 00:00:00",DateUtil.NORMAL_DATE_FORMAT).getTime());
//        System.out.println(DateUtil.formatStrToDate("2018-04-07 00:00:00",DateUtil.NORMAL_DATE_FORMAT).getTime());
//        System.out.println(DateUtil.formatStrToDate("2018-04-08 00:00:00",DateUtil.NORMAL_DATE_FORMAT).getTime());
//        for(int i=0;i<100;i++){
//            System.out.println("select count(1) from lizhi_audio.audio_comment_"+i+" where create_time between "
//                    +DateUtil.formatStrToDate("2018-04-05 00:00:00",DateUtil.NORMAL_DATE_FORMAT).getTime()+" and  "+
//                    +DateUtil.formatStrToDate("2018-04-06 00:00:00",DateUtil.NORMAL_DATE_FORMAT).getTime()+";");
//        }

//        List<String> voiceIds = FileUtils.readLines(new File("D:\\vvvv.txt"));
//        List<String> comments = FileUtils.readLines(new File("D:\\ttt.txt"));
//
//        List<String> result = new ArrayList<>();
//        int preCount = comments.size() / voiceIds.size();
//        int index = 0;
//
//        GuidGenerator guidGenerator = new GuidGenerator(555);
//        for (String voiceId : voiceIds) {
//           // if (index >= comments.size()) break;
//
//
//            for (int j = 0; j < preCount; j++) {
////                if(comments.get(index).length()>=120){
////                    index++;
////                    j--;
////                    continue;
////                }
////                String sendTime = DateUtil.formatDateToString(new Date(getRandomTime()), DateUtil.NORMAL_DATE_FORMAT);
////                String createTime = DateUtil.formatDateToString(new Date(), DateUtil.NORMAL_DATE_FORMAT);
////                String updateTime = DateUtil.formatDateToString(new Date(), DateUtil.NORMAL_DATE_FORMAT);
//
////                String userId = getUserId(voiceId);
//                String band = getBand(voiceId);
//                System.out.println("https://www.lizhi.fm/"+band+"/"+voiceId);
////                Long commentId = guidGenerator.genProgCommentId();
////                System.out.println();
////                result.add("INSERT INTO `lizhi_comments`.`manual_reply_comment`(`comment_id`,`manual_comment_id`,`from_user_id`,`to_user_id`,`content`,`status`,`send_time`,`create_time`,`modify_time`,`audio_id`)" +
////                        "VALUES(" + commentId + ",0,0," + userId + ",'" + comments.get(index) + "',0,'" + sendTime + "','" + createTime + "','" + updateTime + "'," + voiceId + ");");
////
////                System.out.println("INSERT INTO `lizhi_audio_comment`.`manual_reply_comment`(`comment_id`,`manual_comment_id`,`from_user_id`,`to_user_id`,`content`,`status`,`send_time`,`create_time`,`modify_time`,`audio_id`)" +
////                        "VALUES(" + commentId + ",0,0," + userId + ",'" + comments.get(index) + "',0,'" + sendTime + "','" + createTime + "','" + updateTime + "'," + voiceId + ");");
////                index++;
//            }

//        }


//        FileUtils.writeLines(new File("D:\\rrr.txt"), result);
    }

    private static String getUserId(String voiceId) throws Exception{
        Map<String, Object> param = Maps.newHashMap();
        System.out.println(voiceId);
//        String result = HttpClientUtil.get("http://m.pre.lizhi.fm/vodapi/voice/info/" + voiceId, param);

        Connection.Response
                response = Jsoup.connect("http://m.pre.lizhi.fm/vodapi/voice/info/" + voiceId)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:57.0) Gecko/20100101 Firefox/57.0")
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Connection", "keep-alive")
                .header("Host", "music.163.com")
                .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                .header("DNT", "1")
                .header("Pragma", "no-cache")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .timeout(10000)
                .execute();
        String list = response.body();


//        System.out.println(result);
        JSONObject object = JSONObject.parseObject(list).getJSONObject("data").getJSONObject("userVoice").getJSONObject("userInfo");
        return object.getString("userId");
    }


    private static String getBand(String voiceId) throws Exception{
        Map<String, Object> param = Maps.newHashMap();
//        System.out.println(voiceId);
//        String result = HttpClientUtil.get("http://m.pre.lizhi.fm/vodapi/voice/info/" + voiceId, param);

        Connection.Response
                response = Jsoup.connect("http://m.pre.lizhi.fm/vodapi/voice/info/" + voiceId)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:57.0) Gecko/20100101 Firefox/57.0")
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Connection", "keep-alive")
                .header("Host", "music.163.com")
                .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                .header("DNT", "1")
                .header("Pragma", "no-cache")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .timeout(10000)
                .execute();
        String list = response.body();


//        System.out.println(result);
        JSONObject object = JSONObject.parseObject(list).getJSONObject("data").getJSONObject("userVoice").getJSONObject("userInfo");
        return object.getString("band");
    }

    private static Long getRandomTime() {

        Random random = new Random();

        return DateUtil.formatStrToDate("2018-04-08 22:20:00", DateUtil.NORMAL_DATE_FORMAT).getTime() + random.nextInt(3600 * 2 * 1000);
    }
}
