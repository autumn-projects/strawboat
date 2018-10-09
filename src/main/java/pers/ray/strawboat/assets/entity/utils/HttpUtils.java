package pers.ray.strawboat.assets.entity.utils;


import pers.ray.strawboat.assets.entity.IP;

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

        URL u = new URL(url);
        InetSocketAddress addr = new InetSocketAddress(ip.getAddress(), ip.getPort());
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
        HttpURLConnection conn = (HttpURLConnection) u.openConnection(proxy);
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(6000);
        InputStream inputStream = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        BufferedReader in = new BufferedReader(isr);
        String line;
        result = new StringBuffer();
        while ((line = in.readLine()) != null)
            result.append(line);

        conn.disconnect();
        return result.toString();
    }

}
