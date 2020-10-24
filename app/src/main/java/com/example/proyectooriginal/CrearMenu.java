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
import com.example.proyectooriginal.utils.UserDataServe;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CrearMenu extends AppCompatActivity {
    private CrearMenu root = this;

    private EditText nombre,precio;
    private ListView lt_menu,lt_borrar;
    private String Recibido;

    ArrayList<String> ListaMenu;
    ArrayAdapter ADP;

    ArrayList<String> ListaBorrar;
    ArrayAdapter ADP2;

    ArrayList<String> DatosBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_menu);

        nombre=(EditText) findViewById(R.id.titulo);
        precio=(EditText) findViewById(R.id.txt_precio);
        Recibido=getIntent().getStringExtra("ID");

        DatosBD =new ArrayList<String>();

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

                DatosBD.remove(i);
            }
        });
    }

    public void Agregar(View view)
    {

        if(nombre.getText().toString().length()!=0 && precio.getText().toString().length()!=0)
        {
            ListaMenu.add(nombre.getText().toString()+" "+precio.getText().toString()+" Bs");
            ListaBorrar.add("Borrar");

            DatosBD.add(nombre.getText().toString());
            DatosBD.add(precio.getText().toString());
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

    public void Guardar(View view)
    {
        final Boolean[] resultado = {false};
        final int[] aux = {0};
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        for(int i=0;i<DatosBD.size();i=i+2)
        {
            Toast.makeText(root, String.valueOf(i), Toast.LENGTH_LONG).show();
            params.add("nombre",DatosBD.get(i));
            params.add("precio",DatosBD.get(i+1));
            params.add("restaurant",Recibido);
            client.post(Endpoints.MENU_SERVICE, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        if(response.has("msn"))
                        {
                            UserDataServe.MSN = response.getString("msn");
                        }
                        if(UserDataServe.MSN.equals("Menu Registrado"))
                        {
                            resultado[0] =true;
                        }
                        else
                        {
                            resultado[0] =false;
                            aux[0]=aux[0]+1;
                        }

                    }catch(JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            if(!resultado[0])
                break;
        }
        if(aux[0]==0)
            Toast.makeText(root, "El menu ha sido registrado con exito", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(root, "Ha ocurrido algunos Errores durante el registro", Toast.LENGTH_LONG).show();
    }
}