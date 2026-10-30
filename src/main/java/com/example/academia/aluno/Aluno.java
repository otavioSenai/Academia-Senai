package com.example.academia.aluno;

import com.example.academia.ClassesUniversais.*;

import java.time.Instant;
import java.util.*;

public class Aluno extends Usuario {

    private int idade;
    private double altura;
    private double peso;
    private String intensidade;

    private ArrayList<AssinaturaPlano> assinaturaPlanos = new ArrayList<>();
    private Map<Integer, Treino> treinos = new HashMap<>();

    public Aluno(String nome, String senha, String cpf,
                 int idade, double altura, double peso,
                 AssinaturaPlano assinaturaPlano, String intensidade) {

        super(nome, senha, cpf);
        gerarId(BancoDeDados.getInstance().alunos.size());
        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
        this.assinaturaPlanos.add(assinaturaPlano);
        this.intensidade = intensidade;
    }

    //Métodos

    //Normalizar Intensidade
    private static String normalizarIntensidade(String intensidade) {
        if (intensidade == null) {
            return null;
        } else if (intensidade.equalsIgnoreCase("Leve")) {
            return "Leve";
        } else if (intensidade.equalsIgnoreCase("Médio")) {
            return "Médio";
        } else if (intensidade.equalsIgnoreCase("Intenso")) {
            return "Intenso";
        }
        return null;
    }

    /* Eu Ainda Não Estou Conseguindo Identificar o Professor

    Fazer um Feedback Para Um Professor
    public void fazerFeedback(int idModalidade, int idTreinador, String comentario, int qtdEstrelas) {

        boolean modalidadeEncontrado = false;
        Modalidade m = null;
        for(AssinaturaPlano a : assinaturaPlanos){
            if(a.getPlano().getModalidade().getId() == idModalidade){
                modalidadeEncontrado = true;
                m = a.getPlano().getModalidade();
            }
        }
        if(modalidadeEncontrado){
        Modalidade modalidade = m;

            if (qtdEstrelas < 1 || qtdEstrelas > 5 || comentario.isBlank() || modalidade.getTreinador()) {
                System.out.println("Valores Inválidos");
            } else {
                FeedbackTreinador feedback = new FeedbackTreinador();
                feedback.addComentario(comentario, qtdEstrelas);
            }
        }
    }*/

    //Solicitar Trocar De Treinador
    public boolean solicitarTroca(int idTreinador, String comentario) {

        if (BancoDeDados.getInstance().validarIdTreinador(idTreinador)) {
            TrocarTreinador solicitarTroca = new TrocarTreinador();
            solicitarTroca.setComentario(idTreinador, comentario);
            return true;
        }
        return false;
    }

    //Pegar Idade
    public int getIdade() {
        return idade;
    }

    //Mudar Idade
    public boolean setIdade(int idade) {
        if (idade < 13 || idade > 130) {
            return false;
        }
        this.idade = idade;
        return true;
    }

    //Pegar Altura
    public double getAltura() {
        return altura;
    }

    //Mudar Altura
    public boolean setAltura(double altura) {
        if (!Double.isFinite(altura) || altura < 0.50 || altura > 2.50) {
            return false;
        }
        this.altura = altura;
        return true;
    }

    //Pegar Peso
    public double getPeso() {
        return peso;
    }

    //Mudar Peso
    public boolean setPeso(double peso) {
        if (!Double.isFinite(peso) || peso < 30 || peso > 350) {
            return false;
        }
        this.peso = peso;
        return true;
    }

    //Pegar Lista De Assinaturas de  Planos
    public ArrayList<AssinaturaPlano> getPlano() {
        return this.assinaturaPlanos;
    }

    //Mostra As Assinaturas do Aluno
    public void showPlano(){
        for(AssinaturaPlano a : assinaturaPlanos){
            System.out.println(a.getId() + " - " + a);
        }
    }

    //Mostra As Modalidades do Aluno
    public boolean showModalidade(){
        if(assinaturaPlanos.isEmpty()){
            return false;
        }
        for(AssinaturaPlano a : assinaturaPlanos){
            a.showModalidades();
        }
        return true;
    }

    //Pagar Assinatura Plano
    public boolean pagarPlano(int idAssinatura, int idData) {
        if (BancoDeDados.getInstance().validarIdPlano(idAssinatura)){
            if (this.assinaturaPlanos.get(idAssinatura).tempoPagar()){
                //Se Pagar No Prazo Paga Com o Preço Normal
                assinaturaPlanos.get(idAssinatura).pagarPlano(idData);
                return true;
            }
            //Se Pagar Atrasado Tera Que Pagar Uma Taxa Decidida No Plano
        }
        return false;
    }

    //Pegar assinaturaPlano
    public AssinaturaPlano getPlano(int id) {

        boolean idEncontrado = false;
        for (AssinaturaPlano plano : assinaturaPlanos) {
            if (plano.getId() == id) {
                idEncontrado = true;
                return plano;
            }
        }
        return null;
    }

    //Remover Plano
    public boolean removerPlano(int id) {

        boolean idEncontrado = false;
        for(AssinaturaPlano plano : assinaturaPlanos){
            if (plano.getId() == id) {
                assinaturaPlanos.remove(plano);
                return true;
            }
        }
         return false;
    }

    //Pegar Modalidade
    public List<Map<Integer, Modalidade>> getModalidade() {
        List<Map<Integer,Modalidade>>listaModalidades = new ArrayList<>();

        for (AssinaturaPlano p : this.assinaturaPlanos) {
            listaModalidades.add(p.getPlano().getModalidade());
        }
        return listaModalidades;
    }

    //Pegar Intensidade
    public String getIntensidade() {
        return this.intensidade;
    }

    //Mudar Intensidade
    //Eu gostaria que aparecesse uma caixa de escolha com as intensidades
    public boolean setIntensidade(String intensidade) {
        String intensidadeValida = normalizarIntensidade(intensidade);

        if (intensidadeValida == null) {
            return false;
        }
        this.intensidade = intensidadeValida;
        return true;
    }

    //Pegar Treinos
    public Map<Integer, Treino> getTreinos() {
        return this.treinos;
    }

    //Adicionar Treino
    public boolean adicionarTreino(Treino treino) {
        Instant agora = Instant.now();
        if (treino == null
                || treino.getIdTreino() <= 0
                || treino.getNomeTreino() == null
                || treino.getNomeTreino().isBlank()
                || treino.getConteudo() == null
                || treino.getConteudo().isBlank()
                || treino.getData() == null
                || agora.isAfter(treino.getData())){
            return false;
        }
        if(treinos.containsKey(treino.getIdTreino())){
            return false;
        }
        treinos.put(treino.getIdTreino(), treino);
        return true;
    }

    //Editar Treino
    public boolean editarTreino(int idTreino, String nomeTreino,
                                String conteudo, String data) {

        if (nomeTreino == null || nomeTreino.isBlank()
                || conteudo == null || conteudo.isBlank()
                || data == null || data.isBlank()) {
            return false;
        }
        if(treinos.containsKey(idTreino)) {
                treinos.get(idTreino).setNomeTreino(nomeTreino);
                treinos.get(idTreino).setConteudo(conteudo);
                treinos.get(idTreino).setData(data);
                return true;
        }
        return false;
    }

    //Remover Treino
    public boolean removerTreino(int idTreino) {
            if (treinos.containsKey(idTreino)) {
                treinos.remove(idTreino);
                return true;
            }
        return false;
    }

    //Pegar Quantidade De Treinos
    public int getQuantidadeTreinos() {
        return treinos.size();
    }

    //Mostrar Carteirinha
    public void mostrarCarteirinhaAluno() {
        ArrayList<String> nomePlanos = new ArrayList<>();

        for (AssinaturaPlano p : this.assinaturaPlanos) {
            nomePlanos.add(p.getPlano().getNome());
        }
        System.out.println("NOME: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("IDADE: " + idade);
        System.out.println("ALTURA: " + altura + " m");
        System.out.println("PESO CORPORAL: " + peso + " kg");
        System.out.println("INTENSIDADE: " + intensidade);
        System.out.println("MODALIDADES:");
        if (getModalidade().isEmpty()) {
            System.out.println("Nenhuma modalidade selecionada.");
        } else {
            System.out.println("- " + getModalidade());
        }
        System.out.println("PLANO:");
        if(getPlano().isEmpty()){
            System.out.print("Nenhum plano selecionado");
        } else {
          System.out.println("- " + getPlano());
        }
        System.out.println("QUANTIDADE DE TREINOS: " + treinos.size());
    }
}