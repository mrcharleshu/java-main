package com.charles.jdk8.stream;

import java.util.Arrays;
import java.util.List;

class Article {

    private final String title;
    private final String author;
    private final List<String> tags;

    public Article(String title, String author, String... tags) {
        this.title = title;
        this.author = author;
        this.tags = Arrays.asList(tags);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getTags() {
        return tags;
    }
}