package com.example.academia.ClassesUniversais;

import com.example.academia.TrocaProfessor;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import com.example.academia.Treinador.Treinador;

import java.util.HashMap;
import java.util.Map;

public class BancoDeDados {

    private static BancoDeDados banco;

    public Map <Integer, Plano> planos = new HashMap();

    public Map <Integer, Custo> custos = new HashMap<>();
    public Map <Integer, Aluno> alunos = new HashMap<>();
    public Map <Integer, Treinador> treinadores = new HashMap<>();
    public Map <Integer, Administrativo> administrativos = new HashMap<>();

    public Map <Integer, Exercicio> exercicios = new HashMap<>();
    public Map <Integer, Treino> treinos = new HashMap<>();

    public Map <Integer, FeedbackProfessor> feedbacksProfessores = new HashMap<>();
    public Map <Integer, TrocaProfessor> trocasTreinadores = new HashMap<>();

    public ModalidadeEnums[] modalidadeEnums = ModalidadeEnums.values();

    private BancoDeDados(){}

    public static BancoDeDados getInstance(){
        if(banco == null){
            banco = new BancoDeDados();
        }
        return banco;
    }
}