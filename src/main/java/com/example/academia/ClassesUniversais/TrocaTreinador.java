package com.example.academia.ClassesUniversais;

import java.util.Scanner;

public class TrocaTreinador {

    private int id;
    public String comentario;

    //Mudar o Comentário
    public void setComentario(Integer idTreinador, Scanner entrada) {

        if (BancoDeDados.getInstance().treinadores.containsKey(idTreinador)) {
            System.out.println("Escreva Um Comentário Para Trocar De Treinador");
            this.comentario = entrada.nextLine();
        } else {
            System.out.println("Treinador Não Encontrado");
        }
    }

    //Pegar o Comentário
    public void getComentario (int idAluno, int idTreinador){

        if (!BancoDeDados.getInstance().treinadores.containsKey(idTreinador) || !BancoDeDados.getInstance().alunos.containsKey(idAluno)) {
            System.out.println("Valores Inválidos");
        } else {
            System.out.println("Aluno: " + BancoDeDados.getInstance().alunos.get(idAluno));
            System.out.println(this.comentario);
            System.out.println("Professor Solicitado Para Ser Trocado: " + BancoDeDados.getInstance().treinadores.get(idTreinador));
        }
    }
}