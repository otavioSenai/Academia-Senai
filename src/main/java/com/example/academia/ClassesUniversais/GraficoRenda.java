package com.example.academia.ClassesUniversais;

import com.example.academia.aluno.Aluno;

public class GraficoRenda {
    private double renda;
    private boolean situacao;

    /*A renda precisaria do aluno, pois é ele quem paga
    O custo seria mais um ArrayList*/

    public double getRenda() {
        return renda;
    }

    public void setRenda(double renda) {
        this.renda = renda;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }
}

