package com.oscroll.strawboat;

import com.oscroll.strawboat.assets.entity.IP;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class Template {

    private ThreadPoolExecutor filterExecutor;
    private ThreadPoolExecutor providerExecutor;
    private volatile boolean flag = true;

    public Template() {
        filterExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        providerExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    }

    public Template(ThreadPoolExecutor filterExecutor, ThreadPoolExecutor providerExecutor) {
        this.filterExecutor = filterExecutor;
        this.providerExecutor = providerExecutor;
    }

    public void execute() {
        providerExecutor.execute(() -> {
            while (flag = true) {
                List<IP> ipList = getIPList();
                ipList.forEach(ip -> {
                    filterExecutor.execute(() -> {
                        if (validate(ip)) {
                            onAvailable(ip);
                        } else {
                            onUnavailable(ip);
                        }
                    });
                });
            }
        });
    }

    /**
     * 关闭IP池
     */
    public void shutdown() {
        flag = false;
        providerExecutor.shutdown();
        filterExecutor.shutdown();
    }

    /**
     * 获取原始的IP数据
     *
     * @return 原始IP列表
     */
    public abstract List<IP> getIPList();

    /**
     * 校验IP是否可用并获取IP相关信息
     *
     * @param ip 校验的IP
     * @return true可用，false不可用
     */
    public abstract boolean validate(IP ip);

    /**
     * IP可用时的回调方法
     *
     * @param ip IP
     */
    public abstract void onAvailable(IP ip);

    /**
     * IP不可用时的回调方法
     *
     * @param ip IP
     */
    public abstract void onUnavailable(IP ip);

}
