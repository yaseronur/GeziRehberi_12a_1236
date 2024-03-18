package com.example.gezirehberi_12a_1236;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gezirehberi_12a_1236.databinding.ActivityLogoBinding;

public class LogoActivity extends AppCompatActivity {
    private ActivityLogoBinding activityLogoBinding;
    ZamanSayaci timer;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogoBinding = ActivityLogoBinding.inflate(getLayoutInflater());
        View view = activityLogoBinding.getRoot();
        setContentView(view);
        timer = new ZamanSayaci(5000,1000);
        i=0;
        timer.start();
    }
    class ZamanSayaci extends CountDownTimer{
        public ZamanSayaci(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long l) {
            i++;
        }
        @Override
        public void onFinish() {
            Intent intent = new Intent(LogoActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

}