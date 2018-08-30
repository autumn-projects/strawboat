package pers.ray.strawboat.source.kuai;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pers.ray.strawboat.source.xicidaili.Type;
import pers.ray.strawboat.utils.IP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 快代理
 * 官网：http://www.kuaidaili.com/
 * 全部是http代理
 */
public class KuaiProvider {

    public static final String url = "https://www.kuaidaili.com/free/inha/";

    public List<IP> getHttpResource(Type type, int page) {
        List<IP> ipList = new ArrayList<>();

        String url = KuaiProvider.url + page;
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
        KuaiProvider provider = new KuaiProvider();
        List<IP> ipList = provider.getHttpResource(Type.HTTPS, 2);

        for(IP ip :ipList){
            System.out.println(ip +"\t"+ ip.enableProxy());
        }
    }

}
