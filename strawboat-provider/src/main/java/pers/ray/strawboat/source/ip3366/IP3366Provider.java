package pers.ray.strawboat.source.ip3366;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pers.ray.strawboat.utils.IP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 快代理
 * 官网：http://www.ip3366.net/?stype=1&page=1
 * 全部是http代理
 */

public class IP3366Provider {

    public static final String url = "http://www.ip3366.net/?stype=1&page=1";

    public List<IP> getHttpResource() {
        List<IP> ipList = new ArrayList<>();
        String url = IP3366Provider.url;
        try {
            Document document = Jsoup.connect(url).get();

            Elements elements = document.body().select("tbody").select("tr");
            elements.forEach((v) -> {
                String address = v.select("td").eq(0).text();
                int port = Integer.parseInt(v.select("td").eq(1).text());
                IP ip = new IP(address, port);
                ipList.add(ip);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ipList;
    }

    public static void main(String[] args) {
        IP3366Provider provider = new IP3366Provider();
        List<IP> ipList = provider.getHttpResource();

        for(IP ip :ipList){
            System.out.println(ip +"\t"+ ip.enableProxy());
        }
    }

}
