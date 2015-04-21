package com.example.tylerterbush.flashboard;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class StudyActivity extends ActionBarActivity implements
        GestureDetector.OnGestureListener {

    List<Card> cards;
    int num_cards;
    int current_card_num;
    Card current_card;
    boolean viewing_front = true;
    String subject;

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        mDetector = new GestureDetectorCompat(this,this);

        Intent intent = getIntent();

        if(intent.getExtras() == null){
            Log.d("null","null");
            onBackPressed();
        }

        subject = intent.getExtras().getString(getString(R.string.study_subject));
        DataSource dataSource = new DataSource(getApplicationContext());

        TextView current_subject = (TextView) findViewById(R.id.current_subject);
        current_subject.setText("Now Studying: " + subject);

        cards = dataSource.getCardsWithSubject(subject);

//        if(cards.size() < 1){
//            Toast.makeText(getApplicationContext(), "No Cards Found For This Subject",
//                    Toast.LENGTH_SHORT).show();
//            onBackPressed();
//        }

        //now check to see if it needs to be randomized
        if(intent.getExtras().getString(getString(R.string.checked)).equals("Yes")){
            long seed = System.nanoTime();
            Collections.shuffle(cards,new Random(seed));
        }

        TextView tv = (TextView) findViewById(R.id.card_content);
        tv.setText(cards.get(0).getFront());

        num_cards = cards.size();
        current_card_num = 1;
        current_card = cards.get(0);

        displayCardFront();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_study, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            Toast.makeText(getApplicationContext(), "Deleting Card", Toast.LENGTH_SHORT).show();
            deleteCard();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void previousCard(View view){
        if(current_card_num > 1){
            current_card_num--;
            current_card = cards.get(current_card_num - 1);
            viewing_front = true;

            displayCardFront();
        }
        else{
            Toast.makeText(getApplicationContext(), "No previous card", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextCard(View view){
        if(current_card_num < num_cards){
            current_card_num++;
            current_card = cards.get(current_card_num - 1);
            viewing_front = true;

            displayCardFront();
        }
        else{
            Toast.makeText(getApplicationContext(), "No next card", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayCardFront(){
        TextView textView = (TextView) findViewById(R.id.card_number);
        textView.setText(current_card_num + "/" + num_cards);

        TextView flashcard = (TextView) findViewById(R.id.card_content);
        flashcard.setText(current_card.getFront());

        viewing_front = true;
    }

    public void displayCardBack(){
        TextView textView = (TextView) findViewById(R.id.card_number);
        textView.setText(current_card_num + "/" + num_cards);

        TextView flashcard = (TextView) findViewById(R.id.card_content);
        flashcard.setText(current_card.getBack());

        viewing_front = false;
    }

    public void deleteCard(){
        Toast.makeText(getApplicationContext(), "Deleting Card", Toast.LENGTH_SHORT).show();
        DataSource dataSource = new DataSource(getApplicationContext());
        dataSource.deleteCard(current_card.getId());
        if(num_cards > 1) {
            finish();
            startActivity(getIntent());
        }
        else{
            Toast.makeText(getApplicationContext(), "No Cards Remaining", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());

        if(viewing_front){
            displayCardBack();
        }
        else{
            displayCardFront();
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }
}
