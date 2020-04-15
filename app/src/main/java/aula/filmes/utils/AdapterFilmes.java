package aula.filmes.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aula.filmes.model.Filme;
import aula.filmes.R;

public class AdapterFilmes extends BaseAdapter {

    private final List<Filme> filmes;
    private final Activity act;

    public AdapterFilmes(List<Filme> filmes, Activity act) {
        this.filmes = filmes;
        this.act = act;
    }

    @Override
    public int getCount() {
        return filmes.size();
    }

    @Override
    public Object getItem(int position) {
        return filmes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.activity_lista_personalizada, parent, false);
        Filme filme = filmes.get(position);

        TextView titulo = view.findViewById(R.id.lista_filme_personalizada_titulo);
        TextView ano = view.findViewById(R.id.lista_filme_personalizada_ano);
        ImageView poster = view.findViewById(R.id.lista_filme_personalizada_imagem);

        if(filmes.get(position).getTitulo() != null){
            titulo.setText(filme.getTitulo());
            ano.setText(filme.getAno());
            poster.setImageBitmap(filmes.get(position).getPoster());
        }else{
            titulo.setText("Filme não encontrado");
            ano.setText("");
            poster.setImageResource(R.drawable.notfound);
        }

        return view;
    }
}