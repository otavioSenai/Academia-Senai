package com.example.academia.ClassesUniversais;

import java.util.Scanner;

public class FeedbackProfessor {

    private int id;
    public String comentario;
    public int qtdEstrelas;

    //Mudar o Comentário
    public void setComentario(Scanner entrada){
        System.out.println("Escreva Um Comentário");
        String comentario = entrada.nextLine();

        System.out.println("Digite o Número De Estrelas (1-5)");
        int qtdEstrelas = entrada.nextInt();

        if(qtdEstrelas < 1 || qtdEstrelas > 5 || comentario.isBlank()){
            System.out.println("Valores Inválidos");
        }
        else{
            //Inicializar Feedback
        }
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