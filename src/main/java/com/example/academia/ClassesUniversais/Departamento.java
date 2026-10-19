package com.example.academia.ClassesUniversais;

import java.util.ArrayList;

public class Departamento {

    private String nome;
    private final int id;
    private String descricao;

    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();
    private ArrayList<Plano> planos = new ArrayList<>();

    public Departamento(String nome, int id, String descricao){
        this.nome = nome;
        this.id = id;
        this.descricao = descricao;
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

    public String getDescricao(){
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao.isBlank()) {
            System.out.println("Valores Inválidos");
        } else {
            this.descricao = descricao;
        }
    }
}
