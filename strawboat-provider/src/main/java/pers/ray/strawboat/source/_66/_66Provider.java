package pers.ray.strawboat.source._66;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pers.ray.strawboat.utils.IP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 快代理
 * 官网：http://www.kuaidaili.com/
 * 全部是http代理
 */
public class _66Provider {

    public static final String url = "http://www.66ip.cn/nmtq.php?getnum=30&isp=0&anonymoustype=0&start=&ports=&export=&ipaddress=&area=0&proxytype=1&api=66ip";

    public List<IP> getHttpResource() {
        List<IP> ipList = new ArrayList<>();
        String url = _66Provider.url;
        try {
            Document document = Jsoup.connect(url).get();

            Elements elements = document.body().select("tbody").select("tr");
            System.out.println(elements);
//            elements.forEach((v) -> {
//                String address = v.select("td").eq(0).text();
//                int port = Integer.parseInt(v.select("td").eq(1).text());
//                IP ip = new IP(address, port);
//                ipList.add(ip);
//            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ipList;
    }

    public static void main(String[] args) {
        _66Provider provider = new _66Provider();
        List<IP> ipList = provider.getHttpResource();

        for(IP ip :ipList){
            System.out.println(ip +"\t"+ ip.enableProxy());
        }
    }

}
