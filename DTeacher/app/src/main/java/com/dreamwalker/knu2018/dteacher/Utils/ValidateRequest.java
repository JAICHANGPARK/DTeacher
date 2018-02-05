package com.dreamwalker.knu2018.dteacher.Utils;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 2E313JCP on 2017-10-24.
 */

public class ValidateRequest extends StringRequest{

    private static final String URL = "http://kangwonelec.com/diabetes_app/UserValidate.php";
    private Map<String, String> param;

    public ValidateRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        param = new HashMap<>();
        param.put("userID", userID);
    }

    @Override
    public Map<String, String> getParams() {
        return param;
    }
}
