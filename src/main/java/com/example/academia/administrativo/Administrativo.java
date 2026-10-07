package com.example.academia.administrativo;

    import com.example.academia.ClassesUniversais.*;

public class Administrativo extends Funcionario {

    //Construtor
    public Administrativo(int id, String nome, String senha, String cpf, double salario){
        super(id, nome, senha, cpf, salario);
    }

    //Métodos

    //Cadastra Um Departamento
    public void cadastrarDepartamento(String nome){

        Departamento departamento;

        boolean nomeEncontrado = false;

        for(Departamento dp : BancoDeDados.getInstance().departamentos){
            if(dp.getNome().equals(nome)){
                nomeEncontrado = true;
                break;
            }
        }

        if(nome.isEmpty() || nomeEncontrado){
            System.out.println("Valores Inválidos");
        }
        else{
            departamento = new Departamento(nome);
            BancoDeDados.getInstance().departamentos.add(departamento);
            System.out.println("Departamento Cadastrado");
        }
    }

    //Remove Um Departamento
    public void removerDepartamento(String nome){

        if(nome.isEmpty()){
            System.out.println("Valores Inválidos");
        }
        else {

            Departamento dpRemove = null;

            for (Departamento dp : BancoDeDados.getInstance().departamentos) {
                if (dp.getNome().equals(nome)) {
                    dpRemove = dp;
                    break;
                }
            }

            if (dpRemove == null) {
                System.out.println("Valores Inválidos");
            } else {
                BancoDeDados.getInstance().departamentos.remove(dpRemove);
            }
        }
    }

}
