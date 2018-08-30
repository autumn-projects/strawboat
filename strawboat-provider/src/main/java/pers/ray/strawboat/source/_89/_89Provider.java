package pers.ray.strawboat.source._89;

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
public class _89Provider {

    public static final String url = "http://www.89ip.cn/index_1.html";

    public List<IP> getHttpResource() {
        List<IP> ipList = new ArrayList<>();
        String url = _89Provider.url;
        try {
            Document document = Jsoup.connect(url).get();

            Elements elements = document.body().select("tbody").select("tr");
//            System.out.println(elements);
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
        _89Provider provider = new _89Provider();
        List<IP> ipList = provider.getHttpResource();

        for(IP ip :ipList){
            System.out.println(ip +"\t"+ ip.enableProxy());
        }
    }

}
