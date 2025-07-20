package com.zs.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Component
public class ZkNodeWatcher {

    @Autowired
    private CuratorFramework zkClient;

    @PostConstruct
    public void addWatch() throws Exception {
        String path = "/config/mykey";

        zkClient.create().orSetData().creatingParentsIfNeeded()
                .forPath(path, "init-value".getBytes(StandardCharsets.UTF_8));

        NodeCache nodeCache = new NodeCache(zkClient, path);
        nodeCache.getListenable().addListener(() -> {
            byte[] newData = nodeCache.getCurrentData().getData();
            System.out.println("配置发生变化：" + new String(newData));
        });
        nodeCache.start(true);
    }
}
