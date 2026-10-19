package com.example.academia.ClassesUniversais;

import com.example.academia.aluno.Aluno;
import com.example.academia.professor.Professor;

import java.util.ArrayList;

public class BancoDeDados {

    private static BancoDeDados banco;

    public static ArrayList <Departamento> departamentos = new ArrayList<>();
    public static ArrayList <Plano> planos = new ArrayList<>();
    public static ArrayList <Custo> custos = new ArrayList<>();
    public static ArrayList <Aluno> alunos= new ArrayList<>();
    public static ArrayList <Professor> professores= new ArrayList<>();



    private BancoDeDados(){}

    public static BancoDeDados getInstance(){
        if(banco == null){
            banco = new BancoDeDados();
        }
        return banco;
    }


}