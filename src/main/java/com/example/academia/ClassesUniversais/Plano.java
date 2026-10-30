package com.example.academia.ClassesUniversais;

import java.util.HashMap;
import java.util.Map;

public class Plano {

    private String nome;
    private final int id;
    private String descricao;
    private double valor;

    private Map<Integer, Modalidade> modalidade = new HashMap<>();

    public Plano(String nome, int id, String descricao, double valor){
        this.nome = nome;
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    //Métodos

    //Definir Modalidade
    public boolean addModalidade(String modalidadeEscolhida){

        int chaveVazia = this.modalidade.size();
        Modalidade m = this.modalidade.get(chaveVazia);

        if(m == null){
            m = new Modalidade();
            m.escolherModalidade(modalidadeEscolhida);
            this.modalidade.put(chaveVazia, m);
            return true;
        }
        return false;
    }

    //Mudar Modalidade
    public boolean setModalidade(String modalidadeEscolhida){
        if(modalidadeEscolhida.isBlank()){
            return false;
        }
        Modalidade m = new Modalidade();
        m.escolherModalidade(modalidadeEscolhida);
        this.modalidade.put(m.getId(), m);
        return true;
    }

    //Pegar a Modalidade
    public Map<Integer, Modalidade> getModalidade(){
        return modalidade;
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
            return true;
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
    public boolean setDescricao(String descricao) {
        if (descricao.isBlank()) {
            return false;
        } else {
            this.descricao = descricao;
            return true;
        }
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
}