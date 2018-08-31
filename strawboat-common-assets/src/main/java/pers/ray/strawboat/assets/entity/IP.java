package pers.ray.strawboat.assets.entity;

import lombok.Data;

@Data
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

}

