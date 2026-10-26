package com.example.academia.ClassesUniversais;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Plano {

    private String nome;
    private final int id;
    private String descricao;
    private double valor;

    private Instant validade;
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.of("America/Sao_Paulo"));
    private boolean status = true;

    private String modalidade;

    public Plano(String nome, int id, String descricao, double valor, String validade){
        this.nome = nome;
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.validade = Instant.from(this.formatador.parse(validade));
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

    //Ver se o tempo de pagamento passou
    public void tempoPagar(){
        Instant Agora = Instant.now();

        if(Agora.isAfter(this.validade)){
            System.out.println("Pagamento Atrasado");
            this.status = false;
        }
        else{
            System.out.println("Pagamento em Dia");
            this.status = true;
        }
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
    public void addValidade(String validade){
        if(validade.isBlank()){
            System.out.println("Valores Inválidos");
        }
        else {
            this.validade = Instant.from(this.formatador.parse(validade));
        }
    }
}