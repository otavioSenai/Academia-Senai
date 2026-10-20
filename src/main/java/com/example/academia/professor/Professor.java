package com.example.academia.professor;

import com.example.academia.ClassesUniversais.Departamento;
import com.example.academia.ClassesUniversais.Funcionario;
import com.example.academia.ClassesUniversais.Usuario;

import java.util.ArrayList;

public class Professor extends Funcionario {
    private ArrayList<Departamento> departamentos;

    public Professor(int id, String nome, String senha, String cpf, double salario, ArrayList<Departamento> departamentos) {
        super(id, nome, senha, cpf, salario);
        this.departamentos = departamentos;
    }

    public ArrayList<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(ArrayList<Departamento> departamentos) {
        this.departamentos = departamentos;
    }
}
