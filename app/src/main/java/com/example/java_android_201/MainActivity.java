package com.example.java_android_201;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_OPEN_COUNT = "openCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int openCount = getOpenCount();
        if (openCount % 10 == 0) {
            showFeedbackDialog();
        }

        incrementOpenCount();
    }

    private int getOpenCount() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_OPEN_COUNT, 0);
    }

    private void incrementOpenCount() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int openCount = prefs.getInt(KEY_OPEN_COUNT, 0);
        prefs.edit().putInt(KEY_OPEN_COUNT, openCount + 1).apply();
    }

    private void showFeedbackDialog() {
        AlertDialog.Builder ratingdialog = new AlertDialog.Builder(this);
        ratingdialog.setIcon(android.R.drawable.btn_star_big_on);
        ratingdialog.setTitle("Tell us what you think");

        View rb = this.getLayoutInflater().inflate(R.layout.rating_bar, null);
        ratingdialog.setView(rb);

        final RatingBar ratingBar = rb.findViewById(R.id.ratingBar);

        ratingdialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                float rating = ratingBar.getRating();
                if (rating >= 1 && rating <= 3) {
                    showFeedbackResponseDialog();
                } else if (rating >= 4 && rating <= 5) {
                    goToPlayStore();
                }
            }
        });

        ratingdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        ratingdialog.create().show();
    }

    private void showFeedbackResponseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thank you for your feedback");
        builder.setMessage("We will work on improving your experience");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void goToPlayStore() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }
}
