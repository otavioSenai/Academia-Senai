package com.example.academia.ClassesUniversais;

import com.example.academia.aluno.Aluno;


import java.util.Map;

public class TrocarTreinador {

    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }

    private int id = Id(BancoDeDados.getInstance().trocarTreinadores);
    private Aluno aluno;
    public String comentario;

    //Mudar o Comentário
    public boolean setComentario(String comentario, int idAluno) {
        if(!comentario.isBlank() && BancoDeDados.getInstance().validIdAluno(idAluno)) {
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