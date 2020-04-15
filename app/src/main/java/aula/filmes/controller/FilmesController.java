package aula.filmes.controller;

import android.content.Context;

import java.io.File;
import java.util.List;

import aula.filmes.model.Filme;
import aula.filmes.model.FilmeDAO;


public class FilmesController {
    private FilmeDAO model;
    private List<Filme> listaFilmes;

    public FilmesController(Context contexto) {
        model = new FilmeDAO(contexto);
    }

    public void alterarFilme(Filme filmeSelecionado){
        model.alterar(filmeSelecionado);
    }

    public List<Filme> listarAssistir() {
        listaFilmes = model.listarAssistir();
        return listaFilmes;
    }

    public List<Filme> listarAssistidos(){
        listaFilmes = model.listarAssistidos();
        return listaFilmes;
    }

    public void cadastrar(Filme filmeObj) {
        model.cadastrar(filmeObj);
    }

    public void deletar(Filme filmeSelecionado) {
        model.deletar(filmeSelecionado);
    }

    public void close() {
        model.close();
    }


}
