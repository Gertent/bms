package com.rmd.bms.lock.example;

import com.rmd.bms.lock.DistributedLock;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class WMSLockPath extends DistributedLock{

	// 需要自己初始化自己的zk
	private static ZooKeeper zk;

	static{
		// 连接到ZK服务，多个可以用逗号分割写
		try {
			final CountDownLatch connCountDownLatch = new CountDownLatch(1);
			zk = new ZooKeeper("192.168.0.90:2181,192.168.0.90:2182,192.168.0.90:2183", SESSION_TIMEOUT, new Watcher(){
				public void process(WatchedEvent event) {
					if(event.getState()== Event.KeeperState.SyncConnected){
						connCountDownLatch.countDown();
					}
				}
			});
			connCountDownLatch.await();
		} catch (Exception e) {
			System.err.println("初始化zk连接时出错");
			e.printStackTrace();
		}
	}

	@Override
	protected void init() {
		setAppName("WMS");
		setZk(zk);
	}

	/**
	 * 在此定义业务名称、锁名称常量，或使用枚举
	 */
	public WMSLockPath(String businessName,String lockName){
		super(businessName, lockName);
	}

}
