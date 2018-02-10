package com.dreamwalker.knu2018.dteacher.Utils

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

import java.util.HashMap

/**
 * Created by 2E313JCP on 2017-10-24.
 */

class ValidateRequest(userID: String, listener: Response.Listener<String>) : StringRequest(Request.Method.POST, URL, listener, null) {
    private val param: MutableMap<String, String>

    init {
        param = HashMap()
        param["userID"] = userID
    }

    public override fun getParams(): Map<String, String> {
        return param
    }

    companion object {

        private val URL = "http://kangwonelec.com/diabetes_app/UserValidate.php"
    }
}
