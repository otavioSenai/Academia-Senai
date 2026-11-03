package com.example.academia.ClassesUniversais;

public class GraficoRenda {
    private double custo;
    private double renda;
    private double total;
    private boolean situacao;

    public boolean addRenda(int idAluno, int idAssinaturaPlano) {
        if(BancoDeDados.getInstance().validIdAluno(idAluno) && BancoDeDados.getInstance().validarIdAssinaturaPlano(idAssinaturaPlano)){

            AssinaturaPlano assinaturaPlano = BancoDeDados.getInstance().alunos.get(idAluno).getAssinaturaPlano(idAssinaturaPlano);
            double valor = assinaturaPlano.getPlano().getValor();

            if(assinaturaPlano.getStatus()) {
                this.renda += valor;
            } else{
                this.renda += valor + assinaturaPlano.calcularJuro(valor);
            }
            return true;
        }
        return false;
    }

    public void addCusto(int idCusto) {
        if(BancoDeDados.getInstance().validarIdCusto(idCusto)){
            this.custo += BancoDeDados.getInstance().custos.get(idCusto).getValor();
        }
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