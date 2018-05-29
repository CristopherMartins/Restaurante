package com.c.cristopher.restaurante;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RestauranteDAO extends SQLiteOpenHelper {

    public RestauranteDAO(Context context) {
        super(context, "Coletivo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Restaurantes(id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, tipo TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Restaurantes";
        db.execSQL(sql);
        onCreate(db);
    }

    public void inserirRestaurante(Restaurante restaurante){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosRestaurante(restaurante);

        db.insert("Restaurantes", null, dados);
    }

    public void alteraRestaurante(Restaurante restaurante){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosRestaurante(restaurante);
        String[] params = {String.valueOf(restaurante.getId())};
        db.update("Restaurantes", dados, "id = ?", params);
    }

    public void excluirRestaurante (Restaurante restaurante){
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {String.valueOf(restaurante.getId())};
        db.delete("Restaurantes", "id = ?", params);
    }

    private ContentValues pegaDadosRestaurante (Restaurante restaurante){
        ContentValues dados = new ContentValues();
        dados.put("nome", restaurante.getNome());
        dados.put("endereco", restaurante.getEndereco());
        dados.put("tipo", restaurante.getTipo());
        return dados;
    }

    public List<Restaurante> buscaRestaurante(){
        String sql = "SELECT * FROM Restaurantes;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Restaurante> restaurantes = new ArrayList<>();

        while (c.moveToNext()){
            Restaurante restaurante = new Restaurante();

            restaurante.setId(c.getLong(c.getColumnIndex("id")));
            restaurante.setNome(c.getString(c.getColumnIndex("nome")));
            restaurante.setEndereco(c.getString(c.getColumnIndex("endereco")));
            restaurante.setTipo(c.getString(c.getColumnIndex("tipo")));

            restaurantes.add(restaurante);
        }
        c.close();
        return restaurantes;
    }
}
