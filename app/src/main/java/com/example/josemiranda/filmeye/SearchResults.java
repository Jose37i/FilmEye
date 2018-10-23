package com.example.josemiranda.filmeye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchResults extends AppCompatActivity {

    public Button button;
    public Button button1;

    public void button1() {
        button1 = (Button) findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent click = new Intent(SearchResults.this, HomeScreen.class);
                startActivity(click);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        button1();
        button = (Button) findViewById(R.id.button);


        /**
         * checks bundle
         */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String strResult = extras.getString("TITLE");
            TextView textView = findViewById(R.id.Results_MovieName);
            textView.setText(getIntent().getExtras().getString("TITLE"));
            boolean continueProcess = true;
            if (strResult.equalsIgnoreCase("No results found")) {

                button.setVisibility(View.INVISIBLE);
            }

            if (continueProcess == true) {
                String strResult2 = extras.getString("SYNOPSIS");
                TextView textView2 = findViewById(R.id.Reusults_Synopsis);
                textView2.setText(getIntent().getExtras().getString("SYNOPSIS"));

                String strResult3 = extras.getString("RELEASE");
                TextView textView3 = findViewById(R.id.Results_ReleaseDate);
                textView3.setText(getIntent().getExtras().getString("RELEASE"));

                String strResult4 = extras.getString("RUNTIME");
                TextView textView4 = findViewById(R.id.Results_RunTime);
                textView4.setText(getIntent().getExtras().getString("RUNTIME"));


                String strResult5 = extras.getString("REVIEWS");
                TextView textView5 = findViewById(R.id.Results_Reviews);
                textView5.setText(getIntent().getExtras().getString("REVIEWS"));

                String strResult6 = extras.getString("BUDGET");
                TextView textView6 = findViewById(R.id.Results_Budget);
                textView6.setText(getIntent().getExtras().getString("BUDGET"));

                String strResult7 = extras.getString("WEBSITE");
                TextView textView7 = findViewById(R.id.Results_Website);
                textView7.setText(getIntent().getExtras().getString("WEBSITE"));

                String strResult8 = extras.getString("GENRE");
                TextView textView8 = findViewById(R.id.Results_Genre);
                textView8.setText(getIntent().getExtras().getString("GENRE"));

                String strResult9 = extras.getString("PRODUCED");
                TextView textView9 = findViewById(R.id.Results_Producers);
                textView9.setText(getIntent().getExtras().getString("PRODUCED"));

                final String strResult10 = extras.getString("MOVIEID");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent click = new Intent(SearchResults.this, otherResults.class);
                        String review = getReviews(strResult10);
                        click.putExtra("SEARCHVALUE", strResult);
                        click.putExtra("RESULTS", review);
                        startActivity(click);
                    }
                });
            }


        }
    }

    private static String getReviews(String movieID) {
        String info = "Reviewer and Review:\n";

        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/" + movieID + "/reviews?api_key=f91e53ad349204db01179d648b57a758&language=en-US&page=1");
            HttpURLConnection connection1 = (HttpURLConnection) url.openConnection();
            connection1.setRequestMethod("GET");
            connection1.connect();
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
            String lines;
            StringBuffer answer = new StringBuffer();
            while ((lines = reader1.readLine()) != null) {
                answer.append(lines);
            }
            String response1 = answer.toString();

            JSONObject jsonob = new JSONObject(response1);
            JSONArray jsonar = jsonob.getJSONArray("results");

            for (int i = 0; i < 2; i++) {
                JSONObject jsonob1 = jsonar.getJSONObject(i);
                info = info + jsonob1.getString("author") + "\n" + jsonob1.getString("content") + "\n";
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();

        }

        return info;

    }
}
