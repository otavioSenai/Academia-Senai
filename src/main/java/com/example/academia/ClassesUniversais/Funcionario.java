package com.example.academia.ClassesUniversais;

public class Funcionario extends Usuario{

    double salario;

    public Funcionario(int id, String nome, String senha, String cpf, double salario){
        super(id, nome, senha, cpf);
        this.salario = salario;
    }

}
