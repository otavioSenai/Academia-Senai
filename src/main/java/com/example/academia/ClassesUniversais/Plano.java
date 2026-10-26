package com.example.academia.ClassesUniversais;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Plano {

    private String nome;
    private final int id;
    private String descricao;
    private double valor;

    private String modalidade;

    public Plano(String nome, int id, String descricao, double valor){
        this.nome = nome;
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    //Métodos

    //Definir Modalidade
    public void setModalidade(String departamento, Scanner entrada){

        System.out.println("Insira qual departamento pertence ao plano");
        String modalidadeEscolhida = entrada.nextLine();

            Modalidade modalidade = new Modalidade();
            modalidade.escolherModalidade(modalidadeEscolhida);
    }

    //Pegar a Modalidade
    public String getModalidade(){
        return this.modalidade;
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
}