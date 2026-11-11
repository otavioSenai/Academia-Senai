package com.example.academia.ClassesUniversais;

public abstract class Usuario {
    private Integer id;
    private String nome;
    private String senha;
    private String senhaOriginal; // Armazena a senha legível
    private final String cpf;

    public Usuario(String nome, String senha, String cpf){
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
        this.senhaOriginal = "Não registrada";
    }

    public void gerarId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public boolean setNome(String nome){
        if(nome.isBlank()){
            return false;
        }
        else{
            this.nome = nome;
            return true;
        }
    }

    public String getCpf(){
        return this.cpf;
    }

    public String getSenha(){
        return this.senha;
    }

    public boolean setSenha(String senha){
        if(senha.isBlank()){
            return false;
        }
        else{
            this.senha = senha;
            return true;
        }
    }

    public String getSenhaOriginal(){
        return this.senhaOriginal;
    }

    public void setSenhaOriginal(String s){
        this.senhaOriginal = s;
    }
}