package com.example.academia.ClassesUniversais;

import com.example.academia.aluno.Aluno;

public class GraficoRenda {
    private double renda;
    private boolean situacao;

    /*A renda precisaria do aluno, pois é ele quem paga
    O custo seria mais um ArrayList*/

    public double getRenda(int id, String nome, String senha, String cpf, int idade, double altura, double peso, Plano plano, String intensidade) {
        Aluno aluno=new Aluno(id, nome, senha, cpf, idade, altura, peso, plano,intensidade);
        return renda;
    }

    public void setRenda(double renda) {
        this.renda = renda;
    }

    public boolean getSituacao() {

        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }
}

