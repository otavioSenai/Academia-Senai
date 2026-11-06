package com.example.academia.Login;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.CriptografarSenha;
import com.example.academia.Dono.Dono;
import com.example.academia.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Optional;

public class LoginController {

    @FXML private TextField txtCpf;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblErro;

    // Conta do Dono
    public static Dono obterDono() {
        if (BancoDeDados.getInstance().dono.isEmpty()) {
            BancoDeDados.getInstance().dono = Optional.of(new Dono("Dono", CriptografarSenha.SenhaGeral("Senha@123"), "11111111111", 5000.0));
        }
        return BancoDeDados.getInstance().dono.get();
    }

    @FXML
    private void initialize() {
        obterDono();
        lblErro.setVisible(false);
    }

    @FXML
    private void entrar() {
        Dono d = obterDono();

        // Aceita o CPF com ou sem pontos e traço
        String cpfDigitado = txtCpf.getText() == null ? "" : txtCpf.getText().replaceAll("\\D", "");
        String cpfDono = d.getCpf().replaceAll("\\D", "");
        String senhaDigitada = txtSenha.getText() == null ? "" : txtSenha.getText();

        boolean cpfOk = !cpfDigitado.isEmpty() && cpfDigitado.equals(cpfDono);
        boolean senhaOk = !senhaDigitada.isEmpty() && CriptografarSenha.SenhaGeral(senhaDigitada).equals(d.getSenha());

        if (!cpfOk || !senhaOk) {
            lblErro.setText("CPF ou senha incorretos.");
            lblErro.setVisible(true);
            txtSenha.clear();
            return;
        }
        Main.abrirPainelDono();
    }
}