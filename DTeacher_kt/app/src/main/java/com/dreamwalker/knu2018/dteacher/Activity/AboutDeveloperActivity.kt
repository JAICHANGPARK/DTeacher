package com.dreamwalker.knu2018.dteacher.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

import com.dreamwalker.knu2018.dteacher.Const.IntentConst
import com.dreamwalker.knu2018.dteacher.R

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import info.hoang8f.widget.FButton

class AboutDeveloperActivity : AppCompatActivity() {

    @BindView(R.id.GithubButton)
    internal var githubButton: FButton? = null

    @BindView(R.id.qiitaButton)
    internal var qiitaButton: FButton? = null

    @BindView(R.id.instagramButton)
    internal var instagramButton: FButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_developer)

        title = "Yes! Its Me Dreamwalker"
        ButterKnife.bind(this)
    }

    @OnClick(R.id.GithubButton)
    internal fun onGithubClicked(view: View) {
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra(IntentConst.WEB_URL, "https://github.com/JAICHANGPARK")
        startActivity(intent)
    }

    @OnClick(R.id.qiitaButton)
    internal fun onQittaClicked(view: View) {
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra(IntentConst.WEB_URL, "https://qiita.com/Dreamwalker")
        startActivity(intent)
    }

    @OnClick(R.id.instagramButton)
    internal fun onInstaClicked(view: View) {
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra(IntentConst.WEB_URL, "https://www.instagram.com/itsmyowndreamwalker/")
        startActivity(intent)
    }
}
