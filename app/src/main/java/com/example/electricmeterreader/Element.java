package com.example.electricmeterreader;

public class Element {
    private String title;
    private String security;
    private int level;

    public Element(String title, String security, int level) {
        this.title = title;
        this.security = security;
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public String getSecurity() {
        return security;
    }

    public int getLevel() {
        return level;
    }
}
