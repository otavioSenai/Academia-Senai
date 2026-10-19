package com.example.academia.ClassesUniversais;

public class Custo {

    private String nome;
    private final int id;
    private String data;
    private double valor;

    public Custo(String nome, int id, String data, double valor){
        this.nome = nome;
        this.id = id;
        this.data = data;
        this.valor = valor;
    }

    //Métodos

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

    public String getData(){
        return this.data;
    }

    public void setData(String data) {
        if (data.isBlank()) {
            System.out.println("Valores Inválidos");
        } else {
            this.data = data;
        }
    }

}