package com.example.academia.ClassesUniversais;

public class Usuario {

    protected final int id;
    protected String nome;
    private String senha;
    protected final String cpf;

    public Usuario(int id, String nome, String senha, String cpf){
        this.nome = nome;
        this.id = id;
        this.senha = senha;
        this.cpf = cpf;
    }

    public int getId(){return this.id;}

    public String getNome(){return this.nome;}

    protected void setNome(String nome){
        if(nome.isEmpty()){
            System.out.println("Valores Inválidos");
        }
        else{
            this.nome = nome;
        }
    }

    public String getCpf(){return this.cpf;}

    public String getSenha(){return this.senha;}

    private void setSenha(String senha){
        if(senha.isEmpty()){
            System.out.println("Valores inválidos");
        }
        else{
            this.senha = senha;
        }
    }

}
