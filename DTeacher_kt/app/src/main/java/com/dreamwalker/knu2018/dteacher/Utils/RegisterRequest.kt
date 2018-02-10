package com.dreamwalker.knu2018.dteacher.Utils

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

import java.util.HashMap

/**
 * Created by 2E313JCP on 2017-10-24.
 */

class RegisterRequest : StringRequest {
    private var param: MutableMap<String, String>? = null

    constructor(userID: String, userPassword: String, userEmail: String, userProcess: String, listener: Response.Listener<String>) : super(Request.Method.POST, URL, listener, null) {
        param = HashMap()
        param!!["userID"] = userID
        param!!["userPassword"] = userPassword
        param!!["userEmail"] = userEmail
        param!!["userProcess"] = userProcess
    }

    constructor(userID: String, userPassword: String, userEmail: String,
                userName: String, userGender: String, userPhone: String, userBirth: String, userAge: String,
                userHeight: String, userWeight: String,
                userType: String, userOccurDate: String, userMax: String, userMin: String, userDanger: String,
                userDrug: String,
                userProcess: String, listener: Response.Listener<String>) : super(Request.Method.POST, URL, listener, null) {
        param = HashMap()
        param!!["userID"] = userID
        param!!["userPassword"] = userPassword
        param!!["userEmail"] = userEmail

        param!!["userName"] = userName
        param!!["userGender"] = userGender
        param!!["userPhone"] = userPhone
        param!!["userBirth"] = userBirth
        param!!["userAge"] = userAge
        param!!["userHeight"] = userHeight
        param!!["userWeight"] = userWeight

        param!!["userType"] = userType
        param!!["userOccurDate"] = userOccurDate
        param!!["userMax"] = userMax
        param!!["userMin"] = userMin
        param!!["userDanger"] = userDanger
        param!!["userDrug"] = userDrug

        param!!["userProcess"] = userProcess
    }

    public override fun getParams(): Map<String, String>? {
        return param
    }

    companion object {

        private val URL = "http://kangwonelec.com/diabetes_app/UserRegister.php"
    }
}
