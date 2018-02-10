package com.dreamwalker.knu2018.dteacher.Utils

/**
 * Created by 2E313JCP on 2017-10-24.
 */

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

import java.util.HashMap

class LoginRequest(userID: String, userPassword: String, listener: Response.Listener<String>) : StringRequest(Request.Method.POST, URL, listener, null) {
    private val param: MutableMap<String, String>

    init {

        param = HashMap()
        param["userID"] = userID
        param["userPassword"] = userPassword
    }

    public override fun getParams(): Map<String, String> {
        return param
    }

    companion object {


        private val URL = "http://kangwonelec.com/diabetes_app/UserLogin.php"
    }
}