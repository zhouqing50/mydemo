/*
 * Copyright 1999-2011 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qinghuaci.serviceImpl;

import com.alibaba.dubbo.rpc.RpcContext;
import com.qinghuaci.dao.MongoTestDao;
import com.qinghuaci.model.User;
import com.qinghuaci.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service("demoServiceImpl")
public class DemoServiceImpl implements DemoService {

    @Resource
    private MongoTestDao mongoTestDao;

    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        User user = new User();
        user.setAge(22);
        user.setName(name);
        mongoTestDao.save(user);
        return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }

}