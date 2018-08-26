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

    public boolean enableProxy(){
        HttpUtils httpUtils = new HttpUtils();
        try {
            httpUtils.getResponse("https://www.baidu.com", this);
        }catch (IOException e){
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
}
