package com.example.tylerterbush.flashboard;

/**
 * Created by tylerterbush on 3/4/15.
 */
public class Card {
    private long id;
    private String deck_name;
    private String front;
    private String back;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getDeck_name(){
        return this.deck_name;
    }

    public void setDeck_name(String deck_name){
        this.deck_name = deck_name;
    }

    public String getFront(){
        return this.front;
    }

    public void setFront(String front){
        this.front = front;
    }

    public String getBack(){
        return this.back;
    }

    public void setBack(String back){
        this.back = back;
    }

    @Override
    public String toString(){
        return this.front;
    }
}
