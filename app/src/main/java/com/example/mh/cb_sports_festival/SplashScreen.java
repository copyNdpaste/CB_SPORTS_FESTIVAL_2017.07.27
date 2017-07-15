package com.example.mh.cb_sports_festival;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#074E72"))
                .withLogo(R.mipmap.ic_launcher)
                .withHeaderText("모두의 체전에 오신 것을 환영합니다.")
                .withFooterText("Copyright 2017")
                .withBeforeLogoText("심명훈")
                .withAfterLogoText("스플래쉬 화면입니다.");
        //색 지정
        config.getHeaderTextView().setTextColor(android.graphics.Color.WHITE);
        config.getFooterTextView().setTextColor(android.graphics.Color.WHITE);
        config.getAfterLogoTextView().setTextColor(android.graphics.Color.WHITE);
        config.getBeforeLogoTextView().setTextColor(android.graphics.Color.WHITE);

        //보여주기
        View view = config.create();
        //view를 포함
        setContentView(view);
    }
}
