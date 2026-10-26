package com.example.academia.ClassesUniversais;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AssinaturaPlano {

    Plano plano;

    private Map<Integer, Instant> dataPagamento = new HashMap<>();
    private Instant validade;
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.of("America/Sao_Paulo"));
    private String status;
    private Plano plano;

    public AssinaturaPlano(String validade, Plano plano){
        this.validade = Instant.from(this.formatador.parse(validade));
        this.status = "Em Dia";
        this.plano = plano;
    }

    //Ver se o pagamento Atrasou
    public boolean tempoPagar(){
        Instant Agora = Instant.now();

        if(Agora.isAfter(this.validade)){
            System.out.println("Pagamento Atrasado");
            this.status = "Atrasado";
            return false;
        }
        System.out.println("Pagamento em Dia");
        this.status = "Em Dia";
        return true;
    }

    //Pegar Validade
    public Instant getValidade(){
        return this.validade;
    }

    //Mudar Validade
    public void addValidade(String validade){
        if(validade.isBlank()){
            System.out.println("Valores Inválidos");
        }
        else {
            this.validade = Instant.from(this.formatador.parse(validade));
        }
    }
}