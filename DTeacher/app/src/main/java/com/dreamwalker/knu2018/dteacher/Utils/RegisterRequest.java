package com.dreamwalker.knu2018.dteacher.Utils;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 2E313JCP on 2017-10-24.
 */

public class RegisterRequest extends StringRequest {

    private static final String URL = "http://kangwonelec.com/diabetes_app/UserRegister.php";
    private Map<String, String> param;

    public RegisterRequest(String userID, String userPassword, String userEmail, String userProcess, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        param = new HashMap<>();
        param.put("userID", userID);
        param.put("userPassword", userPassword);
        param.put("userEmail", userEmail);
        param.put("userProcess", userProcess);
    }

    public RegisterRequest(String userID, String userPassword, String userEmail,
                           String userName, String userGender, String userPhone, String userBirth, String userAge,
                           String userHeight, String userWeight,
                           String userType, String userOccurDate, String userMax, String userMin, String userDanger,
                           String userDrug,
                           String userProcess, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        param = new HashMap<>();
        param.put("userID", userID);
        param.put("userPassword", userPassword);
        param.put("userEmail", userEmail);

        param.put("userName", userName);
        param.put("userGender", userGender);
        param.put("userPhone", userPhone);
        param.put("userBirth", userBirth);
        param.put("userAge", userAge);
        param.put("userHeight", userHeight);
        param.put("userWeight", userWeight);

        param.put("userType", userType);
        param.put("userOccurDate", userOccurDate);
        param.put("userMax", userMax);
        param.put("userMin", userMin);
        param.put("userDanger", userDanger);
        param.put("userDrug", userDrug);

        param.put("userProcess", userProcess);
    }

    @Override
    public Map<String, String> getParams() {
        return param;
    }
}
