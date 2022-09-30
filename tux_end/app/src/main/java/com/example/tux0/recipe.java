package com.example.tux0;

public class recipe {
    private String name;
    private String summary;
    private String img;
    private String url;
    private String id;

    public recipe(){}

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getsummary() {
        return summary;
    }

    public void setsummary(String info) {
        this.summary = info;
    }

    public String getimg() {
        return img;
    }

    public void setimg(String img) {
        this.img = img;
    }

    public String geturl() {
        return url;
    }

    public void seturl(String url) {this.url = url;}

    public String getid(){return id;}

    public void setid(String id) {this.id = id;}

}
