package com.dreamwalker.knu2018.dteacher.SignUpActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.dreamwalker.knu2018.dteacher.Const.IntentConst
import com.dreamwalker.knu2018.dteacher.R
import com.dreamwalker.knu2018.dteacher.Utils.RegisterRequest

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

import cn.refactor.lib.colordialog.PromptDialog

import com.dreamwalker.knu2018.dteacher.Const.IntentConst.SIGNUP_EXTRA_DATA_0

class SignUpDoneActivity : AppCompatActivity() {
    internal var promptDialog: PromptDialog? = null

    internal var userID: String
    internal var userPassword: String
    internal var userEmail: String
    internal var userName: String
    internal var userGender: String
    internal var userPhone: String
    internal var userBirth: String
    internal var userAge: String
    internal var userHeight: String
    internal var userWeight: String
    internal var userType: String
    internal var userOccurDate: String
    internal var userMax: String
    internal var userMin: String
    internal var userDanger: String
    internal var userDrug: String
    internal var userRegiDataList: ArrayList<String>
    internal var registerType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_done)

        promptDialog = PromptDialog(this@SignUpDoneActivity)

        userRegiDataList = ArrayList()
        registerType = intent.getIntExtra(IntentConst.SIGNUP_REGISTER_TYPE, -1)

        if (registerType == 0) {
            Log.e(TAG, "get 0 : ")
            userRegiDataList = intent.getStringArrayListExtra(INSTANCE.getSIGNUP_EXTRA_DATA_0())
            userID = userRegiDataList[0]
            userPassword = userRegiDataList[1]
            userEmail = userRegiDataList[2]

            for (i in userRegiDataList.indices) {
                Log.e(TAG, "userRegiDataList: " + i + " : " + userRegiDataList[i])
            }

            val responseListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")

                    Log.e(TAG, "jsonResult: " + jsonObject)
                    Log.e(TAG, "sucesse: " + success)

                    if (success) {

                        //new PromptDialog(SignUpDoneActivity.this)
                        promptDialog!!.setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                                .setAnimationEnable(true)
                                .setTitleText("성공")
                                .setContentText("회원 등록에 성공했습니다.")
                                .setPositiveListener("응") { dialog ->
                                    dialog.dismiss()
                                    finish() // 회원가입 성공후 창 닫음.
                                }.show()

                        //                            new PanterDialog(SignUpDoneActivity.this)
                        //                                    .setHeaderBackground(R.drawable.pattern_bg_yellow)
                        //                                    .setTitle("성공")
                        //                                    .setPositive("계속할래요", new View.OnClickListener() {
                        //                                        @Override
                        //                                        public void onClick(View v) {
                        //                                            finish();
                        //                                        }
                        //                                    }) // You can pass also View.OnClickListener as second param
                        //                                    .setMessage("회원가입에 성공했습니다. 환영합니다")
                        //                                    .isCancelable(false)
                        //                                    .show();
                    } else {

                        PromptDialog(this@SignUpDoneActivity)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                                .setAnimationEnable(true)
                                .setTitleText("실패")
                                .setContentText("회원 등록에 실패했습니다.")
                                .setPositiveListener("응") { dialog -> dialog.dismiss() }.show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            val registerRequest = RegisterRequest(userID, userPassword, userEmail, "0", responseListener)
            val queue = Volley.newRequestQueue(this@SignUpDoneActivity)
            queue.add(registerRequest)

        } else if (registerType == 1) {
            Log.e(TAG, "get 1 : ")
            userRegiDataList = intent.getStringArrayListExtra(IntentConst.SIGNUP_EXTRA_DATA_6)
            userID = userRegiDataList[0]
            userPassword = userRegiDataList[1]
            userEmail = userRegiDataList[2]

            userName = userRegiDataList[3]
            userGender = userRegiDataList[4]
            userPhone = userRegiDataList[5]
            userBirth = userRegiDataList[6]
            userAge = userRegiDataList[7]
            userHeight = userRegiDataList[8]
            userWeight = userRegiDataList[9]

            userType = userRegiDataList[10]
            userOccurDate = userRegiDataList[11]
            userMax = userRegiDataList[12]
            userMin = userRegiDataList[13]
            userDanger = userRegiDataList[14]
            userDrug = userRegiDataList[15]

            for (i in userRegiDataList.indices) {
                Log.e(TAG, "userRegiDataList: " + i + " : " + userRegiDataList[i])
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (promptDialog != null) {
            promptDialog!!.dismiss()
            promptDialog = null
        }
    }

    companion object {

        private val TAG = "SignUpDoneActivity"
    }
}
