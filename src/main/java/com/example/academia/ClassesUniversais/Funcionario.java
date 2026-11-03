package com.example.academia.ClassesUniversais;

public abstract class Funcionario extends Usuario{
    double salario;
    public Funcionario(String nome, String senha, String cpf, double salario){
        super(nome, senha, cpf);
        this.salario = salario;
    }

    public double getSalario(){
        return this.salario;
    }

    protected boolean setSalario(double salario) {
        if (salario <= 0) {
            return false;
        } else {
            this.salario = salario;
            return true;
        }
    }
}