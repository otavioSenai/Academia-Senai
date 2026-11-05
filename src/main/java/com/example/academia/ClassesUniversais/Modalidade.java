package com.example.academia.ClassesUniversais;
import com.example.academia.Treinador.Treinador;
import java.util.HashMap;
import java.util.Map;

public class Modalidade {

    public int Id(Map<Integer, ?> mapa) {
        return mapa.keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(-1) + 1;
    }

    private int id;
    private Map <Integer,Treinador> treinadores = new HashMap<>();
    private String nome;
    private String descricao;

    //Construtor
    public Modalidade(String nome, String descricao){
        this.id = Id(BancoDeDados.getInstance().modalidades);
        this.nome = nome;
        this.descricao = descricao;
    }

    // pegar id
    public int getId(){
        return this.id;
    }

    // Mudar o Nome
    public boolean setNome(String nome){
        if(nome.isBlank()){
            return false;
        }
        this.nome = nome;
        return true;
    }

    //Pega o Nome
    public String getNome(){
        return this.nome;
    }

    //Mudar a Descricao
    public boolean setDescricao(String descricao){
        if(descricao.isBlank()){
            return false;
        }
        this.descricao = descricao;
        return true;
    }

    //Pegar Descricao
    public String getDescricao(){
        return this.descricao;
    }

    //Adiciona um Treinador
    public boolean addTreinador(int id, Treinador treinador){
        if(BancoDeDados.getInstance().validarIdTreinador(id)){
            treinadores.put(id, treinador);
            return true;
        }
        else{
            return false;
        }
    }
    //Remove Um Treinador
    public boolean removeTreinador(int idModalidade, int idTreinador){
        if(BancoDeDados.getInstance().validarIdModalidade(idModalidade)){
            treinadores.remove(idTreinador);
            return true;
        }
        return false;
    }

    //Pega o Treinador
    public Treinador getTreinador(int id){
        return this.treinadores.get(id);
    }

    //Pegar os Treinadores
    public Map<Integer, Treinador> getTreinadores(){return this.treinadores;}

    //Mostrar os Treinadores
    public boolean showTreinadores(){
        if(treinadores.isEmpty()){
            return false;
        }
        for(Treinador t : treinadores.values()){
            System.out.println(t.getId() + " - " + t.getNome());
        }
        return true;
    }
}