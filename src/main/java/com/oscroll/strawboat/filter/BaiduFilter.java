package com.oscroll.strawboat.filter;

import com.oscroll.strawboat.assets.entity.IP;
import com.oscroll.strawboat.assets.utils.HttpUtils;

import java.io.IOException;

public class BaiduFilter implements Filter {

    private final static String DOMAIN_NAME = "http://www.baidu.com";

    public boolean filter(IP ip) {
        try {
            HttpUtils.getResponse(DOMAIN_NAME, ip);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
