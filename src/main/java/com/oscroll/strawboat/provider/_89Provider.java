package com.oscroll.strawboat.provider;

import com.oscroll.strawboat.assets.entity.IP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        }

        return ipList;
    }

}
