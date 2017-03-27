package study.spring.exception.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * TestAction
 *
 * @author Wilson Chen
 * @date 2016/4/8
 */
@Controller
@RequestMapping("/sdkapi/v1/pay")
public class TestAction extends BaseController {

    private static Logger log = LoggerFactory.getLogger(TestController.class);
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public
    @ResponseBody
    Map test(HttpServletRequest request,  Integer flag) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        if (new Integer(1).equals(flag)) throw new RuntimeException("asdf");
        if (new Integer(2).equals(flag)) throw new Exception("asefasdg");
        if (new Integer(3).equals(flag)) throw new UnknownError("2341");
        result.put("flag", flag);
        return result;
    }



    /**
     * 发送谷歌adwork
     *
     * @param template
     * @param param
     * @throws java.io.IOException
     */
    private int sendGoogleAdwork(String template, Map<String, Object> param) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(template).append("?");
        Set<Map.Entry<String, Object>> set = param.entrySet();
        int i = 0;
        for (Map.Entry<String, Object> entry : set) {
            if (i > 0) {
                urlBuilder.append("&");
            }
            urlBuilder.append(entry.getKey()).append("=").append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
            i++;
        }
        log.debug("send to google adwork [{}]", urlBuilder);
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        log.debug("result code [{}]", connection.getResponseCode());
        return connection.getResponseCode();
    }

}
