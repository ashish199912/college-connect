package com.ior.cmreclh.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhishek Jha on 06.02.2018.
 */

public class VolleyEditProfileApi extends StringRequest {

    private static final String UPDATE_PROFILE_URL = "http://step2success.co.in/cmrec_abhishek/api/updateProfile.php";
    private Map<String, String> parameters;
    public VolleyEditProfileApi(String RollNo, String Name, String EmailId,String Phone, Response.Listener<String> listener) {
        super(Request.Method.POST, UPDATE_PROFILE_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Name", Name);
        parameters.put("EmailId", EmailId);
        parameters.put("Phone", Phone);
        parameters.put("RollNo", RollNo);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}
