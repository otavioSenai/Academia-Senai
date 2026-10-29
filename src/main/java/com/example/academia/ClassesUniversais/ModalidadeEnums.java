package com.example.academia.ClassesUniversais;

public enum ModalidadeEnums {

    MUSCULACAO("Musculação"),
    PILATE("Pilates"),
    CROSSFIT("Crossfit"),
    CARDIO("Cardio");

    private String Modalidade;

    ModalidadeEnums(String modalidade){

        this.Modalidade = modalidade;

    }

    public String getModalidade(){

        return this.Modalidade;

    }
}