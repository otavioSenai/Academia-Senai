package com.example.academia.ClassesUniversais;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Custo {

    private String nome;
    private final int id;
    private final Instant data;
    private double valor;

    public Custo(String nome, int id, String data, double valor){
        this.nome = nome;
        this.id = id;
        this.valor = valor;
        this.data = Instant.now();
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
    public void setNome(String nome) {

        if (nome.isBlank()) {
            System.out.println("Valores Inválidos");
        } else {
            this.nome = nome;
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
    public void setValor(double valor) {
        if (valor <= 0) {
            System.out.println("Valores Inválidos");
        } else {
            this.valor = valor;
        }
    }

    //Pegar data
    public String getData(){

        ZoneId fusoBrasil = ZoneId.of("America/Sao_Paulo");

        //Exibe a data em um formato específico
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return this.data.atZone(fusoBrasil).format(formatador);
    }
}