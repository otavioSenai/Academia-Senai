package com.example.academia.Treinador;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.Funcionario;
import com.example.academia.ClassesUniversais.Modalidade;

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

    public Modalidade getModalidades(int id) {
        return this.modalidades.get(id);
    }

    public void addModalidades(String modalidade) {

        boolean modalidadeExistente = false;

        for(Modalidade m : BancoDeDados.getInstance().modalidades) {
            if(m.name().equalsIgnoreCase(modalidade)){
                this.modalidades.add(m);
                modalidadeExistente = true;
                break;
            }
        }
        if(!modalidadeExistente){
            System.out.println("Valores Inválidos");
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