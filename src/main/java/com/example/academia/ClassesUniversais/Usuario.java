package com.example.academia.ClassesUniversais;

public class Usuario {

    protected String nome;
    private final String senha;
    protected String cpf;

    public Usuario(String nome, String senha, String cpf){
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
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
