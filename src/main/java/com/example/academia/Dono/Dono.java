package com.example.academia.Dono;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.Funcionario;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.professor.Professor;

public class Dono extends Funcionario  {
    public Dono(int id, String nome, String senha, String cpf, double salario) {

        super(id, nome, senha, cpf, salario);
    }
    public void demitirProfessor(int id){
        Professor professorRemover= null;
        for(Professor prof: BancoDeDados.getInstance().professores){
            if(prof.getId()==id){
                professorRemover=prof;
                BancoDeDados.getInstance().administrativos.remove(professorRemover);

            }
            else if(professorRemover==null){
                System.out.println("Valores Inválidos");
            }
            break;
        }
    }
    public void CadastrarProfessor(int id, String nome, String senha, String cpf, double salario){

        Professor professor= new Professor(id, nome, senha, cpf, salario);
        BancoDeDados.getInstance().professores.add(professor);

    }


}
