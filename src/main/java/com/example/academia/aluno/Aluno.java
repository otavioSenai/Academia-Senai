package com.example.academia.aluno;

import com.example.academia.ClassesUniversais.*;
import com.example.academia.Treinador.Treinador;

import java.time.LocalDate;
import java.util.*;

public class Aluno extends Usuario {

    private int idade;
    private double altura;
    private double peso;
    private String intensidade;

    private Map<Integer, Treinador> treinadores = new HashMap<>();
    private ArrayList<Plano> planos = new ArrayList<>();
    private ArrayList<Treino> treinos = new ArrayList<>();

    public Aluno(int id, String nome, String senha, String cpf,
                 int idade, double altura, double peso,
                 Plano plano, String intensidade) {

        super(id, nome, senha, cpf);
        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
        this.planos.add(plano);
        this.intensidade = intensidade;
    }

    //Métodos

    //Normalizar Intensidade
    private static String normalizarIntensidade(String intensidade) {
        if (intensidade == null) {
            return null;
        }
        else if (intensidade.equalsIgnoreCase("Leve")) {
            return "Leve";
        }
        else if (intensidade.equalsIgnoreCase("Médio")) {
            return "Médio";
        }
        else if (intensidade.equalsIgnoreCase("Intenso")) {
            return "Intenso";
        }
        return null;
    }

    //Fazer um Feedback Para Um Professor
    public void fazerFeedback(Scanner entrada){
        System.out.println("Escreva Um Comentário");
        String comentario = entrada.nextLine();

        System.out.println("Digite o Número De Estrelas (1-5)");
        int qtdEstrelas = entrada.nextInt();

        if(qtdEstrelas < 1 || qtdEstrelas > 5 || comentario.isBlank()){
            System.out.println("Valores Inválidos");
        }
        else{
            FeedbackProfessor feedback = new FeedbackProfessor();
            feedback.setComentario(comentario, qtdEstrelas);
        }
    }

    //Solicitar Trocar De Treinador
    public void solicitarTroca(Scanner entrada){
        System.out.println("Insira o id do treinador");
        int id = entrada.nextInt();

        if(BancoDeDados.getInstance().treinadores.containsKey(id)){
            TrocaTreinador solicitarTroca = new TrocaTreinador();
            solicitarTroca.setComentario(id, entrada);
        }
    }

    //Pegar Idade
    public int getIdade() {
        return idade;
    }

    //Mudar Idade
    public void setIdade(int idade) {
        if (idade < 13 || idade > 130) {
            System.out.println("Idade inválida!");
            return;
        }
        this.idade = idade;
    }

    //Pegar Altura
    public double getAltura() {
        return altura;
    }

    //Mudar Altura
    public void setAltura(double altura) {
        if (!Double.isFinite(altura) || altura < 0.50 || altura > 2.50) {
            System.out.println("Altura inválida!");
            return;
        }
        this.altura = altura;
    }

    //Pegar Peso
    public double getPeso() {
        return peso;
    }

    //Mudar Peso
    public void setPeso(double peso) {
        if (!Double.isFinite(peso) || peso < 30 || peso > 350) {
            System.out.println("Peso inválido!");
            return;
        }
        this.peso = peso;
    }

    //Pegar Plano
    public ArrayList<Plano> getPlano() {

        return this.planos;
    }

    //Adicionar Plano
    public void adicionarPlano(String nome, int id, String descricao, double valor, String validade) {
        if(nome.isBlank() || BancoDeDados.getInstance().planos.containsKey(id) || descricao.isBlank() || valor <= 0 || validade.isBlank()){
            System.out.println("Valores Inválidos");
        }
        else{
            Plano plano = new Plano(nome, id, descricao, valor, validade);
            this.planos.add(plano);
        }
    }

    //Remover Plano
    public void removerPlano(int id){
        if(BancoDeDados.getInstance().planos.containsKey(id)){
            BancoDeDados.getInstance().planos.remove(id);
        }
        else{
            System.out.println("Valores Inválidos");
        }
    }

    //Pegar Modalidade
    public List<String> getModalidade(){
        List<String> listaModalidades = new ArrayList<>();

        for(Plano p : this.planos){
            listaModalidades.add(p.getModalidade());
        }
        return listaModalidades;
    }

    //Pegar Intensidade
    public String getIntensidade() {
        return this.intensidade;
    }

    //Mudar Intensidade
    public void setIntensidade(String intensidade) {
        String intensidadeValida = normalizarIntensidade(intensidade);

        if (intensidadeValida == null) {
            System.out.println("Intensidade inválida!");
            return;
        }
        this.intensidade = intensidadeValida;
    }

    //Pegar Treinos
    public ArrayList<Treino> getTreinos() {
        return new ArrayList<>(treinos);
    }

    //Adicionar Treino
    public boolean adicionarTreino(Treino treino) {
        if (treino == null
                || treino.getIdTreino() <= 0
                || treino.getNomeTreino() == null
                || treino.getNomeTreino().isBlank()
                || treino.getConteudo() == null
                || treino.getConteudo().isBlank()
                || treino.getData() == null) {

            System.out.println("Dados do treino inválidos!");
            return false;
        }
        for (Treino existente : treinos) {
            if (existente.getIdTreino() == treino.getIdTreino()) {
                System.out.println("Este treino já existe!");
                return false;
            }
        }
        treinos.add(treino);
        System.out.println("Treino adicionado!");
        return true;
    }

    //Editar Treino
    public boolean editarTreino(int idTreino, String nomeTreino,
                                String conteudo, LocalDate data) {

        if (nomeTreino == null || nomeTreino.isBlank()
                || conteudo == null || conteudo.isBlank()
                || data == null) {

            System.out.println("Dados do treino inválidos!");
            return false;
        }
        for (Treino treino : treinos) {
            if (treino.getIdTreino() == idTreino) {
                treino.setNomeTreino(nomeTreino);
                treino.setConteudo(conteudo);
                treino.setData(data);

                System.out.println("Treino alterado!");
                return true;
            }
        }
        System.out.println("Treino não encontrado neste aluno!");
        return false;
    }

    //Remover Treino
    public boolean removerTreino(int idTreino) {
        for (int i = 0; i < treinos.size(); i++) {
            if (treinos.get(i).getIdTreino() == idTreino) {
                treinos.remove(i);
                System.out.println("Treino removido!");
                return true;
            }
        }
        System.out.println("Treino não encontrado neste aluno!");
        return false;
    }

    //Pegar Treinos Pela Data
    public ArrayList<Treino> getTreinosPorData(LocalDate data) {
        ArrayList<Treino> treinosDoDia = new ArrayList<>();

        if (data == null) {
            return null;
        }
        for (Treino treino : treinos) {
            if (data.equals(treino.getData())) {
                treinosDoDia.add(treino);
            }
        }
        return treinosDoDia;
    }

    //Pegar Quantidade De Treinos
    public int getQuantidadeTreinos() {
        return treinos.size();
    }

    //Mostrar Carteirinha
    public void mostrarCarteirinhaAluno() {

        ArrayList<String> nomePlanos = new ArrayList<>();

        for(Plano p : this.planos){
            nomePlanos.add(p.getNome());
        }

        System.out.println("========================================");
        System.out.println("          ACADEMIA ÍCARO");
        System.out.println("       CARTEIRINHA DO ALUNO");
        System.out.println("========================================");
        System.out.println("NOME: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("IDADE: " + idade);
        System.out.println("ALTURA: " + altura + " m");
        System.out.println("PESO CORPORAL: " + peso + " kg");
        System.out.println("PLANO: " + (planos == null ? "Não definido" : nomePlanos));
        System.out.println("INTENSIDADE: " + intensidade);

        System.out.println("----------------------------------------");
        System.out.println("MODALIDADES:");

        if (getModalidade().isEmpty()) {
            System.out.println("Nenhuma modalidade selecionada.");
        } else {
            System.out.println("- " + getModalidade());
        }

        System.out.println("----------------------------------------");
        System.out.println("QUANTIDADE DE TREINOS: " + treinos.size());
        System.out.println("========================================");
    }
}