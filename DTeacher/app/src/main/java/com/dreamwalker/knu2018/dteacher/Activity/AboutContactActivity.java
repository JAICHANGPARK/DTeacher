package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dreamwalker.knu2018.dteacher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutContactActivity extends AppCompatActivity {

    @BindView(R.id.buttonContact0)
    Button buttonContact0;
    @BindView(R.id.buttonContact1)
    Button buttonContact1;
    @BindView(R.id.buttonContact2)
    Button buttonContact2;
    @BindView(R.id.buttonContact3)
    Button buttonContact3;
    @BindView(R.id.buttonService0)
    Button buttonService0;
    @BindView(R.id.buttonService1)
    Button buttonService1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("문의 및 도움");
        setContentView(R.layout.activity_about_contact);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.buttonContact0)
    public void onButtonContact0licked(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String[] address = {"itsmejeffrey.dev@gmail.com"};    //이메일 주소 입력
        emailIntent.putExtra(Intent.EXTRA_EMAIL, address);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[개선사항건의]당선생 관련 개선사항 건의");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "개선사항을 건의합니다. 하단에 문제의 내용을 추가해주세요 ");
        startActivity(emailIntent);
    }

    @OnClick(R.id.buttonContact1)
    public void onButtonContact1licked(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String[] address = {"itsmejeffrey.dev@gmail.com"};    //이메일 주소 입력
        emailIntent.putExtra(Intent.EXTRA_EMAIL, address);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[이용불편건의]당선생 이용관련 불편 건의");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "이용불편건의 합니다. 하단에 문제의 내용을 추가해주세요 ");
        startActivity(emailIntent);
    }

    @OnClick(R.id.buttonContact2)
    public void onButtonContact2licked(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String[] address = {"itsmejeffrey.dev@gmail.com"};    //이메일 주소 입력
        emailIntent.putExtra(Intent.EXTRA_EMAIL, address);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[버그 및 이상 신고]당선생 관련 개선사항 건의");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "버그 및 이상 신고 건의합니다. 하단에 문제의 내용을 추가해주세요 ");
        startActivity(emailIntent);
    }

    @OnClick(R.id.buttonContact3)
    public void onButtonContact3licked(View v) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String[] address = {"itsmejeffrey.dev@gmail.com"};    //이메일 주소 입력
        emailIntent.putExtra(Intent.EXTRA_EMAIL, address);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[기타문의]당선생 관련 개선사항 건의");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "기타문의합니다.. 하단에 문제의 내용을 추가해주세요 ");
        startActivity(emailIntent);
    }
}
