package com.example.academia.ClassesUniversais;

import com.example.academia.aluno.Aluno;
import com.example.academia.professor.Professor;

import java.util.Scanner;

public class TrocaProfessor {

    private int id;
    public String comentario;

    //Escreva isso no aluno kenen
    public void setComentario(Scanner entrada){
        System.out.println("Escreva Um Comentário Para Trocar De Professor");
        String comentario = entrada.nextLine();
    }

    public void getComentario(int idAluno, int idProfessor){

        if(idAluno <= 0 || idProfessor <= 0){
            System.out.println("Valores Inválidos");
        }
        else{
            Aluno aluno = null;

            for(Aluno al : BancoDeDados.getInstance().alunos){
                if(al.getId() == idAluno){
                    aluno = al;
                    System.out.println("Aluno: " + al.getNome());
                    System.out.println(this.comentario);
                    break;
                }
            }
            if(aluno == null){
                System.out.println("Aluno Não Encontrado");
            }
            else{
                Professor profe = null;
                for(Professor fessor : BancoDeDados.getInstance().professores){
                    if(fessor.getId() == idProfessor){
                        profe = fessor;
                        System.out.println("Professor Solicitado Para Ser Trocado: " + fessor.getNome());
                        break;
                    }
                }
                if(profe == null){
                    System.out.println("Professor Não Encontrado");
                }
            }
        }
    }
}
