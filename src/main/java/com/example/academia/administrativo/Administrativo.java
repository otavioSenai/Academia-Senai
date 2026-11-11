package com.example.academia.administrativo;
import com.example.academia.ClassesUniversais.*;

import java.util.Map;

public class Administrativo extends Gerencia {
    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }
    //Construtor
    public Administrativo(String nome, String senha, String cpf, double salario){
        super(nome, senha, cpf, salario);
        gerarId(Id(BancoDeDados.getInstance().administrativos));
    }
}