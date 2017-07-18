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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    ImageButton share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeand_navigation);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        //페이스북 API 사용
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        share = (ImageButton) findViewById (R.id.share);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 클릭시 팝업 메뉴가 나오게 하기
                // PopupMenu 는 API 11 레벨부터 제공한다
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, share);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.kakao:
                                Toast.makeText(MainActivity.this, "카카오톡으로 모두의 체전 알리기",Toast.LENGTH_SHORT).show();
                                shareKakao();
                                return true;
                            case R.id.kakaoweb:
                                Toast.makeText(MainActivity.this, "전국체전 홈페이지 알리기",Toast.LENGTH_SHORT).show();
                                shareKakaoWeb();
                                return true;
                            case R.id.facebook:
                                Toast.makeText(MainActivity.this, "페이스북에 글 올리기",Toast.LENGTH_SHORT).show();
                                shareFacebook();
                                return true;
                            case R.id.instagram:
                                Toast.makeText(MainActivity.this, "인스타그램에 글 올리기",Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.twitter:
                                Toast.makeText(MainActivity.this, "트위터에 글 올리기",Toast.LENGTH_SHORT).show();
                                shareTwitter();
                                return true;
                        }

                        return true;
                    }

                });
                popupMenu.show(); // 메뉴를 띄우기
            }
        });

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

    public void shareKakao(){
        try {
            final KakaoLink kakaoLink = KakaoLink.getKakaoLink(this);
            final KakaoTalkLinkMessageBuilder kakaoBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();

            /*메시지 추가*/
            kakaoBuilder.addText("제98회 전국 체육대회");

            /*이미지 가로/세로 사이즈는 80px 보다 커야하며, 이미지 용량은 500kb 이하로 제한된다.*/
            String url = "http://2017sports.chungbuk.go.kr/site/www/images/contents/img_poster2.jpg";
            kakaoBuilder.addImage(url, 1080, 1920);

            /*앱 실행버튼 추가*/
            kakaoBuilder.addAppButton("모두의 체전 앱 실행하기");

            /*메시지 발송*/
            kakaoLink.sendMessage(kakaoBuilder, this);
        } catch (KakaoParameterException e){
            e.printStackTrace();
        }
    }

    public void shareKakaoWeb(){
        try {
            final KakaoLink kakaoLink2 = KakaoLink.getKakaoLink(this);
            final KakaoTalkLinkMessageBuilder kakaoBuilder2 = kakaoLink2.createKakaoTalkLinkMessageBuilder();

            /*메시지 추가*/
            kakaoBuilder2.addText("제98회 전국 체육대회");

            /*이미지 가로/세로 사이즈는 80px 보다 커야하며, 이미지 용량은 500kb 이하로 제한된다.*/
            String url = "http://2017sports.chungbuk.go.kr/site/www/images/contents/img_poster2.jpg";
            kakaoBuilder2.addImage(url, 1080, 1920);

            /*웹 실행버튼 추가*/
            kakaoBuilder2.addWebButton("http://2017sports.chungbuk.go.kr/www/index.do");

            /*메시지 발송*/
            kakaoLink2.sendMessage(kakaoBuilder2, this);
        } catch (KakaoParameterException e){
            e.printStackTrace();
        }
    }

    public void shareFacebook(){
        ShareLinkContent content = new ShareLinkContent.Builder()
                //공유될 링크
                .setContentUrl(Uri.parse("https://www.facebook.com/cb21sports/"))
                .build();
        ShareDialog shareDialog = new ShareDialog(this);

        shareDialog.show(content, ShareDialog.Mode.FEED); //AUTOMATIC, FEED, NATIVE, WEB 등의 다이얼로그 형식이 있다.
    }

    public void shareTwitter(){
        String strLink = null;
        try{
            strLink = String.format("http://twitter.com/intent/tweet?text=%s",
                    URLEncoder.encode("공유할 텍스트를 입력하세요","utf-8"));
        } catch(UnsupportedEncodingException e1){
            e1.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strLink));
        startActivity(intent);
    }

    public void onButton1Clicked(View V){ //대회 소개 페이지로 넘어감
        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);
    }
    public void onButton5Clicked(View V) { //맛집 숙박 사이트(충북 나드리)로 넘어감
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://tour.chungbuk.go.kr/home/sub.php?menukey=225"));
        startActivity(intent);
    }
    public void onButtonHomeClicked(View V) { //홈으로 이동
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
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_menu, menu);

        return true;
    }
*/
}
