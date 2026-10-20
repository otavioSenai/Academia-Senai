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


    public Aluno(int id, String nome, String senha, String cpf, int idade, double altura, double peso, Plano plano, String intensidade) {
        super(id, nome, senha, cpf);
        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
        this.plano = plano;
        this.intensidade = intensidade;
    }

    @Override
    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        if(intensidade.isEmpty()){
            System.out.println("Dados invalidos!");
        }
        else {
            this.intensidade = intensidade;
        }
    }
}


