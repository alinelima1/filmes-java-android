package aula.filmes.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import aula.filmes.controller.FilmesController;
import aula.filmes.model.Filme;
import aula.filmes.R;

public class DetalhesFilmeActivity extends Activity {
    private TextView titulo;
    private TextView ano;
    private TextView duracao;
    private TextView genero;
    private TextView diretor;
    private ImageView poster;
    private ProgressDialog load;
    private String tela;
    Button btnassistir;
    Button btnassistido;
    FilmesController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhesfilme);

        titulo = (TextView)findViewById(R.id.tvTituloDetalhes);
        ano = (TextView)findViewById(R.id.tvAnoDetalhes);
        duracao = (TextView)findViewById(R.id.tvDuracaoDetalhes);
        genero = (TextView)findViewById(R.id.tvGeneroDetalhes);
        diretor = (TextView)findViewById(R.id.tvDiretorDetalhes);
        poster = (ImageView)findViewById(R.id.IvPoster);
        btnassistir = (Button)findViewById(R.id.BtnAssitir);
        btnassistido = (Button)findViewById(R.id.BtnAssistido);

        final Filme filmeObj = (Filme)getIntent().getSerializableExtra("filme");
        tela = (String)getIntent().getSerializableExtra("tela");
        byte[] byteArray = getIntent().getByteArrayExtra("poster");
        Bitmap posterRec = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        filmeObj.setPoster(posterRec);

        titulo.setText("Título do filme: " + filmeObj.getTitulo());
        ano.setText("Ano de Lançamento: " + filmeObj.getAno());
        duracao.setText("Duração do Filme: " + filmeObj.getDuracao());
        genero.setText("Gênero: " + filmeObj.getGenero());
        diretor.setText("Diretor: " + filmeObj.getDiretor());
        poster.setImageBitmap(filmeObj.getPoster());
        if(tela.equals("assistir") || tela.equals("assistidos")){
            btnassistir.setEnabled(false);
            btnassistido.setEnabled(false);
        }

        btnassistir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                filmeObj.setAssistir(true);
                controller = new FilmesController(DetalhesFilmeActivity.this);
                //Verificação para salvar ou cadastrar filme
                if(filmeObj.getId() == null){
                    SQLiteDatabase sql = null;
                    controller.cadastrar(filmeObj);
                }
                controller.close();
                onBackPressed();
            }
        });

        btnassistido.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                filmeObj.setAssistido(true);
                controller = new FilmesController(DetalhesFilmeActivity.this);
                if(filmeObj.getId() == null){
                    controller.cadastrar(filmeObj);
                }
                controller.close();
                onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        Intent intent;
        if(tela.equals("inicial")){
            intent = new Intent(DetalhesFilmeActivity.this,FilmesActivity.class);
        }else if(tela.equals("assistir")){
            intent = new Intent(DetalhesFilmeActivity.this,ListaFilmesAssistirActivity.class);
        }else{
            intent = new Intent(DetalhesFilmeActivity.this,ListaFilmesAssistidosActivity.class);
        }
        startActivity(intent);
        finish();
    }
}