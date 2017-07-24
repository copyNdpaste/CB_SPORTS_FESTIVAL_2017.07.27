package com.example.mh.cb_sports_festival;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class ServiceCall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_call);
    }

    public void onButtonHomeClicked(View V) { //홈으로 이동
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void call1Clicked(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:043-220-5211"));//전국체전 추진단 사무실 043-220-5211~4
        startActivity(intent);
    }
    public void call2Clicked(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:043-220-5212"));//전국체전 추진단 사무실 043-220-5211~4
        startActivity(intent);
    }
    public void call3Clicked(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:043-220-5213"));//전국체전 추진단 사무실 043-220-5211~4
        startActivity(intent);
    }
    public void call4Clicked(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:043-220-5214"));//전국체전 추진단 사무실 043-220-5211~4
        startActivity(intent);
    }
    public void call5Clicked(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:043-220-4004"));//충북 나드리
        startActivity(intent);
    }



}

