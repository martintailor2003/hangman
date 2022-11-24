//package
package com.example.hangman;

//imports
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

//create main class
public class MainActivity extends AppCompatActivity {
    //initialize variables
    public RadioButton easy_button;

    //create main activity
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check start buttons state
        ImageButton button = findViewById(R.id.start_button);
        button.setOnClickListener(new View.OnClickListener() {
            //if clicked call open_game_activity()
            @Override
            public void onClick(View view) {
                open_game_activity();
            }
        });

    }

    //implement this function if start button pressed
    public void open_game_activity(){
        //get mode button state
        easy_button = findViewById(R.id.easy);
        boolean easy_button_state = easy_button.isChecked();


        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("easy_var", easy_button_state);
        startActivity(intent);

    }





}