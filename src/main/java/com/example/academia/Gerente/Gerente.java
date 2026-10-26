package com.example.academia.Gerente;
import com.example.academia.ClassesUniversais.*;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import com.example.academia.Treinador.Treinador;

import java.util.Map;
import java.util.Scanner;


public class Gerente extends Funcionario {
int demissoes=0;
int contratacao=0;
int alunosExpulsos=0;


public Gerente(int id, String nome, String senha, String cpf, double salario){

    super(id, nome, senha, cpf, salario);
}
Scanner sc= new Scanner(System.in);



    public void CarteirinhaGerente(){
        System.out.println("Nome:" + getNome());
        System.out.println("Senha:" + getSenha());
        System.out.println("Demissões:" + demissoes);
        System.out.println("Contratações:" + contratacao);
        System.out.println("Alunos Expulsos:" + alunosExpulsos);
        System.out.println("Salario á Pagar" + getSalario());

    }


public void CadastrarAluno(int id, String nome, String senha, String cpf, int idade, double altura, double peso, Plano plano, String intensidade){
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
public void ExpulsarAluno(int id){

    Aluno alRemove = null;

    for(Map.Entry<Integer, Aluno> entrada : BancoDeDados.getInstance().alunos.entrySet()) {
        Aluno al= entrada.getValue();
        if (al.getId() == id) {
            alRemove = al;
            BancoDeDados.getInstance().alunos.remove(alRemove);
            System.out.println("Aluno Removido");
            alunosExpulsos++;
            break;
        }
        else if(alRemove == null){
        System.out.println("Valores Inválidos");
    }
    break;
}

}
public void cadastrarProfessores(int id, String nome, String senha, String cpf, double salario, String senhaCripto){
    if (!Verificacao.verificarSenha(senhaCripto)) {
        System.out.println("Erro: A senha não atende aos requisitos mínimos de segurança!");
        return; // Interrompe o método e não cadastra
    }

    if (!Verificacao.verificarCpf(cpf)) {
        System.out.println("Erro: CPF inválido!");
        return; // Interrompe o método se o CPF estiver errado
    }

    // 2. Se passou nas verificações, chama a classe para criptografar em SHA-512
    CriptografarSenha cripto = new CriptografarSenha();
    String senhaCriptografada = cripto.SenhaGeral(senhaCripto);
    Treinador treinador = new Treinador(id, nome, senha, cpf, salario);
    BancoDeDados.getInstance().treinadores.put(id,treinador);

        Treinador treinadores = new Treinador(id, nome, senha, cpf, salario);
    BancoDeDados.getInstance().treinadores.put(id,treinador);
    contratacao++;
}

public void demitirProfesores(int id){
 Treinador profRemove=null;
 for(Map.Entry<Integer, Treinador> entrada : BancoDeDados.getInstance().treinadores.entrySet()){
     Treinador prof=entrada.getValue();
     if(prof.getId()==id){
         profRemove = prof;
         BancoDeDados.getInstance().treinadores.remove(profRemove);
         System.out.println("Professor Demitido");
         demissoes++;
         break;
     }
     else if(profRemove==null){
         System.out.println("Valores Inválidos");
     }
break;

 }
}
   public void AddPlanos(String nome, int id, String descricao, double valor, String validade){

        if(nome.isBlank() || BancoDeDados.getInstance().planos.containsKey(id) || id <= 0 || descricao.isBlank() || valor <= 0 || validade.isBlank()){
            Plano planos= new Plano( nome,id, descricao,valor, validade);
            BancoDeDados.getInstance().planos.put(id, planos);
        }
}
   public void RemoverPlano(int id){
    Plano plRemove=null;
    for(Plano pl:BancoDeDados.getInstance().planos.values()){
        if(pl.getId()==id){
            plRemove=pl;
            BancoDeDados.getInstance().planos.remove(plRemove);
        }
        else if(plRemove==null){
            System.out.println("Valores Inválidos");
        }
        break;
    }
}
    public void VerficarCarteirinhaAluno(int id, String nome, String senha, String cpf, int idade, double altura, double peso, Plano plano, String intensidade){
        Aluno aluno=new Aluno(id, nome, senha, cpf, idade, altura, peso, plano, intensidade);
        aluno.mostrarCarteirinhaAluno();

    }

    public void VerficarCarteirinhaProf(String senha, double salario){
        Treinador prof=new Treinador(id, nome, senha, cpf, salario);
        prof.mostrarCarteirinhaTreinador();

    }

public void VerificarGrafico(int idade,String nome,String senha, double altura, double peso, Plano plano, String intensidade ){
        GraficoRenda grafico=new GraficoRenda();
        System.out.println("Renda"+grafico.getRenda());
        System.out.println("Situação" +grafico.getSituacao());
    }
    public void cadastrarAdministrativo(int id, String nome, String senha, String cpf, double salario, String senhaCripto){
        if (!Verificacao.verificarSenha(senhaCripto)) {
            System.out.println("Erro: A senha não atende aos requisitos mínimos de segurança!");
            return; // Interrompe o método e não cadastra
        }

        if (!Verificacao.verificarCpf(cpf)) {
            System.out.println("Erro: CPF inválido!");
            return; // Interrompe o método se o CPF estiver errado
        }

        // 2. Se passou nas verificações, chama a classe para criptografar em SHA-512
        CriptografarSenha cripto = new CriptografarSenha();
        String senhaCriptografada = cripto.SenhaGeral(senhaCripto);

        Administrativo ad=new Administrativo(id, nome,senha, cpf, salario);
        BancoDeDados.getInstance().administrativos.put(id,ad);
        contratacao++;
    }

    public void DemitirAdministrativo(int id){
    Administrativo adRemove= null;
    for(Map.Entry<Integer, Administrativo> entrada : BancoDeDados.getInstance().administrativos.entrySet()){
        Administrativo ad=entrada.getValue();
        if(ad.getId()==id){
            adRemove=ad;
            BancoDeDados.getInstance().administrativos.remove(adRemove);
            demissoes++;

        }
        else if(adRemove==null){
            System.out.println("Valores Inválidos");
        }
        break;
    }

}
public void VerificarCarteirinhaAd(double salario, String senha, String cpf){
    Administrativo administrativo = new Administrativo(id, nome, senha, cpf, salario);
     administrativo.exibirInformacoes();
}

}
