package pers.ray.strawboat.filter.baidu;

import pers.ray.strawboat.assets.entity.IP;
import pers.ray.strawboat.assets.utils.network.HttpUtils;
import pers.ray.strawboat.filter.Filter;

import java.io.IOException;

public class BaiduFilter implements Filter {

    private final static String DOMAIN_NAME = "https://www.baidu.com";

    public boolean filter(IP ip) {
        try {
//            String response = HttpUtils.getResponse(DOMAIN_NAME, ip);
//            System.out.println(response);
            HttpUtils.getResponse(DOMAIN_NAME, ip);
        } catch (IOException e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }

}
