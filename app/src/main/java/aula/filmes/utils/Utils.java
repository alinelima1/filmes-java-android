package aula.filmes.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import aula.filmes.model.Filme;

public class Utils {

    public Filme getJson(String end){
        String json;
        Filme retorno;
        json = Conexao.getJSON(end);
        Log.i("Resultado", json);
        retorno = parseJson(json);

        return retorno;
    }

    private Filme parseJson(String json){
        try {
            Filme filme = new Filme();
            JSONObject jsonObj = new JSONObject(json);
            filme.setTitulo(jsonObj.getString("Title"));
            filme.setAno(jsonObj.getString("Year"));
            filme.setDuracao(jsonObj.getString("Runtime"));
            filme.setGenero(jsonObj.getString("Genre"));
            filme.setDiretor(jsonObj.getString("Director"));
            filme.setPoster(baixarPoster(jsonObj.getString("Poster")));
            return filme;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap baixarPoster(String url) {
        try{
            URL endereco;
            InputStream inputStream;
            Bitmap poster; endereco = new URL(url);
            inputStream = endereco.openStream();
            poster = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return poster;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}