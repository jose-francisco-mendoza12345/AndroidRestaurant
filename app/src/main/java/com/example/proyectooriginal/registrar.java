package com.example.proyectooriginal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectooriginal.utils.Endpoints;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class registrar extends AppCompatActivity{

  //  Button btn1;
    EditText nick,ci,calle,telefono,Tipo,email2,pass2,Fecha;
    Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);




        nick=findViewById(R.id.reg_nick);
        ci=findViewById(R.id.reg_ci);
        calle=findViewById(R.id.reg_calle);
        telefono=findViewById(R.id.reg_telefono);
        Tipo=findViewById(R.id.reg_Tipo);
        email2=findViewById(R.id.reg_email);
        pass2=findViewById(R.id.reg_password);
        Fecha=findViewById(R.id.reg_Fecha);
        btn2=findViewById(R.id.reg_send);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(registrar.this,MainActivity.class);
                startActivity(in);

            }
        });

    }

 /*
    private void sendLogin(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams req=new RequestParams();

        req.put("email",email1.getText().toString());
        req.put("password",pass1.getText().toString());

        client.post(Endpoints.LOGIN_SERVICE+"/login",req,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String msn=response.getString("msn");
                    Toast.makeText(registrar.this, ""+response.getString("token"), Toast.LENGTH_SHORT).show();
                }catch (Exception e){}
            }
        });
    }
*/
    private  void sendRegister(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams req=new RequestParams();
        req.put("nick",nick.getText().toString());
        req.put("ci",Integer.parseInt(ci.getText().toString()));
        req.put("calle",calle.getText().toString());
        req.put("telefono",telefono.getText().toString());
        req.put("Tipo",Tipo.getText().toString());
        req.put("email",email2.getText().toString());
        req.put("password",pass2.getText().toString());
        req.put("Fecha",Fecha.getText().toString());
        client.post(Endpoints.LOGIN_SERVICE,req,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String msn=response.getString("msn");
                    Toast.makeText(registrar.this, ""+response.getString("token"), Toast.LENGTH_SHORT).show();
                }catch (Exception e){}
            }
        });

    }
}