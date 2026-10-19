package com.example.academia.Gerente;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.Funcionario;

import javafx.application.Application;

import javafx.stage.Stage;

import java.util.ArrayList;

import java.util.Scanner;

public class Gerente extends Funcionario {
    Scanner sc= new Scanner(System.in);
    int id=sc.nextInt();
    String nome;
    String texto;

    BancoDeDados bancos = new BancoDeDados();

    public Gerente(String nome, String senha, String cpf, double salario, int id) {
        super(id, nome,senha, cpf, salario);

    }
    public void ListaProfessores(ArrayList<String>professores){

    }
    public void cadastrarProfessor(ArrayList<String> professores){
        do{

        System.out.println("Você deseja adicionar um professor ? (true/false)");
        boolean escolha=sc.nextBoolean();
        if(escolha==true){
            System.out.println("Digite O Nome Do Professor Que Deseja Adicionar:");
            String nome=sc.nextLine();
            System.out.println("Adicionado Na lista: " +"\n"+"A lista atual de alunos:");
            System.out.println("Deseja excluir algum nome da lista? (true/false");
            boolean escolha2=sc.nextBoolean();

            if(escolha2==true){
                System.out.println("Professor retirado da lista");
            }
            else{
                System.out.println("Professor Não retirado da lista");
            }
        }

        else{
            System.out.println("Nenhum professor foi adicionado");
        }

        System.out.println("Caso queira sair do Cadastro, digite: Sair  ");

        texto=sc.nextLine();

    }while (!texto.equalsIgnoreCase("Sair"));

    }


}