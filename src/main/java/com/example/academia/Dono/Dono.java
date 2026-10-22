package com.example.academia.Dono;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.Funcionario;
import com.example.academia.ClassesUniversais.Plano;
import com.example.academia.Gerente.Gerente;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import com.example.academia.professor.Professor;

public class Dono extends Funcionario{
    public Dono(int id, String nome, String senha, String cpf, double salario) {
        super(id, nome, senha, cpf, salario);
    }

    //para evitar repetir a mesma coisa 100 veces
    static Aluno aluno = null;
    static Professor professor = null;
    static Gerente gerente = null;
    static Administrativo administrativo = null;
    static Plano plano = null;
    //para evitar repetir a mesma coisa 100 veces

    //Alunos
    public void cadastrarAluno(){
        BancoDeDados.getInstance().alunos.add(aluno);
        System.out.println(BancoDeDados.getInstance().alunos.get(0));
    }
    public void removerAluno(){
        BancoDeDados.getInstance().alunos.remove(aluno);
    }

    //Professores
    public void cadastrarProfessor(){
        BancoDeDados.getInstance().professores.add(professor);
    }
    public void removerProfessor(){
        BancoDeDados.getInstance().professores.remove(professor);
    }

    //Planos
    public void cadastrarPlano(){
        BancoDeDados.getInstance().planos.add(plano);
    }
    public void removerPlanos(){
        BancoDeDados.getInstance().planos.remove(plano);
    }
    public void verificarPlanos() {
        for (int i = 0; i > BancoDeDados.getInstance().planos.size(); i++)
            BancoDeDados.getInstance().planos.get(i);
    }


}


