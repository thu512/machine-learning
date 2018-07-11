package com.lcj.recycler.model;

import java.util.List;




public class NetData {


    String lastBuildDate;

    int total;

    int start;

    int display;

    List<ImgData> items;

    public NetData() {
    }

    public NetData(String lastBuildDate, int total, int start, int display, List<ImgData> items) {
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.start = start;
        this.display = display;
        this.items = items;
    }

    @Override
    public String toString() {
        return "NetData{" +
                "lastBuildDate='" + lastBuildDate + '\'' +
                ", total=" + total +
                ", start=" + start +
                ", display=" + display +
                ", items=" + items +
                '}';
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public List<ImgData> getItems() {
        return items;
    }

    public void setItems(List<ImgData> items) {
        this.items = items;
    }
}
