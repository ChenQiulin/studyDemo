package study.spring.exception.exception;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import study.spring.exception.controller.TestController;
import study.util.IPUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class StopWatchHandlerInterceptor implements HandlerInterceptor {
    private static Logger log = LoggerFactory.getLogger(TestController.class);

    // private final Map<String,Long> handleTimeInfo = new HashMap<>();

    /**
     * 请求处理时间日志
     */
    private static Logger loggerRequestHandle = LoggerFactory.getLogger("request.handle");

    // NamedThreadLocal: One different variable per request
    private NamedThreadLocal<Long>
            startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        long beginTime = System.currentTimeMillis();
        startTimeThreadLocal.set(beginTime);

        log.debug("preHandle:  " + handler.getClass());


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        log.debug("postHandle:  " + handler.getClass());
    }

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object hander,
                                Exception e) throws Exception {
        long endTime = System.currentTimeMillis();
        long beginTime = startTimeThreadLocal.get();
        long costTime = endTime - beginTime;

        if (loggerRequestHandle.isDebugEnabled()) {
            loggerRequestHandle.debug(String.format("%-25s %-10s  IP:%-15s %s requestBody:%s",
                    request.getRequestURI(),
                    costTime + " ms",
                    IPUtil.getUserIP(request),
                    JSON.toJSONString(request.getParameterMap()), request.getAttribute("sadfasd")));
        }
    }


}