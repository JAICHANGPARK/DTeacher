package com.dreamwalker.knu2018.dteacher.Utils;

/**
 * Created by 2E313JCP on 2017-10-24.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {


    private static final String URL = "http://kangwonelec.com/diabetes_app/UserLogin.php";
    private Map<String, String> param;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        param = new HashMap<>();
        param.put("userID", userID);
        param.put("userPassword", userPassword);
    }

    @Override
    public Map<String, String> getParams() {
        return param;
    }
}