package com.dreamwalker.knu2018.dteacher.Activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamwalker.knu2018.dteacher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;

public class AboutVersionInfoActivity extends AppCompatActivity {

    @BindView(R.id.versionImageView)
    ImageView versionImageView;
    @BindView(R.id.versionText)
    TextView versionText;
    @BindView(R.id.versionButton)
    FButton versionButton;

    String versionNumber;
    PackageInfo i = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_version_info);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.app_icon_hr).into(versionImageView);

        try {
            i = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionNumber = i.versionName;
            versionText.setText(versionNumber);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
