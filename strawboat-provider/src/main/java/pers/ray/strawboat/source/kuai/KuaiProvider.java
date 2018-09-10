package pers.ray.strawboat.source.kuai;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pers.ray.strawboat.assets.entity.IP;
import pers.ray.strawboat.source.Provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 快代理
 * 官网：http://www.kuaidaili.com/
 * 全部是http代理
 */
public class KuaiProvider implements Provider {

    private static final String WEBSITE = "kuaidaili.com";
    private static final String URL = "https://www.kuaidaili.com/free/inha/";

    private String url;
    private int page;

    public KuaiProvider() {
        this(1);
    }

    public KuaiProvider(int page) {
        url = URL;
        this.page = page;
    }

    @Override
    public List<IP> getIPList() {
        List<IP> ipList = new ArrayList<>();

        String url = this.url + this.page;
        try {
            Document document = Jsoup.connect(url).get();

            Elements elements = document.body().select("tbody").select("tr");

            elements.forEach((v) -> {
                String address = v.select("td").eq(0).text();
                int port = Integer.parseInt(v.select("td").eq(1).text());
                IP ip = new IP(address, port, WEBSITE);
                ipList.add(ip);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ipList;
    }

}