package com.example.academia.Treinador;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.Exercicio;
import com.example.academia.ClassesUniversais.Funcionario;
import com.example.academia.ClassesUniversais.Treino;
import java.util.ArrayList;

public class Treinador extends Funcionario {
    private String Especialidade;

    public Treinador(int id, String nome, String senha, String cpf, double salario) {
        super(id, nome, senha, cpf, salario);
    }

    //Pega a Especialidade
    public String getEspecialidade() {
        return Especialidade;
    }

    //Muda a Especialidade
    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.isBlank()) {
            System.out.println("Dados Invalidos");
        } else {
            Especialidade = especialidade;
        }
    }

    //Cadastra o Exercicio
    public void cadastrarExercicio(int idExercicio, String nomeExercicio, String descricao, int repeticaoExercicio, int serieExercicio, String experiencia){
        if(idExercicio <= 0 || nomeExercicio.isBlank() || descricao.isBlank() || repeticaoExercicio <=0 || serieExercicio <= 0 || experiencia.isBlank()){
            System.out.println("Valores Inválidos");
        }
        else{
            Exercicio exercicio = new Exercicio(idExercicio, nomeExercicio, descricao, repeticaoExercicio, serieExercicio, experiencia);
            BancoDeDados.getInstance().exercicios.put(idExercicio, exercicio);
        }
    }

    //Remove o Exercicio
    public void removerExercicio(int idExercicio){
        if(BancoDeDados.getInstance().exercicios.containsKey(idExercicio)){
            BancoDeDados.getInstance().exercicios.remove(idExercicio);
        }
        else{
            System.out.println("Exercicio Não Encontrado");
        }
    }

    //Cadastra o Treino
    public void cadastrarTreino(int idTreino, String nomeTreino, String conteudo, ArrayList<Exercicio> exercicios){
        if(idTreino <= 0 || nomeTreino.isBlank() || conteudo.isBlank() || exercicios == null){
            System.out.println("Valores Inválidos");
        }
        else{
            Treino treino = new Treino(idTreino, nomeTreino, conteudo, exercicios);
            BancoDeDados.getInstance().treinos.put(idTreino, treino);
        }
    }

    //Remover o Treino
    public void removerTreino(int idTreino){
        if(BancoDeDados.getInstance().treinos.containsKey(idTreino)){
            BancoDeDados.getInstance().treinos.remove(idTreino);
        }
        else{
            System.out.println("Valores Inválidos");
        }
    }

    //Mostra a Carteirinha
    public void mostrarCarteirinhaTreinador() {
        System.out.println("Nome: " + this.getNome());
        System.out.println("cpf: " + this.getCpf());
        System.out.println("Especialidade: " + this.Especialidade);
        System.out.println("Salário: " + this.getSalario());
    }
}