package com.rmd.bms;

import com.rmd.bms.lock.example.WMSLockPath;

public class LockTest {

    public static void main(String args[]) {
        for (int i = 0; i < 5; i++) {
            TestLock lock = new TestLock();
            lock.start();
        }
    }

}

class TestLock extends Thread {
    public void run() {
        WMSLockPath lock = new WMSLockPath("order", "lock");
        try {
            System.out.println("线程：" + Thread.currentThread().getId() + "开始获取锁.....");
            lock.acquire();
            System.out.println("线程：" + Thread.currentThread().getId() + "已经取到锁......");
            System.out.println("线程：" + Thread.currentThread().getId() + "开始工作");
            Thread.sleep(1000 * 5);
            System.out.println("线程：" + Thread.currentThread().getId() + "开始释放锁......");
            lock.release();
            System.out.println("线程：" + Thread.currentThread().getId() + "已经释放锁......");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}