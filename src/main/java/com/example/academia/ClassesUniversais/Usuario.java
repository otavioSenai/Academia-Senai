package com.example.academia.ClassesUniversais;

import java.util.Map;

public abstract class Usuario {

    private Integer id;
    private String nome;
    private String senha;
    private final String cpf;

    public Usuario(String nome, String senha, String cpf){
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
    }

    public void gerarId(int id){
        this.id = id;
    }

    public int getId(){return this.id;}

    public String getNome(){return this.nome;}

    public boolean setNome(String nome){
        if(nome.isBlank()){
            return false;
        }
        else{
            this.nome = nome;
            return true;
        }
    }

    public String getCpf(){return this.cpf;}

    public String getSenha(){return this.senha;}

    private boolean setSenha(String senha){
        if(senha.isBlank()){
            return false;
        }
        else{
            this.senha = senha;
            return true;
        }
    }
}