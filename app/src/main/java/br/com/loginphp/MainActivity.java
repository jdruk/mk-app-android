package br.com.loginphp;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText password, email;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        progressBar = findViewById(R.id.progressBar);

        email.setText("54737656372");
    }

    public void onLogin(final View view){

        final String email = this.email.getText().toString();
        email.replaceAll("[^0-9]+", "");

        progressBar.setVisibility(View.VISIBLE);

        String url = "http://192.168.1.23/speed-teste/login/login.php";
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        StringRequest postRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.d("resposta", response);
                    JSONObject jsonObject = new JSONObject(response);
                    int error = jsonObject.getInt("error");
                    if (error == 0){
                        Intent intent = new Intent(view.getContext(), Dashboard.class);
                        intent.putExtra("name", jsonObject.getString("name"));
                        startActivity(intent);
                        progressBar.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_name",email);
                params.put("user_password","");
                return params;
            }
        };
        queue.add(postRequest);

    }
}
