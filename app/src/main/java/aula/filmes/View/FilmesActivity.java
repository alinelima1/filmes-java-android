package aula.filmes.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import aula.filmes.utils.AdapterFilmes;
import aula.filmes.model.Filme;
import aula.filmes.R;
import aula.filmes.utils.Utils;

public class FilmesActivity extends Activity implements Serializable{
    private ListView listaGeral;
    GetJson download = new GetJson();
    private ProgressDialog load;
    public EditText nomeFilme;
    private Button botaoBuscar;
    public Filme filmeObj;

    ByteArrayOutputStream stream = new ByteArrayOutputStream();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);
        nomeFilme = (EditText)findViewById(R.id.edBuscar);
        botaoBuscar = (Button)findViewById(R.id.btnBuscar);
        listaGeral = (ListView)findViewById(R.id.lvListaFilmesGeral);
        registerForContextMenu(listaGeral);

        listaGeral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
                if(filmeObj.getTitulo() != null){
                    Bitmap poster = filmeObj.getPoster();
                    poster.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    Intent form = new Intent(FilmesActivity.this, DetalhesFilmeActivity.class);
                    filmeObj.setPoster(null);
                    form.putExtra("filme", filmeObj);
                    form.putExtra("tela", "inicial");
                    form.putExtra("poster", byteArray);
                    startActivity(form);
                    finish();
                }
            }
        });

        botaoBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                nomeFilme.getText();
                download.execute();
            }
        });

        nomeFilme.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                nomeFilme.setText("");
                listaGeral.setAdapter(null);
                download = new GetJson();
            }
        });
    }

    public void setFilmeObj(Filme filme){
        this.filmeObj = filme;
    }

    public String getNomeFilme(){
        return ""+nomeFilme.getText();
    }

    public void getLista(Filme filme){
        List<Filme> listaFilmesGeral = new ArrayList<Filme>();
        listaFilmesGeral.add(filme);
        AdapterFilmes adapter = new AdapterFilmes(listaFilmesGeral, this);
        listaGeral.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.Assistir:
                intent = new Intent(FilmesActivity.this, ListaFilmesAssistirActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.Assistidos:
                intent = new Intent(FilmesActivity.this, ListaFilmesAssistidosActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private class GetJson extends AsyncTask<Void, Void, Filme> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(FilmesActivity.this, "", "Carregando Informações.");
        }

        @Override
        protected Filme doInBackground(Void... params) {
            Utils util = new Utils();
            Filme temp = util.getJson("http://www.omdbapi.com/?apikey=65454b1e&t="+ getNomeFilme() +"");
            if(temp != null){
                return temp;
            }else{
                return new Filme();
            }
        }

        @Override
        protected void onPostExecute(Filme filme){
            setFilmeObj(filme);
            getLista(filme);
            load.dismiss();
        }
    }

}
