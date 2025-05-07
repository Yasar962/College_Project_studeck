package com.yasar.collegeproject;

public class notes_model {
    private String title;
    private String url;

    public notes_model() {
        // Required empty constructor for Firebase
    }

    public notes_model(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
