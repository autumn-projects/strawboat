package com.oscroll.strawboat.demo;

import com.oscroll.strawboat.assets.entity.IP;
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
import java.util.concurrent.ArrayBlockingQueue;


class Demo {

    private int queueSize = 10;
    ArrayBlockingQueue<IP> queue = new ArrayBlockingQueue<>(queueSize);

    public static void main(String[] args) {
        Demo test = new Demo();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();

        producer.start();
        consumer.start();
    }

    class Consumer extends Thread {

        @Override
        public void run() {
            consume();
        }

        private void consume() {
            while (true) {
                try {
                    IP ip = queue.take();
                    System.out.println("true:" + ip);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Producer extends Thread {

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            List<Provider> providerList = new ArrayList<Provider>() {{
                add(new XiciProvider(Type.HTTP));
                add(new XiciProvider(Type.HTTPS));
                add(new KuaiProvider());
                add(new KuaiProvider(2));
                add(new IP3366Provider());
                add(new _89Provider());
            }};

            List<Filter> filterList = new ArrayList<Filter>() {{
                add(new BaiduFilter());
            }};

            while (true) {

                providerList.forEach(provider ->
                                filterList.forEach(filter -> {
                                    List<IP> ipList = provider.getIPList();

                                    ipList.forEach(ip -> {
                                        try {
                                            if (filter.filter(ip)) {
                                                queue.put(ip);
//                                        System.out.println("put" + (queueSize - queue.size()));
                                            } else {
                                                System.out.println("false:"+ip);
                                            }
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                })
                );

            }
        }
    }
}