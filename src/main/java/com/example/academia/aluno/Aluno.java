package com.example.academia.aluno;

import com.example.academia.ClassesUniversais.Departamento;
import com.example.academia.ClassesUniversais.Plano;
import com.example.academia.ClassesUniversais.Usuario;

import java.util.ArrayList;

public class Aluno extends Usuario {
    private int idade;
    private double altura;
    private double peso;
    private Plano plano;
    private String intensidade;
    private ArrayList<Departamento> departamentos;
    private ArrayList<String> aluno = new ArrayList<>();


    public Aluno(int id, String nome, String senha, String cpf) {
        super(id, nome, senha, cpf);
    }

}
