package com.example.tylerterbush.flashboard;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.graphics.PorterDuff;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends ActionBarActivity implements ReviewFragment.OnFragmentInteractionListener,
        InsertCardsFragment.OnFragmentInteractionListener{

    boolean review;
    boolean insert;
    private DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //preload with 3 subjects and 4 cards in each

        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.button);
        b.getBackground().setColorFilter(Color.GREEN,PorterDuff.Mode.MULTIPLY);
        Button bb = (Button) findViewById(R.id.button2);
        bb.getBackground().setColorFilter(Color.WHITE,PorterDuff.Mode.MULTIPLY);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ReviewFragment frag = new ReviewFragment();

        fragmentTransaction.add(R.id.frame_layout,frag);
        fragmentTransaction.commit();
        review = true;
        insert = false;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Review(View view){
        if(!review) {
            Button b = (Button) findViewById(R.id.button);
            b.getBackground().setColorFilter(Color.GREEN,PorterDuff.Mode.MULTIPLY);
            Button bb = (Button) findViewById(R.id.button2);
            bb.getBackground().setColorFilter(Color.WHITE,PorterDuff.Mode.MULTIPLY);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            ReviewFragment frag = new ReviewFragment();
            fragmentTransaction.replace(R.id.frame_layout,frag);
            fragmentTransaction.commit();

            review = true;
            insert = false;
        }
    }

    public void InsertCards(View view){
        if(!insert) {
            Button b = (Button) findViewById(R.id.button);
            b.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            Button bb = (Button) findViewById(R.id.button2);
            bb.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            InsertCardsFragment frag = new InsertCardsFragment();
            fragmentTransaction.replace(R.id.frame_layout,frag);
            fragmentTransaction.commit();

            insert = true;
            review = false;
        }
    }

    public void startStudying(View view){
        Spinner spinner = (Spinner) findViewById(R.id.spinner_review);
        if(spinner.getSelectedItem() != null) {
            String selected_subject = spinner.getSelectedItem().toString();

            dataSource = new DataSource(getApplicationContext());
            List<Card> list = dataSource.getCardsWithSubject(selected_subject);
            if(list.size() < 1){
                Toast.makeText(getApplicationContext(), "No Cards Found For This Subject",
                        Toast.LENGTH_SHORT).show();
            }
            else {

                Intent i = new Intent(this, StudyActivity.class);
                i.putExtra(getString(R.string.study_subject), selected_subject);

                CheckBox check = (CheckBox) findViewById(R.id.checkbox);
                if (check.isChecked()) {
                    i.putExtra(getString(R.string.checked), "Yes");
                } else {
                    i.putExtra(getString(R.string.checked), "No");
                }

                startActivity(i);
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "No Subjects Have Been Created",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void createNewDeck(View view){
        Intent intent = new Intent(this,InsertNewDeck.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        boolean new_card = intent.getBooleanExtra(getString(R.string.insert_card_boolean),false);

        if(new_card){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            InsertCardsFragment frag = new InsertCardsFragment();
            fragmentTransaction.replace(R.id.frame_layout,frag);
            fragmentTransaction.commit();

            Button b = (Button) findViewById(R.id.button);
            b.getBackground().setColorFilter(Color.WHITE,PorterDuff.Mode.MULTIPLY);
            Button bb = (Button) findViewById(R.id.button2);
            bb.getBackground().setColorFilter(Color.GREEN,PorterDuff.Mode.MULTIPLY);

            insert = true;
            review = false;
        }
    }

    public void insertCard(View view){
        //Get the subject from spinner and get the ID of that subject based on its name
        //Get the card front and back from the 2 edit texts
        //call createCard and pass in the 3 values you found
        String front,back,spinner_text;
        dataSource = new DataSource(getApplicationContext());

        EditText edit_text_front = (EditText) findViewById(R.id.question);
        EditText edit_text_back = (EditText) findViewById(R.id.answer);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_insert_card);



        if(spinner.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "Need to Create a Subject First",
                    Toast.LENGTH_SHORT).show();
        }
        else if(edit_text_front.getText().toString().length() < 1){
            Toast.makeText(getApplicationContext(), "Card Needs to Have a Question",
                    Toast.LENGTH_SHORT).show();
        }
        else if(edit_text_back.getText().toString().length() < 1){
            Toast.makeText(getApplicationContext(), "Card Needs to Have an Answer",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            spinner_text = spinner.getSelectedItem().toString();
            front = edit_text_front.getText().toString();
            back = edit_text_back.getText().toString();
            edit_text_front.setText("");
            edit_text_back.setText("");
            dataSource.createCard(spinner_text, front, back);
            Toast.makeText(getApplicationContext(), "Card Successfully Added",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
