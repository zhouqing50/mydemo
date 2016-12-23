package com.qinghuaci.controller;

import com.google.common.collect.Maps;
import com.qinghuaci.common.JsonKit;
import com.qinghuaci.model.Tuser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Description:
 * Tuser: zhouq
 * Date: 2016/12/9
 */

@Slf4j
@Controller
@RequestMapping("/tuser")
public class TuserController {


    @RequestMapping("/get")
    @ResponseBody
    public String test() {
        Tuser tuser = new Tuser();
        tuser.setId(1);
        tuser.setName("aaa");
        log.info("tuser={}", tuser);
        Map<String, Object> map = Maps.newHashMap();
        map.put("tuser", tuser);
        return JsonKit.object2json(map);
    }
}