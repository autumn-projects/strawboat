package com.oscroll.strawboat.assets.entity;

public class IP {

    private String address;
    private int port;
    private String website;

    public IP() {
    }

    public IP(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public IP(String address, int port, String website) {
        this.address = address;
        this.port = port;
        this.website = website;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}

