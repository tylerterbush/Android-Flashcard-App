package com.example.tylerterbush.flashboard;

/**
 * Created by tylerterbush on 3/4/15.
 */
public class Deck {
    private long id;
    private String name;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
}
