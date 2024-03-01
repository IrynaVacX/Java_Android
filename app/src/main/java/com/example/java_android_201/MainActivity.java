package com.example.java_android_201;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private boolean isFlashlightOn = false;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        } else {
            toggleFlashlight();
            toggleMusic();
        }
    }

    private void toggleFlashlight()
    {
        try {
            android.hardware.camera2.CameraManager cameraManager = (android.hardware.camera2.CameraManager) getSystemService(CAMERA_SERVICE);
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, !isFlashlightOn);
            isFlashlightOn = !isFlashlightOn;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleMusic() {
        if(mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        else
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.sample_music);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
