package com.example.tylerterbush.flashboard;

/**
 * Created by tylerterbush on 3/4/15.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_DECKS = "decks";
    public static final String TABLE_CARDS = "cards";
    public static final String COLUMN_DECK_NAME = "name";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FRONT = "front";
    public static final String COLUMN_BACK = "back";
    public static final String COLUMN_CARD_DECK_NAME = "card_deck_name";

    private static final String DATABASE_NAME = "flashdb.db";
    private static final int DATABASE_VERSION = 4;

    // Database creation sql statement
    private static final String DECKS_CREATE = "create table "
            + TABLE_DECKS + " (" + COLUMN_DECK_NAME + " text not null);";
    private static final String CARDS_CREATE = "create table " + TABLE_CARDS + " (" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_CARD_DECK_NAME + " text not null, "
            + COLUMN_FRONT + " text not null, " + COLUMN_BACK + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d("DBHelper","DB Creating");
        database.execSQL(DECKS_CREATE);
        Log.d("DECKS CREATING","");
        database.execSQL(CARDS_CREATE);
        Log.d("CARDS CREATING","");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DECKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        onCreate(db);
    }

}
