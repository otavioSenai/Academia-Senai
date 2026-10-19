package com.example.academia.administrativo;

    import com.example.academia.ClassesUniversais.*;
    import com.example.academia.aluno.Aluno;

public class Administrativo extends Funcionario {

    //Construtor
    public Administrativo(int id, String nome, String senha, String cpf, double salario){
        super(id, nome, senha, cpf, salario);
    }

    //Métodos

    //Cadastra Um Departamento
    public void cadastrarDepartamento(String nome, int id, String descricao){

        if(id <= 0 || nome.isEmpty() || descricao.isEmpty()){
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

        if(id <= 0){
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

    public void cadastrarCusto(String nome, int id, String data, double valor){

        if(nome.isEmpty() || id <= 0 || data.isEmpty() || valor <= 0){
            System.out.println("Valores Inválidos");
        }
        else{

            Custo custo = new Custo(nome,id, data, valor);
            BancoDeDados.getInstance().custos.add(custo);

        }
    }

    public void removerCusto(int id){
        if(id <=0){
            System.out.println("Valores Inválidos");
        }
        else{

            Custo coRemove = null;

            for(Custo co : BancoDeDados.getInstance().custos){
                if(co.getId() == id){
                    coRemove = co;

                    BancoDeDados.getInstance().custos.remove(coRemove);
                    System.out.println("Custo Removido");
                    break;
                }
            }
            if(coRemove == null){
                System.out.println("Valores Inválidos");
            }
        }
    }

    //Cadastrar Um Plano
    public void cadatrarPlano(String nome, int id, String descricao, double valor){

        if(nome.isEmpty() || id <= 0 || descricao.isEmpty() || valor <= 0) {
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

        if(id <= 0){
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

    //Cadastrar Um Professor
    public void cadastrarProfessor(){

    }

    //Demitir Um Professor
    public void demitirProfessor(){

    }

    //Cadastrar Um Aluno
    public void cadastrarAluno(int id, String nome, String senha, String cpf, int idade, double altura, double peso, String intensidade){

        if(id <= 0 || nome.isEmpty() || senha.isEmpty() || cpf.isEmpty() || idade < 13 || altura <= 0 || peso <= 0 || intensidade.isEmpty()){
            System.out.println("Valores Inválidos");
        }
        else{

            Aluno aluno = new Aluno(id, nome, senha, cpf, idade, altura, peso, intensidade);
            BancoDeDados.getInstance().alunos.add(aluno);
            System.out.println("Aluno Cadastrado");
        }
    }

    //Expulsar Um Aluno
    public void expulsarAluno(int id){

        if(id <= 0){
            System.out.println("Valores Inválidos");
        }

        Aluno alRemove = null;

        for(Aluno al : BancoDeDados.getInstance().alunos){
            if(al.getId() == id){
                alRemove = al;
                BancoDeDados.getInstance().alunos.remove(alRemove);
                System.out.println("Aluno Removido");
                break;
            }
        }
        if(alRemove == null){
            System.out.println("Aluno Não Encontrado");
        }
    }

}