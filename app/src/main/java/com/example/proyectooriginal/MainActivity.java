package com.example.proyectooriginal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private RadioButton registroNormal, registarRestaurante;
    private MainActivity root = this;

    //agregado y borrar
    // private Button btn;
         private  RadioButton RegistroNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email_txt);
        password = (EditText) findViewById(R.id.password_txt);

        registroNormal = (RadioButton) findViewById(R.id.rb_normal);
        registarRestaurante = (RadioButton) findViewById(R.id.rb_registroRes);

         registroNormal = (RadioButton) findViewById(R.id.rb_normal);

        registroNormal .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MainActivity.this, registrar.class);
                startActivity(in);
            }
        });


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

            Toast.makeText(this, "La Sesion se ha Iniciado", Toast.LENGTH_SHORT).show();
        }

        AsyncHttpClient client = new AsyncHttpClient();

        EditText email = root.findViewById(R.id.email_txt);
        EditText password = root.findViewById(R.id.password_txt);

        RequestParams params = new RequestParams();
        params.add("email", email.getText().toString());
        params.add("password", password.getText().toString());

        client.post(Endpoints.LOGIN_SERVICE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.has("msn")) {
                        UserDataServe.MSN = response.getString("msn");
                    }
                    if (response.has("token")) {
                        UserDataServe.TOKEN = response.getString("token");

                    }
                    if (UserDataServe.TOKEN.length() > 150) {
                        Intent intent = new Intent(root, CrearMenu.class);
                        root.startActivity(intent);
                    } else {
                        Toast.makeText(root, response.getString("msn"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }});
    }


    public void Registrar(View view) {
        String Correo = email.getText().toString();
        String Password = password.getText().toString();
        Intent otraActividad = new Intent(this, RestauranteActivity.class);
       /* if (Correo.length() == 0) {
            Toast.makeText(this, "El Correo es necesario", Toast.LENGTH_SHORT).show();
        }
        if (Password.length() == 0) {
            Toast.makeText(this, "La Contraseña es necesaria", Toast.LENGTH_SHORT).show();
        }

        */

        //if (Correo.length() != 0 && Password.length() != 0) {

            if (registroNormal.isChecked() == false && registarRestaurante.isChecked() == false) {
                Toast.makeText(this, "Seleccione una opcion para continuar", Toast.LENGTH_SHORT).show();
            } else {
                if (registroNormal.isChecked() == true) {
                    Toast.makeText(this, "El usuario ha sido Registrado", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Registre los datos de su Restaurante", Toast.LENGTH_SHORT).show();
                    startActivity(otraActividad);
                }
            }


        }

    }

//}