package com.example.academia.aluno;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

    public class AlunoController {

        // 1. VARIABLES VINCULADAS A LA INTERFAZ (fx:id)
        @FXML
        private Label lblDepartamento;

        @FXML
        private Label lblPlano;

        @FXML
        private Label lblIntensidade;

        // 2. MÉTODO DE INICIALIZACIÓN (Se ejecuta automáticamente al abrir la pantalla)
        @FXML
        public void initialize() {
            // Colocamos texto de prueba para verificar que el controlador toma el mando
            lblDepartamento.setText("Musculação");
            lblPlano.setText("Premium");
            lblIntensidade.setText("Médio");
        }

        // 3. MÉTODOS DE ACCIÓN PARA LOS BOTONES DEL MENÚ (On Action)
        @FXML
        private void onInicioClick() {
            System.out.println("Clic en INÍCIO");
        }

        @FXML
        private void onMeusTreinosClick() {
            System.out.println("Clic en MEUS TREINOS");
        }

        @FXML
        private void onDepartamentosClick() {
            System.out.println("Clic en DEPARTAMENTOS");
        }

        @FXML
        private void onPlanoClick() {
            System.out.println("Clic en PLANO");
        }

        @FXML
        private void onCarteirinhaClick() {
            System.out.println("Clic en CARTEIRINHA");
        }

        @FXML
        private void onSairClick() {
            System.out.println("Cerrando sesión...");
        }
    }

