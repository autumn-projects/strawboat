package com.oscroll.strawboat.pool;

import com.oscroll.strawboat.filter.Filter;
import com.oscroll.strawboat.filter.baidu.BaiduFilter;
import com.oscroll.strawboat.provider.Provider;
import com.oscroll.strawboat.provider.Type;
import com.oscroll.strawboat.provider._89._89Provider;
import com.oscroll.strawboat.provider.ip3366.IP3366Provider;
import com.oscroll.strawboat.provider.kuai.KuaiProvider;
import com.oscroll.strawboat.provider.xicidaili.XiciProvider;

import java.util.ArrayList;
import java.util.List;

final class DefaultPoolSetting {

    private List<Provider> providerList;
    private List<Filter> filterList;

    DefaultPoolSetting() {
        providerList = new ArrayList<Provider>() {{
            add(new IP3366Provider());
            add(new _89Provider());
            add(new XiciProvider(Type.HTTP));
            add(new XiciProvider(Type.HTTPS));
            add(new KuaiProvider());
            add(new KuaiProvider(2));
        }};

        filterList = new ArrayList<Filter>() {{
            add(new BaiduFilter());
        }};
    }

    List<Provider> getProviderList() {
        return providerList;
    }

    List<Filter> getFilterList() {
        return filterList;
    }

}
