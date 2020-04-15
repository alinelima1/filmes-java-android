package aula.filmes.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import aula.filmes.model.Filme;

public class FilmeDAO extends SQLiteOpenHelper {

    public FilmeDAO(Context context, String titulo, CursorFactory factory, int version){
        super(context, titulo, factory, version);
    }

    private static final int VERSAO = 3;
    private static final String TABELA = "Filme";
    private static final String DATABASE = "Filmes";
    private static final String TAG = "FILME";

    public FilmeDAO(Context context){
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String ddl = "CREATE TABLE " + TABELA + "( "
                + "id INTEGER PRIMARY KEY, "
                + "titulo TEXT, ano TEXT, duracao TEXT, "
                + "diretor TEXT, assistir BOOLEAN, assistido BOOLEAN, poster BLOB)";
        database.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int versaoAntiga, int versaoNova) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        database.execSQL(sql);
        onCreate(database);
    }

    public void cadastrar(Filme filme){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ContentValues values = new ContentValues();
        values.put("Titulo", filme.getTitulo());
        values.put("Ano", filme.getAno());
        values.put("Duracao", filme.getDuracao());
        values.put("Diretor", filme.getDiretor());
        values.put("Assistir", filme.isAssistir());
        values.put("Assistido", filme.isAssistido());
        Bitmap poster = filme.getPoster();
        poster.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        values.put("Poster", byteArray);
        
        getWritableDatabase().insert(TABELA, null, values);
        Log.i(TAG, "Filme cadastrado: " + filme.getTitulo());
    }

    public List<Filme> listarAssistir(){
        List<Filme> lista = new ArrayList<Filme>();
        String sql = "Select * from Filme order by titulo";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try{
            while(cursor.moveToNext()){
                Filme filme = new Filme();
                filme.setId(cursor.getLong(0));
                filme.setTitulo(cursor.getString(1));
                filme.setAno(cursor.getString(2));
                filme.setDuracao(cursor.getString(3));
                filme.setDiretor(cursor.getString(4));
                if(cursor.getString(5).equals("0")){
                    filme.setAssistir(false);
                }else{
                    filme.setAssistir(true);
                }
                if(cursor.getString(6).equals("0")){
                    filme.setAssistido(false);
                }else{
                    filme.setAssistido(true);
                }
                if(cursor.getBlob(7) != null){
                    byte[] byteArray = cursor.getBlob(7);
                    Bitmap posterRec = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    filme.setPoster(posterRec);
                }
                if(filme.isAssistir()){
                    lista.add(filme);
                }
            }
        }catch (SQLException e){
            Log.e(TAG, e.getMessage());
        }finally {
            cursor.close();
        }
        return lista;
    }

    public List<Filme> listarAssistidos(){
        List<Filme> lista = new ArrayList<Filme>();
        String sql = "Select * from Filme order by titulo";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try{
            while(cursor.moveToNext()){
                Filme filme = new Filme();
                filme.setId(cursor.getLong(0));
                filme.setTitulo(cursor.getString(1));
                filme.setAno(cursor.getString(2));
                filme.setDuracao(cursor.getString(3));
                filme.setDiretor(cursor.getString(4));
                if(cursor.getString(5).equals("0")){
                    filme.setAssistir(false);
                }else{
                    filme.setAssistir(true);
                }
                if(cursor.getString(6).equals("0")){
                    filme.setAssistido(false);
                }else{
                    filme.setAssistido(true);
                }
                if(cursor.getBlob(7) != null){
                    byte[] byteArray = cursor.getBlob(7);
                    Bitmap posterRec = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    filme.setPoster(posterRec);
                }
                if(filme.isAssistido()){
                    lista.add(filme);
                }
            }
        }catch (SQLException e){
            Log.e(TAG, e.getMessage());
        }finally {
            cursor.close();
        }
        return lista;
    }

    public void deletar(Filme filme){
        String[] args = {filme.getId().toString()};

        getWritableDatabase().delete(TABELA, "id=?", args);
        Log.i(TAG, "Filme deletado: " + filme.getTitulo());
    }

    public void alterar(Filme filme){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ContentValues values = new ContentValues();
        values.put("Titulo",filme.getTitulo());
        values.put("Ano",filme.getAno());
        values.put("Duracao",filme.getDuracao());
        values.put("Diretor", filme.getDiretor());
        values.put("Assistir", filme.isAssistir());
        values.put("Assistido", filme.isAssistido());
        Bitmap poster = filme.getPoster();
        poster.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        values.put("Poster", byteArray);

        String[] args = {filme.getId().toString()};

        getWritableDatabase().update(TABELA,values,"id=?",args);
        Log.i(TAG,"Filme Alterado: " + filme.getTitulo());
    }
}
