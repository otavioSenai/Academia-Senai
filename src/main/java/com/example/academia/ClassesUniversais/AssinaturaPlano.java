package com.example.academia.ClassesUniversais;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class AssinaturaPlano {

    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }

    private final int id;
    private Map<Integer, Instant> dataPagamento = new HashMap<>();
    private Instant validade;
    private int mesesValidade;
    private boolean status;
    private Plano plano;
    double juro;

    public AssinaturaPlano(int idPlano, Plano plano){
        this.mesesValidade = BancoDeDados.getInstance().planos.get(idPlano).getMesesValidade();
        this.status = true;
        this.plano = plano;
        this.id = Id(BancoDeDados.getInstance().assinaturaPlano);
        this.juro = BancoDeDados.getInstance().planos.get(idPlano).getJuros();
        ZoneId fuso = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime agora = ZonedDateTime.now(fuso);
        this.validade = agora.plusMonths(this.mesesValidade).toInstant();
    }

    //Calcular Juros
    public double calcularJuro(double valor){
        return (juro / 100.0) * valor;
    }

    //Pegar Id
    public int getId(){
        return this.id;
    }
    //Pegar Plano
    public Plano getPlano(){
        return this.plano;
    }
    // Mudar Plano
    public void setPlano(Plano plano){
        this.plano = plano;
    }
    // Pegar Validade
    public Instant getValidade(){
        return this.validade;
    }
    // Pegar A validade em meses
    public int getMesesValidade(){
        return this.mesesValidade;
    }
    // Mudar Validade
    public boolean setValidade(int mesesValidade){
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
    // Ver se o pagamento Atrasou
    public boolean tempoPagar(){
        Instant Agora = Instant.now();
        if(Agora.isAfter(this.validade)){
            this.status = false;
            return false;
        }
        this.status = true;
        return true;
    }
    // Pegar Status
    public boolean getStatus(){
        return this.status;
    }
    // Efetuar o Pagamento do Plano
    public boolean pagarPlano(int idPlano, int idAluno){
        if(plano.getId() != idPlano){
            return false;
        }
        Instant momentoPagamento = Instant.now();
        this.dataPagamento.put(idPlano, momentoPagamento);
        ZoneId fuso = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime novaValidade = this.validade.atZone(fuso).plusMonths(this.mesesValidade);
        BancoDeDados.getInstance().grafico.addRenda(idAluno,getId());
        this.validade = novaValidade.toInstant();
        this.status = true;
        return true;
    }
    // Pegar a Data Em Que o Pagamento Foi Realizado
    public Instant getDataPagamento(int id){
        return this.dataPagamento.get(id);
    }
}