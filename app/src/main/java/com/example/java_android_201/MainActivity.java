package com.example.java_android_201;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.time.Duration;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.button);

        b.setOnClickListener(v -> {

            //#1
            b.setWidth( b.getWidth() + 10 );

            //#2
            try {
                int clicks_count = Integer.parseInt( (String)b.getText() );
                if (clicks_count < 30) {
                    b.setText( Integer.toString(clicks_count + 1) );
                }
                else {
                    b.setText("Докликался.. X_X");
                    b.setEnabled(false);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        });

        //#3
        Button b3 = findViewById(R.id.button3);

        LocalDateTime targetDateTime = LocalDateTime.of(2024, 2, 7, 8, 50);

        b3.setOnClickListener(v -> {

            Duration duration = Duration.between(LocalDateTime.now(), targetDateTime);

            String msg = getFormattedTime(duration.toDays(), duration.toHours() % 24, duration.toMinutes() % 60, duration.getSeconds() % 60);

            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        });

    }

    public static String getFormattedTime(long days, long hours, long minutes, long seconds) {
        StringBuilder result = new StringBuilder();

        if (days == 1) {
            result.append(days).append(" day").append(" ");
        }
        else if (days > 0) {
            result.append(days).append(" days").append(" ");
        }
        if (hours > 0) {
            result.append(hours).append("h").append(" ");
        }
        if (minutes > 0) {
            result.append(minutes).append("m").append(" ");
        }
        if (seconds > 0) {
            result.append(seconds).append("s");
        }

        return result.toString();
    }
}