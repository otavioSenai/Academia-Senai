package com.example.academia.professor;

import com.example.academia.ClassesUniversais.Modalidade;
import com.example.academia.ClassesUniversais.Funcionario;

import java.util.ArrayList;

public class Treinador extends Funcionario {
    private ArrayList<Modalidade> modalidades;
    private String Especialidade;

    public Treinador(int id, String nome, String senha, String cpf, double salario) {
        super(id, nome, senha, cpf, salario);

    }

    public ArrayList<Modalidade> getDepartamentos() {
        return modalidades;
    }

    public void setDepartamentos(ArrayList<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }
    public void mostrarCarteirinhaProfessor(){
    System.out.println("========================================");
            System.out.println("        ACADEMIA ÍCARO");
            System.out.println("        CARTEIRINHA DO PROFESSOR");
            System.out.println("========================================");
            System.out.println("----------------------------------------");
            System.out.println("----------------------------------------");
            System.out.println("ESPECIALIDADE: " + Especialidade);
            System.out.println("========================================");}
}
