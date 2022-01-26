package com.ior.cmreclh.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;

/**
 * Created by Abhishek Jha on 09.02.2018.
 */

public class VolleyTPOCompanyApi extends StringRequest {

    private static final String TPO_COMPANY_URL = "http://step2success.co.in/cmrec_abhishek/api/getCompanyList.php";
    private Map<String, String> parameters;
    public VolleyTPOCompanyApi(Response.Listener<String> listener) {
        super(Request.Method.POST, TPO_COMPANY_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("null", "null");
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}
