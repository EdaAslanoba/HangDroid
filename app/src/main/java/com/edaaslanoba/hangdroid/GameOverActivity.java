package com.edaaslanoba.hangdroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.TooManyListenersException;


public class GameOverActivity extends ActionBarActivity {

    int scoredPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int score = getIntent().getIntExtra("POINTS_IDENTIFIER", 0); //2nd parameter is the default  value if no intent
        TextView textViewPoints = (TextView) findViewById(R.id.textViewPoints);
        textViewPoints.setText(String.valueOf(score)); //score was an int, pass the String

        scoredPoints = score;
    }

    /**
     *
     * @param v (button clicked)
     */
    public void saveScore(View v) {

        SharedPreferences preferences = getSharedPreferences("MY PREFERENCES", Context.MODE_PRIVATE);

        EditText newText = (EditText) findViewById(R.id.editTextName);
        String name = newText.getText().toString(); //get player's name

        SharedPreferences.Editor editor = preferences.edit(); //shared pref. writing

        String previousScore = preferences.getString("Scores", "");

        editor.putString("Scores", name + " " + scoredPoints + " Points\n" + previousScore);

        editor.commit();

        Toast.makeText(this, "SCORE SAVED", Toast.LENGTH_SHORT).show();

        //finish activity, so do not stay on the game over page once score is saved
        finish(); //finish activity, so do not stay on the game over page once score is saved
    }
}
