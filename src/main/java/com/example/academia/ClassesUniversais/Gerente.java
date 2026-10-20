package com.example.academia.ClassesUniversais;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import com.example.academia.professor.Professor;
import java.util.Scanner;
import com.example.academia.ClassesUniversais.BancoDeDados;


public class Gerente extends Funcionario{
private String nome;
private String senha;
private String cpf;
private int id;
int demissoes=0;
int contratacao=0;
int alunosExpulsos=0;


public Gerente(int id, String nome, String senha, String cpf, double salario){
    super(id, nome, senha, cpf, salario);
}
Scanner sc= new Scanner(System.in);
    Aluno aluno=new Aluno( id,  nome,  senha,  cpf, int idade, double altura, double peso, String intensidade);
    Professor professor = new Professor(id, nome, senha, cpf);

    public void CarteirinhaGerente(){
        System.out.println("Nome:" + getNome());
        System.out.println("Senha:" + getSenha());
        System.out.println("Demissões:" + demissoes);
        System.out.println("Contratações:" + contratacao);
        System.out.println("Alunos Expulsos:" + alunosExpulsos);
        System.out.println("Salario á Pagar" + getSalario());

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
    if(aluno.getNome().isEmpty() || aluno.getId()<0 || aluno.getSenha().isEmpty() || aluno.getCpf().isEmpty() ){
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
public void CadastrarAluno(int id, String nome, String senha, String cpf, int idade, double altura, double peso, String intensidade){
    Aluno alunos=new Aluno(id, nome, senha, cpf);
    BancoDeDados.getInstance().alunos.add(alunos);
    contratacao++;
}
public void ExpulsarAluno(int id){

    Aluno alRemove = null;

    for(Aluno al : BancoDeDados.getInstance().alunos) {
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

    BancoDeDados.getInstance().professores.add(professor);
    contratacao++;
}
public void demitirProfesores(int id){
 Professor profRemove=null;
 for(Professor prof: BancoDeDados.getInstance().professores){
     if(prof.getId()==id){
         profRemove = prof;
         BancoDeDados.getInstance().professores.remove(profRemove);
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
    public void VerficarCarteirinhaAluno(){
//Kenen

    }

    public void VerficarCarteirinhaProf(){
//kenen

    }
public void VerificarGrafico(){
        GraficoRenda();

    }

    public void DemitirAdministrativo(int id){
    Administrativo adRemove= null;
    for(Administrativo ad: BancoDeDados.getInstance().administrativos){
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
public void VerificarCarteirinhaAd(double salario){
    Administrativo ad= new Administrativo(id, nome, senha, cpf, salario);
    System.out.println("Carteirinha Do Administrativo:");
    System.out.println("Nome: " + ad.getNome());
    System.out.println("CPF: " + this.cpf);
    System.out.println("Salário: " + ad.getCpf());
}

}
