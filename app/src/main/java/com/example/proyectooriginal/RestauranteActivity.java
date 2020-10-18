package com.example.proyectooriginal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RestauranteActivity extends AppCompatActivity {
    private EditText nombre, nit, propietario, calle, telf, lon, lat, fecha;
    private ImageView logo, restaurante,AUX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante);

        nombre=(EditText) findViewById(R.id.TextoNombre);
        nit=(EditText) findViewById(R.id.TextoNit);
        propietario=(EditText) findViewById(R.id.TextoPropiet);
        calle=(EditText) findViewById(R.id.TextoCalle);
        telf=(EditText) findViewById(R.id.TextoTelf);
        lon=(EditText) findViewById(R.id.TextoLog);
        lat=(EditText) findViewById(R.id.TextoLat);
        fecha=(EditText) findViewById(R.id.TextFecha);

        logo=(ImageView) findViewById(R.id.FotoLogo);
        restaurante=(ImageView) findViewById(R.id.FotoRestaurante);
    }

    public void SubirLogo(View view)
    {
        AUX=logo;
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicacion"),10);
    }
    public void SubirRestaurante(View view)
    {
        AUX=restaurante;
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicacion"),10);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            Uri path=data.getData();
            AUX.setImageURI(path);
            Toast.makeText(this, "Imagen Insertada Correctamente", Toast.LENGTH_SHORT).show();
        }
    }


    public  void Guargar(View view)
    {
        Intent otraActividad2=new Intent(this,CrearMenu.class);
        if(!nombre.getText().toString().isEmpty() && !nit.getText().toString().isEmpty() &&
                !propietario.getText().toString().isEmpty() && !calle.getText().toString().isEmpty() &&
                !telf.getText().toString().isEmpty() && !lon.getText().toString().isEmpty() &&
                !lat.getText().toString().isEmpty() && !fecha.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Los Datos han sido registrados", Toast.LENGTH_SHORT).show();
            startActivity(otraActividad2);
        }
        else
        {
            Toast.makeText(this, "Debe de Llenar todos los Cuadros", Toast.LENGTH_SHORT).show();
        }
    }
}