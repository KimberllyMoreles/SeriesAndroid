package com.aluno.series;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    SerieServiceBD serieServiceBD;

    ListView lvLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);
        //obtém a instância do objeto de acesso ao banco de dados
        serieServiceBD = SerieServiceBD.getInstance(this);
        //constrói uma instância da classe de modelo

        List<Serie> series = serieServiceBD.getAll();



        lvLista = (ListView) findViewById(R.id.lvLista);

        ArrayAdapter<Serie> adapter = new ArrayAdapter<Serie>(this,
                android.R.layout.simple_list_item_1, series);

        lvLista.setAdapter(adapter);
    }

}
