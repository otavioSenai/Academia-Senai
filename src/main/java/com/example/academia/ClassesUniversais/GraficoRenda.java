package com.example.academia.ClassesUniversais;

import com.example.academia.aluno.Aluno;

public class GraficoRenda {
    private double renda;
    private boolean situacao;

    /*A renda precisaria do aluno, pois é ele quem paga
    O custo seria mais um ArrayList*/

    public void addRenda(int id) {
        if(BancoDeDados.getInstance().alunos.containsKey(id)){


            Aluno aluno = BancoDeDados.getInstance().alunos.get(id);
            for(int i=0;i<aluno.getPlano().size();i++ ){
                this.renda += aluno.getPlano().get(i).getValor();
            }

        }
        else {
            System.out.println("Valores Inválidos");
        }
    }

    public double getRenda(){
        return this.renda;
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

