package com.ior.cmreclh.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;

/**
 * Created by Abhishek Jha on 04.02.2018.
 */

public class VolleySubjectsApi extends StringRequest {

    private static final String SUBJECTS_URL = "http://step2success.co.in/cmrec_abhishek/api/getSubjects.php";
    private Map<String, String> parameters;
    public VolleySubjectsApi(String Regulation, int DeptNo, int Year, int Sem, Response.Listener<String> listener) {
        super(Request.Method.POST, SUBJECTS_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Regulation", Regulation);
        parameters.put("DeptNo", valueOf(DeptNo));
        parameters.put("Year", valueOf(Year));
        parameters.put("Sem", valueOf(Sem));
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
