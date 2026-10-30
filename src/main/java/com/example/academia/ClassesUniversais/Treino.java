package com.example.academia.ClassesUniversais;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Treino {

    private final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.of("America/Sao_Paulo"));
    private Instant data;
    private final int idTreino;
    private String nomeTreino;
    private String conteudo;
    private ArrayList<Exercicio> exercicios = new ArrayList<>();

    public Treino(int idTreino, String nomeTreino, String conteudo, ArrayList<Exercicio> exercicios, String data) {
        this.idTreino = idTreino;
        this.nomeTreino = nomeTreino;
        this.conteudo = conteudo;
        this.exercicios = exercicios;
        this.data = Instant.from(this.formatador.parse(data));
    }

    public Instant getData(){
        return this.data;
    }

    public void setData(String data){
        this.data = Instant.from(this.formatador.parse(data));
    }

    public int getIdTreino() {
        return idTreino;
    }

    public String getNomeTreino() {
        return nomeTreino;
    }

    public boolean setNomeTreino(String nomeTreino) {
        if(nomeTreino.isBlank()) {
            return false;
        }
        this.nomeTreino = nomeTreino;
        return true;
    }

    public String getConteudo() {
        return conteudo;
    }

    public boolean setConteudo(String conteudo) {
        if(conteudo.isBlank()){
            return false;
        }
        this.conteudo = conteudo;
        return true;
    }

    public void mostrarTreino(){
        System.out.println(this.nomeTreino);
        for(int i = 0; i < this.exercicios.size(); i++){
            System.out.println(i + " - " + this.exercicios.get(i));
        }
    }
}