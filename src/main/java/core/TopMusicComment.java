package core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import netease.Api;
import netease.UrlParamPair;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import secret.JSSecret;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MiChong on 2017/11/22 0022.
 *
 * 测试搜索功能，包括搜索歌曲，歌手，用户等
 */
public class TopMusicComment {
    public static void main(String[] args) {
        try {

            List<String> result = new ArrayList<>();
            List<String>  lineList = FileUtils.readLines(new File("D:\\2222.txt"));
            for(String line:lineList){

                Long music_id = Long.parseLong(line.split("\t\t\t")[0]);
                result.add("music name:"+line.split("\t\t\t")[1]+"\tid:"+music_id);
                System.out.println("music name:"+line.split("\t\t\t")[1]+"\tid:"+music_id);

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

                    result.add("********************************热门评论**********************************");
                    for(int j=0;j<hotcommentArr.size();j++) {
                        JSONObject comment = hotcommentArr.getJSONObject(j);
                        String content = comment.getString("content");
                        Long id = comment.getLong("commentId");
                        content = content.replaceAll("\n"," #### ");
                        System.out.println("\t\t\t\t hot comment :" + content );
                        result.add("\t\t\t hot comment :" + content );
                    }
                    result.add("****************************************************");

                    result.add("********************************评论**********************************");
                    JSONArray commentArr = commentObject.getJSONArray("comments");

                    if(commentArr!=null&&!commentArr.isEmpty())
                    for(int j=0;j<commentArr.size();j++) {
                        JSONObject comment = commentArr.getJSONObject(j);
                        String content = comment.getString("content");
                        Long id = comment.getLong("commentId");
                        content = content.replaceAll("\n"," #### ");
//                        System.out.println("\t\t\t\t\t\t\t\t comment content:" + content );
                        result.add("\t\t\t\t comment content:" + content );
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            FileUtils.writeLines(new File("D:\\22222.result"),result);
//            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
