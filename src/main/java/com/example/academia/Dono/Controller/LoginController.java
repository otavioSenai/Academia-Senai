package com.example.academia.Dono.Controller;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.CriptografarSenha;
import com.example.academia.ClassesUniversais.Verificacao;
import com.example.academia.Dono.Dono;
import com.example.academia.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

public class LoginController {

    @FXML private TextField txtCpf;
    @FXML private PasswordField txtSenha;
    @FXML private TextField txtSenhaVisivel;
    @FXML private Label lblErro;
    @FXML private FontIcon iconOlho;
    private boolean senhaVisivel = false;

    public static Dono obterDono() {
        BancoDeDados bd = BancoDeDados.getInstance();
        if (bd.dono.isEmpty()) {
            Dono d = new Dono("Dono Master", CriptografarSenha.SenhaGeral("Senha@123"), "11111111111", 0);
            d.setSenhaOriginal("Senha@123");
            bd.dono = Optional.of(d);
        }
        return bd.dono.get();
    }

    @FXML
    private void initialize() {
        obterDono();
        lblErro.setVisible(false);
        txtSenhaVisivel.textProperty().bindBidirectional(txtSenha.textProperty());

        txtCpf.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;
            String digits = newVal.replaceAll("\\D", "");
            if (digits.length() > 11) digits = digits.substring(0, 11);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digits.length(); i++) {
                if (i == 3 || i == 6) sb.append(".");
                if (i == 9) sb.append("-");
                sb.append(digits.charAt(i));
            }
            Platform.runLater(() -> {
                txtCpf.setText(sb.toString());
                txtCpf.positionCaret(sb.length());
            });
        });
    }

    @FXML
    private void alternarVisibilidadeSenha() {
        senhaVisivel = !senhaVisivel;
        txtSenhaVisivel.setVisible(senhaVisivel);
        txtSenha.setVisible(!senhaVisivel);
        if (senhaVisivel) { iconOlho.setIconLiteral("fas-eye"); }
        else { iconOlho.setIconLiteral("fas-eye-slash"); }
    }

    @FXML
    private void sairConfirmacao() {
        Stage stage = new Stage();
        stage.initOwner(Main.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT); // Remove as pontas brancas

        VBox root = new VBox();
        root.setStyle("-fx-background-color: #06090e; -fx-border-color: #141923; -fx-border-width: 1px; -fx-border-radius: 6; -fx-background-radius: 6;");
        root.setPrefSize(450, 220);
        root.setMinSize(450, 220);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setStyle("-fx-padding: 10px 16px 0 0;");
        Button btnX = new Button("X");
        btnX.setStyle("-fx-background-color: transparent; -fx-text-fill: #ef4444; -fx-font-weight: bold; -fx-font-size: 16px;");
        btnX.setOpacity(0);
        btnX.setDisable(true);
        header.getChildren().add(btnX);

        VBox content = new VBox(16);
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-padding: 0 32 32 32;");

        Label lblMsg = new Label("Você tem certeza que deseja sair do sistema?\nTodos os dados não salvos serão perdidos no momento que sair.");
        lblMsg.setStyle("-fx-text-fill: #f8fafc; -fx-font-size: 15px;");
        lblMsg.setWrapText(true);
        lblMsg.setPrefWidth(380);

        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setStyle("-fx-background-color: #ef4444; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand; -fx-background-radius: 6; -fx-border-radius: 6;");
        btnConfirmar.setOnAction(e -> System.exit(0));

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: transparent; -fx-border-color: #94a3b8; -fx-text-fill: #94a3b8; -fx-border-radius: 6; -fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 8 16;");
        btnCancelar.setOnAction(e -> stage.close());
        btnCancelar.setCancelButton(true);

        HBox boxBotoes = new HBox(16, btnCancelar, btnConfirmar);
        boxBotoes.setAlignment(Pos.CENTER);

        content.getChildren().addAll(lblMsg, new Region(), boxBotoes);
        root.getChildren().addAll(header, content);

        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT); // Remove o fundo branco quadrado
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void entrar() {
        Dono d = obterDono();
        String cpfDigitadoCru = txtCpf.getText() == null ? "" : txtCpf.getText().trim();
        String senhaDigitada = txtSenha.getText() == null ? "" : txtSenha.getText();

        if (!Verificacao.verificarCpf(cpfDigitadoCru) || !Verificacao.verificarSenha(senhaDigitada)) {
            lblErro.setText("Formato de CPF ou Senha inválido.");
            lblErro.setVisible(true);
            txtSenha.clear();
            return;
        }

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
        try { Main.abrirPainelDono(); } catch (Exception e){ e.printStackTrace(); }
    }
}