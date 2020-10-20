package com.example.proyectooriginal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class registrar extends AppCompatActivity {
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
        Intent otraActividad = new Intent(this, RestauranteActivity.class);
        String Nombre=nombre.getText().toString();
        String Ci=ci.getText().toString();
        String Calle=calle.getText().toString();
        String Telf=telef.getText().toString();
        String tipo="";
        String Email=correo.getText().toString();
        String Password=password.getText().toString();
        if(Nombre.length()==0 || Ci.length()==0 || Telf.length()==0 || Email.length()==0 || Password.length()==0)
        {
            Toast.makeText(this, "Debe de llenar todos los Datos", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(rb_registrar.isChecked()==false)
            {
                tipo="Cliente";
                Toast.makeText(this, Nombre+" fue Registrado", Toast.LENGTH_SHORT).show();
            }
            else
            {
                tipo="Propietario";
            }
        }

        //Codigo para el registro a la base de datos
        //------------------------

        //------------------------
        if(tipo.equals("Propietario"))
        {
            startActivity(otraActividad);
        }
    }
}