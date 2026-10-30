package com.example.academia.ClassesUniversais;

public class TrocarTreinador {

    private int id;
    public String comentario;

    //Mudar o Comentário
    public boolean setComentario(Integer idTreinador, String comentario) {
        if (BancoDeDados.getInstance().treinadores.containsKey(idTreinador)) {
            this.comentario = comentario;
            return true;
        }
        return false;
    }

    //Pegar o Comentário
    public void getComentario (int idAluno, int idTreinador){

        if (!BancoDeDados.getInstance().validarIdTreinador(idTreinador) || !BancoDeDados.getInstance().validIdAluno(idAluno)) {
            System.out.println("Valores Inválidos");
        } else {
            System.out.println("Aluno: " + BancoDeDados.getInstance().alunos.get(idAluno));
            System.out.println(this.comentario);
            System.out.println("Professor Solicitado Para Ser Trocado: " + BancoDeDados.getInstance().treinadores.get(idTreinador));
        }
    }
}