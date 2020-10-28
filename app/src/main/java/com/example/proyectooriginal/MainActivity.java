package com.example.proyectooriginal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.proyectooriginal.utils.Endpoints;
import com.example.proyectooriginal.utils.UserDataServe;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity<LoadCompo> extends AppCompatActivity {
    private EditText email, password;
    private MainActivity root = this;
    private RadioButton RegistroNormal;
    private  final int CODE_PERMISSIONS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email_txt);
        password = (EditText) findViewById(R.id.password_txt);

        solicitarPermiso();
    }

    private void solicitarPermiso() {
        int Internet= ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.INTERNET);
        int Lectura= ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
        int EScritura= ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(Internet!=PackageManager.PERMISSION_GRANTED || Lectura!=PackageManager.PERMISSION_GRANTED || EScritura!=PackageManager.PERMISSION_GRANTED)
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.INTERNET,
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_PERMISSIONS);
            }
        }
    }


    public void Sesion(View view) {
        String Correo = email.getText().toString();
        String Password = password.getText().toString();

        if (Correo.length() == 0) {
            Toast.makeText(this, "El Correo es necesario", Toast.LENGTH_SHORT).show();
        }
        if (Password.length() == 0) {
            Toast.makeText(this, "La Contraseña es necesaria", Toast.LENGTH_SHORT).show();
        }
        if (Correo.length() != 0 && Password.length() != 0) {

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();


            params.add("email", Correo);
            params.add("password", Password);

            client.post(Endpoints.LOGIN_SERVICE, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        if(response.has("msn"))
                        {
                            UserDataServe.MSN = response.getString("msn");
                        }
                        if(response.has("token"))
                        {
                            UserDataServe.TOKEN = response.getString("token");
                        }
                        if(UserDataServe.TOKEN.length()>15)
                        {
                            /*Intent intent=new Intent(root, registrar.class);
                            root.startActivity(intent);*/
                            Toast.makeText(root,"El usuario ha sido iniciado Sesion",Toast.LENGTH_SHORT).show();
                            Toast.makeText(root,response.getString("token"),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(root,response.getString("msn"),Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    public void Registrar(View view) {
        Intent otraActividad = new Intent(this, registrar.class);
        startActivity(otraActividad);
    }


}