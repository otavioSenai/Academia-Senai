package com.example.academia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.initStyle(javafx.stage.StageStyle.UNDECORATED); // Remove botões do Windows
        mostrarLogin();
        stage.show();
    }

    public static void mostrarLogin() {
        trocarCena("/login-view.fxml", "Academia Ícaro - Login");
    }

    // Só é chamado pelo LoginController depois que CPF e senha foram validados
    public static void abrirPainelDono() {
        trocarCena("/dono-view.fxml", "Academia Ícaro - Painel Dono");
    }

    public static Stage getStage() {
        return stage;
    }

    private static void trocarCena(String caminhoFxml, String titulo) {
        try {
            java.net.URL fxmlUrl = Main.class.getResource(caminhoFxml);
            if (fxmlUrl == null) {
                throw new RuntimeException("O ARQUIVO NÃO FOI ENCONTRADO: " + caminhoFxml);
            }
            Parent raiz = javafx.fxml.FXMLLoader.load(fxmlUrl);

            // Se a cena ainda não existir (primeira vez), cria uma. Se já existir, apenas troca o recheio.
            if (stage.getScene() == null) {
                Scene cena = new Scene(raiz);
                stage.setScene(cena);
            } else {
                stage.getScene().setRoot(raiz);
            }
            // Aplica o CSS na cena atual
            java.net.URL css = Main.class.getResource("/VisaoGeral.css");
            if (css != null) {
                stage.getScene().getStylesheets().clear();
                stage.getScene().getStylesheets().add(css.toExternalForm());
            }
            stage.setTitle(titulo);

            // Garante que a tela cheia se mantém sem interrupções
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar a tela: " + caminhoFxml, e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}