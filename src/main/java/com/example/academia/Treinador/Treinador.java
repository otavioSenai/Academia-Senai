package com.example.academia.Treinador;

import com.example.academia.ClassesUniversais.Funcionario;

import java.util.ArrayList;

public class Treinador extends Funcionario {
    private ArrayList<Modalidade> modalidades = new ArrayList<>();
    private String Especialidade;

    public Treinador(int id, String nome, String senha, String cpf, double salario) {
        super(id, nome, senha, cpf, salario);

    }

    public String getEspecialidade() {
        return Especialidade;
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade==null||especialidade.isBlank()){
            System.out.println("Dados Invalidos");
        }
        else {
        Especialidade = especialidade;
         }
    }

    public ArrayList<Modalidade> getModalidades() {

        return modalidades;
    }

    public void setModalidades(ArrayList<Modalidade> modalidades) {
        if (modalidades==null){
            System.out.println("Dados Invalidos");
        }
        else {
            this.modalidades = modalidades;
        }
    }

    public void mostrarCarteirinhaTreinador(){
    System.out.println("========================================");
            System.out.println("        ACADEMIA ÍCARO");
            System.out.println("        CARTEIRINHA DO PROFESSOR");
            System.out.println("========================================");
            System.out.println("----------------------------------------");
            System.out.println("----------------------------------------");
            System.out.println("ESPECIALIDADE: " + Especialidade);
            System.out.println("========================================");}

}
