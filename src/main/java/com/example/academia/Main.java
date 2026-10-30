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
    private static Scene cenaDono;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        stage.setTitle("Academia Ícaro - Painel Dono");

        Parent fxmlDono = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/academia/dono/telas/Teste/dono-view.fxml")));
        cenaDono = new Scene(fxmlDono, 1280, 720);

        URL css = getClass().getResource("/com/example/academia/dono/telas/Teste/VisaoGeral1.css");

        if(css != null){
            cenaDono.getStylesheets().add(css.toExternalForm());
        }
        else{
            System.out.println("Erro: Sistema CSS não encontrado");
        }

        stage.setScene(cenaDono);
        stage.setMinWidth(1024);
        stage.setMinHeight(768);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}