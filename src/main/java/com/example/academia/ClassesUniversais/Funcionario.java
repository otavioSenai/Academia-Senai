package com.example.academia.ClassesUniversais;

public class Funcionario extends Usuario{
    String departamento;
    double salario;
    public Funcionario(int id, String nome, String senha, String cpf, double salario){
        super(id, nome, senha, cpf);
        this.salario = salario;
    }

    protected double getSalario(){
        return this.salario;
    }

    protected void setSalario(double salario) {

        if (salario <= 0) {
            System.out.println("Valores Inválidos");
        } else {
            this.salario = salario;
        }
    }
}