package com.example.academia.Gerente;
import com.example.academia.ClassesUniversais.*;
import com.example.academia.administrativo.Administrativo;

public class Gerente extends Gerencia {
    int demissoes=0;
    int contratacao=0;
    int alunosExpulsos=0;
    int cadastroAlunos=0;

    public Gerente(String nome, String senha, String cpf, double salario){
        super(nome, senha, cpf, salario);
        gerarId(0);
    }

    public boolean cadastrarAdministrativo(String nome, String senha, String cpf, double salario){
        if(nome.isBlank() || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf) || salario <= 0){
            return false;
        }
        Administrativo administrativo = new Administrativo(nome,CriptografarSenha.SenhaGeral(senha), cpf, salario);
        administrativo.setSenhaOriginal(senha);
        BancoDeDados.getInstance().administrativos.put(administrativo.getId(), administrativo);
        return true;
    }

    //Demitir Administrativo
    public boolean demitirAdministrativo(int id){
        if(BancoDeDados.getInstance().administrativos.containsKey(id)){
            BancoDeDados.getInstance().administrativos.remove(id);
            this.demissoes++;
            return true;
        }
        else{
            return false;
        }
    }
}