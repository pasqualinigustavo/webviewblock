package com.webviewblock.domain;

public class History {

    public History(String time, String url) {
        this.time = time;
        this.url = url;
    }

    private String url = null;
    private String time = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        History history = (History) o;
        return this.url.equalsIgnoreCase(history.url);
    }
}
