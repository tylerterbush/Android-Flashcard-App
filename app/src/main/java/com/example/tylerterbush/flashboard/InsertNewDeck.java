package com.example.tylerterbush.flashboard;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class InsertNewDeck extends ActionBarActivity {

    private DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_deck);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insert_new_deck, menu);
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

    public void cancel(View view) {
        super.onBackPressed();
    }

    public void submitDeck(View view){

        dataSource = new DataSource(this.getApplicationContext());

        EditText edit = (EditText) findViewById(R.id.edit_text);
        String s = edit.getText().toString();

        if(s.length() >= 1) {
            dataSource.createDeck(s);
            //super.onBackPressed();
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(getString(R.string.insert_card_boolean),true);

            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Invalid Deck Name", Toast.LENGTH_SHORT).show();
        }
    }

}
