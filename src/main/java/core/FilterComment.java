package core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import netease.Api;
import netease.UrlParamPair;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import secret.JSSecret;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by MiChong on 2017/11/22 0022.
 *
 * 测试搜索功能，包括搜索歌曲，歌手，用户等
 */
public class FilterComment {
    public static void main(String[] args) {

        Set<String> filterSet = Sets.newHashSet("文案","文稿","文章","背景音乐","bgm","歌","原唱","歌名","资料","照顾自己","移民","录音","录播","求歌名","牛批","牛逼","荔枝妹","主播","加油","百度","楼","微信号","微信","v","wei","xin","qq","QQ","weibo","微博","邮箱","啊啊啊","图片","射","诶","标题","FM","呀呀呀","晚安","转发","嗯","小心心","职位","哈哈哈（>3）","咳","联系","直播","颜文字：╮(╯▽╰)╭","~(≧▽≦)/~","ฅ՞•ﻌ•՞ฅ","づ","(●⊙(工)⊙●)づ","ฅ(๑","̀ㅅ","́๑)ฅ","╮(","•́ω•̀",")╭","(ಡωಡ)","(●°u°●)​","(♡◡♡)");
        try {

            List<String> result = new ArrayList<>();
            List<String>  lineList = FileUtils.readLines(new File("D:\\3333.result"));
            for(String line:lineList){
                String content = line.trim().replaceAll("\t","");

              //  System.out.println(content);

                if(content.startsWith("comment ")||content.startsWith("hot ")){
                    content = content.replaceAll("comment content:","").replaceAll(" hot comment :","");
//                    System.out.println(content);
                    if(content.length()<15){
                        System.out.println(content.length()+"----->"+line);
                        continue;
                    }
                    boolean pass = false;
                    for(String fliter:filterSet){
                        if(content.contains(fliter)){
                            System.out.println(fliter+"---->"+line);
                            pass = true;
                            break;
                        }
                    }
                    if(pass)continue;

                }
                    result.add(line);


            }
            FileUtils.writeLines(new File("D:\\3333.result.filter"),result);
//            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
