package com.example.academia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    private static Stage stage;

    private static Scene cenaAdministrativo;
    private static Scene cenaAdministrativoAddDepartamento;


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Academia Ícaro - Painel Administrador");


        Parent fxmlAdministrativo = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/academia/administrativo/telas/Administrativo.fxml")));
        cenaAdministrativo = new Scene(fxmlAdministrativo, 600, 400);

        Parent fxmlAdministrativoAddDepartamento = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/academia/administrativo/telas/AdministrativoAddDepartamento.fxml")));
        cenaAdministrativoAddDepartamento = new Scene(fxmlAdministrativoAddDepartamento, 300, 200);

        stage.setScene(cenaAdministrativo);
        stage.show();
    }

    public static void telaAddDepartamento(){
        if(cenaAdministrativo != null && cenaAdministrativoAddDepartamento != null) {
            stage.setScene(cenaAdministrativoAddDepartamento);
        }
    }

    public static void main(String[]args){
        launch(args);
    }
}