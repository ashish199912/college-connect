package com.ior.cmreclh.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhishek Jha on 02.02.2018.
 */

public class VolleyLoginApi extends StringRequest {

    private static final String LOGIN_URL = "http://step2success.co.in/cmrec_abhishek/api/login.php";
    private Map<String, String> parameters;
    public VolleyLoginApi(String RollNo, String Password, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Password", Password);
        parameters.put("RollNo", RollNo);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}
