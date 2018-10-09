package pers.ray.strawboat.filter.xyq;

import pers.ray.strawboat.assets.entity.IP;
import pers.ray.strawboat.assets.entity.utils.HttpUtils;
import pers.ray.strawboat.filter.Filter;

import java.io.IOException;

public class XyqFilter implements Filter {

    private final static String DOMAIN_NAME = "https://recommd.xyq.cbg.163.com/cgi-bin/recommend.py?callback=Request.JSONP.request_map.request_0&_=1535683309957&school=1&act=recommd_by_role&page=1&count=15&search_type=overall_search_role&view_loc=overall_search";

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
