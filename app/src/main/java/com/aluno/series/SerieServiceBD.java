package com.aluno.series;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aluno.series.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aluno on 14/12/16.
 */
public class SerieServiceBD extends SQLiteOpenHelper {

    private static String TAG = "serieserviceBD";
    private static String NAME = "serie.sqlite";
    private static int VERSION = 1;
    private static SerieServiceBD serieServiceBD = null;

    private SerieServiceBD(Context context) {
        super(context, NAME, null, VERSION);
        getWritableDatabase();
    }

    public static SerieServiceBD getInstance(Context context){
        if (serieServiceBD == null){
            serieServiceBD = new SerieServiceBD(context);
            return serieServiceBD;
        }
        return serieServiceBD;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists serie" +
                "( _id integer primary key autoincrement, " +
                " nome text not null, " +
                "sinopse text not null, " +
                "diretor text not null, " +
                "status integer not null);";
        Log.d(TAG, "Criando a tabela serie. Aguarde ...");
        sqLiteDatabase.execSQL(sql);
        Log.d(TAG, "Tabela serie criada com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*
        CRUD
     */
    public List<Serie> getAll(){
        //abre a conexão com o bd
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        try {
            return toList(sqLiteDatabase.rawQuery("select * from serie", null));
        }finally {
            sqLiteDatabase.close(); //libera o recurso
        }

    }
    

    public long save(Serie serie){

        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco

        try{
            //tupla com: chave, valor
            ContentValues values = new ContentValues();
            values.put("nome", serie.nome);
            values.put("sinopse", serie.sinopse);
            values.put("diretor", serie.diretor);
            values.put("status", serie.status);

            //realiza a operação
            if(serie._id == null){
                //insere no banco de dados
                return db.insert("serie", null, values);
            }else{
                //altera no banco de dadoswrap_content
                values.put("_id", serie._id);
                return db.update("serie", values, "_id=" + serie._id, null);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close(); //libera o recurso
        }

        return 0L; //caso não realize a operação
    }

    public long delete(Serie serie){
        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco
        try{
            return db.delete("serie", "_id=?", new String[]{String.valueOf(serie._id)});
        }
        finally {
            db.close(); //libera o recurso
        }
    }

    /*
        Utilitários
     */
    //converte de Cursor em uma List
    private List<Serie> toList(Cursor c) {
        List<Serie> series = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Serie serie = new Serie();

                // recupera os atributos do cursor para o serie
                serie._id = c.getLong(c.getColumnIndex("_id"));
                serie.nome = c.getString(c.getColumnIndex("nome"));
                serie.sinopse = c.getString(c.getColumnIndex("sinopse"));
                serie.diretor = c.getString(c.getColumnIndex("diretor"));
                serie.status = c.getInt(c.getColumnIndex("status"));

                series.add(serie);

            } while (c.moveToNext());
        }

        return series;
    }

    public Serie getByname(String nome){
        SQLiteDatabase db = getReadableDatabase();
        Serie serie = new Serie();;
        try {
            //retorna uma List para os registros contidos no banco de dados
            // select * from carro
            Cursor c = db.rawQuery("SELECT  * FROM serie where nome LIKE'" + nome + "'", null);

            if(c.moveToFirst()) {
                serie.sinopse = c.getString(2);
                serie.diretor = c.getString(3);
                serie.nome = c.getString(1);
                serie._id = c.getLong(0);
                serie.status = c.getInt(4);
            }
        } finally {
            db.close();
        }
        return serie;
    }
}