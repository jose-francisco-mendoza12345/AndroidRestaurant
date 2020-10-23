package com.example.proyectooriginal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proyectooriginal.utils.Endpoints;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CrearMenu extends AppCompatActivity {
    private EditText nombre,precio;
    private ListView lt_menu,lt_borrar;
    Button guardar;

    ArrayList<String> ListaMenu;
    ArrayAdapter ADP;

    ArrayList<String> ListaBorrar;
    ArrayAdapter ADP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_menu);

        nombre=(EditText) findViewById(R.id.titulo);
        precio=(EditText) findViewById(R.id.txt_precio);
        guardar=findViewById(R.id.guardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(CrearMenu.this,CrearMenu.class);
                startActivity(in);

            }
        });

        ListaMenu = new ArrayList<>();//creacion del ArrayList<>
        ADP = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,ListaMenu);
        lt_menu=(ListView) findViewById(R.id.lista_menu);
        lt_menu.setAdapter(ADP);

        ListaBorrar = new ArrayList<>();//Creacion del ArratList<>
        ADP2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,ListaBorrar);
        lt_borrar=(ListView) findViewById(R.id.lista_borrar);
        lt_borrar.setAdapter(ADP2);



        lt_borrar.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(CrearMenu.this, "La Orden ha Sido Eliminada", Toast.LENGTH_LONG).show();
                ListaMenu.remove(i);
                ListaBorrar.remove(i);
                ADP.notifyDataSetChanged();
                ADP2.notifyDataSetChanged();
            }
        });
    }
   public void Guardar(View view)
    {


    }

    public void Agregar(View view)
    {
        // creacion del menu con la base de datos
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams req= new RequestParams();
        req.put("nombre",nombre.getText().toString());
        req.put("precio",precio.getText().toString());
        client.get(Endpoints.MENU_SERVICE,req,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

            }
        });
        if(nombre.getText().toString().length()!=0 && precio.getText().toString().length()!=0)
        {
            ListaMenu.add(nombre.getText().toString()+" "+precio.getText().toString()+"Bs");
            ListaBorrar.add("Borrar");
        }
        else
        {
            Toast.makeText(this, "Debe de llenar todas las Casillas", Toast.LENGTH_LONG).show();
        }
        ADP.notifyDataSetChanged();
        ADP2.notifyDataSetChanged();

        nombre.setText("");
        precio.setText("");
    }
}