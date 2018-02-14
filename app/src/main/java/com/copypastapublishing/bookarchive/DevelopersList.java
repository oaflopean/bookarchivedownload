package com.copypastapublishing.bookarchive;

/**
 * Created by EKENE on 7/23/2017.
 */

public class DevelopersList {

    private String title;
    private String author;
    private String text;
    private String epub;
    private String id;


    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getEpub() {
        return epub;
    }

    public String getId() {
        return id;
    }


    public String getAuthor() {
        return author;
    }

    public DevelopersList(String title, String author, String text, String epub, String id) {
        this.title = title;
        this.author =author;
        this.text=text;
        this.epub=epub;
        this.id=id;

    }
}
