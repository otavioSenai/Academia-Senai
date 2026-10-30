package com.example.academia.ClassesUniversais;

import java.util.ArrayList;

public class Treino {
    private final int idTreino;
    private String nomeTreino;
    private String conteudo;
    private ArrayList<Exercicio> exercicios = new ArrayList<>();

    public Treino(int idTreino, String nomeTreino, String conteudo, ArrayList<Exercicio> exercicios) {
        this.idTreino = idTreino;
        this.nomeTreino = nomeTreino;
        this.conteudo = conteudo;
        this.exercicios = exercicios;
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

    public void mostrarTreino(){

        System.out.println(this.nomeTreino);
        for(int i = 0; i < this.exercicios.size(); i++){
            System.out.println(i + " - " + this.exercicios.get(i));
        }

    }
}