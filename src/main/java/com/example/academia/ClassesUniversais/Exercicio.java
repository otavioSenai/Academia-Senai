package com.example.academia.ClassesUniversais;


import java.util.Map;


public class Exercicio {
    private final int idExercicio;
    private String descricao;
    private String nomeExercicio;
    private String experiencia;
    private int repeticaoExercicio;
    private int serieExercicio;


    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }


    public Exercicio(String nomeExercicio, String descricao, int repeticaoExercicio, int serieExercicio, String experiencia) {
        this.idExercicio = Id(BancoDeDados.getInstance().exercicios);
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


    public boolean setNomeExercicio(String nomeTreino){
        if(nomeTreino.isBlank()){
            return false;
        }
        this.nomeExercicio = nomeTreino;
        return true;
    }


    public String getDescricao() {
        return descricao;
    }


    public boolean setDescricao(String descricao) {
        if(descricao.isBlank()){
            return false;
        }
        this.descricao = descricao;
        return true;
    }


    public int getRepeticaoExercicio() {
        return repeticaoExercicio;
    }


    public boolean setRepeticaoExercicio(int repeticaoExercicio) {
        if(repeticaoExercicio <= 0){
            return false;
        }
        this.repeticaoExercicio = repeticaoExercicio;
        return true;
    }


    public int getSerieExercicio(){
        return serieExercicio;
    }


    public boolean setIdExercicio(int serieExercicio){
        if(serieExercicio <= 0){
            return false;
        }
        this.serieExercicio=serieExercicio;
        return true;
    }
    public String getExperiencia(){
        return experiencia;
    }


    public boolean setExperiencia(String experiencia){
        if(experiencia.isBlank()){
            return false;
        }
        this.experiencia=experiencia;
        return true;
    }
}
