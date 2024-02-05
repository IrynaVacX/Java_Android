package com.example.java_android_201;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.button);

//        #1_example
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Button x = (Button)v;
//                x.setText("ᓚᘏᗢ");
//
//                Toast.makeText(MainActivity.this,
//                    "Hello from handler!",
//                    Toast.LENGTH_SHORT).show();
//        }});
//        #2_example
        b.setOnClickListener(v -> {
            b.setText("ᓚᘏᗢ");

            Toast.makeText(MainActivity.this,
                    "Hello from handler!",
                    Toast.LENGTH_SHORT).show();
        });

    }

    public void func(View view) {
        Toast.makeText(this, "Example :3", Toast.LENGTH_SHORT).show();
    }
}