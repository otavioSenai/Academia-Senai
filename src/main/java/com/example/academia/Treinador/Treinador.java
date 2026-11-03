package com.example.academia.Treinador;

import com.example.academia.ClassesUniversais.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Treinador extends Funcionario {
    private String especialidade;
    private HashMap<Integer, TrocarTreinador> solicitarTroca = new HashMap<>();
    private HashMap<Integer, FeedbackTreinador> feedback = new HashMap<>();

    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }

    public Treinador(String nome, String senha, String cpf, double salario, String especialidade) {
        super(nome, senha, cpf, salario);
        gerarId(Id(BancoDeDados.getInstance().treinadores));
        this.especialidade = especialidade;
    }

    //Pega a Especialidade
    public String getEspecialidade() {
        return especialidade;
    }

    //Muda a Especialidade
    public boolean setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.isBlank()) {
            return false;
        } else {
            especialidade = especialidade;
            return true;
        }
    }

    //Adicionar Solicitação de Troca
    public boolean addTroca(String comentario, int idAluno){
        TrocarTreinador t = new TrocarTreinador();
        if(t.setComentario(comentario, idAluno)) {
            solicitarTroca.put(t.getId(), t);
            BancoDeDados.getInstance().trocarTreinadores.put(t.getId(), t);
            return true;
        }
        return false;
    }

    //Pegar Solicitação de Troca
    public Map<Integer, TrocarTreinador> getSolicitacaoTroca(){
        return this.solicitarTroca;
    }

    //Adicionar Feedback
    public boolean addFeedback(String comentario, int qtdEstrelas, int idAluno){
        FeedbackTreinador f = new FeedbackTreinador();

        if(f.addComentario(comentario, qtdEstrelas, idAluno)) {
            feedback.put(f.getId(), f);
            BancoDeDados.getInstance().feedbacksTreinadores.put(f.getId(), f);
            return true;
        }
        return false;
    }

    //Pegar Feedback
    public Map<Integer, FeedbackTreinador> getFeedback(){
        return this.feedback;
    }

    //Cadastra o Exercicio
    public boolean cadastrarExercicio(String nomeExercicio, String descricao, int repeticaoExercicio, int serieExercicio, String experiencia){
        if(nomeExercicio.isBlank() || descricao.isBlank() || repeticaoExercicio <=0 || serieExercicio <= 0 || experiencia.isBlank()){
            return false;
        }
        else{
            Exercicio exercicio = new Exercicio(nomeExercicio, descricao, repeticaoExercicio, serieExercicio, experiencia);
            BancoDeDados.getInstance().exercicios.put(exercicio.getIdExercicio(), exercicio);
            return true;
        }
    }

    //Remove o Exercicio
    public boolean removerExercicio(int idExercicio){
        if(BancoDeDados.getInstance().exercicios.containsKey(idExercicio)){
            BancoDeDados.getInstance().exercicios.remove(idExercicio);
            return true;
        }
        else{
            return false;
        }
    }

    //Cadastra o Treino
    public boolean cadastrarTreino(String data,String nomeTreino, String conteudo, ArrayList<Exercicio> exercicios){
        if(data.isBlank() || nomeTreino.isBlank() || conteudo.isBlank() || exercicios == null){
            return false;
        }
        else{
            Treino treino = new Treino(nomeTreino, conteudo, exercicios, data);
            BancoDeDados.getInstance().treinos.put(treino.getIdTreino(), treino);
            return true;
        }
    }

    //Remover o Treino
    public boolean removerTreino(int idTreino){
        if(BancoDeDados.getInstance().treinos.containsKey(idTreino)){
            BancoDeDados.getInstance().treinos.remove(idTreino);
            return true;
        }
        else{
            return false;
        }
    }

    //Mostra a Carteirinha
    public void mostrarCarteirinhaTreinador() {
        System.out.println("Nome: " + this.getNome());
        System.out.println("cpf: " + this.getCpf());
        System.out.println("Especialidade: " + this.especialidade);
        System.out.println("Salário: " + this.getSalario());
    }
}