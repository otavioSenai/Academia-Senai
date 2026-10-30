package com.example.academia.Dono.Controller.Teste;

import com.example.academia.ClassesUniversais.AssinaturaPlano;
import com.example.academia.ClassesUniversais.Plano;
import com.example.academia.aluno.Aluno;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class VisaoGeralController implements Initializable{

    @FXML private TableColumn<Aluno, String> colAluno;
    @FXML private TableColumn<Plano, String> colPlano;
    @FXML private TableColumn<Plano, Double> colValor;
    @FXML private TableColumn<AssinaturaPlano, String> colStatus;
    @FXML private TableColumn<AssinaturaPlano, String> colData;

    @FXML private Label lblFaturamento;
    @FXML private Label lblFolhaPagamento;
    @FXML private Label lblCustos;
    @FXML private Label lblLucro;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabela();
        inicializarMetricasVazias();
    }

    private void configurarTabela() {
        colAluno.setCellValueFactory(new PropertyValueFactory<>("aluno"));
        colPlano.setCellValueFactory(new PropertyValueFactory<>("plano"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
    }

    private void inicializarMetricasVazias() {
        // Regra 4: Não preencher com dados fictícios. Inicializando estado vazio para o DB futuro.
        lblFaturamento.setText("R$ 0,00");
        lblFolhaPagamento.setText("R$ 0,00");
        lblCustos.setText("R$ 0,00");
        lblLucro.setText("R$ 0,00");
    }
}