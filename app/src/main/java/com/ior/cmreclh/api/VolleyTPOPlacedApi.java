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

public class VolleyTPOPlacedApi extends StringRequest {

    private static final String TPO_PLACED_URL = "http://step2success.co.in/cmrec_abhishek/api/getPlacementList.php";
    private Map<String, String> parameters;
    public VolleyTPOPlacedApi(Response.Listener<String> listener) {
        super(Request.Method.POST, TPO_PLACED_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("null", "null");
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}
