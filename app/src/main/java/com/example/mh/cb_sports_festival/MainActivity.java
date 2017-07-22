package com.example.mh.cb_sports_festival;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int REQUEST_EXTERNAL_STORAGE_CODE = 1;
    boolean permissionCheck = false;

    ViewPager viewPager;

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
        // FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.social_floating_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.floating_kakaotalk);//카카오 앱 공유
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.floating_kakaotalkweb);//카카오 웹 공유
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.floating_facebook);//페북 페이지
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.floating_instagram);//인스타그램 연결
        floatingActionButton5 = (FloatingActionButton) findViewById(R.id.floating_twitter);//트위터 연결

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabHost host = (TabHost) findViewById(R.id.tabHost);  //탭바의 탭 호스트 정의
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


    public void onRequestPermission(){
        int permissionReadStorage = ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionReadStorage == PackageManager.PERMISSION_DENIED ||
                permissionWriteStorage == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_CODE);
        }
        else{
            permissionCheck = true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case REQUEST_EXTERNAL_STORAGE_CODE:
                for(int i=0; i<permissions.length; i++){
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if(permission.equals(android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                        if(grantResult == PackageManager.PERMISSION_GRANTED)
                        {
                            Toast.makeText(this,"허용했으니 가능함", Toast.LENGTH_SHORT).show();
                            permissionCheck = true;
                        }
                        else{
                            Toast.makeText(this, "허용하지 않으면 공유 못함",Toast.LENGTH_SHORT).show();
                            permissionCheck = false;
                        }
                    }
                }
                break;
        }
    }

    public void shareKakao() {
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
        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }
    }

    public void shareKakaoWeb() {
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
        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }
    }

    public void shareFacebook() {
        ShareLinkContent content = new ShareLinkContent.Builder()
                //공유될 링크
                .setContentUrl(Uri.parse("https://www.facebook.com/cb21sports/"))
                .build();
        ShareDialog shareDialog = new ShareDialog(this);

        shareDialog.show(content, ShareDialog.Mode.FEED); //AUTOMATIC, FEED, NATIVE, WEB 등의 다이얼로그 형식이 있다.
    }

    public void shareInstagram() {
        onRequestPermission();
        if (permissionCheck) {
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.sports);
            String storage = Environment.getExternalStorageDirectory().getAbsolutePath();
            String fileName = "이미지명.png";

            String folderName = "/폴더명/";
            String fullPath = storage + folderName;
            File filePath;

            try {
                filePath = new File(fullPath);
                if (!filePath.isDirectory()) {
                    filePath.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(fullPath + fileName);
                bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            Uri uri = Uri.fromFile(new File(fullPath, fileName));
            try {
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.putExtra(Intent.EXTRA_TEXT, "텍스트는 지원하지 않음");
                share.setPackage("com.instagram.android");
                startActivity(share);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "인스타그램이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

        } else if (id == R.id.introduce) {
            new TabActivity();
        } else if (id == R.id.game_date) {

        } else if (id == R.id.game_event) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}