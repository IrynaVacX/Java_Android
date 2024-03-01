package com.example.java_android_201;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private int count;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        count = sharedPreferences.getInt("count", 0);
        textView = findViewById(R.id.textView);
        textView.setText("Четные входы : " + (count / 2));

    }

    @Override
    protected void onResume() {
        super.onResume();
        count++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("count", count);
        editor.apply();
        if (count % 2 != 0)
        {
            textView.setText("Четные входы : " + (count / 2));
        }
    }
}
