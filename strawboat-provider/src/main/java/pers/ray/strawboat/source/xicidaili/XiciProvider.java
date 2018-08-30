package pers.ray.strawboat.source.xicidaili;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pers.ray.strawboat.source.Provider;
import pers.ray.strawboat.utils.IP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 西刺代理
 * 官网：http://www.xicidaili.com/
 */
public class XiciProvider implements Provider {

    public List<IP> getHttpResource(Type type, int page) {
        List<IP> ipList = new ArrayList<>();

        String url = type.toString() + page;
        try {
            Document document = Jsoup.connect(url).get();

            Elements elements = document.body().select("tr");
            elements.remove(0);

            elements.forEach((v) -> {
                String address = v.select("td").eq(1).text();
                int port = Integer.parseInt(v.select("td").eq(2).text());
                IP ip = new IP(address, port);
                ipList.add(ip);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ipList;
    }

    public static void main(String[] args) {
        XiciProvider provider = new XiciProvider();
        List<IP> ipList = provider.getHttpResource(Type.HTTPS, 1);

        for(IP ip :ipList){
            System.out.println(ip +"\t"+ ip.enableProxy());
        }
    }

    @Override
    public List<IP> getIPList() {
        return getHttpResource(Type.HTTPS,1);
    }
}
