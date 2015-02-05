package com.edaaslanoba.hangdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


public class GameActivity extends ActionBarActivity {

    String gameWord = "word";
    int failCounter = 0;
    int guessedLetters = 0;
    int score = 0;
    int wordLength = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
//        setRandomWord();
        Log.d("MYLOG" , "The new word is: " + gameWord);
    }

    public void setRandomWord() {
        //get 4 letter words from a dictionary
        //create word list from dictionary.txt
        List<String> words = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("dictionary.txt")));
            String line = "";
            while ( (line = br.readLine()) != null) {
                if (line.length() == wordLength) { //4
                    words.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("An Exception is caught: " + e.getMessage());
        }

        //now pick a random word from the list
//        Random rand = new Random();
//        gameWord = words.get(rand.nextInt(words.size()));

//        words.add("book");

        int randomIndex = (int)(Math.random() * words.size());
        gameWord = words.get(randomIndex);
    }

    /**
     * retrieve the letter entered in the edit text
     *
     * @param v (button clicked)
     */
    public void introduceLetter(View v) {

        EditText newLetter = (EditText) findViewById(R.id.editTextLetter);
        String letter = newLetter.getText().toString();

        newLetter.setText("");

        if (letter.length() == 1) {
            checkLetter(letter.toLowerCase());
        } else {
            Toast.makeText(this, "Please enter a letter", Toast.LENGTH_SHORT).show();
        }
    }

    //check if the letter introduced by the player exists in the word
    public void checkLetter(String userInputLetter) {


        char letterIntroduced = userInputLetter.charAt(0);

        boolean letterGuessed = false;

        for (int ii = 0; ii < gameWord.length(); ii++) {
            if (gameWord.charAt(ii) == letterIntroduced) {
                Toast.makeText(this, "Yes, there is a match", Toast.LENGTH_SHORT).show();
                letterGuessed = true;
                guessedLetters++;
                displayGuessedLetter(ii, letterIntroduced);
            }
        }

        if (letterGuessed == false) {
            letterFailed(Character.toString(letterIntroduced));
        }

        if (guessedLetters == gameWord.length()) {
            //guessed all letters:
            Toast.makeText(this, "YOU WIN", Toast.LENGTH_LONG).show();
            score++;
            newGame();
        }
    }

    //display the letter the player guesses correctly
    public void displayGuessedLetter(int indexOfLetter, char letterGuessed) {

        LinearLayout layoutLetter = (LinearLayout) findViewById(R.id.layoutLetters);

        TextView newText = (TextView) layoutLetter.getChildAt(indexOfLetter);

        newText.setText(Character.toString(letterGuessed));
    }

    //display the letter the player guesses wrong
    public void letterFailed(String wrongLetterGuessed) {

        TextView wrongLetter = (TextView) findViewById(R.id.textView7);
        String previouslyGuessedWrongLetter = wrongLetter.getText().toString();

        //if the letter has been guessed before, do not display it again and display an error message
        //failCounter should not be incremented in this case, do not change the background image
        for (int ii = 0; ii < previouslyGuessedWrongLetter.length(); ii++) {
            if (wrongLetterGuessed.charAt(0) == previouslyGuessedWrongLetter.charAt(ii)) {
                Toast.makeText(this, "Please enter a letter you haven't guessed before", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        wrongLetter.setText(previouslyGuessedWrongLetter + wrongLetterGuessed);
        //every time the player guesses wrong, increment the fail counter
        failCounter++;
        ImageView newImageView = (ImageView) findViewById(R.id.imageView);

        //every time the player guesses wrong, change the background image - hence: hang the droid
        switch (failCounter) {
            case 1:
                newImageView.setImageResource(R.drawable.hangdroid_1);
                break;
            case 2:
                newImageView.setImageResource(R.drawable.hangdroid_2);
                break;
            case 3:
                newImageView.setImageResource(R.drawable.hangdroid_3);
                break;
            case 4:
                newImageView.setImageResource(R.drawable.hangdroid_4);
                break;
            case 5:
                newImageView.setImageResource(R.drawable.hangdroid_5);
                break;
            case 6:
                gameOver(); //game over screen to save score
                break;
        }
    }

    public void gameOver() {
        Intent gameOver = new Intent(this, GameOverActivity.class);
        gameOver.putExtra("POINTS_IDENTIFIER", score); //sending the score to the gameOverActivity
        startActivity(gameOver);

        //finish game activity to back to home page at the end of the game
        finish();
    }

    public void newGame() {

        //clear textView for previously guessed letters
        TextView textViewFailed = (TextView) findViewById(R.id.textView7);
        textViewFailed.setText(""); //delete all previously entered letters

        //clear textView for the word to be guessed
        LinearLayout layoutLetter = (LinearLayout) findViewById(R.id.layoutLetters);
        for (int ii = 0; ii < layoutLetter.getChildCount(); ii++) {
            TextView newText = (TextView) layoutLetter.getChildAt(ii);
            newText.setText("_");
        }

        //clear imageView for hanging the droid background for each wrong letter
        ImageView newGameImageView = (ImageView) findViewById(R.id.imageView);
        newGameImageView.setImageResource(R.drawable.hangdroid_0);

        //reset counts
        guessedLetters = 0;
        failCounter = 0;
    }

}
