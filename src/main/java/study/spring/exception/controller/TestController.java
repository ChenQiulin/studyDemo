package study.spring.exception.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import study.spring.exception.exception.BusinessException;
import study.spring.exception.exception.ParameterException;
import study.spring.exception.service.TestService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TestController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);


    @Resource
    private TestService testService;

    @RequestMapping(value = "/controller.do", method = RequestMethod.GET)
    public void controller(HttpServletResponse response, Integer id) throws Exception {
        logger.debug("Id:" + id);
        switch (id) {
            case 1:
                throw new BusinessException("10", "controller10");
            case 2:
                throw new BusinessException("20", "controller20");
            case 3:
                throw new BusinessException("30", "controller30");
            case 4:
                throw new BusinessException("40", "controller40");
            case 5:
                throw new BusinessException("50", "controller50");
            default:
                throw new ParameterException("Controller Parameter Error");
        }
    }

    @RequestMapping(value = "/service.do", method = RequestMethod.GET)
    public void service(HttpServletResponse response, Integer id) throws Exception {
        testService.exception(id);
    }

    @RequestMapping(value = "/dao.do", method = RequestMethod.GET)
    public void dao(HttpServletResponse response, Integer id) throws Exception {
        testService.dao(id);
    }
}