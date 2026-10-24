package com.example.academia.ClassesUniversais;

import com.example.academia.Dono.TrocaProfessor;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import com.example.academia.professor.Treinador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BancoDeDados {

    private static BancoDeDados banco;

    public List <Modalidade> modalidades = new ArrayList<>();
    public List <Plano> planos = new ArrayList<>();

    public Map <Integer, Custo> custos = new HashMap<>();
    public Map <Integer, Aluno> alunos = new HashMap<>();
    public Map <Integer, Treinador> treinadores = new HashMap<>();
    public Map <Integer, Administrativo> administrativos = new HashMap<>();

    public Map <Integer, Exercicio> exercicios = new HashMap<>();
    public Map <Integer, Treino> treinos = new HashMap<>();

    public Map <Integer, FeedbackProfessor> feedbacksProfessores = new HashMap<>();
    public Map <Integer, TrocarProfessor> trocasProfessores = new HashMap<>();

    private BancoDeDados(){}

    public static BancoDeDados getInstance(){
        if(banco == null){
            banco = new BancoDeDados();
        }
        return banco;
    }
}