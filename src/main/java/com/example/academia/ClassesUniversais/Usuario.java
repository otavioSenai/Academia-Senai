package com.example.academia.ClassesUniversais;

public class Usuario {

    protected int id;
    protected String nome;
    private String senha;
    protected final String cpf;

    public Usuario(int id, String nome, String senha, String cpf){
        this.nome = nome;
        this.id = id;
        this.senha = senha;
        this.cpf = cpf;
    }

    public int id(){
        return this.id;
    }
    public String getNome(){
        return this.nome;
    }
    public String getCpf(){
        return this.cpf;
    }
    public String getSenha(){
        return this.senha;
    }

}
