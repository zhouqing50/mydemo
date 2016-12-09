package com.qinghuaci.controller.zk;

import com.qinghuaci.common.constants.CommonStant;
import com.qinghuaci.utils.CuratorZkClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.utils.ZKPaths;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Description:
 * User: zhouq
 * Date: 2016/12/8
 */
@Slf4j
@Controller
@RequestMapping("/zk")
public class CuratorZkController {

    @Resource
    private CuratorZkClient curatorZkClient;

    /**
     * 创建或者更新zk节点数据
     * @param path
     * @param data
     * @return
     * @throws Exception
     */
    @RequestMapping("/createOrUpdate")
    @ResponseBody
    public String createOrUpdate(@RequestParam(value = "path") String path, @RequestParam(value = "data") String data) throws Exception {
        CuratorFramework client = curatorZkClient.getClient();
        String cPath = curatorZkClient.makePath(path);
        if (null == client.checkExists().forPath(cPath)) {
            client.create().creatingParentsIfNeeded().forPath(cPath);
            log.info("cpath path={}, data={}", cPath, data);
        }
        client.setData().forPath(cPath, data.getBytes(CommonStant.UTF_8));
        return new String(client.getData().forPath(cPath), CommonStant.UTF_8);
    }

    /**
     * 监控子节点数据变化
     * @throws Exception
     */
    @RequestMapping("/cache")
    public void cache() throws Exception {
        CuratorFramework client = curatorZkClient.getClient();
        String zPath = curatorZkClient.makePath("java1");
        if (null == client.checkExists().forPath(zPath)) {
            client.create().creatingParentsIfNeeded().forPath(zPath, "hello".getBytes(CommonStant.UTF_8));
            log.info("path data={}", new String(client.getData().forPath(zPath), CommonStant.UTF_8));
        }
        NodeCache nodeCache = new NodeCache(client, zPath, true);
        nodeCache.start();
        NodeCacheListener listener = new NodeCacheListener() {

            @Override
            public void nodeChanged() throws Exception {
                if (nodeCache.getCurrentData() != null)
                    System.out.println("Node changed: "
                            + nodeCache.getCurrentData().getPath() + ", value: "
                            + new String(nodeCache.getCurrentData().getData()));
            }
        };
        nodeCache.getListenable().addListener(listener);

//        nodeCache.getListenable().addListener(() -> {
//            ChildData childData = nodeCache.getCurrentData();
//            if (null == childData){
//                log.info("this Node delete, path={}", zPath);
//            }else {
//                log.info("this Node changed, data={}", new String(childData.getData(), CommonStant.UTF_8));
//            }
//        });
    }

    /**
     * 监控子节点数据变化
     * @throws Exception
     */
    @RequestMapping("/childrenCache")
    public void childrenCache() throws Exception {
        CuratorFramework client = curatorZkClient.getClient();
        String path = curatorZkClient.getPerPath();
        if (null == client.checkExists().forPath(path)) {
            client.create().creatingParentsIfNeeded().forPath(path, "hello".getBytes(CommonStant.UTF_8));
            log.info("path data={}", new String(client.getData().forPath(path), CommonStant.UTF_8));
        }
        PathChildrenCache cache = new PathChildrenCache(client, path, true);

        cache.getListenable().addListener((curatorFramework, pathChildrenCacheEvent) -> {
            switch (pathChildrenCacheEvent.getType()) {
                case CHILD_ADDED: {
                    log.info("Node added, path={}, data={}", ZKPaths.getNodeFromPath(pathChildrenCacheEvent.getData().getPath()),
                            new String(pathChildrenCacheEvent.getData().getData(), CommonStant.UTF_8));
                    break;
                }

                case CHILD_UPDATED: {
                    log.info("Node changed, path={}, data={}", ZKPaths.getNodeFromPath(pathChildrenCacheEvent.getData().getPath()),
                            new String(pathChildrenCacheEvent.getData().getData(), CommonStant.UTF_8));
                    break;
                }

                case CHILD_REMOVED: {
                    log.info("Node removed, path={}", ZKPaths.getNodeFromPath(pathChildrenCacheEvent.getData().getPath()));
                    break;
                }
            }
        });
        cache.start();
    }
}
