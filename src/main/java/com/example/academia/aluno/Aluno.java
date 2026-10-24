package com.example.academia.aluno;

import com.example.academia.ClassesUniversais.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Aluno extends Usuario {

    private int idade;
    private double altura;
    private double peso;
    private Plano plano;
    private String intensidade;

    private final ArrayList<Modalidade> modalidades = new ArrayList<>();
    private final ArrayList<Treino> treinos = new ArrayList<>();

    public Aluno(int id, String nome, String senha, String cpf,
                 int idade, double altura, double peso,
                 Plano plano, String intensidade) {

        super(id, nome, senha, cpf);

        if (idade < 13 || idade > 130) {
            throw new IllegalArgumentException("Idade inválida.");
        }

        if (!Double.isFinite(altura) || altura < 0.50 || altura > 2.50) {
            throw new IllegalArgumentException("Altura inválida.");
        }

        if (!Double.isFinite(peso) || peso < 30 || peso > 350) {
            throw new IllegalArgumentException("Peso inválido.");
        }

        String intensidadeValida = normalizarIntensidade(intensidade);

        if (intensidadeValida == null) {
            throw new IllegalArgumentException("Intensidade inválida.");
        }

        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
        this.plano = plano;
        this.intensidade = intensidadeValida;
    }

    private static String normalizarIntensidade(String intensidade) {
        if (intensidade == null) {
            return null;
        }

        if (intensidade.equalsIgnoreCase("Leve")) {
            return "Leve";
        }

        if (intensidade.equalsIgnoreCase("Médio")) {
            return "Médio";
        }

        if (intensidade.equalsIgnoreCase("Intenso")) {
            return "Intenso";
        }

        return null;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 13 || idade > 130) {
            System.out.println("Idade inválida!");
            return;
        }

        this.idade = idade;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        if (!Double.isFinite(altura) || altura < 0.50 || altura > 2.50) {
            System.out.println("Altura inválida!");
            return;
        }

        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (!Double.isFinite(peso) || peso < 30 || peso > 350) {
            System.out.println("Peso inválido!");
            return;
        }

        this.peso = peso;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public String getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(String intensidade) {
        String intensidadeValida = normalizarIntensidade(intensidade);

        if (intensidadeValida == null) {
            System.out.println("Intensidade inválida!");
            return;
        }

        this.intensidade = intensidadeValida;
    }

    public ArrayList<Modalidade> getModalidades() {
        return new ArrayList<>(modalidades);
    }

    public void adicionarModalidades(Modalidade modalidade) {
        if (modalidade == null) {
            System.out.println("Modalidade inválida!");
            return;
        }

        boolean modalidadeRegistrada = false;

        for (Modalidade registrada : BancoDeDados.getInstance().modalidades) {
            if (registrada != null && registrada.getId() == modalidade.getId()) {
                modalidadeRegistrada = true;
                break;
            }
        }

        if (!modalidadeRegistrada) {
            System.out.println("Esta modalidade não está cadastrada na academia!");
            return;
        }

        for (Modalidade existente : modalidades) {
            if (existente.getId() == modalidade.getId()) {
                System.out.println("Esta modalidade já foi adicionada!");
                return;
            }
        }

        modalidades.add(modalidade);
        System.out.println("Modalidade adicionada!");
    }

    public void removerModalidade(int id) {
        for (int i = 0; i < modalidades.size(); i++) {
            if (modalidades.get(i).getId() == id) {
                modalidades.remove(i);
                System.out.println("Modalidade removida!");
                return;
            }
        }

        System.out.println("Modalidade não encontrada!");
    }

    public ArrayList<Treino> getTreinos() {
        return new ArrayList<>(treinos);
    }

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

    public ArrayList<Treino> getTreinosPorData(LocalDate data) {
        ArrayList<Treino> treinosDoDia = new ArrayList<>();

        if (data == null) {
            return treinosDoDia;
        }

        for (Treino treino : treinos) {
            if (data.equals(treino.getData())) {
                treinosDoDia.add(treino);
            }
        }

        return treinosDoDia;
    }

    public int getQuantidadeTreinos() {
        return treinos.size();
    }

    public void mostrarCarteirinhaAluno() {
        System.out.println("========================================");
        System.out.println("          ACADEMIA ÍCARO");
        System.out.println("       CARTEIRINHA DO ALUNO");
        System.out.println("========================================");
        System.out.println("NOME: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("IDADE: " + idade);
        System.out.println("ALTURA: " + altura + " m");
        System.out.println("PESO CORPORAL: " + peso + " kg");
        System.out.println("PLANO: " + (plano == null ? "Não definido" : plano.getNome()));
        System.out.println("INTENSIDADE: " + intensidade);

        System.out.println("----------------------------------------");
        System.out.println("MODALIDADES:");

        if (modalidades.isEmpty()) {
            System.out.println("Nenhuma modalidade selecionada.");
        } else {
            for (Modalidade modalidade : modalidades) {
                System.out.println("- " + modalidade.getNome());
            }
        }

        System.out.println("----------------------------------------");
        System.out.println("QUANTIDADE DE TREINOS: " + treinos.size());
        System.out.println("========================================");
    }
}