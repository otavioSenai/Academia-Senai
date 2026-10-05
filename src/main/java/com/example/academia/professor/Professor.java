package com.example.academia.professor;

import com.example.academia.ClassesUniversais.Departamento;
import com.example.academia.ClassesUniversais.Usuario;

import java.util.ArrayList;

public class Professor extends Usuario {
    private ArrayList<Departamento> departamentos;

    public Professor(int id, String nome, String senha, String cpf) {
        super(id, nome, senha, cpf);
    }
}
