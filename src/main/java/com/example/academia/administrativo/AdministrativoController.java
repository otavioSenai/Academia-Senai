package com.example.academia.administrativo;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.academia.ClassesUniversais.Departamento;
import com.example.academia.Main;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdministrativoController {

    @FXML private Button AcessarDepartamento;

    ObservableList<Departamento> listaDepartamentos = FXCollections.observableArrayList();

    @FXML
    protected void MudarCor(){

        AcessarDepartamento.setStyle("-fx-background-color: #f1ba2a;" + "-fx-text-fill: white;" + "-fx-font: 11px");

    }

    @FXML
    protected void clicarBotao(){
        Main.clicarBotao();
    }
}