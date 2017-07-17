//package com.example.mh.cb_sports_festival;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import com.kakao.kakaolink.KakaoLink;
//import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
//import com.kakao.util.KakaoParameterException;
//
//public class PopUpMenu extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.popup_menu, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.kakao:
//                Toast.makeText(this, "카카오톡!!",Toast.LENGTH_SHORT).show();
//                shareKakao();
//                return true;
//            case R.id.facebook:
//                Toast.makeText(this, "페이스북!!",Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.instagram:
//                Toast.makeText(this, "인스타그램!!",Toast.LENGTH_SHORT).show();
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//    public void shareKakao(){
//        try {
//            final KakaoLink kakaoLink = KakaoLink.getKakaoLink(this);
//            final KakaoTalkLinkMessageBuilder kakaoBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
//
//            /*메시지 추가*/
//            kakaoBuilder.addText("카카오링크 테스트");
//
//            /*이미지 가로/세로 사이즈는 80px 보다 커야하며, 이미지 용량은 500kb 이하로 제한된다.*/
//            String url = "http://2017sports.chungbuk.go.kr/site/www/images/contents/img_poster2.jpg";
//            kakaoBuilder.addImage(url, 1080, 1920);
//
//            /*앱 실행버튼 추가*/
//            kakaoBuilder.addAppButton("앱 실행");
//
//            /*메시지 발송*/
//            kakaoLink.sendMessage(kakaoBuilder, this);
//        } catch (KakaoParameterException e){
//            e.printStackTrace();
//        }
//    }
//}
