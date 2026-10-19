package com.example.academia.administrativo;

    import com.example.academia.ClassesUniversais.*;

public class Administrativo extends Funcionario {

    //Construtor
    public Administrativo(int id, String nome, String senha, String cpf, double salario){
        super(id, nome, senha, cpf, salario);
    }

    //Métodos

    //Cadastra Um Departamento
    public void cadastrarDepartamento(String nome, int id, String descricao){

        if(id == 0 || nome.isBlank() || descricao.isBlank()){
            System.out.println("Valores Inválidos");
        }
        else {
            boolean idEncontrado = false;

            for (Departamento dp : BancoDeDados.getInstance().departamentos) {
                if (dp.getId() == id) {
                    idEncontrado = true;
                    break;
                }
            }

            if (idEncontrado) {

                System.out.println("Departamento Já Existente");

            } else {

                Departamento departamento = new Departamento(nome, id, descricao);
                BancoDeDados.getInstance().departamentos.add(departamento);
                System.out.println("Departamento Cadastrado");

            }
        }
    }

    //Remove Um Departamento
    public void removerDepartamento(int id){

        if(id == 0){
            System.out.println("Valores Inválidos");
        }
        else {
            Departamento dpRemove = null;

            for (Departamento dp : BancoDeDados.getInstance().departamentos) {
                if (dp.getId() == id) {
                    dpRemove = dp;

                    BancoDeDados.getInstance().departamentos.remove(dpRemove);

                    System.out.println("Departamento Removido");
                    break;
                }
            }

            if (dpRemove == null) {
                System.out.println("Valores Inválidos");
            }
        }
    }

    //Cadastrar Um Plano
    public void cadatrarPlano(String nome, int id, String descricao, double valor){

        if(nome.isBlank() || id == 0 || descricao.isBlank() || valor == 0) {
            System.out.println("Valores Inválidos");
        }
        else {

        Plano plano = new Plano(nome, id, descricao, valor);
        BancoDeDados.getInstance().planos.add(plano);
        System.out.println("Plano Cadastrado");

        }
    }

    //Remover Um Plano
    public void removerPlano(int id){

        if(id == 0){
            System.out.println("Valores Inválidos");
        }
        else{

            Plano plRemove = null;

            for(Plano pl : BancoDeDados.getInstance().planos){
                if(pl.getId() == id){
                    plRemove = pl;

                    BancoDeDados.getInstance().planos.remove(plRemove);

                    System.out.println("Plano Removido");
                    break;
                }
            }
            if(plRemove == null){
                System.out.println("Valores Inválidos");
            }
        }
    }

    public void cadastrarCusto(){

    }

}