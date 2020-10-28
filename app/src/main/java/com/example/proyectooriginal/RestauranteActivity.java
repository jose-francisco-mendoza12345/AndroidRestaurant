package com.example.proyectooriginal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectooriginal.utils.Endpoints;
import com.example.proyectooriginal.utils.UserDataServe;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class RestauranteActivity extends AppCompatActivity {
    private RestauranteActivity root = this;

    private EditText nombre, nit, propietario, calle, telf, lon, lat;
    private ImageView logo, restaurante,AUX;
    private int control1=0, control2=0;
    private String control="";
    private Uri imagen1, imagen2;
    String almacen;
    String almacen2;
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

        logo=(ImageView) findViewById(R.id.FotoLogo);
        restaurante=(ImageView) findViewById(R.id.FotoRestaurante);
    }


    public void SubirLogo(View view)
    {
        control1=1;
        control="logo";

        AUX=logo;
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicacion"),10);
    }
    public void SubirRestaurante(View view)
    {
        control2=1;
        control="Foto";
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

            if(control.equals("logo"))
            {
                imagen1=path;
                almacen=getRealPath(root, path);
            }
            else
            {
                imagen2=path;
                almacen2=getRealPath(root, path);
            }
            Toast.makeText(this, "Imagen Insertada Correctamente", Toast.LENGTH_SHORT).show();
        }
    }
    String getRealPath(Context ctx, Uri path){
        String uri=null;
        Cursor cursor = ctx.getContentResolver().query(path,null,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            int i=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            uri=cursor.getString(i);
            cursor.close();
        }
        return uri;
    }


    public  void Guargar(View view) throws FileNotFoundException {
        final Intent otraActividad=new Intent(this,CrearMenu.class);
        if(!nombre.getText().toString().isEmpty() && !nit.getText().toString().isEmpty() &&
                !propietario.getText().toString().isEmpty() && !calle.getText().toString().isEmpty() &&
                !telf.getText().toString().isEmpty() && !lon.getText().toString().isEmpty() &&
                !lat.getText().toString().isEmpty())
        {
            if(control1!=0 && control2!=0)
            {

                final AsyncHttpClient client = new AsyncHttpClient();
                final RequestParams params = new RequestParams();

                File archivo=new File(almacen);
                File archivo2=new File(almacen2);

                params.add("nombre", nombre.getText().toString());
                params.add("nit", nit.getText().toString());
                params.add("propietario", propietario.getText().toString());
                params.add("calle", calle.getText().toString());
                params.add("telefono", telf.getText().toString());
                params.add("lat", lat.getText().toString());
                params.add("lng", lon.getText().toString());
                params.put("logo", archivo);
                params.put("fotoLugar", archivo2);

                client.post(Endpoints.RESTAURANTE_SERVICE, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            if(response.has("msn"))
                            {
                                UserDataServe.MSN = response.getString("msn");
                            }
                            if(UserDataServe.MSN.equals("Restaurante Registrado"))
                            {

                                Toast.makeText(root, response.getString("msn"), Toast.LENGTH_SHORT).show();

                                client.get(Endpoints.RESTAURANTE_SERVICE,params, new JsonHttpResponseHandler() {
                                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                        try {
                                            JSONObject jresponse = response.getJSONObject(0);
                                            String id = jresponse.getString("_id");

                                            otraActividad.putExtra("ID",id);
                                            startActivity(otraActividad);

                                        }catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                        Toast.makeText(root, "Error Al consultar a la Base de Datos", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else
                                Toast.makeText(root, "Ha ocurrido un error al momento de registrar", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            else
                Toast.makeText(this, "Debe de insertar las imagenes", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Debe de Llenar todos los Cuadros", Toast.LENGTH_SHORT).show();
        }
    }
}