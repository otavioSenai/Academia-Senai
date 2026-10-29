package com.example.academia.ClassesUniversais;

public class Verificacao {

    //Verificar Se a Senha Está Correta
    public static boolean verificarSenha(String senha) {
        CriptografarSenha cripto=new CriptografarSenha();

        final int MinimoLetraMaiuscula = 1;
        final int MinimoNumero = 1;
        final int MinimoCaractereEspecial = 1;
        final int TamanhoMinimo = 8;

        if (senha == null || senha.length() < TamanhoMinimo) {
            return false;
        }

        int contMaiusculo = 0;
        int contNumero = 0;
        int contCaractereEspecial = 0;

        for (int i = 0; i < senha.length(); i++) {

            char caractereAtual = senha.charAt(i);

            if (Character.isUpperCase(caractereAtual)) {
                contMaiusculo++;
            }
            if (Character.isDigit(caractereAtual)) {
                contNumero++;
            }
            if (!Character.isLetterOrDigit(caractereAtual) && !Character.isWhitespace(caractereAtual)) {
                contCaractereEspecial++;
            }
        }
        return contCaractereEspecial >= MinimoCaractereEspecial && contMaiusculo >= MinimoLetraMaiuscula && contNumero >= MinimoNumero;
    }

    //Verificar Se o cpf Está Correto
    public static boolean verificarCpf(String cpf) {

        final int Tamanho = 14;
        final int qtdNumero = 11;
        final int qtdCaractereEspecial = 3;

        if (cpf == null || cpf.length() != Tamanho) {
            return false;
        }

        int contNumero = 0;
        int contCaractereEspecial = 0;

        for (int i = 0; i < cpf.length(); i++) {

            char caractereAtual = cpf.charAt(i);

            if (i == 3 && caractereAtual == '.' || i == 7 && caractereAtual == '.' || i == 11 && caractereAtual == '-') {
                contCaractereEspecial++;
            } else if (i != 3 && i != 7 && i != 11 && Character.isDigit(caractereAtual)) {
                contNumero++;
            }
        }
        return contCaractereEspecial == qtdCaractereEspecial && contNumero == qtdNumero;
    }
}