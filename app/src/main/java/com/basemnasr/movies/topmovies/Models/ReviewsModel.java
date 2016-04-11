package com.basemnasr.movies.topmovies.Models;

/**
 * Created by BasemNasr on 4/11/2016.
 */
public class ReviewsModel {

    String author,content;

    public ReviewsModel(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
