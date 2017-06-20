package com.aluno.series;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SerieServiceBD serieServiceBD;
    private static Serie serie = null;

    private EditText etNome;
    private EditText etSinopse;
    private EditText etDiretor;
    private RadioButton rbAndamento;
    private RadioButton rbFinalizada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtém a instância do objeto de acesso ao banco de dados
        serieServiceBD = SerieServiceBD.getInstance(this);
        //constrói uma instância da classe de modelo
        serie = new Serie();

        //mapeia os componentes de UI
        etNome = (EditText) findViewById(R.id.editText_Nome);
        etSinopse = (EditText) findViewById(R.id.editText_Sinopse);
        etDiretor = (EditText) findViewById(R.id.editText_Diretor);
        rbAndamento = (RadioButton) findViewById(R.id.rbAndamento);
        rbFinalizada = (RadioButton) findViewById(R.id.rbFinalizada);
        etNome.requestFocus();
        rbAndamento.setChecked(true);
    }

    //limpa o formulário
    private void limparFormulario(){
        etNome.setText(null);
        etSinopse.setText(null);
        etDiretor.setText(null);
        rbAndamento.setChecked(true);
        etNome.requestFocus();
        serie = new Serie(); //apaga dados antigos
    }

    public void salvar(View view){
        if(!etNome.getText().toString().isEmpty() &&
                !etSinopse.getText().toString().isEmpty() &&
                !etDiretor.getText().toString().isEmpty() &&
                (rbAndamento.isChecked() || rbFinalizada.isChecked())) {
            if(serie._id == null){ //se é uma inclusão
                serie = new Serie(); //apaga dados antigos
            }
            serie.nome = etNome.getText().toString();
            serie.sinopse = etSinopse.getText().toString();
            serie.diretor = etDiretor.getText().toString();

            if(rbAndamento.isChecked())
                serie.status = 1;
            else
                serie.status = 2;
            Log.d("Status", "Contato que será salvo: " + serie.toString());
            serieServiceBD.save(serie);
            limparFormulario();
            Toast.makeText(MainActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarPorNome(View view){
        serie = new Serie();

        serie = serieServiceBD.getByname(String.valueOf(etNome.getText()));
        etSinopse.setText(serie.sinopse);
        etDiretor.setText(serie.diretor);

        if (serie.status == 1)
            rbAndamento.setChecked(true);
        else
            rbFinalizada.setChecked(true);
    }

    public void excluir(View v){

        if(serie._id != null && serie._id > 0) {

            serieServiceBD.delete(serie);
            limparFormulario();
            Toast.makeText(MainActivity.this, "Excluído com sucesso", Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(MainActivity.this, "Nunhuma série selecionada", Toast.LENGTH_SHORT).show();
        }

    }

    public void listar(View v){
        Intent intent = new Intent(MainActivity.this, ListaActivity.class);
        startActivity(intent);
    }


}
