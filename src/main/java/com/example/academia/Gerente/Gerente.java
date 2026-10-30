package com.example.academia.Gerente;
import com.example.academia.ClassesUniversais.*;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import com.example.academia.Treinador.Treinador;

import javax.swing.plaf.basic.BasicLookAndFeel;

import java.util.Map;
import java.util.Scanner;


public class Gerente extends Funcionario {
int demissoes=0;
int contratacao=0;
int alunosExpulsos=0;


public Gerente(int id, String nome, String senha, String cpf, double salario){

    super(id, nome, senha, cpf, salario);
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
    public void CadastrarAluno(int id, String nome, String senha, String cpf, int idade, double altura, double peso, AssinaturaPlano plano, String intensidade){
        if (id <= 0 || BancoDeDados.getInstance().alunos.containsKey(id)|| nome.isBlank() || cpf.isBlank() || idade < 13 || altura <= 0 || peso <= 0 || intensidade.isBlank() || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf)){
            System.out.println("Valores Inválidos");
        }
        else{
            // Se passou nas verificações, chama a classe para criptografar em SHA-512
            String senhaCriptografada = CriptografarSenha.SenhaGeral(senha);
            Aluno aluno = new Aluno(id, nome, senhaCriptografada, cpf, idade, altura, peso, plano, intensidade);
            BancoDeDados.getInstance().alunos.put(id, aluno);
        }
    }

    //Expulsar Aluno
    public void expulsarAluno(int id){
        if(BancoDeDados.getInstance().alunos.containsKey(id)){
            BancoDeDados.getInstance().alunos.remove(id);
        }
        else{
            System.out.println("Aluno Não Encontrado");
        }
    }

    //Cadastrar Professor
    public void cadastrarProfessor(int id, String nome, String senha, String cpf, double salario){
        if(BancoDeDados.getInstance().treinadores.containsKey(id) || id <= 0 || nome.isBlank() || Verificacao.verificarSenha(senha) || Verificacao.verificarCpf(cpf) || salario <= 0){
            System.out.println("Valores Inválidos");
        }
        else{
            // Se passou nas verificações, chama a classe para criptografar em SHA-512
            String senhaCriptografada = CriptografarSenha.SenhaGeral(senha);
            Treinador treinador = new Treinador(id, nome, senhaCriptografada, cpf, salario);
            BancoDeDados.getInstance().treinadores.put(id, treinador);
        }
    }

    //Demitir Professor
    public void demitirProfessor(int id){
        if(BancoDeDados.getInstance().treinadores.containsKey(id)){
            BancoDeDados.getInstance().treinadores.remove(id);
        }
        else{
            System.out.println("Professor Não Encontrado");
        }
    }

    //Cadastrar Administrativo
    public void cadastrarAdministrativo(int id, String nome, String senha, String cpf, double salario){
        if(BancoDeDados.getInstance().administrativos.containsKey(id) || id <= 0 || nome.isBlank() || Verificacao.verificarSenha(senha) || Verificacao.verificarCpf(cpf) || salario <= 0){
            System.out.println("Valores Inválidos");
        }
        else{
            // Se passou nas verificações, chama a classe para criptografar em SHA-512
            String senhaCriptografada = CriptografarSenha.SenhaGeral(senha);
            Administrativo administrativo = new Administrativo(id, nome, senhaCriptografada, cpf, salario);
            BancoDeDados.getInstance().administrativos.put(id, administrativo);
        }
    }

    //Demitir Administrativo
    public void demitirAdministrativo(int id){
        if(BancoDeDados.getInstance().administrativos.containsKey(id)){
            BancoDeDados.getInstance().administrativos.remove(id);
        }
        else{
            System.out.println("Administrativo Não Encontrado");
        }
    }

    //Adicionar Plano
    public void addPlano(String nome, int id, String descricao, double valor, String validade){
        if(BancoDeDados.getInstance().planos.containsKey(id) || id <= 0 || nome.isBlank() || descricao.isBlank() || valor <= 0 || validade.isBlank()){
            System.out.println("Valores Inválidos");
        }
        else{
            Plano plano = new Plano(nome, id, descricao, valor);
            BancoDeDados.getInstance().planos.put(id, plano);
        }
    }

    //Remover Plano
    public void removerPlano(int id){
        if(BancoDeDados.getInstance().planos.containsKey(id)){
            BancoDeDados.getInstance().planos.remove(id);
        }
        else{
            System.out.print("Plano Não Encontrado");
        }
    }

    //Ver Carteirinha do Aluno
    public void carteirinhaAluno(int id, String nome, String senha, String cpf, int idade, double altura, double peso, AssinaturaPlano plano, String intensidade){
        Aluno aluno=new Aluno(id, nome, senha, cpf, idade, altura, peso, plano, intensidade);
        aluno.mostrarCarteirinhaAluno();
    }

    //Ver Carteirinha do Treinador
    public void carteirinhaTreinador(String senha, double salario){
        Treinador prof=new Treinador(id, nome, senha, cpf, salario);
        prof.mostrarCarteirinhaTreinador();
    }

    //Ver Carteirinha do Administrativo
    public void CarteirinhaAdministrativo(double salario, String senha, String cpf){
        Administrativo administrativo = new Administrativo(id, nome, senha, cpf, salario);
        administrativo.exibirInformacoes();
    }

    //Verificar Gráfico
    public void VerificarGrafico(int idade,String nome,String senha, double altura, double peso, Plano plano, String intensidade ){
        GraficoRenda grafico=new GraficoRenda();
        System.out.println("Renda"+grafico.getRenda());
        System.out.println("Situação" +grafico.getSituacao());
    }
}