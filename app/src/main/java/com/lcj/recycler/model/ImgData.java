package com.lcj.recycler.model;



public class ImgData {

    String title;

    String link;

    String thumbnail;

    String sizeheight;


    String sizewidth;

    public ImgData() {
    }

    public ImgData(String title, String link, String thumbnail, String sizeheight, String sizewidth) {
        this.title = title;
        this.link = link;
        this.thumbnail = thumbnail;
        this.sizeheight = sizeheight;
        this.sizewidth = sizewidth;
    }

    @Override
    public String toString() {
        return "ImgData{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", sizeheight='" + sizeheight + '\'' +
                ", sizewidth='" + sizewidth + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSizeheight() {
        return sizeheight;
    }

    public void setSizeheight(String sizeheight) {
        this.sizeheight = sizeheight;
    }

    public String getSizewidth() {
        return sizewidth;
    }

    public void setSizewidth(String sizewidth) {
        this.sizewidth = sizewidth;
    }
}
