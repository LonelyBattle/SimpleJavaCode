package com.zs.controller;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/zk")
public class ZkController {

    private final CuratorFramework zkClient;

    public ZkController(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    @PostMapping("/create")
    public String createNode(@RequestParam String path, @RequestParam String data) {
        try {
            if (zkClient.checkExists().forPath(path) == null) {
                zkClient.create()
                        .creatingParentsIfNeeded()
                        .forPath(path, data.getBytes(StandardCharsets.UTF_8));
                return "节点创建成功: " + path;
            } else {
                return "节点已存在: " + path;
            }
        } catch (Exception e) {
            return "创建失败: " + e.getMessage();
        }
    }

    @GetMapping("/get")
    public String getNode(@RequestParam String path) {
        try {
            byte[] bytes = zkClient.getData().forPath(path);
            return "节点数据: " + new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "获取失败: " + e.getMessage();
        }
    }

    @DeleteMapping("/delete")
    public String deleteNode(@RequestParam String path) {
        try {
            zkClient.delete().forPath(path);
            return "节点删除成功: " + path;
        } catch (Exception e) {
            return "删除失败: " + e.getMessage();
        }
    }

    @PostMapping("/lock")
    public String doWithLock(@RequestParam(defaultValue = "/locks/mylock") String lockPath) {
        InterProcessMutex lock = new InterProcessMutex(zkClient, lockPath);
        try {
            if (lock.acquire(5, TimeUnit.SECONDS)) {
                try {
                    Thread.sleep(3000);
                    return "执行成功（已加锁）";
                } finally {
                    lock.release();
                }
            } else {
                return "获取锁失败";
            }
        } catch (Exception e) {
            return "执行失败: " + e.getMessage();
        }
    }
}
