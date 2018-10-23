package com.example.josemiranda.filmeye;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

public class HomeScreen extends AppCompatActivity {

    /*************************
     * String: takenInput - we will take the text from editText and set it to variable
     * RadioGroup : radioGroup - its the name of the group of radio buttons
     * RadioButton: radioButton - the radio button that is checked is assigned to this radioButton
     * TextView: mTextView1 - the text view that contains the movies that are now playing
     * TextView : mTextView2 - the text view that contains the movies that are upcoming
     *****************************/
    private String takenInput;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    public TextView mTextView1;
    public TextView mTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        /**
         * This is random i had to put in to make it run
         */
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /**
         * Finds the text view id and is assigned to mTextView1
         */
        mTextView1 = findViewById(R.id.textView);
        try
        {
            /**
             * Parse the now playing movies
             */
            URL url1 = new URL("https://api.themoviedb.org/3/movie/now_playing?api_key=f91e53ad349204db01179d648b57a758&language=en-US&page=2&region=US");
            HttpURLConnection connection1 = (HttpURLConnection)url1.openConnection();
            connection1.setRequestMethod("GET");
            connection1.connect();
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
            String lines;
            StringBuffer answer1 = new StringBuffer();
            while( (lines = reader1.readLine()) != null)
            {
                answer1.append(lines);
            }
            String response1 = answer1.toString();

            JSONObject jsonob2 = new JSONObject(response1);
            JSONArray jsonar1 = jsonob2.getJSONArray("results");

            String allinfo = "Now Playing:\n";
            /**
             * shows out the first ten movies
             */
            for (int i = 0; i < 10; i++)
            {
                JSONObject jsonob3 = jsonar1.getJSONObject(i);
                allinfo = allinfo + "     " + jsonob3.getString("title") +  "                        " + jsonob3.getString("release_date") +"\n";
            }
            mTextView1.setText(allinfo);
        }
        catch(IOException|JSONException e)
        {
            e.printStackTrace();
        }

        mTextView2 = findViewById(R.id.textView2);
        try
        {
            URL url = new URL("https://api.themoviedb.org/3/movie/upcoming?api_key=f91e53ad349204db01179d648b57a758&language=en-US&page=2&region=US");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer answer = new StringBuffer();
            while( (line = reader.readLine()) != null)
            {
                answer.append(line);
            }
            String response1 = answer.toString();

            JSONObject jsonob1 = new JSONObject(response1);
            JSONArray jsonar = jsonob1.getJSONArray("results");

            String alltitles = "Upcoming Movies:\n";
            for (int i = 0; i < 10; i++)
            {
                JSONObject jsonob2 = jsonar.getJSONObject(i);
                alltitles = alltitles + "     " + jsonob2.getString("title") +  "                                 " + jsonob2.getString("release_date") +"\n";
            }
            mTextView2.setText(alltitles);
        }
        catch(IOException|JSONException e)
        {
            e.printStackTrace();
        }

        /**
         * takes the text from the editText
         */
        final EditText input = findViewById(R.id.editText1);
        /**
         * finds the radio group by looking for the ID
         */
        radioGroup = findViewById(R.id.radioGroup);
        /**
         * if one of the radio buttons is selected from the radio group, this will execute
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
                /**
                 * If search for movie is selected
                 */
                if(radioButton.getText().equals("Search for Movie"))
                {

                    takenInput = input.getText().toString();

                    JSONParser tester = new JSONParser();

                    String [] array = new String[9];
                    try {
                        array = tester.parse(takenInput);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent i = new Intent(getApplicationContext(),SearchResults.class);
                    if(array[0]==null)
                    {
                        i.putExtra("TITLE","No results found");
                        startActivity(i);

                    }
                    else {
                        i.putExtra("TITLE",array[0]);
                        i.putExtra("SYNOPSIS", array[1]);
                        i.putExtra("RELEASE", array[2]);
                        i.putExtra("RUNTIME", array[3]);
                        i.putExtra("REVIEWS", array[4]);
                        i.putExtra("BUDGET", array[5]);
                        i.putExtra("WEBSITE", array[6]);
                        i.putExtra("GENRE", array[7]);
                        i.putExtra("PRODUCED", array[8]);
                        i.putExtra("MOVIEID", array[9]);
                        startActivity(i);
                    }
                }
                /**
                 * If search by genre is selected
                 */
                if(radioButton.getText().equals("Search by genre"))
                {
                    takenInput = input.getText().toString();
                    JSONParser tester2 = new JSONParser();
                    String result=null;
                    try
                    {
                        result=tester2.genreParse(takenInput);
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                    Intent i = new Intent(getApplicationContext(),otherResults.class);
                    if (result.contains("No results found, click back to search again"))
                    {
                        i.putExtra("SEARCHVALUE",result);
                    }
                    else {
                        i.putExtra("RESULTS", result);
                        i.putExtra("SEARCHVALUE", takenInput);
                    }
                    startActivity(i);
                }
                /**
                 * if search for a person is selected
                 */
                if(radioButton.getText().equals("Search for a Person"))
                {
                    takenInput = input.getText().toString();
                    JSONParser tester4 = new JSONParser();
                    String result=null;
                    try
                    {
                        result=tester4.personParse(takenInput);
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                    Intent i = new Intent(getApplicationContext(),otherResults.class);
                    if (result.equals("No results found, click back to search again"))
                    {
                        i.putExtra("SEARCHVALUE",result);
                    }
                    else {
                        i.putExtra("RESULTS", result);
                        i.putExtra("SEARCHVALUE", takenInput);
                    }
                    startActivity(i);
                }
            }
        });
    }
}
