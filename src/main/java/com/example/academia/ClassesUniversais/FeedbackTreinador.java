package com.example.academia.ClassesUniversais;

public class FeedbackTreinador {

    private int id;
    public String comentario;
    public int qtdEstrelas;

    //Mudar o Comentário
    public void addComentario(String comentario, int qtdEstrelas){

        this.comentario = comentario;
        this.qtdEstrelas = qtdEstrelas;
    }

    //Pegar o Comentário
    public boolean getComentario(int idAluno){

        if(BancoDeDados.getInstance().alunos.containsKey(idAluno)){
            System.out.println("Aluno: " + BancoDeDados.getInstance().alunos.get(idAluno) + "\n");
            System.out.println(this.comentario);
            System.out.println("Estrelas: " + this.qtdEstrelas);
            return true;
        }
        return false;
    }
}