package com.example.academia.Dono.Controller.Teste;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.Funcionario;
import com.example.academia.Treinador.Treinador;
import com.example.academia.administrativo.Administrativo;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import org.kordamp.ikonli.javafx.FontIcon;

public class QuadroFuncionalController {

    @FXML private FlowPane painelFuncionarios;
    @FXML private TextField txtPesquisa;
    @FXML private Button btnFiltroTodos;
    @FXML private Button btnFiltroTreinadores;
    @FXML private Button btnFiltroAdms;

    private enum FiltroAtivo { TODOS, TREINADORES, ADMINISTRATIVOS }
    private FiltroAtivo filtroAtual = FiltroAtivo.TODOS;

    @FXML
    public void initialize() {
        carregarTodos();

        txtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            aplicarFiltros(newValue.toLowerCase());
        });
    }

    @FXML
    private void carregarTodos() {
        filtroAtual = FiltroAtivo.TODOS;
        atualizarEstiloBotoes(btnFiltroTodos);
        aplicarFiltros(txtPesquisa.getText().toLowerCase());
    }

    @FXML
    private void carregarTreinadores() {
        filtroAtual = FiltroAtivo.TREINADORES;
        atualizarEstiloBotoes(btnFiltroTreinadores);
        aplicarFiltros(txtPesquisa.getText().toLowerCase());
    }

    @FXML
    private void carregarAdministrativos() {
        filtroAtual = FiltroAtivo.ADMINISTRATIVOS;
        atualizarEstiloBotoes(btnFiltroAdms);
        aplicarFiltros(txtPesquisa.getText().toLowerCase());
    }

    private void atualizarEstiloBotoes(Button ativo) {
        String inativoStyle = "-fx-background-color: #181c23; -fx-text-fill: #c7c6c6; -fx-background-radius: 6px;";
        String ativoStyle = "-fx-background-color: #f1ba2a; -fx-text-fill: #04070d; -fx-font-weight: bold; -fx-background-radius: 6px;";

        btnFiltroTodos.setStyle(inativoStyle);
        btnFiltroTreinadores.setStyle(inativoStyle);
        btnFiltroAdms.setStyle(inativoStyle);
        ativo.setStyle(ativoStyle);
    }

    private void aplicarFiltros(String termo) {
        painelFuncionarios.getChildren().clear();

        if (filtroAtual == FiltroAtivo.TODOS || filtroAtual == FiltroAtivo.TREINADORES) {
            for (Treinador treinador : BancoDeDados.getInstance().treinadores.values()) {
                if (treinador.getNome().toLowerCase().contains(termo) || String.valueOf(treinador.getId()).contains(termo)) {
                    painelFuncionarios.getChildren().add(criarCard(treinador, "Treinador"));
                }
            }
        }

        if (filtroAtual == FiltroAtivo.TODOS || filtroAtual == FiltroAtivo.ADMINISTRATIVOS) {
            for (Administrativo adm : BancoDeDados.getInstance().administrativos.values()) {
                if (adm.getNome().toLowerCase().contains(termo) || String.valueOf(adm.getId()).contains(termo)) {
                    painelFuncionarios.getChildren().add(criarCard(adm, "Administrativo"));
                }
            }
        }
    }

    /**
     * Cria dinamicamente a interface da carteirinha baseada nas saídas exibirInformacoes() e mostrarCarteirinhaTreinador() do original.
     */
    private VBox criarCard(Funcionario func, String cargo) {
        VBox card = new VBox();
        card.getStyleClass().add("card-panel");
        card.setPrefWidth(350);
        card.setSpacing(16);
        card.setStyle("-fx-padding: 0 0 16 0; -fx-background-color: #06090e; -fx-border-color: #0e1116; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        // Faixa Superior
        Region faixaDourada = new Region();
        faixaDourada.setPrefHeight(6);
        faixaDourada.setStyle(cargo.equals("Treinador") ? "-fx-background-color: #f1ba2a; -fx-background-radius: 8px 8px 0 0;" : "-fx-background-color: #808080; -fx-background-radius: 8px 8px 0 0;");

        // Cabeçalho (Ícone, Nome, ID, Cargo)
        HBox cabecalho = new HBox(12);
        cabecalho.setPadding(new Insets(16, 16, 0, 16));
        cabecalho.setAlignment(Pos.CENTER_LEFT);

        FontIcon icone = new FontIcon(cargo.equals("Treinador") ? "fas-dumbbell" : "fas-briefcase");
        icone.setIconSize(32);
        icone.setIconColor(javafx.scene.paint.Color.web(cargo.equals("Treinador") ? "#f1ba2a" : "#808080"));

        VBox infoNome = new VBox(2);
        Label lblNome = new Label(func.getNome());
        lblNome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #f8fafc;");

        Label lblMatricula = new Label("ID: " + func.getId() + " • " + cargo.toUpperCase());
        lblMatricula.setStyle("-fx-font-size: 11px; -fx-text-fill: #94a3b8; -fx-font-family: monospace;");
        infoNome.getChildren().addAll(lblNome, lblMatricula);

        cabecalho.getChildren().addAll(icone, infoNome);

        // Dados Corporativos
        GridPane matriz = new GridPane();
        matriz.setHgap(24);
        matriz.setVgap(16);
        matriz.setPadding(new Insets(8, 16, 0, 16));

        matriz.add(criarDadoCard("CPF Cadastrado", func.getCpf()), 0, 0);

        // Extrai o Salário (Necessita coerção/cast seguro pois getSalario() é protected na original)
        // Nota: É necessário garantir que getSalario() esteja acessível (public) no domínio original, ou usar um método delegador.
        String salarioStr = "Visão Restrita";
        if (func instanceof Treinador || func instanceof Administrativo) {
            // Adaptado para reflexão simples caso getSalario não tenha sido exposto, assumindo que foi exposto na UI
            salarioStr = "Informação confidencial"; // Placeholder caso encapsulado
        }

        // Verifica se é Treinador para exibir Especialidade
        if (func instanceof Treinador) {
            Treinador treinador = (Treinador) func;
            String esp = treinador.getEspecialidade() != null ? treinador.getEspecialidade() : "Não informada";
            matriz.add(criarDadoCard("Especialidade", esp), 0, 1);
        }

        card.getChildren().addAll(faixaDourada, cabecalho, matriz);
        return card;
    }

    private VBox criarDadoCard(String titulo, String valor) {
        VBox box = new VBox(2);
        Label lblTitulo = new Label(titulo.toUpperCase());
        lblTitulo.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #64748b;");
        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #f8fafc;");
        box.getChildren().addAll(lblTitulo, lblValor);
        return box;
    }
}
