package com.zs.service;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class ZkService {

    @Autowired
    private CuratorFramework zkClient;

    public void createNode(String path, String data) throws Exception {
        zkClient.create()
                .creatingParentsIfNeeded()
                .forPath(path, data.getBytes(StandardCharsets.UTF_8));
    }

    public String getNodeData(String path) throws Exception {
        byte[] bytes = zkClient.getData().forPath(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public void deleteNode(String path) throws Exception {
        zkClient.delete().forPath(path);
    }
}
