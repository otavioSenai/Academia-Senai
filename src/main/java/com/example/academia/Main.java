package com.example.academia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    private static Stage stage;

    private static Scene cenaDono;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Academia Ícaro - Painel Dono");

        Parent fxmlDono = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getResource(
                                "/com/example/academia/dono/telas/telasDono.fxml"
                        )
                )
        );

        cenaDono = new Scene(fxmlDono, 600, 400);

        stage.setScene(cenaDono);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}