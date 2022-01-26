package com.ior.cmreclh.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;

/**
 * Created by Abhishek Jha on 05.02.2018.
 */

public class VolleyMaterialBriefApi extends StringRequest {

    private static final String MATERIAL_BRIEF_URL = "http://step2success.co.in/cmrec_abhishek/api/getMaterialBrief.php";
    private Map<String, String> parameters;
    public VolleyMaterialBriefApi(String SubjectCode, Response.Listener<String> listener) {
        super(Request.Method.POST, MATERIAL_BRIEF_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("SubjectCode", SubjectCode);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}
