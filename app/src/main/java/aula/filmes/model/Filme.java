package aula.filmes.model;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.Serializable;

public class Filme implements Serializable {
    private Long id;
    String titulo;
    String ano;
    String duracao;
    String genero;
    String diretor;
    boolean assistir = false;
    boolean assistido = false;
    private Bitmap poster;

    public Filme(String titulo, String ano, String duracao, String genero, String diretor){
        this.titulo = titulo;
        this.ano = ano;
        this.duracao = duracao;
        this.genero = genero;
        this.diretor = diretor;
        this.poster = null;
    }

    public Filme(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public boolean isAssistir() {
        return assistir;
    }

    public void setAssistir(boolean assistir) {
        this.assistir = assistir;
    }

    public boolean isAssistido() {
        return assistido;
    }

    public void setAssistido(boolean assistido) {
        this.assistido = assistido;
    }

    public Bitmap getPoster() {
        return poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

    public String toString(){
        return "Título: " + titulo + "\nAno: " + ano;
    }

}
