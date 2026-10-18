import java.util.Scanner;
package com.example.academia.administrativo;

    import com.example.academia.ClassesUniversais.*;

public class Gerente extends Funcionario {
    public static void main(String[] args) {
        Gerente gerente = new Gerente(1, "João", "senha123", "123456789", 5000.0);
        gerente.cadastrarDepartamento("Departamento de Musculação");
        gerente.cadastrarDepartamento("Departamento de Cardio");
         gerente.cadastrarDepartamento("Departamento de Crossfit");
          gerente.cadastrarDepartamento("Departamento de Fitness");
            
}
        
    } 
    Scanner sc= new Scanner(System.in);
    public Gerente(int id, String nome, String senha, String cpf, double salario){
        super(id, nome, senha, cpf, salario);
    }
   public void cadastrarDepartamento(String nome){

        Departamento departamento;

        boolean nomeEncontrado = false;

        for(Departamento dp : BancoDeDados.getInstance().departamentos){
            if(dp.getNome().equals(nome)){
                nomeEncontrado = true;
                break;
            }
        }

        if(nome.isEmpty() || nomeEncontrado){
            System.out.println("Valores Inválidos");
        }
        else{
            departamento = new Departamento(nome);
            BancoDeDados.getInstance().departamentos.add(departamento);
            System.out.println("Departamento Cadastrado");
        }
    }

    //Remove Um Departamento
    public void removerDepartamento(String nome){

        if(nome.isEmpty()){
            System.out.println("Valores Inválidos");
        }
        else {

            Departamento dpRemove = null;

            for (Departamento dp : BancoDeDados.getInstance().departamentos) {
                if (dp.getNome().equals(nome)) {
                    dpRemove = dp;
                    break;
                }
            }

            if (dpRemove == null) {
                System.out.println("Valores Inválidos");
            } else {
                BancoDeDados.getInstance().departamentos.remove(dpRemove);
            }
        }
    }
    public void Planos(String nome){
       

    }

   
}
    
