package com.example.academia.Treinador;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.Funcionario;
import com.example.academia.ClassesUniversais.ModalidadeEnums;
import java.util.ArrayList;

public class Treinador extends Funcionario {
    private ArrayList<ModalidadeEnums> modalidadeEnums = new ArrayList<>();
    private String Especialidade;

    public Treinador(int id, String nome, String senha, String cpf, double salario) {
        super(id, nome, senha, cpf, salario);
    }

    public String getEspecialidade() {
        return Especialidade;
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.isBlank()) {
            System.out.println("Dados Invalidos");
        } else {
            Especialidade = especialidade;
        }
    }

    public ModalidadeEnums getModalidades(int id) {
        return this.modalidadeEnums.get(id);
    }

    public void addModalidades(String modalidade) {

        boolean modalidadeExistente = false;

        for (ModalidadeEnums m : BancoDeDados.getInstance().modalidadeEnums) {
            if (m.name().equalsIgnoreCase(modalidade)) {
                this.modalidadeEnums.add(m);
                modalidadeExistente = true;
                break;
            }
        }
        if (!modalidadeExistente) {
            System.out.println("Valores Inválidos");
        }
    }

    public void mostrarCarteirinhaTreinador() {
        System.out.println("ESPECIALIDADE: " + Especialidade);
    }
}