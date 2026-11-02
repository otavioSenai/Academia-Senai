package com.example.academia.ClassesUniversais;

import java.util.HashMap;
import java.util.Map;

public class Plano {

    private String nome;
    private final int id;
    private String descricao;
    private double valor;
    private int mesesValidade;

    private Map<Integer, Modalidade> modalidade = new HashMap<>();

    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }

    public Plano(String nome, String descricao, double valor, int mesesValidade){
        this.nome = nome;
        this.id = Id(BancoDeDados.getInstance().planos);
        this.descricao = descricao;
        this.valor = valor;
        this.mesesValidade = mesesValidade;
    }

    //Métodos

    //Definir Modalidade
    public boolean addModalidade(int id){
        if(BancoDeDados.getInstance().validarIdModalidade(id)){
            Modalidade m = BancoDeDados.getInstance().modalidades[id];
            this.modalidade.put(m.getId(), m);
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

    //Pegar a Validade
    public int getMesesValidade(){
        return this.mesesValidade;
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