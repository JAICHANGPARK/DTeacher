package com.dreamwalker.knu2018.dteacher.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.dreamwalker.knu2018.dteacher.R

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

class AboutContactActivity : AppCompatActivity() {

    @BindView(R.id.buttonContact0)
    internal var buttonContact0: Button? = null
    @BindView(R.id.buttonContact1)
    internal var buttonContact1: Button? = null
    @BindView(R.id.buttonContact2)
    internal var buttonContact2: Button? = null
    @BindView(R.id.buttonContact3)
    internal var buttonContact3: Button? = null
    @BindView(R.id.buttonService0)
    internal var buttonService0: Button? = null
    @BindView(R.id.buttonService1)
    internal var buttonService1: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "문의 및 도움"
        setContentView(R.layout.activity_about_contact)
        ButterKnife.bind(this)

    }

    @OnClick(R.id.buttonContact0)
    fun onButtonContact0licked(v: View) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        val address = arrayOf("itsmejeffrey.dev@gmail.com")    //이메일 주소 입력
        emailIntent.putExtra(Intent.EXTRA_EMAIL, address)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[개선사항건의]당선생 관련 개선사항 건의")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "개선사항을 건의합니다. 하단에 문제의 내용을 추가해주세요 ")
        startActivity(emailIntent)
    }

    @OnClick(R.id.buttonContact1)
    fun onButtonContact1licked(v: View) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        val address = arrayOf("itsmejeffrey.dev@gmail.com")    //이메일 주소 입력
        emailIntent.putExtra(Intent.EXTRA_EMAIL, address)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[이용불편건의]당선생 이용관련 불편 건의")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "이용불편건의 합니다. 하단에 문제의 내용을 추가해주세요 ")
        startActivity(emailIntent)
    }

    @OnClick(R.id.buttonContact2)
    fun onButtonContact2licked(v: View) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        val address = arrayOf("itsmejeffrey.dev@gmail.com")    //이메일 주소 입력
        emailIntent.putExtra(Intent.EXTRA_EMAIL, address)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[버그 및 이상 신고]당선생 관련 개선사항 건의")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "버그 및 이상 신고 건의합니다. 하단에 문제의 내용을 추가해주세요 ")
        startActivity(emailIntent)
    }

    @OnClick(R.id.buttonContact3)
    fun onButtonContact3licked(v: View) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        val address = arrayOf("itsmejeffrey.dev@gmail.com")    //이메일 주소 입력
        emailIntent.putExtra(Intent.EXTRA_EMAIL, address)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[기타문의]당선생 관련 개선사항 건의")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "기타문의합니다.. 하단에 문제의 내용을 추가해주세요 ")
        startActivity(emailIntent)
    }
}
