package com.rmd.bms.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁实现
 * 说明：
 * 1、暂不支持重入，如果有需求再做改进
 * 2、默认获取锁失败，会重试3次
 *
 * @author zuoguodong
 */
public abstract class DistributedLock extends LockPath implements Lock {

    // 会话超时时间，设置为与系统默认时间一致
    public static final int SESSION_TIMEOUT = 3000;

    private static final int MAX_RETRY_COUNT = 3;

    private String lockPath = "";

    //创建 ZooKeeper 实例
    private ZooKeeper zk;

    protected abstract void init();

    /**
     * 用于监听节点被删除
     */
    class CountDownWatcher implements Watcher {
        CountDownLatch countDownLatch = null;

        CountDownWatcher(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        public void process(WatchedEvent event) {
            if (event.getType().equals(EventType.NodeDeleted)) {
                countDownLatch.countDown();
            }
        }
    }

    /**
     * 初始化锁
     * @param businessName
     * @param lockName
     */
    public DistributedLock(String businessName, String lockName) {
        super(businessName, lockName);
        init();
    }

    /**
     * 获取锁，如果没有得到就等待
     */
    public void acquire() throws Exception {
        if (!this.acquire(-1, null)) {
            throw new Exception("在路径" + this.getLockPathStr() + "创建锁" + this.getLockName() + "失败！");
        }
    }

    /**
     * 获取锁，直到超时
     *
     * @param time 超时时间
     * @param unit time参数的单位
     * @throws Exception
     * @return是否获取到锁
     */
    public boolean acquire(long time, TimeUnit unit) throws Exception {
        final long startMillis = System.currentTimeMillis();
        final Long millisToWait = (unit != null) ? unit.toMillis(time) : null;
        boolean hasTheLock = false;
        boolean isDone = false;
        int retryCount = 0;
        //网络闪断需要重试一试
        while (!isDone) {
            isDone = true;
            try {
                createBasePath();
                lockPath = createLockNode();
                hasTheLock = waitToLock(startMillis, millisToWait, lockPath);
            } catch (Exception e) {
                if (retryCount++ < MAX_RETRY_COUNT) {
                    isDone = false;
                } else {
                    throw e;
                }
            }
        }
        return hasTheLock;
    }

    private boolean waitToLock(Long startMillis, Long millisToWait, String lockNode) throws Exception {
        // 获取到锁的标识
        boolean haveTheLock = false;
        boolean doDelete = false;
        try {
            while (!haveTheLock) {
                // 2、获取指定路径下排序后的第一个节点，即最小节点
                String firstNode = this.getFirstNode();
                // 3、判断新建的节点是否为所有子节点下的第一个节点,如是取锁成功
                if (lockNode.equals(firstNode)) {
                    haveTheLock = true;
                } else {
                    // 4、如果不是监听比当前节点小的子节点的删除事件
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    CountDownWatcher watcher = new CountDownWatcher(countDownLatch);
                    Stat stat = zk.exists(this.getLockPathStr() + firstNode, watcher);
                    if (stat != null) {
                        if (millisToWait != null) {
                            millisToWait -= (System.currentTimeMillis() - startMillis);
                            if (millisToWait <= 0) {
                                doDelete = true; // timed out - delete our node
                                break;
                            }
                            countDownLatch.await(millisToWait, TimeUnit.MICROSECONDS);
                        } else {
                            countDownLatch.await();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            doDelete = true;
            throw e;
        } finally {
            if (doDelete) {
                zk.delete(this.getLockPathStr() + lockNode, -1);
            }
        }
        return haveTheLock;
    }

    /**
     * 释放锁
     *
     * @throws Exception
     */
    public void release() throws Exception {
        try {
            String path = this.getLockPathStr() + lockPath;
            Stat stat = zk.exists(path, false);
            if (stat != null) {
                zk.delete(path, -1);
            }
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 创建基础路径
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void createBasePath() throws KeeperException, InterruptedException {
        Stat stat = zk.exists(this.getLockPathStr(), false);
        if (stat != null) {
            return;
        }
        String basePath = "";
        for (String path : this.getLockPath()) {
            basePath += "/" + path;
            stat = zk.exists(basePath, false);
            if(stat == null){
            	zk.create(basePath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }
    }

    /**
     * 创建锁点节，并返回节点名称
     *
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    private String createLockNode() throws KeeperException, InterruptedException {
        String lockPath = this.getLockPathStr() + "/" + this.getLockName();
        String nodePath = zk.create(lockPath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        return nodePath.substring(nodePath.lastIndexOf("/"), nodePath.length());
    }

    /**
     * 获取排序后的最小节点
     *
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    private String getFirstNode() throws KeeperException, InterruptedException {
        List<String> list = zk.getChildren(this.getLockPathStr(), false);
        Collections.sort(list, new Comparator<String>() {
            public int compare(String lhs, String rhs) {
                return getLockNodeNumber(lhs, getLockName()).compareTo(getLockNodeNumber(rhs, getLockName()));
            }
        });
        return "/" + list.get(0);
    }

    /**
     * 获取临时节点的序号
     *
     * @param str
     * @param lockName
     * @return
     */
    private String getLockNodeNumber(String str, String lockName) {
        int index = str.lastIndexOf(lockName);
        if (index >= 0) {
            index += lockName.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }
}
