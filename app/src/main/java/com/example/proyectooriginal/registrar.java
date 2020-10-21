package com.example.proyectooriginal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class registrar extends AppCompatActivity {
    private registrar root = this;

    private EditText nombre, ci, calle, telef, correo, password;
    private RadioButton rb_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        nombre=(EditText) findViewById(R.id.txt_nombre);
        ci=(EditText) findViewById(R.id.txt_ci);
        calle=(EditText) findViewById(R.id.txt_calle);
        telef=(EditText) findViewById(R.id.txt_telf);
        correo=(EditText) findViewById(R.id.txt_correo);
        password=(EditText) findViewById(R.id.txt_password);

        rb_registrar=(RadioButton) findViewById(R.id.rb_registrar);
    }

    public void RegistrarDatos(View view)
    {
        final String Nombre=nombre.getText().toString();
        String Ci=ci.getText().toString();
        String Calle=calle.getText().toString();
        String Telf=telef.getText().toString();
        String tipo="";
        String Email=correo.getText().toString();
        String Password=password.getText().toString();
        if(Nombre.length()==0 || Ci.length()==0 || Telf.length()==0 || Email.length()==0)
        {
            Toast.makeText(this, "Debe de llenar todos los Datos", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(rb_registrar.isChecked()==false)
            {
                tipo="cliente";
            }
            else
            {
                tipo="propietario";
            }
        }

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("nombre", Nombre);
        params.add("ci", Ci);
        params.add("telefono", Telf);
        params.add("tipo", tipo);
        params.add("email", Email);
        params.add("password", Password);


        final String finalTipo = tipo;
        client.post(Endpoints.USUARIO_SERVICE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (!response.has("msn")) {
                        if(finalTipo.equals("propietario"))
                        {
                            Intent otraActividad=new Intent(root,RestauranteActivity.class);
                            startActivity(otraActividad);
                        }
                        else
                            Toast.makeText(root, Nombre+" Registrado", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(root, response.getString("msn"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
