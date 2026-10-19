package com.example.academia.aluno;

import com.example.academia.ClassesUniversais.Departamento;
import com.example.academia.ClassesUniversais.Plano;
import com.example.academia.ClassesUniversais.Usuario;

import java.util.ArrayList;

public class Aluno extends Usuario {
    private int idade;
    private double altura;
    private double peso;
    private Plano plano;
    private String intensidade;
    private ArrayList<Departamento> departamentos;
    private ArrayList<String> treinos;


    public Aluno(int id, String nome, String senha, String cpf) {
        super(id, nome, senha, cpf);
        this.departamentos = new ArrayList<>();
        this.treinos = new ArrayList<>();
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
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
        this.intensidade = intensidade;
    }

    public ArrayList<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(ArrayList<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public ArrayList<String> getTreinos() {
        return treinos;
    }

    public void setTreinos(ArrayList<String> treinos) {
        this.treinos = treinos;
    }
}

