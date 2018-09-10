package pers.ray.strawboat.source._89;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pers.ray.strawboat.assets.entity.IP;
import pers.ray.strawboat.source.Provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 官网：http://www.89ip.cn
 */
public class _89Provider implements Provider {

    private static final String WEBSITE = "89ip.cn";
    private static final String URL = "http://www.89ip.cn/index_1.html";

    @Override
    public List<IP> getIPList() {
        List<IP> ipList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(URL).get();

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