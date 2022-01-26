package com.ior.cmreclh.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhishek Jha on 09.02.2018.
 */

public class VolleyChangeRegulationApi extends StringRequest {

    private static final String CHANGE_REGULATION_URL = "http://step2success.co.in/cmrec_abhishek/api/changeRegulation.php";
    private Map<String, String> parameters;
    public VolleyChangeRegulationApi(String RollNo, String Regulation, String Year, String Sem, Response.Listener<String> listener) {
        super(Request.Method.POST, CHANGE_REGULATION_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Regulation", Regulation);
        parameters.put("Year", Year);
        parameters.put("RollNo", RollNo);
        parameters.put("Sem", Sem);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}
