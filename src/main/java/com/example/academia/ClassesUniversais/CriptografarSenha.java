package com.example.academia.ClassesUniversais;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
public class CriptografarSenha {

    public static String SenhaGeral(String senhaCriptografada){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = md.digest(senhaCriptografada.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao inicializar o algoritmo SHA-512", e);
        }
    }
}
