package aula.filmes.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import aula.filmes.controller.FilmesController;
import aula.filmes.utils.AdapterFilmes;
import aula.filmes.model.Filme;
import aula.filmes.R;

public class ListaFilmesAssistirActivity extends Activity {
    private ListView lvListagem;
    private List<Filme> listaFilmes;
    private Filme filmeSelecionado = null;
    Button action;
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    FilmesController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listasfilmes);
        lvListagem = (ListView)findViewById(R.id.lvListaFilmes);
        action = (Button)findViewById(R.id.BtnAction);
        action.setText("Marcar Assistido");
        controller = new FilmesController(this);

        //Operação para click curto (apenas seleciona)
        lvListagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
                lvListagem.setBackgroundColor(2);
                filmeSelecionado = listaFilmes.get(posicao);
                Toast toast = Toast.makeText(getApplicationContext(), "Filme selecionado: " + filmeSelecionado.getTitulo(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //Operação para click longo
        lvListagem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
                //Marca o filme selecionado na ListView
                filmeSelecionado = listaFilmes.get(posicao);
                if(filmeSelecionado.getTitulo() != null){
                    Bitmap poster = filmeSelecionado.getPoster();
                    poster.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    Intent form = new Intent(ListaFilmesAssistirActivity.this, DetalhesFilmeActivity.class);
                    filmeSelecionado.setPoster(null);
                    form.putExtra("filme", filmeSelecionado);
                    form.putExtra("tela", "assistir");
                    form.putExtra("poster", byteArray);
                    startActivity(form);
                    finish();
                }
                return false; //true não executa o click longo, false executa o click longo.
            }
        });

        action.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(filmeSelecionado != null) {
                    filmeSelecionado.setAssistido(true);
                    filmeSelecionado.setAssistir(false);
                    controller.alterarFilme(filmeSelecionado);
                    carregarLista();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Nunhum filme selecionado!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private void carregarLista(){
        this.listaFilmes = controller.listarAssistir();
        controller.close();
        AdapterFilmes adapter = new AdapterFilmes(listaFilmes, this);
        lvListagem.setAdapter(adapter);
    }

    public void onBackPressed() {
        Intent intent = new Intent(ListaFilmesAssistirActivity.this,FilmesActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onResume(){
        super.onResume();
        this.carregarLista();
    }
}
