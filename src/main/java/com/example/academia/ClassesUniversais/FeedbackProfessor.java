package com.example.academia.ClassesUniversais;

import com.example.academia.aluno.Aluno;

import java.util.Scanner;

public class FeedbackProfessor {

    private int id;
    public String comentario;
    public int qtdEstrelas;


    public void setComentario(Scanner entrada){
        System.out.println("Escreva Um Comentário");
        String comentario = entrada.nextLine();

        System.out.println("Digite o Número De Estrelas (1-5)");
        int qtdEstrelas = entrada.nextInt();

        if(qtdEstrelas < 1 || qtdEstrelas > 5){
            System.out.println("Valores Inválidos");
        }
        else{
            //Inicializa o FeedbackProfessor
        }
    }

    //Pegar o Comentário
    public void getComentario(int id){

        if(id <= 0){
            System.out.println("Valores Inválidos");
        }
        else{
            Aluno aluno = null;
            for(Aluno al : BancoDeDados.getInstance().alunos){
                if(al.getId() == id){
                    aluno = al;
                    System.out.println("Aluno: " + al.getNome());
                    System.out.println(this.comentario);
                    System.out.println("Estrelas: " + this.qtdEstrelas);
                    break;
                }
            }
            if(aluno == null){
                System.out.println("Aluno Não Encontrado");
            }
        }
    }
}