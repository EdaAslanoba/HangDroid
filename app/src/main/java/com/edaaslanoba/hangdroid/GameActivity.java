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


public class GameActivity extends ActionBarActivity {

    String gameWord = "word";
    int failCounter = 0;
    int guessedLetters = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
    }*/


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
            checkLetter(letter);
        } else {
            Toast.makeText(this, "Please enter a letter", Toast.LENGTH_LONG).show();
        }
    }

    //check if the letter introduced by the player exists in the word
    public void checkLetter(String userInputLetter) {

        //if the button says check letter, keep playing the game
        Button buttonName = (Button)findViewById(R.id.button);
        if (buttonName.getText()=="Check letter") {
            Log.d("MYLOG", "button is check letter");
            char letterIntroduced = userInputLetter.charAt(0);

            boolean letterGuessed = false;

            for (int ii = 0; ii < gameWord.length(); ii++) {
                if (gameWord.charAt(ii) == letterIntroduced) {
                    Toast.makeText(this, "Yes, there is a match", Toast.LENGTH_LONG).show();
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
                newGame();
            }
        }
        else {
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
                Toast.makeText(this, "Please enter a letter you haven't guessed before", Toast.LENGTH_LONG).show();
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
            case 5: //game over
                newImageView.setImageResource(R.drawable.hangdroid_5);
                Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show();
                gameOver();
                break;
        }
    }

    public void gameOver() {
        Button button = (Button)findViewById(R.id.button);
        button.setText("New Game");
    }

    public void newGame() {

        //change button name back
        Button changeButtonName = (Button)findViewById(R.id.button);
        changeButtonName.setText("Check letter");
        //clear textView for previously guessed letters
        TextView textViewFailed = (TextView) findViewById(R.id.textView7);
        textViewFailed.setText(""); //delete all previously entered letters

        //clear textView for the word to be guessed
        LinearLayout layoutLetter = (LinearLayout) findViewById(R.id.layoutLetters);
        for (int ii = 0 ; ii < layoutLetter.getChildCount(); ii++) {
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
