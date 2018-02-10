package com.dreamwalker.knu2018.dteacher.Activity

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

import com.dreamwalker.knu2018.dteacher.Const.IntentConst
import com.dreamwalker.knu2018.dteacher.R

import butterknife.BindView
import butterknife.ButterKnife
import dmax.dialog.SpotsDialog

class WebActivity : AppCompatActivity() {
    @BindView(R.id.web_view)
    internal var webView: WebView? = null
    internal var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        ButterKnife.bind(this)

        alertDialog = SpotsDialog(this)
        alertDialog.show()

        webView!!.settings.javaScriptEnabled = true
        webView!!.webChromeClient = WebChromeClient()

        webView!!.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                alertDialog.dismiss()
            }
        }

        if (intent != null) {
            if (!intent.getStringExtra(IntentConst.WEB_URL).isEmpty()) {
                webView!!.loadUrl(intent.getStringExtra(IntentConst.WEB_URL))
            }
        }

    }
}
