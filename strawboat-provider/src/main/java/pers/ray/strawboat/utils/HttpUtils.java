package pers.ray.strawboat.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    public String getResponse(String url) {
        StringBuffer result = new StringBuffer();
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Host","www.xicidaili.com");
            conn.setRequestProperty("Connection","keep-alive");
            conn.setRequestProperty("Pragma","no-cache");
            conn.setRequestProperty("Cache-Control","no-cache");
            conn.setRequestProperty("Upgrade-Insecure-Requests","1");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
            conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            conn.setRequestProperty("Referer","https://www.baidu.com/link?url=mT5yB-svqfwo6O12Fs4GhKSKgE3Qj53kGt0kPOttcOYGHM4lAh_SqWE0TAMdbAPE&wd=&eqid=82367d6d0003bdc8000000065b8139b6");
            conn.setRequestProperty("Accept-Encoding","gzip, deflate");
            conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
            conn.setRequestProperty("Cookie","free_proxy_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFVEkiJWEyM2U1OTBkYmE5OTk3ZTlhYzk5NDFkZmFiYWU3MTFlBjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMXNZc2VraVJJNzJmeElrSHUxZ09QWE9VakY2ZGVKWnNPODY1SVNIY2phdnM9BjsARg%3D%3D--72594dc8a14c3568cf7ad89fbd8135bbdc765d72; Hm_lvt_0cf76c77469e965d2957f0553e6ecf59=1533797006,1535195581,1535195824; Hm_lpvt_0cf76c77469e965d2957f0553e6ecf59=1535195824");

            InputStream inputStream = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");

            BufferedReader in = new BufferedReader(isr);
            String line;
            result = new StringBuffer();
            while ((line = in.readLine()) != null)
                result.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String url = "http://www.xicidaili.com/";
        System.out.println(new HttpUtils().getResponse(url));
    }
}
