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
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    ImageButton share;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5;

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

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.social_floating_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.floating_kakaotalk);//카카오 앱 공유
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.floating_kakaotalkweb);//카카오 웹 공유
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.floating_facebook);//페북 페이지
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.floating_instagram);//인스타그램 연결
        floatingActionButton5 = (FloatingActionButton) findViewById(R.id.floating_twitter);//트위터 연결
/*
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent shareKakao = getOpenFacebookIntent(MainActivity.this);
                startActivity(shareKakao);
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Intent kakaoIntent = getOpenTwitterIntent(MainActivity.this);
                startActivity(kakaoIntent);
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent facebookIntent = getOpenFacebookIntent(MainActivity.this);
                startActivity(facebookIntent);
            }
        });

        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent instagramIntent = getOpenInstagramIntent(MainActivity.this);
                startActivity(instagramIntent);
            }
        });

        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent twitterIntent = getOpenTwitterIntent(MainActivity.this);
                startActivity(twitterIntent);
            }
        });*/

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
    /*
    public static Intent getOpenKakaoIntent(Context context) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.kakao.talk", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://2017sports.chungbuk.go.kr/www/index.do")); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                  //  Uri.parse("https://www.facebook.com/karthikofficialpage")); //catches and opens a url to the desired page
            Uri.parse("http://2017sports.chungbuk.go.kr/www/index.do"));//전국체전 홈페이지로 이동하기
        }

    }
    public static Intent getOpenKakaoWebIntent(Context context) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.cb", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://2017sports.chungbuk.go.kr/www/index.do")); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    //  Uri.parse("https://www.facebook.com/karthikofficialpage")); //catches and opens a url to the desired page
                    Uri.parse("http://2017sports.chungbuk.go.kr/www/index.do"));//전국체전 홈페이지로 이동하기
        }
    }
    public static Intent getOpenFacebookIntent(Context context) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.cb", 0); //Checks if Twitter is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/cb21sports/")); //Trys to make intent with Twitter's's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/cb21sports/")); //catches and opens a url to the desired page
        }
    }
    public static Intent getOpenInstagramIntent(Context context) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.instagram.android", 0); //Checks if Instagram is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/accounts/login/")); //Trys to make intent with Instagram's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/accounts/login/")); //catches and opens a url to the desired page
        }
    }

    public static Intent getOpenTwitterIntent(Context context) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.twitter.android", 0); //Checks if YT is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/")); //Trys to make intent with YT's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/")); //catches and opens a url to the desired page
        }
    }
*/
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
            kakaoBuilder.addAppButton("모두의 체전 앱 실행하기");//구글 플레이 스토어에 앱을 등록해야 한다!!!

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
    public void shareInstagram(){
        ShareLinkContent content = new ShareLinkContent.Builder()
                //공유될 링크
                .setContentUrl(Uri.parse("https://www.instagram.com/"))
                .build();
        ShareDialog shareDialog = new ShareDialog(this);

        shareDialog.show(content, ShareDialog.Mode.FEED); //AUTOMATIC, FEED, NATIVE, WEB 등의 다이얼로그 형식이 있다.
    }
    public void shareTwitter(){
        String strLink = null;
        try{
            strLink = String.format("http://twitter.com/intent/tweet?text=%s",
                    URLEncoder.encode("전국 체전에 대해 공유할 텍스트를 입력하세요","utf-8"));
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
    public void onButton15Clicked(View V) { //콜센터로 이동
        Intent intent = new Intent(this, ServiceCall.class);
        startActivity(intent);
    }
    public void onkakaoClicked(View V) { //카카오로 모두의 체전 앱 공유
         shareKakao();
    }
    public void onkakaowebClicked(View V) { //카카오로 전국체전 사이트 공유
        shareKakaoWeb();
    }
    public void onfacebookClicked(View V) { //전국체전 페이스북 사이트 이동
        shareFacebook();
    }
    public void oninstagramClicked(View V) { //인스타그램으로 이동
        shareInstagram();
    }
    public void ontwitterClicked(View V) { //트위터 글올리기로 이동
        shareTwitter();
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
        } else if (id == R.id.news) {
            Toast.makeText(MainActivity.this,"dddd",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.introduce) {
            new TabActivity();
        } else if (id == R.id.game_date) {
            Toast.makeText(MainActivity.this,"dddd",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.game_event) {
            Toast.makeText(MainActivity.this,"dddd",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}