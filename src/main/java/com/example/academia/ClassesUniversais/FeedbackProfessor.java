package com.example.academia.ClassesUniversais;

import java.util.Scanner;

public class FeedbackProfessor {

    private int id;
    public String comentario;
    public int qtdEstrelas;

    //Mudar o Comentário
    public void setComentario(String comentario, int qtdEstrelas){

        this.comentario = comentario;
        this.qtdEstrelas = qtdEstrelas;
    }

    //Pegar o Comentário
    public void getComentario(int idAluno){

        if(BancoDeDados.getInstance().alunos.containsKey(idAluno)){
            System.out.println("Aluno: " + BancoDeDados.getInstance().alunos.get(idAluno) + "\n");
            System.out.println(this.comentario);
            System.out.println("Estrelas: " + this.qtdEstrelas);
        }
        else{
            System.out.println("Valores Inválidos");
        }
    }
}