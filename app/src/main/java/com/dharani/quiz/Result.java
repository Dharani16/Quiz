package com.dharani.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = (TextView)findViewById(R.id.textView_result);


    }
}
