package com.example.academia.ClassesUniversais;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import com.example.academia.professor.Treinador;

import java.util.Map;
import java.util.Scanner;


public class Gerente extends Funcionario{
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
        Aluno aluno=new Aluno(id, nome, senha, cpf, idade, altura, peso, plano, intensidade);
    System.out.println("Nome:");
    String nomeAluno = sc.nextLine();
    System.out.println("Id:");
    aluno.getId();
    System.out.println("Senha:");
    aluno.getSenha();
    System.out.println("CPF:");
    aluno.getCpf();
    System.out.println("Idade:");
    int idadeAluno = sc.nextInt();
    if(aluno.getNome().isEmpty() || aluno.getId()<0 || aluno.getSenha().isEmpty() || aluno.getCpf().isEmpty() ){
        System.out.println("Aluno não foi cadastrado");

    }
    else if(aluno.getId()==id){
        aluno.setNome(nomeAluno);
        aluno.setIdade(idadeAluno);
        System.out.println("Este aluno já pertencema a nossa Academia");
    }
    else{
        System.out.println("Aluno Cadastrado !");
    }
}
public void cadastrarDepartamento(String descricao){
    Modalidade modalidade =new Modalidade(nome,id,descricao);
    BancoDeDados.getInstance().modalidades.add(modalidade);
    if(BancoDeDados.getInstance().alunos == null){
        System.out.println("Este Departamento Não Existe No Sistema");
    }
    else{
        System.out.println("Este Departamento Já Existe No Sistema");
    }
}
    public void excluirDepartamento(int id){
        Modalidade depRemove=null;
        for( Modalidade departamento:BancoDeDados.getInstance().modalidades){
            if(departamento.getId()==id){
                BancoDeDados.getInstance().modalidades.remove(depRemove);
                System.out.println("Departamento Excluído");
            }
            else if(depRemove==null){
                System.out.println("Valores Inválidos");
            }
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
public void cadastrarProfessores(int id, String nome, String senha, String cpf){
    Treinador treinador = new Treinador(id, nome, senha, cpf, salario);
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
   public void acessarPlanos(String nome, int id, String descricao, double valor){
    Plano planos= new Plano( nome,id, descricao,valor);
    BancoDeDados.getInstance().planos.add(planos);
}
   public void RemoverPlano(int id){
    Plano plRemove=null;
    for(Plano pl:BancoDeDados.getInstance().planos){
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

    public void VerficarCarteirinhaProf(String senha){
        Treinador prof=new Treinador(id, nome, senha, cpf, salario);
        prof.mostrarCarteirinhaProfessor();

    }

public void VerificarGrafico(int idade,String nome,String senha, double altura, double peso, Plano plano, String intensidade ){
        GraficoRenda grafico=new GraficoRenda();
        System.out.println("Renda"+grafico.getRenda( id, nome, senha,  cpf, idade, altura, peso,  plano, intensidade));
        System.out.println("Situação" +grafico.getSituacao());
    }
    public void cadastrarAdministrativo(int id, String nome, String senha, String cpf, double salario){
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
