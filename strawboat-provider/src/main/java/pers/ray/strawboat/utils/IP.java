package pers.ray.strawboat.utils;


import java.io.IOException;

public class IP {

    private String address;
    private int port;

    public IP() {
    }

    public IP(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public boolean enableProxy() {
        HttpUtils httpUtils = new HttpUtils();
        try {
            String s = httpUtils.getResponse("https://recommd.xyq.cbg.163.com/cgi-bin/recommend.py?callback=Request.JSONP.request_map.request_0&_=1535614711999&school=1&act=recommd_by_role&page=1&count=15&search_type=overall_search_role&view_loc=overall_search", this);
            String s2 = httpUtils.getResponse("http://www.baidu.com", this);
            System.out.println(s);
        } catch (IOException e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return this.address + ":" + this.port;
    }
}
