package com.rmd.bms.lock;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * 锁的路径类，各项目可以扩展此类
 * 锁在zookeeper中的路径为
 * /lock/appName/businessName/lockName
 *
 * @author zuoguodong
 */
public abstract class LockPath {
    /**
     * 锁在zookeeper中的根目录
     */
    private static final String BASE_PATH = "lock";

    /**
     * 项目名称,需要实现类赋值
     */
    private String appName;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 锁标识
     */
    private String lockName;

    /**
     * 不充许实例化空对像
     */
    private LockPath() {
    }

    /**
     * 构造业务名称和锁名称
     * @param businessName
     * @param lockName
     */
    public LockPath(String businessName, String lockName) {
        this.businessName = businessName;
        this.lockName = lockName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取锁的路径，只有包内可以访问
     *
     * @return
     */
    protected String[] getLockPath() {
        String[] lockPath = new String[3];
        lockPath[0] = BASE_PATH;
        if (this.appName == null || "".equals(this.appName)) {
            throw new RuntimeException("appName" + "不能为空");
        }
        lockPath[1] = this.appName;
        if (this.businessName == null || "".equals(this.businessName)) {
            throw new RuntimeException("businessName" + "不能为空");
        }
        lockPath[2] = this.businessName;
        return lockPath;
    }

    /**
     * 获取锁的名称
     *
     * @return
     */
    protected String getLockName() {
        if (this.lockName == null || "".equals(this.lockName)) {
            throw new RuntimeException("lockName" + "不能为空");
        }
        return this.lockName;
    }

    protected String getLockPathStr() {
        String[] lockPath = this.getLockPath();
        StringBuffer sb = new StringBuffer("/");
        sb.append(lockPath[0]).append("/");
        sb.append(lockPath[1]).append("/");
        sb.append(lockPath[2]);
        return sb.toString();
    }


    /**
     * 当前机器当前线程唯一标识
     * 使用此标识进行重入锁判断
     *
     * @return
     */
    protected byte[] getData() {
        StringBuffer sb = new StringBuffer();
        sb.append(getMAC());
        sb.append(getPid());
        sb.append(Thread.currentThread().getId());
        return sb.toString().getBytes();
    }

    /**
     * 获取机器的MAC地址
     *
     * @return
     */
    private String getMAC() {
        StringBuffer macStr = new StringBuffer();
        try {
            InetAddress ia = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            for (int i = 0; i < mac.length; i++) {
                //字节转换为整数
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
                if (str.length() == 1) {
                    macStr.append("0" + str);
                } else {
                    macStr.append(str);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("获取机器MAC地址时出错");
        }
        return macStr.toString();
    }

    /**
     * JVM当前进程ID
     *
     * @return
     */
    private String getPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return name.split("@")[0];
    }

}
