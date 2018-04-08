package core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import netease.Api;
import netease.UrlParamPair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import secret.JSSecret;

/**
 * Created by MiChong on 2017/11/22 0022.
 */
public class Demo2Copy {
    public static void main(String[] args) {
        try {
            String music_id = "516392300";
           UrlParamPair urlpp = Api.getDetailOfPlaylist(music_id);
            //    UrlParamPair urlpp = Api.getCommentList(music_id,20);
            String reqStr = urlpp.getParas().toJSONString();
            System.out.println("req_str:"+reqStr);
            //某个歌的评论json地址
            //http://music.163.com/weapi/v1/resource/comments/R_SO_4_516392300?csrf_token=1ac15bcb947b3900d9e8e6039d121a81
            Connection.Response
                    commentResponse = Jsoup.connect("http://music.163.com/weapi/v3/playlist/detail?csrf_token=")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:57.0) Gecko/20100101 Firefox/57.0")
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Connection", "keep-alive")
                    .header("Host", "music.163.com")
                    .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                    .header("DNT", "1")
                    .header("Pragma", "no-cache")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data(JSSecret.getDatas(reqStr))
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(10000)
                    .execute();
            String commentRspStr = commentResponse.body();
            System.out.println(commentRspStr);

            JSONObject commentObject = JSONObject.parseObject(commentRspStr);

            JSONArray hotcomment = commentObject.getJSONArray("hotComments");
            if(hotcomment!=null)
            for(int i=0;i<hotcomment.size();i++) {
                JSONObject element = hotcomment.getJSONObject(i);
                String content = element.getString("content");
                Long id = element.getLong("commentId");
                System.out.println("\t\t\t\t\tcontent:" + content + "\t\tid:" + id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
