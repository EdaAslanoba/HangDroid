package com.edaaslanoba.hangdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MultiplayerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
    }

    public void startMultiPlayerGame (View v) {
        EditText editText = (EditText) findViewById(R.id.editTextMulti);
        String wordToGuess = editText.getText().toString();

        //clear textview
        editText.setText("");

        Intent newIntent = new Intent(this, GameMultiActivity.class);
        newIntent.putExtra("WORD_IDENTIFIER", wordToGuess);

        startActivity(newIntent);

        //finish game activity to back to home page at the end of the game
    }
}
