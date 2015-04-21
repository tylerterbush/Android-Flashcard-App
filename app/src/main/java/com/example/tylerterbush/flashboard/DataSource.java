package com.example.tylerterbush.flashboard;

/**
 * Created by tylerterbush on 3/4/15.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.w3c.dom.Comment;

public class DataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private Context context;
    private String[] allColumnsDecks = {MySQLiteHelper.COLUMN_DECK_NAME};
    private String[] allColumnsCards = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_CARD_DECK_NAME,
            MySQLiteHelper.COLUMN_FRONT, MySQLiteHelper.COLUMN_BACK};
    public DataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createDeck(String deckName){
        this.open();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DECK_NAME, deckName);
        database.insert(MySQLiteHelper.TABLE_DECKS,null,values);
        this.close();
    }

    public List<Deck> getAllDecks(){
        List<Deck> decks = new ArrayList<Deck>();
        this.open();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DECKS,allColumnsDecks
                ,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Deck deck = cursorToDeck(cursor);
            decks.add(deck);
            cursor.moveToNext();
        }
        this.close();
        return decks;
    }

    public List<Card> getAllCards(){
        List<Card> cards = new ArrayList<Card>();
        this.open();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CARDS,allColumnsCards
                ,null,null,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Card card = cursorToCard(cursor);
            cards.add(card);
            cursor.moveToNext();
        }
        this.close();
        return cards;
    }

    public void createCard(String deck_name, String front, String back){
        this.open();
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_CARD_DECK_NAME, deck_name);
        values.put(MySQLiteHelper.COLUMN_FRONT, front);
        values.put(MySQLiteHelper.COLUMN_BACK, back);

        database.insert(MySQLiteHelper.TABLE_CARDS, null,values);

//        Cursor cursor = database.query(MySQLiteHelper.TABLE_CARDS,
//                allColumnsCards, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
//                null, null, null);
//        cursor.moveToFirst();
//        Card newCard = cursorToCard(cursor);
        this.close();
        //return newCard;
    }

    public List<Card> getCardsWithSubject(String subject){

        this.open();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CARDS, allColumnsCards,
                MySQLiteHelper.COLUMN_CARD_DECK_NAME + "=" + "\'" + subject + "\'" + ";"
                ,null,null,null,null);
        cursor.moveToFirst();

        List<Card> cards = new ArrayList<Card>();
        while(!cursor.isAfterLast()){
            Card card = cursorToCard(cursor);
            cards.add(card);
            cursor.moveToNext();
        }
        this.close();
        return cards;
    }

    public void deleteCard(long id){
        this.open();
        database.delete(MySQLiteHelper.TABLE_CARDS, MySQLiteHelper.COLUMN_ID + "=" + id,null);
        this.close();
    }

    private Deck cursorToDeck(Cursor cursor){
        Deck deck  = new Deck();
        deck.setName(cursor.getString(0));
        return deck;
    }

    private Card cursorToCard(Cursor cursor){
        Card card  = new Card();
        card.setId(cursor.getLong(0));
        card.setDeck_name(cursor.getString(1));
        card.setFront(cursor.getString(2));
        card.setBack(cursor.getString(3));


        return card;
    }


}
