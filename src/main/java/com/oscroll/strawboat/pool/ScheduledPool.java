package com.oscroll.strawboat.pool;

import com.oscroll.strawboat.assets.entity.IP;
import com.oscroll.strawboat.filter.Filter;
import com.oscroll.strawboat.provider.Provider;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

public class ScheduledPool {

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    private BlockingQueue<IP> ipQueue = new LinkedBlockingDeque<>();
    private List<Provider> providerList;
    private List<Filter> filterList;
    private volatile boolean flag = true;

    public ScheduledPool() {
        DefaultPoolSetting setting = new DefaultPoolSetting();
        providerList = setting.getProviderList();
        filterList = setting.getFilterList();
    }

    public ScheduledPool(List<Provider> providerList, List<Filter> filterList) {
        this.providerList = providerList;
        this.filterList = filterList;
    }

    public void execute() {
        while (flag) {
            providerList.forEach(provider -> {
                        List<IP> ipList = provider.getIPList();
                        ipList.forEach(ip -> {
                            IPFilter filter = new IPFilter(ip);
                            executor.execute(filter);
                        });
                    }
            );
        }
    }

    public void shutDown() {
        if (flag) flag = false;
    }

    public IP take() {
        try {
            return ipQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    class IPFilter implements Runnable {

        private IP ip;

        IPFilter(IP ip) {
            this.ip = ip;
        }

        @Override
        public void run() {
            if (filterList == null) {
                return;
            }

            filterList.forEach(filter -> {
                if (filter.filter(ip)) {
                    try {
                        ipQueue.put(ip);
                        System.out.println("true:" + ip);
                        System.out.println(ipQueue.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("false:" + ip);
                }
            });
        }
    }


}
