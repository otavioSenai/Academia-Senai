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
        stage.setMinWidth(1024);
        stage.setMinHeight(768);
        // O sistema SEMPRE começa na tela de login do Dono
        mostrarLogin();
        stage.show();
    }

    public static void mostrarLogin() {
        trocarCena("/com/example/academia/dono/telas/login-view.fxml", "Academia Ícaro - Login");
    }

    // Só é chamado pelo LoginController depois que CPF e senha foram validados
    public static void abrirPainelDono() {
        trocarCena("/com/example/academia/dono/telas/dono-view.fxml", "Academia Ícaro - Painel Dono");
    }

    private static void trocarCena(String caminhoFxml, String titulo) {
        try {
            Parent raiz = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(caminhoFxml)));
            Scene cena = new Scene(raiz, 1280, 720);

            URL css = Main.class.getResource("/com/example/academia/dono/telas/VisaoGeral.css");
            if (css != null) {
                cena.getStylesheets().add(css.toExternalForm());
            } else {
                System.out.println("Erro: Sistema CSS não encontrado");
            }

            stage.setTitle(titulo);
            stage.setScene(cena);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar a tela: " + caminhoFxml, e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}