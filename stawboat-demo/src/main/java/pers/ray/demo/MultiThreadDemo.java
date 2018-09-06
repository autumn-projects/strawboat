package pers.ray.demo;

import pers.ray.strawboat.assets.entity.IP;
import pers.ray.strawboat.filter.Filter;
import pers.ray.strawboat.filter.baidu.BaiduFilter;
import pers.ray.strawboat.source.Provider;
import pers.ray.strawboat.source.Type;
import pers.ray.strawboat.source.xicidaili.XiciProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class MultiThreadDemo {

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);


    public static void main(String[] args) {
        MultiThreadDemo demo = new MultiThreadDemo();

        List<Provider> providerList = new ArrayList<Provider>() {{
            add(new XiciProvider(Type.HTTP));
//            add(new XiciProvider(Type.HTTPS));
//            add(new KuaiProvider());
//            add(new KuaiProvider(2));
//            add(new IP3366Provider());
//            add(new _89Provider());
        }};


        providerList.forEach(provider -> {
                    List<IP> ipList = provider.getIPList();
                    ipList.forEach(ip -> {
                        DemoFilter filter = demo.new DemoFilter(ip);
                        demo.executor.execute(filter);
                    });
                }

        );

    }


    class DemoFilter implements Runnable {

        private IP ip;

        public DemoFilter(IP ip) {
            this.ip = ip;
        }

        List<Filter> filterList = new ArrayList<Filter>() {{
            add(new BaiduFilter());
        }};

        @Override
        public void run() {
            filterList.forEach(filter -> {
                if (filter.filter(ip)) {
                    System.out.println("true:" + ip);
                } else {
                    System.out.println("false:" + ip);
                }
            });

        }

    }
}