package com.qinghuaci.controller;

import com.google.common.collect.Maps;
import com.qinghuaci.common.JsonKit;
import com.qinghuaci.model.Tuser;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/{id}")
    @ResponseBody
    @ApiOperation(value = "根据用户id获取用户对象", httpMethod = "GET", response = String.class, notes = "根据用户id获取用户对象")
    public String getUser( @ApiParam(name = "id", value = "用户Id", required = true) @PathVariable Integer id) {
        Tuser tuser = new Tuser();
        tuser.setId(id);
        tuser.setName("aaa");
        log.info("tuser={}", tuser);
        Map<String, Object> map = Maps.newHashMap();
        map.put("tuser", tuser);
        return JsonKit.object2json(map);
    }
}