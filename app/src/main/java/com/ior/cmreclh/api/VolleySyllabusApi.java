package com.ior.cmreclh.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;

/**
 * Created by Abhishek Jha on 06.02.2018.
 */

public class VolleySyllabusApi extends StringRequest {

    private static final String SYLLABUS_URL = "http://step2success.co.in/cmrec_abhishek/api/getSyllabus.php";
    private Map<String, String> parameters;
    public VolleySyllabusApi(String SyllabusCode, Response.Listener<String> listener) {
        super(Request.Method.POST, SYLLABUS_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("SyllabusCode", SyllabusCode);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
