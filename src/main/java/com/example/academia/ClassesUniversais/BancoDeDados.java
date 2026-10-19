package com.example.academia.ClassesUniversais;

import com.example.academia.aluno.Aluno;
import com.example.academia.professor.Professor;

import java.util.ArrayList;

public class BancoDeDados {

    private static BancoDeDados banco;

    ArrayList<Departamento> departamentos = new ArrayList<>();
    ArrayList<Plano> planos = new ArrayList<>();
    ArrayList<Funcionario> funcionarios = new ArrayList<>();
    ArrayList<Usuario> usuarios = new ArrayList<>();
    ArrayList<Custo> custos = new ArrayList<>();
    ArrayList <Aluno> alunos= new ArrayList<>();
    ArrayList <Professor> professores= new ArrayList<>();



    private BancoDeDados(){}

    public static BancoDeDados getInstance(){
        if(banco == null){
            banco = new BancoDeDados();
        }
        return banco;
    }


}