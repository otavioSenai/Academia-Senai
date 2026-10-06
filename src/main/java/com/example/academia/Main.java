package com.example.academia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // 1. Busca y carga la interfaz gráfica que construimos en FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/academia/aluno/aluno-view.fxml"));

        // 2. Crea la escena indicando el tamaño inicial de la ventana (ancho: 900px, alto: 600px)
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        // 3. Configura el título de la barra superior de la ventana
        stage.setTitle("Academia Ícaro - Painel do Aluno");

        // 4. Asigna la escena a la ventana y la muestra en pantalla
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Inicia el ciclo de vida de la aplicación JavaFX
        launch();
    }
}