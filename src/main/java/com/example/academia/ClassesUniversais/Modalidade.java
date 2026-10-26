package com.example.academia.ClassesUniversais;

import com.example.academia.Treinador.Treinador;

import java.util.HashMap;
import java.util.Map;

public class Modalidade {

    static Map treinadores = new HashMap();
    String modalidade;

    //Escolhe a Modalidade
    public void escolherModalidade(String modalidadeEscolhida){
        boolean ModalidadeExistente = false;

        for(ModalidadeEnums m : BancoDeDados.getInstance().modalidadeEnums){
            if(m.name().equalsIgnoreCase(modalidadeEscolhida)){

                this.modalidade = m.toString();
                ModalidadeExistente = true;
                break;
            }
        }
        if(!ModalidadeExistente){
            System.out.println("Valores Inválidos");
        }
    }

    //Pega a Modalidade
    public String getModalidade(){
        return this.modalidade;
    }

    //Adiciona um Professor
    public void addTreinador(int id, Treinador treinador){
        if(!treinadores.containsKey(id)){
            treinadores.put(id, treinador);
        }
        else{
            System.out.println("Valores Inválidos");
        }
    }

    //Pega o Treinador
    public String getTreinador(int id){
        if(treinadores.containsKey(id)){
            return treinadores.get(id).toString();
        }
        return "Professor Não Enontrado";
    }
}
