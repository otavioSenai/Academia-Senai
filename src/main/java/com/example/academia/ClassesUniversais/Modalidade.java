package com.example.academia.ClassesUniversais;

public enum Modalidade {

    MUSCULACAO("Musculação"),
    PILATE("Pilates"),
    CROSSFIT("Crossfit"),
    CARDIO("Cardio");

    private String Modalidade;

    Modalidade(String modalidade){
        this.Modalidade = modalidade;
    }

    public String getModalidade(){
        return this.Modalidade;
    }

}
