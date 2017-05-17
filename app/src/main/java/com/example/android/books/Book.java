package com.example.android.books;

import java.util.List;

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
    public Book(String title, String subtitle, List<String> autors, String description){
        this.title          = title;
        this.subtitle       = subtitle;
        this.autors         = getAutors(autors);
        this.description    = description;
    }

    public String getTitle(){

        return title;
    }

    public String getSubtitle(){

        return subtitle;
    }

    public String getAutors(){

        return autors;
    }

    public String getDescription(){
        return description;
    }

    private String getAutors(List<String> autors){
        String autor = null;
        for (int i = 0 ; i < autors.size(); i++){
            autor = autors.get(i);
            if(i == (autors.size()-1)){
               autor = autor.trim() + ",";
            } else {
                autor = autor.trim() + ".";
            }
        }
        return autor;
    }
}
