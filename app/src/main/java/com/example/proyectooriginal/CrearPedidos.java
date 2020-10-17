package com.example.proyectooriginal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CrearPedidos extends AppCompatActivity {
        //private EditText nombre,precio;
        private ListView lt_pedido ,lt_insertar;

        ArrayList<String> ListaPedidos;
        ArrayAdapter ADP;

        ArrayList<String> ListaInsertar;
        ArrayAdapter ADP2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_crear_pedido);

          //  nombre=(EditText) findViewById(R.id.txt_nombre);
           // precio=(EditText) findViewById(R.id.txt_precio);

            ListaPedidos = new ArrayList<>();//creacion del ArrayList<>
            ADP = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,ListaPedidos);
            lt_pedido=(ListView) findViewById(R.id.Lista_Pedidos);
            lt_insertar.setAdapter(ADP);

            ListaInsertar = new ArrayList<>();//Creacion del ArratList<>
            ADP2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,ListaInsertar);
            lt_insertar=(ListView) findViewById(R.id.Lista_Pedidos);
            lt_insertar.setAdapter(ADP2);



            lt_insertar.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Toast.makeText(com.example.proyectooriginal.CrearPedidos.this, "La Orden ha Sido agregada", Toast.LENGTH_LONG).show();
                    ListaPedidos.remove(i);
                    ListaInsertar.remove(i);
                    ADP.notifyDataSetChanged();
                    ADP2.notifyDataSetChanged();
                }
            });
        }
/*
        public void Agregar(View view)
        {
            if(nombre.getText().toString().length()!=0 && precio.getText().toString().length()!=0)
            {
                ListaPedidos.add(nombre.getText().toString()+" "+precio.getText().toString()+"Bs");
                ListaInsertar.add("Insertar");
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
*/
    }
