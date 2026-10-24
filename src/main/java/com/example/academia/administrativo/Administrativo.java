package com.example.academia.administrativo;

    import com.example.academia.ClassesUniversais.*;
    import com.example.academia.aluno.Aluno;
    import com.example.academia.Treinador.Treinador;

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

    //Cadastrar Custo
    public void cadastrarCusto(String nome, int id, String data, double valor){

        if(nome.isBlank() || id <= 0 || BancoDeDados.getInstance().custos.containsKey(id) || data.isBlank() || valor <= 0){
            System.out.println("Valores Inválidos");
        }
        else{
            Custo custo = new Custo(nome,id, data, valor);
            BancoDeDados.getInstance().custos.put(id, custo);
        }
    }

    //Remover Custo
    public void removerCusto(int id){
        if(!BancoDeDados.getInstance().custos.containsKey(id)){
            System.out.println("Valores Inválidos");
        }
        else{
            BancoDeDados.getInstance().custos.remove(id);
            System.out.println("Custo Removido");
        }
    }

    //Cadastrar Um Plano
    public void cadatrarPlano(String nome, int id, String descricao, double valor, String validade){

        if(nome.isBlank() || id <= 0 || BancoDeDados.getInstance().planos.containsKey(id) || descricao.isBlank() || valor <= 0 || validade.isBlank()) {
            System.out.println("Valores Inválidos");
        }
        else {
        Plano plano = new Plano(nome, id, descricao, valor, validade);
        BancoDeDados.getInstance().planos.put(id, plano);
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

    //Cadastrar Um Treinador
    public void cadastrarTreinador(int id, String nome, String senha, String cpf, double salario){

        if(id <= 0 || BancoDeDados.getInstance().treinadores.containsKey(id) || nome.isBlank() || cpf.isBlank() || salario <= 0 || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf)){
            System.out.println("Valores Inválidos");
        }
        else{
            Treinador treinador = new Treinador(id, nome, senha, cpf, salario);
            BancoDeDados.getInstance().treinadores.put(id, treinador);
            System.out.println("Treinador Cadastrado");
        }
    }

    //Demitir Um Treinador
    public void demitirTreinador(int id){

        if(!BancoDeDados.getInstance().treinadores.containsKey(id)){
            System.out.println("Valores Inválidos");
        }
        else{
            BancoDeDados.getInstance().treinadores.remove(id);
            System.out.println("Treinador Removido");
        }
    }

    //Cadastrar Um Aluno
    public void cadastrarAluno(int id, String nome, String senha, String cpf, int idade, double altura, double peso, Plano plano, String intensidade){

            if (id <= 0 || BancoDeDados.getInstance().alunos.containsKey(id)|| nome.isBlank() || cpf.isBlank() || idade < 13 || altura <= 0 || peso <= 0 || intensidade.isBlank() || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf)){
                System.out.println("Valores Inválidos");
            } else {
                Aluno aluno = new Aluno(id, nome, senha, cpf, idade, altura, peso, plano, intensidade);
                BancoDeDados.getInstance().alunos.put(id, aluno);
                System.out.println("Aluno Cadastrado");
            }
    }

    //Expulsar Um Aluno
    public void expulsarAluno(int id) {

        if (!BancoDeDados.getInstance().alunos.containsKey(id)) {
            System.out.println("Valores Inválidos");
        } else {
            BancoDeDados.getInstance().alunos.remove(id);
            System.out.println("Aluno Removido");
        }
    }
}