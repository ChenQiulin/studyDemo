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
 *
 * 测试搜索功能，包括搜索歌曲，歌手，用户等
 */
public class MyDemo {
    public static void main(String[] args) {
        try {
            String searchContent="爱我";
            UrlParamPair upp = Api.SearchMusicList(searchContent,"1");
            String req_str = upp.getParas().toJSONString();
//            System.out.println("req_str:"+req_str);
            Connection.Response
                    response = Jsoup.connect("http://music.163.com/weapi/cloudsearch/get/web?csrf_token=")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:57.0) Gecko/20100101 Firefox/57.0")
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Connection", "keep-alive")
                    .header("Host", "music.163.com")
                    .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                    .header("DNT", "1")
                    .header("Pragma", "no-cache")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data(JSSecret.getDatas(req_str))
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(10000)
                    .execute();
            String list = response.body();

            System.out.println("搜索： "+searchContent);
            JSONObject  jsonObject = JSONObject.parseObject(list);

//            System.out.println(list);
            JSONArray songArr = jsonObject.getJSONObject("result").getJSONArray("songs");
            for(int i=0;i<songArr.size();i++){
                JSONObject element = songArr.getJSONObject(i);
                String name = element.getString("name");
                Long music_id = element.getLong("id");
                System.out.println("music name:"+name+"\tid:"+music_id);

                try {

                    UrlParamPair urlpp = Api.getDetailOfPlaylist(String.valueOf(music_id));
                    String reqStr = urlpp.getParas().toJSONString();
//                    System.out.println("req_str:"+reqStr);
                    //某个歌的评论json地址
                    //http://music.163.com/weapi/v1/resource/comments/R_SO_4_516392300?csrf_token=1ac15bcb947b3900d9e8e6039d121a81
                    Connection.Response
                            commentResponse = Jsoup.connect("http://music.163.com/weapi/v1/resource/comments/R_SO_4_"+music_id+"?csrf_token=")
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
//                    System.out.println(commentRspStr);

                    JSONObject commentObject = JSONObject.parseObject(commentRspStr);

                    JSONArray hotcommentArr = commentObject.getJSONArray("hotComments");

                    for(int j=0;j<hotcommentArr.size();j++) {
                        JSONObject comment = hotcommentArr.getJSONObject(j);
                        String content = comment.getString("content");
                        Long id = comment.getLong("commentId");
                        System.out.println("\t\t hot comment content:" + content + "\tid:" + id);
                    }

                    JSONArray commentArr = commentObject.getJSONArray("comments");

                    for(int j=0;j<commentArr.size();j++) {
                        JSONObject comment = commentArr.getJSONObject(j);
                        String content = comment.getString("content");
                        Long id = comment.getLong("commentId");
                        System.out.println("\t\t comment content:" + content + "\tid:" + id);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
//            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
