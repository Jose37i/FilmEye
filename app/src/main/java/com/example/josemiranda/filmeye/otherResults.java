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

public class otherResults extends AppCompatActivity {

    public Button button2;

    public void button2()
    {
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent click = new Intent(otherResults.this,HomeScreen.class);
                startActivity(click);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_results);
        button2();
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String str = extras.getString("SEARCHVALUE");

            TextView textView5 = findViewById(R.id.other_SearchValue);

            textView5.setText(getIntent().getExtras().getString("SEARCHVALUE"));

            String str2=extras.getString("RESULTS");

            TextView textView2 = findViewById(R.id.other_SearchResults);

            textView2.setText(getIntent().getExtras().getString("RESULTS"));


        }
    }
}
