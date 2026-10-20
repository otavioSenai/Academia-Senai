package com.example.academia.ClassesUniversais;

import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import com.example.academia.professor.Professor;

import java.util.ArrayList;

public class BancoDeDados {

    private static BancoDeDados banco;

    public ArrayList <Departamento> departamentos = new ArrayList<>();
    public ArrayList <Plano> planos = new ArrayList<>();
    public ArrayList <Custo> custos = new ArrayList<>();
    public ArrayList <Aluno> alunos= new ArrayList<>();
    public ArrayList <Professor> professores= new ArrayList<>();
    public ArrayList<Administrativo> administrativos = new ArrayList<>();



    private BancoDeDados(){}

    public static BancoDeDados getInstance(){
        if(banco == null){
            banco = new BancoDeDados();
        }
        return banco;
    }


}