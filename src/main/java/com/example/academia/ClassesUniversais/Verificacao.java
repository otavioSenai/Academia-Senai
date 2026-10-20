package com.example.academia.ClassesUniversais;

public class Verificacao {

    //Verificar Se a Senha Está Correta
    public static boolean verificarSenha(String senha){

        final int MinimoLetraMaiuscula = 1;
        final int MinimoNumero = 1;
        final int MinimoCaractereEspecial = 1;
        final int TamanhoMinimo = 8;

        if(senha == null || senha.length() < TamanhoMinimo){
            return false;
        }

            int contMaiusculo = 0;
            int contNumero = 0;
            int contCaractereEspecial = 0;

            for (int i = 0; i < senha.length(); i++) {

                char c =  senha.charAt(i);

                if(Character.isUpperCase(c)){
                    contMaiusculo++;
                }
                if(Character.isDigit(c)){
                    contNumero++;
                }
                if(!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)){
                    contCaractereEspecial++;
                }
            }
        return contCaractereEspecial >= MinimoCaractereEspecial && contMaiusculo >= MinimoLetraMaiuscula && contNumero >= MinimoNumero;
    }

    //Verificar Se o cpf Está Correto
    public static boolean verificarCpf(String cpf){

        final int Tamanho = 14;
        final int qtdNumero = 11;
        final int qtdCaractereEspecial = 3;

        if(cpf == null || cpf.length() < Tamanho){
            return false;
        }

        int contNumero = 0;
        int contCaractereEspecial = 0;

        //Verifica os 11 números
            for(int i = 0; i < cpf.length(); i++){
                if(i == 3 || i == 7 || i == 11){
                    continue;
                }
                if(Character.isDigit(cpf.charAt(i))){
                    contNumero ++;
                }
            }

            //Verifica os 3 caracteres especiais
            for(int i = 0; i < cpf.length(); i ++){
                if(i == 3 || i == 7 || i == 11){
                    if(Character.isDigit(1)) {
                        contCaractereEspecial++;
                    }
                }
            }
            return contCaractereEspecial == qtdCaractereEspecial && contNumero == qtdNumero;
    }
}