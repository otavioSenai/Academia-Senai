package com.example.academia.ClassesUniversais;


import com.example.academia.Dono.Dono;
import com.example.academia.Gerente.Gerente;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import com.example.academia.Treinador.Treinador;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class BancoDeDados {


    private static BancoDeDados banco;


    public HashMap<Integer, Modalidade> modalidades = new HashMap<>();


    public Map <Integer, Plano> planos = new HashMap<>();
    public Map <Integer, AssinaturaPlano> assinaturaPlano = new HashMap<>();


    public Map <Integer, Custo> custos = new HashMap<>();
    public Map <Integer, Aluno> alunos = new HashMap<>();
    public Map <Integer, Treinador> treinadores = new HashMap<>();
    public Map <Integer, Administrativo> administrativos = new HashMap<>();
    public Map <Integer, Exercicio> exercicios = new HashMap<>();
    public Map <Integer, Treino> treinos = new HashMap<>();


    public Map <Integer, FeedbackTreinador> feedbacksTreinadores = new HashMap<>();
    public Map <Integer, TrocarTreinador> trocarTreinadores = new HashMap<>();


    public GraficoRenda grafico = new GraficoRenda();


    public Optional<Gerente> gerente = Optional.empty();


    public Optional<Dono> dono = Optional.empty();


    private BancoDeDados(){}


    public static BancoDeDados getInstance(){
        if(banco == null){
            banco = new BancoDeDados();
        }
        return banco;
    }


    //Pegar Modalidades
    public HashMap<Integer, Modalidade> getModalidade(){
        return this.modalidades;
    }


    //Validar Id da Modalidade
    public boolean validarIdModalidade(int id){
        return modalidades.containsKey(id);
    }


    //Pegar Planos
    public Map<Integer, Plano> getPlano(){
        return this.planos;
    }


    //Validar Id do Plano
    public boolean validarIdPlano(int id){
        return planos.containsKey(id);
    }


    //Validar Id Da Assinatura Plano
    public boolean validarIdAssinaturaPlano(int id){
        return assinaturaPlano.containsKey(id);
    }


    //Pegar a Assinatura Dos Planos
    public Map<Integer, AssinaturaPlano> getAssinaturaPlano() {
        return this.assinaturaPlano;
    }


    //Pegar Custos
    public Map<Integer, Custo> getCustos() {
        return this.custos;
    }


    //Validar Id do Custo
    public boolean validarIdCusto(int id){
        return custos.containsKey(id);
    }


    //Pegar Alunos
    public Map<Integer, Aluno> getAlunos() {
        return this.alunos;
    }


    //Validar Id do Aluno
    public boolean validIdAluno(int id){
        return alunos.containsKey(id);
    }


    //Pegar Treinadores
    public Map<Integer, Treinador> getTreinadores(){
        return this.treinadores;
    }


    //Validar Id do Treinador
    public boolean validarIdTreinador(int id){
        return treinadores.containsKey(id);
    }


    //Pegar Administrativos
    public Map<Integer, Administrativo> getAdministrativos() {
        return this.administrativos;
    }


    //Validar Id do Administrativo
    public boolean validarIdAdministrativo(int id){
        return administrativos.containsKey(id);
    }


    //Pegar Exercicios
    public Map<Integer, Exercicio> getExercicios(){
        return this.exercicios;
    }


    //Validar Id do Exercicio
    public boolean validarIdExercicio(int id){
        return exercicios.containsKey(id);
    }


    //Pegar Treinos
    public Map<Integer, Treino> getTreinos(){
        return this.treinos;
    }


    //Validar Id do Treino
    public boolean validarIdTreino(int id){
        return treinos.containsKey(id);
    }


    //Pegar Feedback Dos Treinadores
    public Map<Integer, FeedbackTreinador> getFeedbacksTreinadores(){
        return this.feedbacksTreinadores;
    }


    //Validar Id Do Feedback Do Treinador
    public boolean validarIdFeedbackTreinador(int id){
        return feedbacksTreinadores.containsKey(id);
    }


    //pegar Solicitação Para Trocar De Treinadores
    public Map<Integer, TrocarTreinador> getTrocarTreinadores(){
        return this.trocarTreinadores;
    }


    //Validar Id Da Solicitação Para Trocar De Treinador
    public boolean validarIdTrocarTreinador(int id){
        return trocarTreinadores.containsKey(id);
    }
}