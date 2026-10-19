package com.example.academia.ClassesUniversais;

public class Plano {

    private String nome;
    private final int id;
    private String descricao;
    private double valor;
    private boolean validade;

    public Plano(String nome, int id, String descricao, double valor){
        this.nome = nome;
        this.id = id;
        this.descricao = descricao;
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

    //Pegar Descrição
    public String getDescricao(){
        return this.descricao;
    }

    //Mudar Descrição
    public void setDescricao(String descricao) {
        if (descricao.isBlank()) {
            System.out.println("Valores Inválidos");
        } else {
            this.descricao = descricao;
        }
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

    //Pega Validade
    public boolean getValidade(){
        return this.validade;
    }

    //Mudar Validade
    public void setValidade(boolean validade){
        this.validade = validade;
    }

}