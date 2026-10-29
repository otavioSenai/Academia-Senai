package com.example.academia.ClassesUniversais;
import java.time.LocalDate;

public class Treino {
    private final int idTreino;
    private String nomeTreino;
    private String conteudo;
    private LocalDate data;

    public Treino(int idTreino, String nomeTreino, String conteudo, LocalDate data) {
        this.idTreino = idTreino;
        this.nomeTreino = nomeTreino;
        this.conteudo = conteudo;
        this.data = data;
    }

    public int getIdTreino() {
        return idTreino;
    }

    public String getNomeTreino() {
        return nomeTreino;
    }

    public void setNomeTreino(String nomeTreino) {
        this.nomeTreino = nomeTreino;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}