package com.oscroll.strawboat.pool;

import com.oscroll.strawboat.Template;
import com.oscroll.strawboat.assets.entity.IP;
import com.oscroll.strawboat.filter.Filter;
import com.oscroll.strawboat.provider.Provider;

import java.util.List;

public class ScheduledPool extends Template {

    private List<Provider> providerList;
    private List<Filter> filterList;
    private Integer providerIndex = 0;

    public ScheduledPool() {
        DefaultPoolSetting setting = new DefaultPoolSetting();
        providerList = setting.getProviderList();
        filterList = setting.getFilterList();
    }

    @Override
    public List<IP> getIPList() {
        List<IP> ipList = providerList.get(providerIndex).getIPList();
        providerIndex++;
        if (providerIndex == providerList.size() - 1) providerIndex = 0;
        return ipList;
    }

    @Override
    public boolean validate(IP ip) {
        for (Filter filter : filterList) {
            /* 如果没有通过校验*/
            if (!filter.filter(ip)) return false;
        }
        return true;
    }

    @Override
    public void onAvailable(IP ip) {
        System.out.println("通过校验：" + ip);
    }

    @Override
    public void onUnavailable(IP ip) {
        System.out.println("不通过校验：" + ip);
    }


}
