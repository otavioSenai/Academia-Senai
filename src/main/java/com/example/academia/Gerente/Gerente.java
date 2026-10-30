package com.example.academia.Gerente;
import com.example.academia.ClassesUniversais.*;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import com.example.academia.Treinador.Treinador;

public class Gerente extends Funcionario {
int demissoes=0;
int contratacao=0;
int alunosExpulsos=0;


public Gerente(String nome, String senha, String cpf, double salario){
    super(nome, senha, cpf, salario);
    gerarId(0);
}

    //Mostra a Carteirinha do Gerente
    public void CarteirinhaGerente(){
        System.out.println("Nome:" + getNome());
        System.out.println("Senha:" + getSenha());
        System.out.println("Demissões:" + demissoes);
        System.out.println("Contratações:" + contratacao);
        System.out.println("Alunos Expulsos:" + alunosExpulsos);
        System.out.println("Salario á Pagar" + getSalario());

    }

    //Cadastrar Aluno
    public boolean CadastrarAluno(int id, String nome, String senha, String cpf, int idade, double altura, double peso, AssinaturaPlano plano, String intensidade){
        if (id <= 0 || BancoDeDados.getInstance().validIdAluno(id)|| nome.isBlank() || cpf.isBlank() || idade < 13 || altura <= 0 || peso <= 0 || intensidade.isBlank() || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf)){
            return false;
        }
        else{
            // Se passou nas verificações, chama a classe para criptografar em SHA-512
            String senhaCriptografada = CriptografarSenha.SenhaGeral(senha);
            Aluno aluno = new Aluno(nome, senhaCriptografada, cpf, idade, altura, peso, plano, intensidade);
            BancoDeDados.getInstance().alunos.put(id, aluno);
            return true;
        }
    }

    //Expulsar Aluno
    public boolean expulsarAluno(int id){
        if(BancoDeDados.getInstance().alunos.containsKey(id)){
            BancoDeDados.getInstance().alunos.remove(id);
            return true;
        }
        else{
            return false;
        }
    }

    //Cadastrar Treinador
    public boolean cadastrarTreinador(int id, String nome, String senha, String cpf, double salario, String especialidade){
        if(BancoDeDados.getInstance().validarIdTreinador(id) || id <= 0 || nome.isBlank() || Verificacao.verificarSenha(senha) || Verificacao.verificarCpf(cpf) || salario <= 0){
            return false;
        }
        else{
            // Se passou nas verificações, chama a classe para criptografar em SHA-512
            String senhaCriptografada = CriptografarSenha.SenhaGeral(senha);
            Treinador treinador = new Treinador(nome, senhaCriptografada, cpf, salario, especialidade);
            BancoDeDados.getInstance().treinadores.put(id, treinador);
            return true;
        }
    }

    //Demitir Treinador
    public boolean demitirTreinador(int id){
        if(BancoDeDados.getInstance().treinadores.containsKey(id)){
            BancoDeDados.getInstance().treinadores.remove(id);
            return true;
        }
        else{
            return false;
        }
    }

    //Cadastrar Administrativo
    public boolean cadastrarAdministrativo(int id, String nome, String senha, String cpf, double salario){
        if(BancoDeDados.getInstance().validarIdAdministrativo(id) || id <= 0 || nome.isBlank() || Verificacao.verificarSenha(senha) || Verificacao.verificarCpf(cpf) || salario <= 0){
            return false;
        }
        else{
            // Se passou nas verificações, chama a classe para criptografar em SHA-512
            String senhaCriptografada = CriptografarSenha.SenhaGeral(senha);
            Administrativo administrativo = new Administrativo(nome, senhaCriptografada, cpf, salario);
            BancoDeDados.getInstance().administrativos.put(id, administrativo);
            return true;
        }
    }

    //Demitir Administrativo
    public boolean demitirAdministrativo(int id){
        if(BancoDeDados.getInstance().administrativos.containsKey(id)){
            BancoDeDados.getInstance().administrativos.remove(id);
            return true;
        }
        else{
            return false;
        }
    }

    //Adicionar Plano
    public boolean addPlano(String nome, int id, String descricao, double valor, String validade){
        if(BancoDeDados.getInstance().validarIdPlano(id) || id <= 0 || nome.isBlank() || descricao.isBlank() || valor <= 0 || validade.isBlank()){
            return false;
        }
        else{
            Plano plano = new Plano(nome, id, descricao, valor);
            BancoDeDados.getInstance().planos.put(id, plano);
            return true;
        }
    }

    //Remover Plano
    public boolean removerPlano(int id){
        if(BancoDeDados.getInstance().planos.containsKey(id)){
            BancoDeDados.getInstance().planos.remove(id);
            return true;
        }
        else{
            return false;
        }
    }

    //Ver Carteirinha do Aluno
    public void carteirinhaAluno(int id){
        BancoDeDados.getInstance().alunos.get(id).mostrarCarteirinhaAluno();
    }

    //Ver Carteirinha do Treinador
    public void carteirinhaTreinador(int id){
        BancoDeDados.getInstance().treinadores.get(id).mostrarCarteirinhaTreinador();
    }

    //Ver Carteirinha do Administrativo
    public void CarteirinhaAdministrativo(int id){
        BancoDeDados.getInstance().administrativos.get(id).exibirInformacoes();
    }

    //Verificar Gráfico
    public void VerificarGrafico(int idade,String nome,String senha, double altura, double peso, Plano plano, String intensidade ){
        GraficoRenda grafico=new GraficoRenda();
        System.out.println("Renda: "+grafico.getRenda());
        System.out.println("Situação: " +grafico.getSituacao());
    }
}