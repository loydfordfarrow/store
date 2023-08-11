package com.store;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    JSONObject resJSONObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button loginBtn = (Button) findViewById(R.id.login_submit);
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                login(username.getText().toString(),password.getText().toString());
            }
        });
    }

    protected void login(String username, String password) {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);//LOADING
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://connect.api.com.ph/login/api?username="+username+"&password="+password;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (null != response) {
                            resJSONObj = response;
                            try {
                                Toast.makeText(getApplicationContext(), response.getString("status"), Toast.LENGTH_LONG).show(); // DEBUG
                                if(response.getString("status").compareTo("200")==0){
                                    setContentView(R.layout.activity_main);
                                }else{
                                    Toast.makeText(getApplicationContext(), response.getString("status").toString(), Toast.LENGTH_LONG).show(); // DEBUG
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                senderBtn.performClick();
            }
        });
        queue.add(request);
    }

}