package com.example.academia.Dono.Controller;

import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.GraficoRenda;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DonoVisaoGeral {

    @FXML private Label lblRendaTotal;
    @FXML private Label lblSituacao;

    @FXML private Label lblTotalAlunos;
    @FXML private Label lblTotalTreinadores;
    @FXML private Label lblTotalAdms;

    @FXML
    public void initialize() {
        atualizarDashboard();
    }

    @FXML
    private void atualizarDashboard() {
        BancoDeDados db = BancoDeDados.getInstance();

        // 1. Atualizar Contadores de Volume Operacional
        lblTotalAlunos.setText(String.valueOf(db.alunos.size()));
        lblTotalTreinadores.setText(String.valueOf(db.treinadores.size()));
        lblTotalAdms.setText(String.valueOf(db.administrativos.size()));

        // 2. Calcular Renda Bruta Baseada na Regra de Negócio (GraficoRenda.java)
        GraficoRenda grafico = new GraficoRenda();

        // A regra original exige que iteremos pelos alunos e adicionemos a renda pelo ID
        // Vamos percorrer as chaves (IDs) dos alunos no mapa
        for (Integer idAluno : db.alunos.keySet()) {
            grafico.addRenda(idAluno);
        }

        // Formatação do valor para moeda
        String rendaFormatada = String.format("R$ %,.2f", grafico.getRenda());
        lblRendaTotal.setText(rendaFormatada);

        // Define a situação
        grafico.setSituacao(true); // Assumimos Operação Ativa
        lblSituacao.setText(grafico.getSituacao() ? "Operacional" : "Inativo");
    }
}
