package com.example.academia.ClassesUniversais;

import com.example.academia.aluno.Aluno;

public class GraficoRenda {
    private double custo;
    private double renda;
    private double total;
    private boolean situacao;

    public boolean addRenda(int id) {
        if (BancoDeDados.getInstance().validIdAluno(id)) {
            for(Aluno aluno : BancoDeDados.getInstance().alunos.values()){
                for(AssinaturaPlano a : aluno.getListaPlano()) {
                    if (!a.getStatus()){
                        return false;
                    }
                    this.renda += a.getPlano().getValor();
                }
            }
            return true;
        }
        return false;
    }

    public boolean addCusto(int id) {
        if (BancoDeDados.getInstance().validarIdCusto(id)) {
            for (Custo c : BancoDeDados.getInstance().custos.values()) {
                custo += c.getValor();
            }
            return true;
        }
        return false;
    }

    public void somarDespesas(){
        total = this.renda - this.custo;
        if(total <= 0){
            this.situacao = false;
            return;
        }
        this.situacao = true;
    }

    public void verGrafico(){
        System.out.println("Renda: " + this.renda);
        System.out.println("Custo: " + this.custo);
        System.out.println("Total: " + this.total);
        System.out.println("Situação: " + this.situacao);
    }

    public double getRenda(){
        return this.renda;
    }

    public double getCusto(){
        return this.custo;
    }

    public double getTotal(){
        return this.total;
    }

    public boolean getSituacao() {
        return situacao;
    }
}