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

public class VolleyModelPaperApi extends StringRequest {

    private static final String MODEL_PAPER_URL = "http://step2success.co.in/cmrec_abhishek/api/getModelPaper.php";
    private Map<String, String> parameters;
    public VolleyModelPaperApi(String SubjectCode, String ModelPaperNo, Response.Listener<String> listener) {
        super(Request.Method.POST, MODEL_PAPER_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("SubjectCode", SubjectCode);
        parameters.put("ModelPaperNo", ModelPaperNo);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}
