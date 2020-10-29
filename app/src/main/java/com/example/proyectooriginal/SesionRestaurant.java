package com.example.proyectooriginal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proyectooriginal.utils.Endpoints;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SesionRestaurant extends AppCompatActivity {
    private SesionRestaurant root = this;

    private ListView lista;
    ArrayList<String> ListaMenu;
    ArrayList<String> ID;
    ArrayAdapter ADP;

    private String Recibido_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion_restaurant);

        Recibido_user=getIntent().getStringExtra("ID_Cliente");

        lista=(ListView)findViewById(R.id.listaRest);
        getRestarante();

        final Intent i=new Intent(root, RealizarPedido.class);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i.putExtra("id_user",Recibido_user);
                i.putExtra("id_restaurante",ID.get(position));
                startActivity(i);
                Toast.makeText(root,String.valueOf(lista.getItemAtPosition(position)),Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getRestarante()
    {
        ListaMenu = new ArrayList<>();
        ID = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get(Endpoints.RESTAURANTE_SERVICE,params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for(int i=0;i<response.length();i++)
                    {
                        JSONObject jresponse = response.getJSONObject(i);
                        ListaMenu.add(jresponse.getString("nombre"));
                        ID.add(jresponse.getString("_id"));
                    }
                    ADP = new ArrayAdapter<String>(root,R.layout.list_item_modificado,ListaMenu);
                    lista.setAdapter(ADP);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(root, "Error Al consultar a la Base de Datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}