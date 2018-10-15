package com.oscroll.strawboat.demo;

import com.oscroll.strawboat.pool.ScheduledPool;


class Demo {

    public static void main(String[] args) {
        ScheduledPool cp = new ScheduledPool();

        new Thread(() -> {
            cp.execute();
        }).start();


        while (true) {
            System.out.println("============" + cp.take());
        }

    }

}