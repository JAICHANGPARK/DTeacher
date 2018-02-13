package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;


import com.dreamwalker.knu2018.dteacher.Fragment.IntroSlideFragment;
import com.dreamwalker.knu2018.dteacher.R;
import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by 2E313JCP on 2017-10-18.
 */

public class IntroActivity extends AppIntro {

    private static final String TAG = "IntroActivity";
    private AlertDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // TODO: 2017-11-23 원래는 없었는데 초기 설치시 서버 포트 주소 입력이 없으면 팅기는 현상이 있어 추가하게 되었다.
//        SharedPreferences sharedPreferences = getSharedPreferences("SettingInit", Context.MODE_PRIVATE);
//        address = sharedPreferences.getString("addressInit", "");
//        port = sharedPreferences.getString("portInit", "");
//        Log.e(TAG, "addressInit: " + address);
//        Log.e(TAG, "portInit: " + port);

//        SharedPreferences pref = getSharedPreferences("ActivityIntro", Context.MODE_PRIVATE);
//        //만약 키값에서 얻어온 값이 false이면 액티비티를 실행하고
//        if (pref.getBoolean("activity_executed", false)) {
//            Intent intent = new Intent(this, DiaryActivity.class);
//            startActivity(intent);
//            Log.e(TAG, "onCreate: " + pref.getBoolean("activity_executed", false));
//            finish();
//        } else {
//            SharedPreferences.Editor ed = pref.edit();
//            ed.putBoolean("activity_executed", true);
//            ed.commit();
//        }

        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide_01)); // 당뇨 기록
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide_02)); // 운동 기록
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide_03)); // 식사 기록
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide_04)); // 투약 기록
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide_05)); // 장비 동기화
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide_06)); // 설명 완료
        setFadeAnimation();
        //addSlide(IntroFragment.newInstance(R.layout.intro_slide_03));
        //addSlide(Intro2Fragment.newInstance(R.layout.intro_slide_04)); // 와이파이 셋팅
        //askForPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        startActivity(new Intent(IntroActivity.this, HomeActivity.class));
        finish();

//        if (address.equals("") && port.equals("")) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(IntroActivity.this);
//            dialog = builder.setMessage("서버 주소와 포트 주소를 입력해주세요")
//                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(IntroActivity.this, SettingInitActivity.class);
//                            startActivity(intent);
//                        }
//                    }).create();
//            dialog.show();
//        } else {
//            startActivity(new Intent(IntroActivity.this, DiaryActivity.class));
//            finish();
//        }
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        startActivity(new Intent(IntroActivity.this, HomeActivity.class));
        finish();
//        if (address.equals("") && port.equals("")) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(IntroActivity.this);
//            dialog = builder.setMessage("서버 주소와 포트 주소를 입력해주세요").setNegativeButton("확인", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent(IntroActivity.this, SettingInitActivity.class);
//                    startActivity(intent);
//                }
//            }).create();
//            dialog.show();
//        } else {
//            startActivity(new Intent(IntroActivity.this, DiaryActivity.class));
//            finish();
//        }
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
