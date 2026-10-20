package com.example.academia.Dono;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.Funcionario;
import com.example.academia.Gerente.Gerente;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import com.example.academia.professor.Professor;

public class Dono extends Funcionario{
    public Dono(int id, String nome, String senha, String cpf, double salario) {
        super(id, nome, senha, cpf, salario);
    }

    public void cadastrarAluno(){
        Aluno aluno = null;
        BancoDeDados.getInstance().alunos.add(aluno);
        System.out.println(BancoDeDados.getInstance().alunos.get(0));
    }

}


