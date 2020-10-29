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
        String nombre="";
        String precio="";
        String id="";
        for(int i=0;i<DatosBD.size();i=i+2)
        {
            nombre=DatosBD.get(i);
            precio=DatosBD.get(i+1);
            id=Recibido;
            Registro(nombre,precio,id);
        }
        finishAffinity();
    }

    public void Registro(String A, String B, String C)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("nombre",A);
        params.add("precio",B);
        params.add("restaurant",C);
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
                        Toast.makeText(root, response.getString("msn"), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(root, "Error al momento de Registrar", Toast.LENGTH_LONG).show();
                    }
                }catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //Toast.makeText(root, "Registrado", Toast.LENGTH_LONG).show();
    }
}