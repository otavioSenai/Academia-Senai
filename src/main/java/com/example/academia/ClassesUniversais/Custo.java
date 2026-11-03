package com.example.academia.ClassesUniversais;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Custo {

    private String nome;
    private final int id;
    private final Instant data;
    private double valor;

    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }

    public Custo(String nome, double valor){
        this.nome = nome;
        this.id = Id(BancoDeDados.getInstance().custos);
        this.valor = valor;
        this.data = Instant.now();
        BancoDeDados.getInstance().grafico.addCusto(this.id);
    }

    //Métodos

    public void mostrarCusto(){
        System.out.println("Nome:" + this.nome);
        System.out.println("Id:" + this.id);
        System.out.println("Data:" + this.data);
        System.out.println("Valor:" + this.valor);
    }

    //Pegar Nome
    public String getNome(){
        return this.nome;
    }

    //Mudar Nome
    public boolean setNome(String nome) {
        if (nome.isBlank()) {
            return false;
        } else {
            this.nome = nome;
            return true;
        }
    }

    //Pegar Id
    public int getId(){
        return this.id;
    }

    //Pegar Valor
    public double getValor(){
        return this.valor;
    }

    //Mudar Valor
    public boolean setValor(double valor) {
        if (valor <= 0) {
            return false;
        } else {
            this.valor = valor;
            return true;
        }
    }

    //Pegar data
    public String getData(){
        ZoneId fusoBrasil = ZoneId.of("America/Sao_Paulo");
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return this.data.atZone(fusoBrasil).format(formatador);
    }
}