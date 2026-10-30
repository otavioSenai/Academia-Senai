package com.example.academia.Treinador;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.Exercicio;
import com.example.academia.ClassesUniversais.Funcionario;
import com.example.academia.ClassesUniversais.Treino;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Treinador extends Funcionario {
    private String especialidade;

    public Treinador(String nome, String senha, String cpf, double salario, String especialidade) {
        super(nome, senha, cpf, salario);
        gerarId(BancoDeDados.getInstance().treinadores.size());
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

    //Cadastra o Exercicio
    public boolean cadastrarExercicio(int idExercicio, String nomeExercicio, String descricao, int repeticaoExercicio, int serieExercicio, String experiencia){
        if(idExercicio <= 0 || nomeExercicio.isBlank() || descricao.isBlank() || repeticaoExercicio <=0 || serieExercicio <= 0 || experiencia.isBlank()){
            return false;
        }
        else{
            Exercicio exercicio = new Exercicio(idExercicio, nomeExercicio, descricao, repeticaoExercicio, serieExercicio, experiencia);
            BancoDeDados.getInstance().exercicios.put(idExercicio, exercicio);
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
    public boolean cadastrarTreino(String data, int idTreino, String nomeTreino, String conteudo, ArrayList<Exercicio> exercicios){
        if(idTreino <= 0 || nomeTreino.isBlank() || conteudo.isBlank() || exercicios == null){
            return false;
        }
        else{
            Treino treino = new Treino(idTreino, nomeTreino, conteudo, exercicios, data);
            BancoDeDados.getInstance().treinos.put(idTreino, treino);
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