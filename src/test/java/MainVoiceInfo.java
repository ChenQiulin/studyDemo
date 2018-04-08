import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.api.client.util.Maps;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import study.util.DateUtil;
import study.util.HttpClientUtil;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/9/19.
 */
public class MainVoiceInfo {

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
//
        List<String> list = FileUtils.readLines(new File("D:\\voiceComment2018-03-15.txt"));
        final List<String> resultArr = new ArrayList<>();
        int i = 0;


        final Map<Long,Integer> voiceCountMap = new HashMap<>();
        final Map<Long,Integer> bandCountMap = new HashMap<>();
        final Map<String,Integer> timeCountMap = new HashMap<>();
        final Map<Long,Integer> userCountMap = new HashMap<>();
        final Map<Integer,Integer> statusCountMap = new HashMap<>();
        final List <String> error = new ArrayList<>();

        for (final String line : list) {

            System.out.println(line);

            i++;
            final int j = i;

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = JSONObject.parseObject(line);

                    Map<String, Object> param = Maps.newHashMap();
//                    final Long sheetId = Long.valueOf(line.split("\t\t")[0].trim());
//                    param.put("id",sheetId);
//                    param.put("page",1);
                     param.put("voiceId",object.getString("audio_id"));
//            System.out.print(sheetId);
                    String result = HttpClientUtil.postByJson("http://192.168.10.122:10080/queryVoice" , param);
                    JSONObject resultObj = JSONObject.parseObject(result);
                    if(resultObj.getInteger("code")!=0){
                        System.out.println("error "+ object.getString("audio_id"));
                        error.add(object.getString("audio_id"));
                    }

                    Long voiceId = object.getLong("audio_id");
                    Long userId = object.getLong("user_id");
                    boolean isHv = object.getBoolean("isHeartVoice");
                    Long createTime = object.getLong("create_time");
                    String time = DateUtil.convertDateToStr(new Date(createTime),"yyyy-MM-dd HH");
                    Long band = resultObj.getJSONObject("data").getLong("userId");
                    Integer status = resultObj.getJSONObject("data").getInteger("status");


                    if(voiceCountMap.containsKey(voiceId)){
                        voiceCountMap.put(voiceId,voiceCountMap.get(voiceId)+1);
                    }else{
                        voiceCountMap.put(voiceId,1);
                    }

                    if(userCountMap.containsKey(userId)){
                        userCountMap.put(userId,userCountMap.get(userId)+1);
                    }else{
                        userCountMap.put(userId,1);
                    }

                    if(bandCountMap.containsKey(band)){
                        bandCountMap.put(band,bandCountMap.get(band)+1);
                    }else{
                        voiceCountMap.put(band,1);
                    }

                    if(timeCountMap.containsKey(time)){
                        timeCountMap.put(time,voiceCountMap.get(time)+1);
                    }else{
                        timeCountMap.put(time,1);
                    }
                    if(statusCountMap.containsKey(status)){
                        statusCountMap.put(status,statusCountMap.get(status)+1);
                    }else{
                        statusCountMap.put(status,1);
                    }

                    String resultline = band+"\t"+voiceId+"\t"+userId+"\t"+time+"\t"+isHv+"\t\t"+object.get("content");
                    resultArr.add(resultline);
                    System.out.println(resultline);
                    //   System.out.println(j+"#"+rs);


                }
            });


        }
        executorService.shutdown();
        try {//等待直到所有任务完成
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
            sortByComparator(userCountMap);
            sortByComparator(voiceCountMap);
            sortByComparator(bandCountMap);
            sortByComparator(timeCountMap);
            System.out.println("errorSize   " +error.size());
            FileUtils.writeLines(new File("D:\\15.txt"), resultArr);

            FileUtils.writeLines(new File("D:\\15user.txt"), userCountMap.entrySet());
            FileUtils.writeLines(new File("D:\\15voice"), voiceCountMap.entrySet());
            FileUtils.writeLines(new File("D:\\15band.txt"), bandCountMap.entrySet());
            FileUtils.writeLines(new File("D:\\15time.txt"), timeCountMap.entrySet());
            FileUtils.writeLines(new File("D:\\15status.txt"), statusCountMap.entrySet());
            FileUtils.writeLines(new File("D:\\15error.txt"), error);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static Map sortByComparator(Map unsortMap) {
        List list = new LinkedList(unsortMap.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        Map sortedMap = new LinkedHashMap();

        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;

    }

}
