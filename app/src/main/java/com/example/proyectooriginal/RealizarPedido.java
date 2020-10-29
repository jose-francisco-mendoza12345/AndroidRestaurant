package com.example.proyectooriginal;

import androidx.appcompat.app.AppCompatActivity;

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

public class RealizarPedido extends AppCompatActivity {
    private RealizarPedido root = this;

    private ListView lista;
    ArrayList<String> ListaMenu;
    ArrayAdapter ADP;
    private String usuario, restaurante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_pedido);
        usuario=getIntent().getStringExtra("id_user");
        restaurante=getIntent().getStringExtra("id_restaurante");

        lista=(ListView)findViewById(R.id.listMenu);
        getMenu();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(root,String.valueOf(lista.getItemAtPosition(position)),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getMenu()
    {
        ListaMenu = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get(Endpoints.MENU_SERVICE,params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    for(int i=0;i<response.length();i++)
                    {
                        JSONObject jresponse = response.getJSONObject(i);
                        Toast.makeText(root, jresponse.getString("restaurant")+"--------------", Toast.LENGTH_SHORT).show();

                        if(restaurante.equals(jresponse.getString("restaurant")))
                        {
                            ListaMenu.add(jresponse.getString("nombre"));
                        }
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