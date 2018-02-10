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

class SettingActivity : AppCompatActivity() {

    @BindView(R.id.buttonDeveloper)
    internal var buttonDeveloper: Button? = null
    @BindView(R.id.buttonVersion)
    internal var buttonVersion: Button? = null
    @BindView(R.id.buttonOpenSourceLicense)
    internal var buttonOpenSourceLicense: Button? = null
    @BindView(R.id.buttonUserPrivacy)
    internal var buttonUserPrivacy: Button? = null
    @BindView(R.id.buttonWearable)
    internal var buttonWearable: Button? = null
    @BindView(R.id.buttonDiabetesDevice)
    internal var buttonDiabetesDevice: Button? = null
    @BindView(R.id.buttonMachine)
    internal var buttonMachine: Button? = null
    @BindView(R.id.buttonContact)
    internal var buttonContact: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "SETTING"
        setContentView(R.layout.activity_setting)
        ButterKnife.bind(this)

    }

    @OnClick(R.id.buttonDeveloper)
    fun onButtonDeveloperClicked(v: View) {
        startActivity(Intent(this@SettingActivity, AboutDeveloperActivity::class.java))
        Toast.makeText(this, "buttonDeveloper clicked", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.buttonVersion)
    fun onButtonVersionClicked(v: View) {
        startActivity(Intent(this@SettingActivity, AboutVersionInfoActivity::class.java))
        Toast.makeText(this, "Version clicked", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.buttonOpenSourceLicense)
    fun onButtonOSLClicked(v: View) {
        startActivity(Intent(this@SettingActivity, AboutOSLActivity::class.java))
        Toast.makeText(this, "OSL clicked", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.buttonUserPrivacy)
    fun onButtonUserPrivacyClicked(v: View) {
        startActivity(Intent(this@SettingActivity, AboutUserActivity::class.java))
        Toast.makeText(this, "buttonUserPrivacy clicked", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.buttonWearable)
    fun onButtonWearableClicked(v: View) {
        startActivity(Intent(this@SettingActivity, SyncWearableActivity::class.java))
        Toast.makeText(this, "onButtonWearableClicked clicked", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.buttonDiabetesDevice)
    fun onButtonDiabetesDeviceClicked(v: View) {
        startActivity(Intent(this@SettingActivity, SyncBSMActivity::class.java))
        Toast.makeText(this, "buttonDiabetesDevice clicked", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.buttonMachine)
    fun onButtonMachineClicked(v: View) {
        startActivity(Intent(this@SettingActivity, SyncFEActivity::class.java))
        Toast.makeText(this, "buttonMachine clicked", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.buttonContact)
    fun onButtonContactClicked(v: View) {
        startActivity(Intent(this@SettingActivity, AboutContactActivity::class.java))
        Toast.makeText(this, "buttonContact clicked", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val TAG = "SettingActivity"
    }

}
