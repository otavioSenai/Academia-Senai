package com.example.academia.ClassesUniversais;

import com.example.academia.aluno.Aluno;

public class TrocarTreinador {

    private int id;
    private Aluno aluno;
    public String comentario;

    //Mudar o Comentário
    public boolean setComentario(String comentario, int idAluno) {
        if(!comentario.isBlank() || BancoDeDados.getInstance().validIdAluno(idAluno)) {
            this.comentario = comentario;
            this.aluno = BancoDeDados.getInstance().alunos.get(idAluno);
            this.id = BancoDeDados.getInstance().trocarTreinadores.size();
            return true;
        }
        return false;
    }

    //Pegar a Solicitação de Troca
    public void getSolicitacaoTroca (int idTreinador){

        if (!BancoDeDados.getInstance().validarIdTreinador(idTreinador)) {
            System.out.println("Valores Inválidos");
        } else {
            System.out.println("Aluno: " + this.aluno.getNome());
            System.out.println(this.comentario);
            System.out.println("Professor Solicitado Para Ser Trocado: " + BancoDeDados.getInstance().treinadores.get(idTreinador));
        }
    }

    //Pegar o id do comentário
    public int getId(){
        return this.id;
    }

    //Pegar o Aluno
    public Aluno getAluno(){
        return this.aluno;
    }
}