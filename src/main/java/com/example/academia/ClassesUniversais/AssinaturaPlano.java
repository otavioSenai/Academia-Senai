package com.example.academia.ClassesUniversais;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class AssinaturaPlano {

    private int id;
    private Map<Integer, Instant> dataPagamento = new HashMap<>();
    private Instant validade;
    private int mesesValidade;
    private String status;
    private Plano plano;

    public AssinaturaPlano(int validade, Plano plano){
        this.mesesValidade = validade;
        this.status = "Em Dia";
        this.plano = plano;

        ZoneId fuso = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime agora = ZonedDateTime.now(fuso);
        this.validade = agora.plusMonths(this.mesesValidade).toInstant();
    }


    //Pegar Id
    public int getId(){
        return this.id;
    }

    public Plano getPlano(){
        return this.plano;
    }

    //Mudar Plano
    public void setPlano(Plano plano){
        this.plano = plano;
    }

    //Pegar Validade
    public Instant getValidade(){
        return this.validade;
    }

    //Mudar Validade
    public boolean addValidade(int mesesValidade){
        if(mesesValidade <= 0){
            return false;
        }
        else {
            this.mesesValidade = mesesValidade;
            ZoneId fuso = ZoneId.of("America/Sao_Paulo");
            this.validade = Instant.now().atZone(fuso).plusMonths(this.mesesValidade).toInstant();
            return true;
        }
    }

    //Ver se o pagamento Atrasou
    public boolean tempoPagar(){
        Instant Agora = Instant.now();

        if(Agora.isAfter(this.validade)){
            this.status = "Atrasado";
            return false;
        }
        this.status = "Em Dia";
        return true;
    }

    //Pegar Status
    public String getStatus(){
        return this.status;
    }

    //Efutuar o Pagamento do Plano
    public boolean pagarPlano(int id){
        if(plano.getId() != id){
            return false;
        }
        Instant momentoPagamento = Instant.now();
        this.dataPagamento.put(id, momentoPagamento);

        ZoneId fuso = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime validadeZona = this.validade.atZone(fuso);

        ZonedDateTime novaValidade = validadeZona.plusMonths(this.mesesValidade);
        this.validade = novaValidade.toInstant();
        this.status = "Em Dia";
        return true;
    }

    //Pegar a Data Em Que o Pagamento Foi Realizado
    public Instant getDataPagamento(int id){
        return this.dataPagamento.get(id);
    }

    //Mostrar as Modalidade
    public boolean showModalidades(){
        if(plano.getModalidade().isEmpty()){
            return false;
        }
        for(Modalidade m : plano.getModalidade().values()){
            System.out.println(m.getId() + " - " + m.getNome());
            System.out.println(m.getDescricao());
            m.showTreinadores();
        }
        return true;
    }
}