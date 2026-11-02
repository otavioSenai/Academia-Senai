package com.example.academia.aluno;

import com.example.academia.ClassesUniversais.*;
import com.example.academia.Treinador.Treinador;

import java.util.*;

public class Aluno extends Usuario {

    private int idade;
    private double altura;
    private double peso;
    private String intensidade;

    private ArrayList<AssinaturaPlano> assinaturaPlanos = new ArrayList<>();
    private Map<Integer, Treino> treinos = new HashMap<>();
    private ArrayList<Treinador> treinadores = new ArrayList<>();

    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }

    public Aluno(String nome, String senha, String cpf,
                 int idade, double altura, double peso,
                 AssinaturaPlano assinaturaPlano, String intensidade, Treinador treinador) {

        super(nome, senha, cpf);
        gerarId(Id(BancoDeDados.getInstance().getAlunos()));
        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
        this.assinaturaPlanos.add(assinaturaPlano);
        this.treinadores.add(treinador);
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

    //Mudar de Treinador
    public boolean setTreinador(int idModalidade, int idTreinadorAntigo, int idTreinadorNovo){
        if(BancoDeDados.getInstance().validarIdTreinador(idTreinadorAntigo) && BancoDeDados.getInstance().validarIdTreinador(idTreinadorNovo) && this.treinadores.contains(BancoDeDados.getInstance().treinadores.get(idTreinadorAntigo))) {

            boolean modalidadeEncontrado = false;
            boolean treinadorAntigoEncontrado = false;
            boolean treinadoNovoEncontrado = false;

            for (Modalidade m : BancoDeDados.getInstance().modalidades) {
                if (m.getId() == idModalidade) {
                    modalidadeEncontrado = true;
                    for (Treinador t : m.getTreinadores().values()) {
                        if (t.getId() == idTreinadorAntigo) {
                            treinadorAntigoEncontrado = true;
                        } else if (t.getId() == idTreinadorNovo) {
                            treinadoNovoEncontrado = true;
                        }
                    }
                }
            }
            if (modalidadeEncontrado && treinadoNovoEncontrado && treinadorAntigoEncontrado) {
                treinadores.set(idTreinadorAntigo, BancoDeDados.getInstance().modalidades[idModalidade].getTreinador(idTreinadorNovo));
                return true;
            }
            return false;
        }
        return false;
    }

    //Fazer um Feedback Para Um Professor
    public boolean fazerFeedback(int idTreinador, String comentario, int qtdEstrelas) {

        if (BancoDeDados.getInstance().validarIdTreinador(idTreinador)) {
            BancoDeDados.getInstance().treinadores.get(idTreinador).addFeedback(comentario, qtdEstrelas, getId());
            return true;
        }
        return false;
    }

    //Solicitar Trocar De Treinador
    public boolean solicitarTroca(int idTreinador, String comentario) {

        if (BancoDeDados.getInstance().validarIdTreinador(idTreinador)) {
            BancoDeDados.getInstance().treinadores.get(idTreinador).addTroca(comentario, getId());
            return true;
        }
        return false;
    }

    //Ver se o Plano está atrasado
    public boolean vierPlanoAtrasado(int idPlano) {
        return assinaturaPlanos.get(idPlano).tempoPagar();
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
    public ArrayList<AssinaturaPlano> getListaPlano() {
        return this.assinaturaPlanos;
    }

    //Mostra As Assinaturas do Aluno
    public void showPlano() {
        for (AssinaturaPlano a : assinaturaPlanos) {
            System.out.println(a.getId() + " - " + a);
        }
    }

    //Mostra As Modalidades do Aluno
    public boolean showModalidade() {
        if (assinaturaPlanos.isEmpty()) {
            return false;
        }
        for (AssinaturaPlano a : assinaturaPlanos) {
            a.showModalidades();
        }
        return true;
    }

    //Pagar Assinatura Plano
    public boolean pagarPlano(int idAssinatura, int idData) {
        if (BancoDeDados.getInstance().validarIdPlano(idAssinatura)) {
            if (this.assinaturaPlanos.get(idAssinatura).tempoPagar()) {
                assinaturaPlanos.get(idAssinatura).pagarPlano(idData, getId());
                return true;
            }
            //Se Pagar Atrasado Tera Que Pagar Uma Taxa Decidida No Plano
        }
        return false;
    }

    //Pegar assinaturaPlano
    public AssinaturaPlano getPlano(int id) {
        for (AssinaturaPlano plano : assinaturaPlanos) {
            if (plano.getId() == id) {
                return plano;
            }
        }
        return null;
    }

    //Remover Plano
    public boolean removerPlano(int id) {

        boolean idEncontrado = false;
        for (AssinaturaPlano plano : assinaturaPlanos) {
            if (plano.getId() == id) {
                assinaturaPlanos.remove(plano);
                return true;
            }
        }
        return false;
    }

    //Pegar Modalidade
    public List<Map<Integer, Modalidade>> getModalidade() {
        List<Map<Integer, Modalidade>> listaModalidades = new ArrayList<>();

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

    //Editar Treino
    public boolean editarTreino(int idTreino, String nomeTreino,
                                String conteudo, String data) {

        if (nomeTreino == null || nomeTreino.isBlank()
                || conteudo == null || conteudo.isBlank()
                || data == null || data.isBlank()) {
            return false;
        }
        if (treinos.containsKey(idTreino)) {
            treinos.get(idTreino).setNomeTreino(nomeTreino);
            treinos.get(idTreino).setConteudo(conteudo);
            treinos.get(idTreino).setData(data);
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
        if (getListaPlano().isEmpty()) {
            System.out.print("Nenhum plano selecionado");
        } else {
            System.out.println("- " + getListaPlano());
        }
        System.out.println("QUANTIDADE DE TREINOS: " + treinos.size());
    }
}