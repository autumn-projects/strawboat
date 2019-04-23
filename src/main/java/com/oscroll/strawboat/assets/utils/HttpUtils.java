package com.oscroll.strawboat.assets.utils;


import com.oscroll.strawboat.assets.entity.IP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUtils {

    public static String getResponse(String url, IP ip) throws IOException {
        StringBuffer result;
        long connectBeginTime, connectEndTime, readBeginTime, readEndTime;

        URL u = new URL(url);
        InetSocketAddress addr = new InetSocketAddress(ip.getAddress(), ip.getPort());
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理

        /* 获取http连接，并计算连接耗时 */
        connectBeginTime = System.currentTimeMillis();
        HttpURLConnection conn = (HttpURLConnection) u.openConnection(proxy);
        connectEndTime = System.currentTimeMillis();
        ip.setConnectTime((int) (connectEndTime - connectBeginTime) / 1000);

        conn.setRequestMethod("GET");
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(6000);

        readBeginTime = System.currentTimeMillis();
        InputStream inputStream = conn.getInputStream();
        readEndTime = System.currentTimeMillis();

        InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(isr);
        String line;
        result = new StringBuffer();
        while ((line = in.readLine()) != null)
            result.append(line);

        /* 计算网速 */
        int size = result.toString().getBytes().length / 1024;
        ip.setSpeed((int) (readEndTime - readBeginTime) / 1000 / size);

        conn.disconnect();
        return result.toString();
    }

}
