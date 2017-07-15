package com.example.mh.cb_sports_festival;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeand_navigation);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabHost host = (TabHost)findViewById(R.id.tabHost);  //탭바의 탭 호스트 정의
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("공지사항");
        spec.setContent(R.id.tab1);
        spec.setIndicator("공지사항");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("대회뉴스");
        spec.setContent(R.id.tab2);
        spec.setIndicator("대회뉴스");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("+");
        spec.setContent(R.id.tab3);
        spec.setIndicator("+");
        host.addTab(spec);
    }



    public void onButton1Clicked(View V){ //대회 소개 페이지로 넘어감
        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);
    }
    public void onButton5Clicked(View V) { //맛집 숙박 사이트(충북 나드리)로 넘어감
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://tour.chungbuk.go.kr/home/sub.php?menukey=225"));
        startActivity(intent);
    }
    public void onButtonHomeClicked(View V) { //맛집 숙박 사이트(충북 나드리)로 넘어감
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //햄버거메뉴
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.notice) {
            // Handle the camera action
        } else if (id == R.id.news) {

        } else if (id == R.id.introduce) {

        } else if (id == R.id.game_date) {

        } else if (id == R.id.game_event) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}
