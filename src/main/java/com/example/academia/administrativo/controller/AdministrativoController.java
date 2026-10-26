package com.example.academia.administrativo.controller;

import com.example.academia.Main;
import javafx.animation.TranslateTransition;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class AdministrativoController {

    @FXML private Pane menuLateral;

    @FXML private StackPane rootPane;

    @FXML private Button AcessarDepartamento;

    @FXML private final Pane peliculaFundo = new Pane();


    public void initialize() {
        fecharMenu();
    }

    @FXML
    public void abrirMenu(){
        menuLateral.setManaged(true);
        menuLateral.setVisible(true);

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(250), menuLateral);
        slideIn.setToX(0);
        slideIn.play();
    }

    @FXML
    public void fecharMenu(){

        TranslateTransition slideOut = new TranslateTransition(Duration.millis(250), menuLateral);
        slideOut.setToX(124);
        slideOut.setOnFinished(e -> menuLateral.setVisible(false));
        slideOut.play();
    }

    @FXML
    protected void MudarCor(){

        AcessarDepartamento.setStyle("-fx-background-color: #f1ba2a;" + "-fx-text-fill: white;" + "-fx-font: 11px");

    }

    @FXML
    protected void clicarBotao(){
    }
}