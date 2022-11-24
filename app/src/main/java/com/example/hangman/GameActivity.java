package com.example.hangman;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.reflect.Array;
import java.util.Arrays;

public class GameActivity<edited_string> extends AppCompatActivity {
    //initialize variables
    // #constans
    public static int max_trys;
    public static final int row_number = 4;
    public static int letter_counter = 0;
    public static int column_number; // will get value later
    public static String x = "abcdefghijklmnopqrstuvwxyz"; //"qwertzuiopasdfghjklyxcvbnm"
    public static final String[] alphabet = x.split("");
    public static int button_width;
    public final static String[] words_easy = {"cat","dog","bird","house","sun","moon","cup","mug","car","book","bottle","gold","smile","cake","bed","phone","pen","sleep","note","dance","coffee","dawn","dad","girl","boy","pet","word","light","skull","brave","game","play","summer","cold","hot"};
    public final static String[] words_hard = {"abruptly","absurd","abyss","affix","askew","avenue","awkward","axiom","azure","bagpipes","bandwagon","banjo","bayou","beekeeper","bikini","blitz","blizzard","boggle","boxcar","boxful","buckaroo","buffoon","buxom","buzzard","buzzing","buzzwords","caliph","cobweb","cockiness","croquet","crypt","curacao","cycle","daiquiri","dirndl","disavow","dizzying","duplex","dwarves", "embezzle","equip","espionage","exodus","faking","fishhook","fixable","fjord","flapjack","flopping","fluffiness","flyby","foxglove","frazzled","frizzled","fuchsia","funny"," gabby","galaxy","galvanize","gizmo","glowworm","glyph","gnarly","gnostic","gossip","grogginess","haiku","haphazard","hyphen","iatrogenic","icebox","injury","ivory","ivy","jackpot"," jaundice","jawbreaker","jaywalk","jazziest","jazzy","jelly","jigsaw","jinx","jiujitsu","jockey","jogging","joking","jovial","joyful","juicy","jukebox","jumbo","kayak","kazoo","keyhole","khaki"," kilobyte","kiosk","kitsch","kiwifruit","knapsack","larynx","lengths","lucky","luxury","lymph","marquis","matrix","megahertz","microwave","mnemonic","mystify","naphtha","nightclub","nowadays","nymph","onyx","ovary","oxidize","oxygen","pajama","peekaboo","phlegm","pixel","pizazz","pneumonia","polka","pshaw","psyche","puppy","puzzling","quartz","queue","quips","quixotic"," quiz","quizzes","quorum","razzmatazz","rhubarb","rhythm","rickshaw","schnapps","scratch","shiv","snazzy","sphinx","spritz","squawk","staff"," syndrome","thriftless","thumbscrew","topaz","transcript","transgress","transplant","twelfth","twelfths","unknown","unworthy","unzip","uptown","vaporize"," vixen","vodka","voodoo","vortex","voyeurism","walkway","waltz","wave","wavy","waxy","wellspring","wheezy","whiskey","whizzing","whomever","wimpy","witchcraft","wizard"," woozy","wristwatch","xylophone","yachtsman","yippee","yoked","youthful","yummy","zephyr","zigzag","zigzagging","zilch","zipper","zodiac","zombie"};
    public static String[] words;

    //#dinamics
    public static int trys_in_game_activity;
    public static int word_length;
    public static boolean game_running = true;
    public static String[] letters_array;
    public static String[] finded_letters_array;
    public static String[] untried_letters = new String[26];

    //initialing process
    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        //get parameters
        Bundle bundle = getIntent().getExtras();
        boolean easy_var = bundle.getBoolean("easy_var");
        words = easy_var ? words_easy : words_hard;

        //create the view
        setContentView(R.layout.activity_game);

        //button functions
        Button btn = findViewById(R.id.button0);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_game();
            }
        });
        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });
        //customizing the view /keyboardLayout
        create_keyboard();
        //create random word
        select_random_word();
    }
    //end if init
    
    
    //FUNCTIONS

    //keyboard initialization function
    private void create_keyboard(){
        //create layouts
        LinearLayout layout_x = findViewById(R.id.layout_for_keyboard);
        for(int i=0; i<row_number; i++){
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f);
            layout_x.addView(layout, layoutParams);
            //set column number
            if(i==0 | i==3){
                column_number = 6;
            }else{
                column_number = 7;
            }
            //add buttons
            for(int y=0; y<column_number; y++){
                //add 26 button
                if (letter_counter < 26) {
                    Button button = new Button(this);
                    button.setPadding(0,0,0,0);
                    button.setBackgroundColor(Color.rgb(146,74,54));
                    button.setTextColor(getResources().getColor(R.color.white_grey));
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1.0f
                    );
                    //String button_text = (String) Array.get(alphabet,letter_counter);
                    Log.d("Whole array", String.join("",alphabet));
                    String button_text = alphabet[letter_counter];
                    Log.d("letter counter", Integer.toString(letter_counter));
                    Log.d("button text", button_text);
                    button.setText(button_text);
                    layoutParams1.setMargins(10,10,10,10);

                    button.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            on_keyboard_click(button);
                        }
                    });
                layout.addView(button, layoutParams1);
                //align keyboard buttons after layout creation
                if(letter_counter==5 | letter_counter==25){
                    layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            last_button_function(button,layout);
                        }
                    });
                }
                letter_counter = letter_counter + 1;
                }
            }//end of button creation
        }//end of layout creation for
        letter_counter = 0;
    }

    //edit keyboard layout after last button created
    private void last_button_function(Button button,LinearLayout layout){
        button_width = button.getWidth();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)layout.getLayoutParams();
        layoutParams.setMarginStart(button_width/2);
        layoutParams.setMarginEnd(button_width/2);
        layout.setLayoutParams(layoutParams);

    }

    //create random word and letter matrix for the word
    private void select_random_word(){
        //VARIABLES TO CHANGE
        //int trys_in_game_activity;
        //String[] letters_array;
        //String[] finded_letters_array;
        //int word_length;
        //String[] untried_letters;
        //boolean game running

        //get a random word
        int length = words.length;
        int min = 0;
        int max = length-1;
        int random_int = (int) (Math.random()*((max-min+1)+min));
        String random_word = words[random_int];
        game_running = true;

        //create an array from the word
        letters_array = new String[length];
        finded_letters_array = random_word.split(""); //length declaration of finded_letters_array
        letters_array = random_word.split("");
        for(int i=0; i<finded_letters_array.length; ++i){
            finded_letters_array[i] = " _ ";
        }

        //get the word length
        word_length = letters_array.length;
        //reset trys number
        trys_in_game_activity = 8;
        //reset untried letters array
        for(int i=0; i<alphabet.length; ++i){
            untried_letters[i] = alphabet[i];
        }
        ImageView img = findViewById(R.id.actualHangImage);
        img.setBackgroundResource(R.drawable.img_start);

    }

    //do this when a keyboard button clicked
    private void on_keyboard_click(Button button){
        //#VARIABLES
        //int trys_in_game_activity;
        //String[] letters_array;
        //String[] finded_letters_array;
        //int word_length; n
        //String[] untried_letters;

        //get button text and imageview
        String letter = (String) button.getText();
        Log.d("Selected letter", letter);
        Log.d("Array after get text", String.join("",alphabet));

        //check letter has been tried or not
        Boolean valid_click = false;
        String c0;
        for(int i0=0; i0<untried_letters.length; ++i0){
            c0 = untried_letters[i0];
            if(letter == c0){
                valid_click = true;
                untried_letters[i0] = "";
                break;
            }
        }
        if(trys_in_game_activity <= 0){
            valid_click = false;
        }
        String s0 = Boolean.toString(valid_click);
        Log.d("Valid click", s0);
        Log.d("Array before click", String.join("",alphabet));

        //do the job if the letter is not tried
        if(valid_click==true & game_running==true){
            //check letter presence in the word
            String c;
            boolean letter_in_array_bool = false;
            for(int i0=0; i0<word_length; ++i0) {
                c = letters_array[i0];
                if(letter.equals(c)) {
                    letter_in_array_bool = true;
                    Log.d("In if", c);
                    break;
                }
            }
            String s1 = Boolean.toString(letter_in_array_bool);
            Log.d("Letter in array", s1);

            //replace "_" with letter
            for(int i1=0; i1<word_length; ++i1){
                c = letters_array[i1];
                if(letter.equals(c)){
                    finded_letters_array[i1] = c;
                }

            }

            //get imageView
            ImageView img = findViewById(R.id.actualHangImage);

            //if letter is not in array
            if(letter_in_array_bool==false){
                button.setBackgroundColor(Color.rgb(255,0,0));
                trys_in_game_activity = trys_in_game_activity - 1;
                Log.isLoggable("trys", trys_in_game_activity);
                switch (trys_in_game_activity){
                    case 7:
                        img.setBackgroundResource(R.drawable.img0);
                        break;
                    case 6:
                        img.setBackgroundResource(R.drawable.img1);
                        break;
                    case 5:
                        img.setBackgroundResource(R.drawable.img2);
                        break;
                    case 4:
                        img.setBackgroundResource(R.drawable.img3);
                        break;
                    case 3:
                        img.setBackgroundResource(R.drawable.img4);
                        break;
                    case 2:
                        img.setBackgroundResource(R.drawable.img5);
                        break;
                    case 1:
                        img.setBackgroundResource(R.drawable.img7);
                        break;
                    default:
                        img.setBackgroundResource(R.drawable.img9);
                        rewrite_text(letters_array);
                        game_running = false;
                        break;

                }

            //if letter is in the array
            }else{
                Log.d("Letter in word", "true");
                //set button color
                button.setBackgroundColor(Color.rgb(1,113,0));
                rewrite_text(finded_letters_array);
                //set win image and end the game
                Log.d("finded letters array", Arrays.toString(finded_letters_array));
                Log.d("letter_array", Arrays.toString(letters_array));
                //compare arrays
                boolean won = false;
                for(int i=0; i<letters_array.length; ++i){
                    if(letters_array[i]==finded_letters_array[i]){
                        won = true;
                    }else{
                        won = false;
                        break;
                    }
                }
                if(won){
                    //set image to win
                    img.setBackgroundResource(R.drawable.img_win);
                    game_running = false;
                }
            }




        }//do nothing if click is not valid




        //button.setBackgroundResource(R.drawable.check_png);
    }


    private void rewrite_text(String[] s){
        String edited_string = finded_letters_array[0];
        String sv = String.join("", s);
        for(int i=0; i<finded_letters_array.length; ++i){
            //String actual_letter = finded_letters_array[i];
        }
        TextView tv = findViewById(R.id.textView0);
        tv.setText(sv);
    }
    //
    private void new_game(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }

    public void exit(){
        Intent intent = getIntent();
        finish();
    }
}