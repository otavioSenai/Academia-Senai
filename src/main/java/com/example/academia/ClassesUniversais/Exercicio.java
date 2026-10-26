package com.example.academia.ClassesUniversais;

import java.time.LocalDate;

public class Exercicio {
    private final int idExercicio;
    private String descricao;
    private String nomeExercicio;
    private String experiencia;
    private int repeticaoExercicio;
    private int serieExercicio;


    public Exercicio(int idExercicio, String nomeExercicio, String descricao, int repeticaoExercicio, int serieExercicio, String experiencia) {
        this.idExercicio = idExercicio;
        this.nomeExercicio = nomeExercicio;
        this.descricao = descricao;
        this.repeticaoExercicio= repeticaoExercicio;
        this.serieExercicio=serieExercicio;
        this.experiencia=experiencia;
    }

    public int getIdExercicio() {

        return idExercicio;
    }

    public String getNomeExercicio() {

        return nomeExercicio;
    }

    public void setNomeExercicio(String nomeTreino)
    {
        this.nomeExercicio = nomeTreino;
    }

    public String getDescricao() {

        return descricao;
    }

    public void setDescricao(String descricao) {

        this.descricao = descricao;
    }

    public int getRepeticaoExercicioExercicio() {

        return repeticaoExercicio;
    }

    public void setDataExercicio(int repeticaoExercicio) {

        this.repeticaoExercicio = repeticaoExercicio;
    }
    public int getSerieExercicio(){
        return serieExercicio;
    }
    public void setIdExercicio(int serieExercicio){
        this.serieExercicio=serieExercicio;
    }
    public String getExperiencia(){
        return experiencia;
    }
    public void setExperiencia(){
        this.experiencia=experiencia;
    }

}
