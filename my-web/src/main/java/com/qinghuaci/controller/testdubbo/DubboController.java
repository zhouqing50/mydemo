package com.qinghuaci.controller.testdubbo;

import com.qinghuaci.model.Tuser;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:
 * Tuser: zhouq
 * Date: 2016/12/9
 */
@ApiModel(description = "测试")
@Slf4j
@Controller
@RequestMapping("/dubbo")
public class DubboController {

//    @Resource
//    private DemoService demoService;

    @RequestMapping("/test")
    @ResponseBody
    @ApiOperation(value = "test测试", httpMethod = "POST", response = Tuser.class, notes = "test")
    public Tuser test(@ApiParam(required = true, name = "tuser", value = "用户信息") @RequestBody Tuser tuser) {
        //String ww = demoService.sayHello("zzzz");
        String ww = "aabbcc";
        log.info("tuser={}", tuser);
        return tuser;
    }

    @RequestMapping("/dubbo")
    @ResponseBody
    @ApiOperation(value = "dubbo测试", httpMethod = "POST", response = Tuser.class, notes = "测试dubbo")
    public Tuser dubbo(@ApiParam(required = true, name = "tuser", value = "用户信息") @RequestBody Tuser tuser) {
        //String ww = demoService.sayHello("zzzz");
        String ww = "aabbcc";
        log.info("tuser={}", tuser);
        return tuser;
    }
}