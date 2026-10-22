package com.example.academia.administrativo;

    import com.example.academia.ClassesUniversais.*;
    import com.example.academia.aluno.Aluno;
    import com.example.academia.professor.Professor;

public class Administrativo extends Funcionario {

    //Construtor
    public Administrativo(int id, String nome, String senha, String cpf, double salario){
        super(id, nome, senha, cpf, salario);
    }

    //Métodos

    //Exibir Carterinha Do Administrativo
    public void exibirInformacoes(){
        System.out.println("Id: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Salário: " + getSalario());
    }

    //Cadastra Uma Modalidade
    public void cadastrarModalidade(String nome, int id, String descricao){

        if(id <= 0 || nome.isBlank() || descricao.isBlank()){
            System.out.println("Valores Inválidos");
        }
        else {
            boolean idEncontrado = false;

            for (Modalidade dp : BancoDeDados.getInstance().modalidades) {
                if (dp.getId() == id) {
                    idEncontrado = true;
                    break;
                }
            }
            if (idEncontrado) {
                System.out.println("Departamento Já Existente");
            } else {

                Modalidade modalidade = new Modalidade(nome, id, descricao);
                BancoDeDados.getInstance().modalidades.add(modalidade);
                System.out.println("Departamento Cadastrado");

            }
        }
    }

    //Remove Uma Modalidade
    public void removerModalidade(int id){

        if(id <= 0){
            System.out.println("Valores Inválidos");
        }

        else {
            Modalidade dpRemove = null;

            for (Modalidade dp : BancoDeDados.getInstance().modalidades) {
                if (dp.getId() == id) {
                    dpRemove = dp;

                    BancoDeDados.getInstance().modalidades.remove(dpRemove);

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

        if(nome.isBlank() || id <= 0 || data.isBlank() || valor <= 0){
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

        if(nome.isBlank() || id <= 0 || descricao.isBlank() || valor <= 0) {
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
    public void cadastrarProfessor(int id, String nome, String senha, String cpf, double salario){

        if(id <= 0 || nome.isBlank() || cpf.isBlank() || salario <= 0 || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf)){
            System.out.println("Valores Inválidos");
        }
        else{

            Professor professor = new Professor(id, nome, senha, cpf, salario);
            BancoDeDados.getInstance().professores.add(professor);
            System.out.println("Professor Cadastrado");
        }

    }

    //Demitir Um Professor
    public void demitirProfessor(int id){

            if(id <= 0){
                System.out.println("Valores Inválidos");
            }
            else{
                Professor prRemove = null;

                for(Professor pr : BancoDeDados.getInstance().professores){
                    if(pr.getId() == id){
                        prRemove = pr;

                        BancoDeDados.getInstance().professores.remove(prRemove);
                        System.out.println("Professor Removido");
                        break;
                    }
                }
                if(prRemove == null){
                    System.out.println("Professor Não Encontrado");
                }
            }
    }

    //Cadastrar Um Aluno
    public void cadastrarAluno(int id, String nome, String senha, String cpf, int idade, double altura, double peso, String intensidade, Plano plano){

            if (id <= 0 || nome.isBlank() || cpf.isBlank() || idade < 13 || altura <= 0 || peso <= 0 || intensidade.isBlank() || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf)){
                System.out.println("Valores Inválidos");
            } else {

                Aluno aluno = new Aluno(id, nome, senha, cpf, idade, altura, peso, plano, intensidade);
                BancoDeDados.getInstance().alunos.add(aluno);
                System.out.println("Aluno Cadastrado");
            }
    }

    //Expulsar Um Aluno
    public void expulsarAluno(int id) {

        if (id <= 0) {
            System.out.println("Valores Inválidos");
        } else {
            Aluno alRemove = null;

            for (Aluno al : BancoDeDados.getInstance().alunos) {
                if (al.getId() == id) {
                    alRemove = al;
                    BancoDeDados.getInstance().alunos.remove(alRemove);
                    System.out.println("Aluno Removido");
                    break;
                }
            }
            if (alRemove == null) {
                System.out.println("Aluno Não Encontrado");
            }
        }
    }
}