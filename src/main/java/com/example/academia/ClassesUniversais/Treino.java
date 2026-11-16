package com.example.academia.ClassesUniversais;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class Treino {

    private final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.of("America/Sao_Paulo"));
    private Instant data;
    private final int idTreino;
    private String nomeTreino;
    private String conteudo;
    private ArrayList<Exercicio> exercicios = new ArrayList<>();

    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }

    public Treino(String nomeTreino, String conteudo, ArrayList<Exercicio> exercicios, String data) {
        this.idTreino = Id(BancoDeDados.getInstance().treinos);
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
}