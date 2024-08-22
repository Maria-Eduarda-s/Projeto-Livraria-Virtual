package Livro;

import java.util.Arrays;

abstract class Livro {

    public String titulo;
    public String[] autores;
    public String editora;
    public float preco;

    public Livro(String titulo, String[] autores, String editora, float preco) {
        this.titulo = titulo;
        this.autores = autores;
        this.editora = editora;
        this.preco = preco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String[] getAutores() {
        return autores;
    }

    public void setAutores(String[] autores) {
        this.autores = autores;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        String autoresStr = Arrays.toString(autores);
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autores='" +  autoresStr +
                ", editora='" + editora + '\'' +
                ", preco=" + preco +
                '}';
    }
}