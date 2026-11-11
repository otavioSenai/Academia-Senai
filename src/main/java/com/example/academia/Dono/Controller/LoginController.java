package com.example.academia.Dono.Controller;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.CriptografarSenha;
import com.example.academia.ClassesUniversais.Verificacao;
import com.example.academia.Dono.Dono;
import com.example.academia.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

public class LoginController {
    @FXML
    private TextField txtCpf;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private TextField txtSenhaVisivel; // NOVO CAMPO
    @FXML
    private Label lblErro;

    @FXML
    private FontIcon iconOlho;

    private boolean senhaVisivel = false; // ESTADO DO OLHO

    public static Dono obterDono() {
        BancoDeDados bd = BancoDeDados.getInstance();
        if (bd.dono.isEmpty()) {
            bd.dono = Optional.of(new Dono("Dono Master", CriptografarSenha.SenhaGeral("Senha@123"), "11111111111", 5000.0));
        }
        return bd.dono.get();
    }

    @FXML
    private void initialize() {
        obterDono();
        lblErro.setVisible(false);
        txtSenhaVisivel.textProperty().bindBidirectional(txtSenha.textProperty());
    }

    @FXML
    private void alternarVisibilidadeSenha() {
        senhaVisivel = !senhaVisivel;
        txtSenhaVisivel.setVisible(senhaVisivel);
        txtSenha.setVisible(!senhaVisivel);
        if (senhaVisivel) {
            iconOlho.setIconLiteral("fas-eye");
        } else {
            iconOlho.setIconLiteral("fas-eye-slash");
        }
    }

    @FXML
    private void fecharApp() {
        System.exit(0);
    }

    @FXML
    private void entrar() {
        Dono d = obterDono();

        String cpfDigitadoCru = txtCpf.getText() == null ? "" : txtCpf.getText().trim();
        String senhaDigitada = txtSenha.getText() == null ? "" : txtSenha.getText();

        // 1. VALIDAÇÃO DE FORMATO DO CPF E DA SENHA UTILIZANDO A SUA CLASSE
        if (!Verificacao.verificarCpf(cpfDigitadoCru) || !Verificacao.verificarSenha(senhaDigitada)) {
            lblErro.setText("Formato de CPF ou Senha inválido.");
            lblErro.setVisible(true);
            txtSenha.clear();
            return;
        }

        // 2. COMPARAÇÃO DE CREDENCIAIS
        String cpfDigitado = cpfDigitadoCru.replaceAll("\\D", "");
        String cpfDono = d.getCpf().replaceAll("\\D", "");

        boolean cpfOk = cpfDigitado.equals(cpfDono);
        boolean senhaOk = CriptografarSenha.SenhaGeral(senhaDigitada).equals(d.getSenha());

        if (!cpfOk || !senhaOk) {
            lblErro.setText("CPF ou senha incorretos.");
            lblErro.setVisible(true);
            txtSenha.clear();
            return;
        }

        try {
            Main.abrirPainelDono();
        } catch (Exception e){
            e.printStackTrace(); // Imprime o erro vermelho no terminal do Intellij/Eclipse
        }
    }
}