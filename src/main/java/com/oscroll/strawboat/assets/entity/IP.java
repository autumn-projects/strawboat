package com.oscroll.strawboat.assets.entity;

public class IP {

    private String address;
    private int port;
    private String website;

    private int connectTime;

    /**
     * 网速，以kb/s为单位
     */
    private int speed;

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

    public int getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(int connectTime) {
        this.connectTime = connectTime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "IP{" +
                "address='" + address + '\'' +
                ", port=" + port +
                ", website='" + website + '\'' +
                ", connectTime=" + connectTime +
                ", speed=" + speed +
                '}';
    }
}

