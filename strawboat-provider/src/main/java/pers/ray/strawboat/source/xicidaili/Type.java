package pers.ray.strawboat.source.xicidaili;

public enum Type {

    HTTPS("http://www.xicidaili.com/wn/"), HTTP("http://www.xicidaili.com/wt/");

    private String url;

    Type(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }

}
