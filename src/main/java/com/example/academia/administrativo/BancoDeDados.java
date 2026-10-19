package com.example.academia.administrativo;

import com.example.academia.ClassesUniversais.*;

import java.util.ArrayList;

public class BancoDeDados {

    private static BancoDeDados banco;

    ArrayList<Departamento> departamentos = new ArrayList<>();
    ArrayList<Plano> planos = new ArrayList<>();
    ArrayList<Funcionario> funcionarios = new ArrayList<>();
    ArrayList<Usuario> usuarios = new ArrayList<>();
    ArrayList<Custo> custos = new ArrayList<>();

    private BancoDeDados(){}

    public static BancoDeDados getInstance(){
        if(banco == null){
            banco = new BancoDeDados();
        }
        return banco;
    }

}