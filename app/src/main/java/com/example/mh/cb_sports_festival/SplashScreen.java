package com.example.mh.cb_sports_festival;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
       // setContentView(R.layout.main_imagexml);




        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                //.withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(1000)
                .withBackgroundColor(Color.parseColor("#ffffffff")) //스플래시 배경 색

                //.withHeaderText("모두의 체전에 오신 것을 환영합니다.")
                //.withBeforeLogoText("심명훈")
                .withLogo(R.mipmap.cb);
                //.withAfterLogoText("제98회 전국체육대회")
               // .withFooterText("Copyright 2017");
        //색 지정
       // config.getHeaderTextView().setTextColor(android.graphics.Color.WHITE);
      //  config.getFooterTextView().setTextColor(android.graphics.Color.WHITE);
      //  config.getAfterLogoTextView().setTextColor(android.graphics.Color.WHITE);
        //config.getBeforeLogoTextView().setTextColor(android.graphics.Color.WHITE);

        //보여주기
        View view = config.create();
        //view를 포함
        setContentView(view);
    }
}
