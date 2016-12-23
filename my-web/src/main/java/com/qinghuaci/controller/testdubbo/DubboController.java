package com.qinghuaci.controller.testdubbo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:
 * Tuser: zhouq
 * Date: 2016/12/9
 */

@Slf4j
@Controller
@RequestMapping("/dubbo")
public class DubboController {

//    @Resource
//    private DemoService demoService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        //String ww = demoService.sayHello("zzzz");
        String ww = "aabbcc";
        log.info("ww={}", ww);
        return ww;
    }
}