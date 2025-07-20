package com.zs.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ZkDistributedLock {

    @Autowired
    private CuratorFramework zkClient;

    public void doWithLock() {
        InterProcessMutex lock = new InterProcessMutex(zkClient, "/locks/mylock");
        try {
            if (lock.acquire(5, TimeUnit.SECONDS)) {
                try {
                    System.out.println("执行分布式任务...");
                    Thread.sleep(3000);
                } finally {
                    lock.release();
                }
            } else {
                System.out.println("获取锁失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
