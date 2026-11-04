
package com.example.academia.aluno;

import com.example.academia.ClassesUniversais.Plano;
import com.example.academia.ClassesUniversais.Treino;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class AlunoController {
/*
    @FXML
    private Label lblNome;

    @FXML
    private Label lblIdade;

    @FXML
    private Label lblAltura;

    @FXML
    private Label lblPeso;

    @FXML
    private Label lblPlano;

    @FXML
    private Label lblIntensidade;

    @FXML
    private Label lblQuantidadeTreinos;

    @FXML
    private Label lblTreinos;

    private Aluno aluno;

    // Executado automaticamente quando o FXML é carregado
    @FXML
    private void initialize() {

        // Plano de exemplo para testar a tela
        Plano planoExemplo = new Plano(
                "Premium",
                1,
                "Acesso à academia",
                99.90,
                "Mensal"
        );

        // Aluno de exemplo
        aluno = new Aluno(
                1,
                "Aluno Exemplo",
                "123456",
                "12345678900",
                20,
                1.75,
                75.0,
                planoExemplo,
                "Médio"
        );

        atualizarTela();
    }

    // Atualiza as informações visuais
    private void atualizarTela() {

        lblNome.setText(aluno.getNome());

        lblIdade.setText(aluno.getIdade() + " anos");

        lblAltura.setText(
                String.format(Locale.forLanguageTag("pt-BR"),
                        "%.2f m", aluno.getAltura())
        );

        lblPeso.setText(
                String.format(Locale.forLanguageTag("pt-BR"),
                        "%.1f kg", aluno.getPeso())
        );

        lblIntensidade.setText(aluno.getIntensidade());

        // Mostrar os planos do aluno
        ArrayList<Plano> planos = aluno.getPlano();

        if (planos == null || planos.isEmpty()) {
            lblPlano.setText("Nenhum plano");
        } else {
            StringBuilder nomesPlanos = new StringBuilder();

            for (Plano plano : planos) {
                if (plano != null) {
                    if (nomesPlanos.length() > 0) {
                        nomesPlanos.append(", ");
                    }

                    nomesPlanos.append(plano.getNome());
                }
            }

            lblPlano.setText(
                    nomesPlanos.length() > 0
                            ? nomesPlanos.toString()
                            : "Nenhum plano"
            );
        }

        // Mostrar a quantidade de treinos
        ArrayList<Treino> treinos = aluno.getTreinos();

        lblQuantidadeTreinos.setText(
                treinos.size() + (treinos.size() == 1
                        ? " treino"
                        : " treinos")
        );

        // Mostrar os treinos cadastrados
        if (treinos.isEmpty()) {
            lblTreinos.setText(
                    "Você ainda não possui treinos cadastrados."
            );
        } else {
            StringBuilder texto = new StringBuilder();

            DateTimeFormatter formatoData =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Treino treino : treinos) {
                if (treino == null) {
                    continue;
                }

                texto.append("Treino: ")
                        .append(treino.getNomeTreino())
                        .append("\nData: ")
                        .append(treino.getData() != null
                                ? treino.getData().format(formatoData)
                                : "Não definida")
                        .append("\n")
                        .append(treino.getConteudo())
                        .append("\n\n");
            }

            lblTreinos.setText(texto.toString());
        }
    }*/
}
