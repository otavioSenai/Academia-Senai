package com.example.academia.ClassesUniversais;

import com.example.academia.Treinador.Treinador;

import java.util.HashMap;
import java.util.Map;

public class Modalidade {

    private int id = BancoDeDados.getInstance().modalidades.length;
    private Map <Integer,Treinador> treinadores = new HashMap();
    private String nome;
    private String descricao;

    //Escolhe a Modalidade
    public boolean escolherModalidade(String modalidadeEscolhida){
        if(BancoDeDados.getInstance().compareEnums(modalidadeEscolhida)) {
            this.nome = modalidadeEscolhida;
            return true;
        }
        return false;
    }

    //pegar id
    public int getId(){
        return this.id;
    }

    //Pega a Modalidade
    public String getNome(){
        return this.nome;
    }

    //Mudar a Descricao
    public boolean setDescricao(String descricao){
        if(descricao.isBlank()){
            return false;
        }
        this.descricao = descricao;
        return true;
    }

    //Pegar Descricao
    public String getDescricao(){
        return this.descricao;
    }

    //Adiciona um Treinador
    public boolean addTreinador(int id, Treinador treinador){
        if(BancoDeDados.getInstance().validarIdTreinador(id)){
            treinadores.put(id, treinador);
            return true;
        }
        else{
            return false;
        }
    }

    //Remove Um Treinador
    public boolean removeTreinador(int id){
        if(BancoDeDados.getInstance().validarIdModalidade(id)){
            treinadores.remove(id);
            return true;
        }
        return false;
    }

    //Pega o Treinador
    public Treinador getTreinador(int id){
            return this.treinadores.get(id);
    }

    //Pegar os Treinadores
    public Map<Integer, Treinador> getTreinadores(){return this.treinadores;}

    //Mostrar os Treinadores
    public boolean showTreinadores(){
        if(treinadores.isEmpty()){
            return false;
        }
        for(Treinador t : treinadores.values()){
            System.out.println(t.getId() + " - " + t.getNome());
        }
        return true;
    }
}