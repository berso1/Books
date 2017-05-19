package com.example.android.books;

/**
 * Created by berso on 5/16/17.
 */

public class Book {

    //Book varibles
    private String title;
    private String subtitle;
    private String autors;
    private String description;

    //Book constructor
    public Book(String title, String subtitle, String autors, String description) {
        this.title = title;
        this.subtitle = subtitle;
        this.autors = autors;
        this.description = description;
    }

    public String getTitle() {

        return title;
    }

    public String getSubtitle() {

        return subtitle;
    }

    public String getAutors() {

        return autors;
    }

    public String getDescription() {
        return description;
    }


}
