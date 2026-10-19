package com.example.academia.ClassesUniversais;
import com.example.academia.aluno.Aluno;
import com.example.academia.professor.Professor;
import java.util.Scanner;

import com.example.academia.ClassesUniversais.BancoDeDados;


public class Gerente{
private String nome;
private String senha;
private String cpf;
private int id;
Scanner sc= new Scanner(System.in);
    Aluno aluno=new Aluno( id, nome, senha, cpf);
    Professor professor = new Professor(id, nome, senha, cpf);
public void setNome(String nome) {

    this.nome = nome;
}
public void setSenha(String senha) {

    this.senha = senha;
}
public void setCpf(String cpf) {
    this.cpf = cpf;
}

public String getNome() {

    return nome;
}

public String getSenha() {

    return senha;
}
public String getCpf(){
    return cpf;

}

public void CadastrarAluno(int id, String nome, String senha , String cpf){

    System.out.println("Nome:");
    aluno.getNome();
    System.out.println("Id:");
    aluno.getId();
    System.out.println("Senha:");
    aluno.getSenha();
    System.out.println("CPF:");
    aluno.getCpf();
    if(aluno.getNome().isEmpty() && aluno.getId()<0 && aluno.getSenha().isEmpty() && aluno.getCpf().isEmpty() ){
        System.out.println("Aluno não foi cadastrado");

    }
    else if(aluno.getId()==id){
        System.out.println("Este aluno já pertencema a nossa Academia");
    }
    else{
        System.out.println("Aluno Cadastrado !");
    }
}
public void cadastrarDepartamento(String descricao){
    Departamento departamento=new Departamento(nome,id,descricao);
    BancoDeDados.getInstance().departamentos.add(departamento);
}
public void ExpulsarAluno(int id){

    Aluno alRemove = null;

    for(Aluno al : BancoDeDados.getInstance().alunos) {
        if (al.getId() == id) {
            alRemove = al;
            BancoDeDados.getInstance().alunos.remove(alRemove);
            System.out.println("Aluno Removido");
            break;
        }
    }
    if(alRemove == null){
        System.out.println("Valores Inválidos");
    }

}
public void cadastrarProfessores(int id, String nome, String senha, String cpf){

    BancoDeDados.getInstance().professores.add(professor);
}
public void demitirProfesores(){
 Professor prof=null;
 for(Aluno prof: BancoDeDados.getInstance().professores){
//     profRemove = prof;
//     BancoDeDados.getInstance().professores.remove(profRemove);

 }
}


}
