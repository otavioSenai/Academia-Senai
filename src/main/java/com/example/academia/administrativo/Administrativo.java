package com.example.academia.administrativo;

    import com.example.academia.ClassesUniversais.*;
    import com.example.academia.aluno.Aluno;
    import com.example.academia.Treinador.Treinador;

    import java.util.Map;

public class Administrativo extends Funcionario {

    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }

    //Construtor
    public Administrativo(String nome, String senha, String cpf, double salario){
        super(nome, senha, cpf, salario);
        gerarId(Id(BancoDeDados.getInstance().administrativos));
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
    public boolean cadastrarCusto(String nome, int id, String data, double valor){

        if(nome.isBlank() || id <= 0 || BancoDeDados.getInstance().validarIdCusto(id) || data.isBlank() || valor <= 0){
            return false;
        }
        else{
            Custo custo = new Custo(nome,id, data, valor);
            BancoDeDados.getInstance().custos.put(id, custo);
            return true;
        }
    }

    //Remover Custo
    public boolean removerCusto(int id){

        if(BancoDeDados.getInstance().validarIdCusto(id)){
            BancoDeDados.getInstance().custos.remove(id);
            return true;
        }
        else{
            return false;
        }
    }

    //Cadastrar Um Plano
    public boolean cadatrarPlano(String nome, int id, String descricao, double valor, int mesesValidade){

        if(nome.isBlank() || id <= 0 || BancoDeDados.getInstance().validarIdPlano(id) || descricao.isBlank() || valor <= 0 || mesesValidade <= 0) {
            return false;
        }
        else {
            Plano plano = new Plano(nome, id, descricao, valor, mesesValidade);
            BancoDeDados.getInstance().planos.put(id, plano);
            return true;
        }
    }

    //Remover Um Plano
    public boolean removerPlano(int id){

        if(BancoDeDados.getInstance().validarIdPlano(id)){
            BancoDeDados.getInstance().planos.remove(id);
            return true;
        }
        else{
            return false;
        }
    }

    //Cadastrar Um Treinador
    public boolean cadastrarTreinador(int id, String nome, String senha, String cpf, double salario, String especialidade){

        if(id <= 0 || BancoDeDados.getInstance().validarIdTreinador(id) || especialidade.isBlank() || nome.isBlank() || cpf.isBlank() || salario <= 0 || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf)){
            return false;
        }
        else{
            Treinador treinador = new Treinador(nome, senha, cpf, salario, especialidade);
            BancoDeDados.getInstance().treinadores.put(id, treinador);
            return true;
        }
    }

    //Demitir Um Treinador
    public boolean demitirTreinador(int id){

        if(BancoDeDados.getInstance().validarIdTreinador(id)){
            BancoDeDados.getInstance().treinadores.remove(id);
            return true;
        }
        else{
            return false;
        }
    }

    //Cadastrar a Assinatura do Plano
    public boolean cadastrarAssinaturaPlano(int id, int validade, Plano plano){
        if(validade <= 0 || plano == null || id <= 0){
            return false;
        }
        else{
            AssinaturaPlano assinaturaPlano = new AssinaturaPlano(validade, plano);
            BancoDeDados.getInstance().assinaturaPlano.put(id,assinaturaPlano);
            return true;
        }
    }

    //Cadastrar Um Aluno
    public boolean cadastrarAluno(int id, String nome, String senha, String cpf, int idade, double altura, double peso, AssinaturaPlano plano, String intensidade, Treinador treinador){

            if (id <= 0 || BancoDeDados.getInstance().validIdAluno(id)|| nome.isBlank() || cpf.isBlank() || idade < 13 || altura <= 0 || peso <= 0 || intensidade.isBlank()
                    || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf) || BancoDeDados.getInstance().validarIdTreinador(treinador.getId())){
                return false;
            } else {
                Aluno aluno = new Aluno(nome, senha, cpf, idade, altura, peso, plano, intensidade, treinador);
                BancoDeDados.getInstance().alunos.put(id, aluno);
                return true;
            }
    }

    //Expulsar Um Aluno
    public boolean expulsarAluno(int id) {

        if (BancoDeDados.getInstance().validIdAluno(id)) {
            BancoDeDados.getInstance().alunos.remove(id);
            return true;
        }
        else {
            return false;
        }
    }
}