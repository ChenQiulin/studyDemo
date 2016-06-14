/**
 * Copyright 2015 ProficientCity Inc.
 */
package study.util;

import java.net.URLEncoder;

/**
 * 网络有关的工具方法
 * @author Harry Wu
 */
public class NetUtil {

    /**
     * 编码非英文参数
     * 
     * @param param 非英文参数
     * @param targetCode 目标字符
     * @return
     */
    public static String encodeString(String param, String targetCode) {
        String encodeUrl = "";
        if (StringUtil.isNullOrBlank(param)) {
            return "";
        }
        try {
            encodeUrl = URLEncoder.encode(param, targetCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeUrl;
    }
}
