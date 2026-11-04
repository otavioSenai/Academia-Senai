package com.example.academia.ClassesUniversais;


import com.example.academia.aluno.Aluno;


import java.util.Map;


public class FeedbackTreinador {


    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }


    private int id = Id(BancoDeDados.getInstance().feedbacksTreinadores);
    private String comentario;
    private int qtdEstrelas;
    private Aluno aluno;


    //Mudar o Comentário
    public boolean addComentario(String comentario, int qtdEstrelas, int idAluno){
        if(comentario.isBlank() || qtdEstrelas < 1 || qtdEstrelas > 5 || !BancoDeDados.getInstance().validIdAluno(idAluno)){
            return false;
        }
        this.comentario = comentario;
        this.qtdEstrelas = qtdEstrelas;
        this.aluno = BancoDeDados.getInstance().alunos.get(idAluno);
        this.id = BancoDeDados.getInstance().feedbacksTreinadores.size();
        return true;
    }


    //Pegar o Feedback
    public void getFeedback(){
        System.out.println("Aluno: " + this.aluno.getNome() + "\n");
        System.out.println(this.comentario);
        System.out.println("Estrelas: " + this.qtdEstrelas);
    }


    //Pegar o Id
    public int getId(){
        return this.id;
    }


    //Pegar Comentario
    public String getComentario(){
        return this.comentario;
    }


    //Pegar a Quantidade de Estrelas
    public int getQtdEstrelas(){
        return this.qtdEstrelas;
    }


    //Pegar Aluno
    public Aluno getAluno(){
        return this.aluno;
    }
}
