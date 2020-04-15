package aula.filmes.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class ListaFilmesAssistidosActivity extends Activity {
    private ListView lvListagem;
    private List<Filme> listaFilmes;
    private int adapterLayout = android.R.layout.simple_list_item_1;
    private Filme filmeSelecionado = null;
    Button action;
    FilmesController controller;

    ByteArrayOutputStream stream = new ByteArrayOutputStream();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listasfilmes);
        lvListagem = (ListView)findViewById(R.id.lvListaFilmes);
        action = (Button)findViewById(R.id.BtnAction);
        action.setText("Excluir");

        //Operação para click curto
        lvListagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
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
                    Intent form = new Intent(ListaFilmesAssistidosActivity.this, DetalhesFilmeActivity.class);
                    filmeSelecionado.setPoster(null);
                    form.putExtra("filme", filmeSelecionado);
                    form.putExtra("tela", "assistidos");
                    form.putExtra("poster", byteArray);
                    startActivity(form);
                    finish();
                }
                return false; //true não executa o click simples, false executa o click simples.
            }
        });

        action.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(filmeSelecionado != null){
                    excluirFilme();
                }
            }
        });

    }

    private void carregarLista(){
        controller = new FilmesController(this);
        this.listaFilmes = controller.listarAssistidos();
        controller.close();
        AdapterFilmes adapter = new AdapterFilmes(listaFilmes, this);
        lvListagem.setAdapter(adapter);
    }

    private void excluirFilme(){
        if(filmeSelecionado != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Confirma a exclusão de: " + filmeSelecionado.getTitulo() + "?");

            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    controller = new FilmesController(ListaFilmesAssistidosActivity.this);
                    controller.deletar(filmeSelecionado);
                    controller.close();
                    carregarLista();
                    filmeSelecionado = null;
                }
            });
            builder.setNegativeButton("Não", null);
            AlertDialog dialog = builder.create();
            dialog.setTitle("Confirmação de operação");
            dialog.show();

        }else {
            Toast toast = Toast.makeText(getApplicationContext(), "Nunhum filme selecionado!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(ListaFilmesAssistidosActivity.this,FilmesActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onResume(){
        super.onResume();
        this.carregarLista();
    }

}
