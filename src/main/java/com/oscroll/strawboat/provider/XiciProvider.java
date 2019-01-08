package com.oscroll.strawboat.provider;

import com.oscroll.strawboat.assets.entity.IP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XiciProvider implements Provider {

    private static final String WEBSITE = "xicidaili.com";
    private static final String HTTPS_URL = "http://www.xicidaili.com/wn/";
    private static final String HTTP_URL = "http://www.xicidaili.com/wt/";

    private String url;
    private int page;

    public XiciProvider(Type type) {
        this(type, 1);
    }

    public XiciProvider(Type type, int page) {
        this.page = page;

        switch (type) {
            case HTTPS:
                url = HTTPS_URL;
                break;
            case HTTP:
                url = HTTP_URL;
                break;
        }
    }

    @Override
    public List<IP> getIPList() {
        List<IP> ipList = new ArrayList<>();

        String url = this.url + this.page;
        try {
            Document document = Jsoup.connect(url).get();

            Elements elements = document.body().select("tr");
            elements.remove(0);

            elements.forEach((v) -> {
                String address = v.select("td").eq(1).text();
                int port = Integer.parseInt(v.select("td").eq(2).text());
                IP ip = new IP(address, port, WEBSITE);
                ipList.add(ip);
            });
        } catch (IOException e) {

        }

        return ipList;
    }
}
