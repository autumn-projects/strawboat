package com.oscroll.strawboat.pool;

import com.oscroll.strawboat.assets.entity.IP;
import com.oscroll.strawboat.filter.Filter;
import com.oscroll.strawboat.provider.Provider;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 在指定时间内IP不可重复的IP池
 */
public class UnrepeatablePool {

    private static UnrepeatablePool pool;
    private List<Provider> providerList;
    private List<Filter> filterList;
    private static final Object LOCK = new Object();

    private UnrepeatablePool() {
        DefaultPoolSetting setting = new DefaultPoolSetting();
        providerList = setting.getProviderList();
        filterList = setting.getFilterList();
    }

    private UnrepeatablePool(List<Provider> providerList, List<Filter> filterList) {
        this.providerList = providerList;
        this.filterList = filterList;
    }

    public static UnrepeatablePool getInstance() {
        if (pool == null) {
            synchronized (LOCK) {
                return pool == null ? new UnrepeatablePool() : pool;
            }
        } else {
            return pool;
        }
    }

    public static UnrepeatablePool getInstance(List<Provider> providerList, List<Filter> filterList) {
        if (pool == null) {
            synchronized (LOCK) {
                return pool == null ? new UnrepeatablePool(providerList, filterList) : pool;
            }
        } else {
            return pool;
        }
    }

    /**
     * 销毁IP池
     */
    public void destory() {
        pool = null;
    }

    /**
     * 保存IP的默认时间，超过该时间则清除
     */
    private static final int NCPU = Runtime.getRuntime().availableProcessors();
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NCPU + 1);
    private BlockingQueue<IP> ipQueue = new LinkedBlockingDeque<>();
    private long maxAliveTime = 24 * 60 * 60 * 1000; // 默认一天，以毫秒计算
    private volatile boolean running = false;
    /**
     * 控制IP的唯一性，受最大时间与最大数量的影响
     */
    private ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

    public void execute() {
        running = true;

        executor.execute(() -> {
            while (running) {
                for (Provider provider : providerList) {
                    // 获取ip
                    List<IP> ipList = provider.getIPList();
                    String address;
                    int port;
                    Long time, currentTime;

                    for (IP ip : ipList) {
                        address = ip.getAddress();
                        port = ip.getPort();
                        address += port;

                        time = map.get(address);
                        currentTime = System.currentTimeMillis();
                        // ip存在且未超过限定时间
                        if (time != null && currentTime - time < maxAliveTime) {
                            continue;
                        }
                        // ip存在已超过限定时间或新的ip
                        if (time == null || currentTime - time > maxAliveTime) {
                            map.put(address, currentTime);

                            // 过滤ip
                            if (filterList != null) {
                                for (Filter filter : filterList) {
                                    executor.execute(() -> {
                                        if (filter.filter(ip)) {
                                            try {
                                                ipQueue.put(ip);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                }

                clearOvertimeEntry();
            }
        });
    }

    public IP take() {
        try {
            return ipQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void stop() {
        running = false;
    }

    private void clearOvertimeEntry() {
        Set<Map.Entry<String, Long>> set = map.entrySet();
        Iterator<Map.Entry<String, Long>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> entry = iterator.next();
            String key = entry.getKey();
            Long time = entry.getValue();

            if (System.currentTimeMillis() - time > maxAliveTime) {
                map.remove(key);
            }
        }
    }
}
