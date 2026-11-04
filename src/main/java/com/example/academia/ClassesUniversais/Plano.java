package com.example.academia.ClassesUniversais;


import java.util.HashMap;
import java.util.Map;


public class Plano {


    private String nome;
    private final int id;
    private String descricao;
    private double valor;
    private int mesesValidade;
    private double juros;


    private Map<Integer, Modalidade> modalidade = new HashMap<>();


    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }


    public Plano(String nome, String descricao, double valor, int mesesValidade, double juros){
        this.nome = nome;
        this.id = Id(BancoDeDados.getInstance().planos);
        this.descricao = descricao;
        this.valor = valor;
        this.mesesValidade = mesesValidade;
        this.juros = juros;
    }


    //Métodos


    //Adicionar Modalidade Do Banco de Dados
    public boolean addModalidade(int id){
        if(BancoDeDados.getInstance().validarIdModalidade(id)){
            Modalidade m = BancoDeDados.getInstance().modalidades.get(id);
            this.modalidade.put(m.getId(), m);
            return true;
        }
        return false;
    }


    //Pegar Juros
    public double getJuros(){
        return this.juros;
    }


    //Pegar a Validade
    public int getMesesValidade(){
        return this.mesesValidade;
    }


    //Mudar Validade
    public boolean setMesesValidade(int mesesValidade){
        if(mesesValidade <= 0){
            return false;
        }
        else{
            this.mesesValidade = mesesValidade;
            return true;
        }
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
            this.nome = nome;
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
