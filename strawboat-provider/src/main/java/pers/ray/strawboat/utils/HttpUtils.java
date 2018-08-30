package pers.ray.strawboat.utils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class HttpUtils {

    public String getResponse(String url, IP ip) throws IOException {
        StringBuffer result = new StringBuffer();

        URL u = new URL(url);
        InetSocketAddress addr = new InetSocketAddress(ip.getAddress(), ip.getPort());
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
        HttpURLConnection conn = (HttpURLConnection) u.openConnection(proxy);
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(6000);
//            conn.setRequestProperty("Host", "www.xicidaili.com");
//            conn.setRequestProperty("Connection", "keep-alive");
//            conn.setRequestProperty("Pragma", "no-cache");
//            conn.setRequestProperty("Cache-Control", "no-cache");
//            conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
//            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//            conn.setRequestProperty("Referer", "https://www.baidu.com/link?url=mT5yB-svqfwo6O12Fs4GhKSKgE3Qj53kGt0kPOttcOYGHM4lAh_SqWE0TAMdbAPE&wd=&eqid=82367d6d0003bdc8000000065b8139b6");
//            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
//            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
//            conn.setRequestProperty("Cookie", "free_proxy_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFVEkiJWEyM2U1OTBkYmE5OTk3ZTlhYzk5NDFkZmFiYWU3MTFlBjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMXNZc2VraVJJNzJmeElrSHUxZ09QWE9VakY2ZGVKWnNPODY1SVNIY2phdnM9BjsARg%3D%3D--72594dc8a14c3568cf7ad89fbd8135bbdc765d72; Hm_lvt_0cf76c77469e965d2957f0553e6ecf59=1533797006,1535195581,1535195824; Hm_lpvt_0cf76c77469e965d2957f0553e6ecf59=1535195824");

        InputStream inputStream = conn.getInputStream();
//            GZIPInputStream gis = new GZIPInputStream(inputStream);
        InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");

        BufferedReader in = new BufferedReader(isr);
        String line;
        result = new StringBuffer();
        while ((line = in.readLine()) != null)
            result.append(line);

        conn.disconnect();
        return result.toString();
    }

    public void jsoup() {
        try {
            Document document = Jsoup.connect("http://www.xicidaili.com/wt/1").get();
            Elements elements = document.body().select("tr");
            elements.remove(0);

            elements.forEach((v) -> {
                String address = v.select("td").eq(1).text();
                int port = Integer.parseInt(v.select("td").eq(2).text());
                IP ip = new IP(address, port);

                System.out.print(ip.getAddress() + ":" + ip.getPort()+"\t");
                if (ip.enableProxy()) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "http://www.xicidaili.com/wt/1";
        new HttpUtils().jsoup();

//        try {
//            System.out.println(new HttpUtils().getResponse("https://www.baidu.com",new IP("203.130.46.108",9090)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
