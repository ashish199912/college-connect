package com.ior.cmreclh.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;

/**
 * Created by Abhishek Jha on 02.02.2018.
 */

public class VolleyRegisterApi extends StringRequest {

    private static final String REGISTER_URL = "http://step2success.co.in/cmrec_abhishek/api/register.php";
    private Map<String, String> parameters;
    public VolleyRegisterApi(String Name,String FatherName,String Gender,String EmailId,String Phone,String RollNo,String Password,
                             String Regulation,int Year,int Sem,int DeptNo, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Name", Name);
        parameters.put("FatherName", FatherName);
        parameters.put("Gender", Gender);
        parameters.put("EmailId", EmailId);
        parameters.put("Phone", Phone);
        parameters.put("RollNo", RollNo);
        parameters.put("Password", Password);
        parameters.put("Regulation", Regulation);
        parameters.put("Year", valueOf(Year));
        parameters.put("Sem", valueOf(Sem));
        parameters.put("DeptNo", valueOf(DeptNo));

    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }

}
