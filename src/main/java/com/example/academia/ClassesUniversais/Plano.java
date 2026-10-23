package com.example.academia.ClassesUniversais;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class Plano {

    private String nome;
    private final int id;
    private String descricao;
    private double valor;

    private Instant validade;
    private boolean status;

    public Plano(String nome, int id, String descricao, double valor){
        this.nome = nome;
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;

    }

    //Métodos

    //Definir Departamentos
    public void setDepartamento(String departamento, Scanner entrada){

        System.out.println("Insira qual departamento pertence ao plano");
        String dp = entrada.nextLine();

        for()

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

    //Pegar Status
    public boolean getStatus(){
        return this.status;
    }

    //Pegar Validade
    public Instant getValidade(){
        return this.validade;
    }

    //Mudar Validade
    public void setValidade(String validade){
        this.validade.with(Instant.parse(validade));
    }


    //Ver se o tempo de pagamento passou
    public void tempoPagar(){
        Instant Agora = Instant.now();

        if(Agora.isAfter(validade)){
            System.out.println("Pagamento Atrasado");
            this.status = false;
        }
        else{
            System.out.println("Pagamento em Dia");
            this.status = true;
        }
    }

}