package com.oscroll.strawboat.filter.baidu;

import com.oscroll.strawboat.assets.entity.IP;
import com.oscroll.strawboat.assets.entity.utils.HttpUtils;
import com.oscroll.strawboat.filter.Filter;

import java.io.IOException;

public class BaiduFilter implements Filter {

    private final static String DOMAIN_NAME = "https://www.baidu.com";

    public boolean filter(IP ip) {
        try {
            HttpUtils.getResponse(DOMAIN_NAME, ip);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
