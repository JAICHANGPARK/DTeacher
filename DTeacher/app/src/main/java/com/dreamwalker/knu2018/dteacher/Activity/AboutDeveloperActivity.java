package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dreamwalker.knu2018.dteacher.Const.IntentConst;
import com.dreamwalker.knu2018.dteacher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;

public class AboutDeveloperActivity extends AppCompatActivity {

    @BindView(R.id.GithubButton)
    FButton githubButton;

    @BindView(R.id.qiitaButton)
    FButton qiitaButton;

    @BindView(R.id.instagramButton)
    FButton instagramButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developer);

        setTitle("Yes! Its Me Dreamwalker");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.GithubButton)
    void onGithubClicked(View view) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(IntentConst.WEB_URL, "https://github.com/JAICHANGPARK");
        startActivity(intent);
    }

    @OnClick(R.id.qiitaButton)
    void onQittaClicked(View view) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(IntentConst.WEB_URL, "https://qiita.com/Dreamwalker");
        startActivity(intent);
    }

    @OnClick(R.id.instagramButton)
    void onInstaClicked(View view) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(IntentConst.WEB_URL, "https://www.instagram.com/itsmyowndreamwalker/");
        startActivity(intent);
    }
}
