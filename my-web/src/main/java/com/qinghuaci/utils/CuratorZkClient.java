package com.qinghuaci.utils;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Description:
 * User: zhouq
 * Date: 2016/12/8
 */
@Slf4j
public class CuratorZkClient implements InitializingBean, DisposableBean {

    private  String zkServers = "10.22.0.11:2181";
    private RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    private String path = "/home/zhouq";
    private volatile CuratorFramework client;

    public CuratorZkClient(String zkServers, String path) throws IllegalArgumentException {
        if (!Strings.isNullOrEmpty(path) && !Strings.isNullOrEmpty(zkServers)){
            this.path = path;
            this.zkServers = zkServers;
        }else {
            throw new IllegalArgumentException(String.format("zkServers=%s,path=%s", new Object[]{zkServers, path}));
        }

    }

    private void  createClient(){
        this.client = CuratorFrameworkFactory.newClient(zkServers, retryPolicy);
        this.client.start();
        log.info("zk start. zkServers={}", zkServers);
    }

    public CuratorFramework getClient(){
        return client;
    }

    public String getPerPath(){
        return path;
    }

    public String makePath(String childrenPath){
        return ZKPaths.makePath(path, childrenPath);
    }
    @Override
    public void destroy() throws Exception {
        if (null != this.client){
            client.close();
            log.warn("client closed");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == this.client){
            createClient();
        }
    }
}
