package com.example.academia.Gerente;
import com.example.academia.ClassesUniversais.Departamento;
import com.example.academia.ClassesUniversais.Funcionario;
import com.example.academia.ClassesUniversais.Plano;
import com.example.academia.ClassesUniversais.Usuario;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Scanner;

public class BancoDeDados {
    private static BancoDeDados banco;

    ArrayList<Funcionario> funcionarios= new ArrayList<>();
    ArrayList<Departamento> departamentos= new ArrayList<>();
    ArrayList<Plano> planos= new ArrayList<>();
    ArrayList<Usuario> usuarios= new ArrayList<>();

    BancoDeDados(){}
    public static BancoDeDados getInstance(){
        if(banco==null){
            banco=new BancoDeDados();
        }
        return banco;

    }

}