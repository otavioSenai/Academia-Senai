package com.example.academia.Dono.Controller;

import com.example.academia.ClassesUniversais.*;
import com.example.academia.Main;
import com.example.academia.Dono.Dono;
import com.example.academia.Gerente.Gerente;
import com.example.academia.Treinador.Treinador;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DonoController {
    // Navegação
    @FXML private Button btnNavGeral, btnNavAlunos, btnNavPlanos, btnNavTreinadores, btnNavModalidades, btnNavAdmin, btnNavCustos;
    @FXML private ScrollPane viewDashboard;
    @FXML private VBox viewAlunos, viewPlanos, viewTreinadores, viewModalidades, viewAdmin, viewCustos;
    @FXML private FlowPane containerAlunos, containerPlanos, containerTreinadores, containerModalidades, containerAdmin, containerCustos;
    @FXML private Label lblTotalAlunos, lblTotalFunc, lblRenda, lblCusto, lblLucro, lblNomeUsuarioTop;
    @FXML private FlowPane boxRegistrosRecentes;

    // Componentes de Pesquisa
    @FXML private TextField txtBuscaGeral;
    @FXML private ComboBox<String> cbFiltroBusca;
    @FXML private ScrollPane viewBusca;
    @FXML private FlowPane containerBusca;

    private Dono dono;

    private final String cardStyle = "-fx-background-color: #06090e; -fx-border-color: #141923; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 16; -fx-min-width: 280; -fx-max-width: 320;";
    private final String btnDangerStyle = "-fx-background-color: transparent; -fx-border-color: #ef4444; -fx-text-fill: #ef4444; -fx-border-radius: 6; -fx-cursor: hand; -fx-padding: 8;";
    private final String btnAcessarStyle = "-fx-background-color: transparent; -fx-border-color: #f1ba2a; -fx-text-fill: #f1ba2a; -fx-border-radius: 6; -fx-cursor: hand; -fx-padding: 8;";
    private final String btnEditarStyle = "-fx-background-color: transparent; -fx-border-color: #94a3b8; -fx-text-fill: #94a3b8; -fx-border-radius: 6; -fx-cursor: hand; -fx-padding: 8;";
    private final String btnSalvarStyle = "-fx-background-color: #f1ba2a; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand; -fx-background-radius: 6;";

    @FXML
    private void initialize() {
        dono = LoginController.obterDono();
        if (dono != null && lblNomeUsuarioTop != null) lblNomeUsuarioTop.setText(dono.getNome());
        mostrarVisaoGeral();
        configurarPesquisa();

        Platform.runLater(() -> {
            if (viewDashboard != null) viewDashboard.requestFocus();
        });
    }

    // ==========================================
    // SISTEMA DE PESQUISA GLOBAL INTELIGENTE
    // ==========================================
    private void configurarPesquisa() {
        if (txtBuscaGeral != null && containerBusca != null) {
            if (cbFiltroBusca != null) {
                cbFiltroBusca.getItems().addAll("Todos", "Dono", "Modalidade", "Treinador", "Aluno", "Plano", "Custo", "Administrativo");
                cbFiltroBusca.setValue("Todos");
                estilizarComboBox(cbFiltroBusca);
                cbFiltroBusca.valueProperty().addListener((obs, oldV, newV) -> executarBusca());
            }
            txtBuscaGeral.textProperty().addListener((obs, oldV, newV) -> executarBusca());
        }
    }

    private void executarBusca() {
        if (txtBuscaGeral == null || containerBusca == null) return;

        String termo = txtBuscaGeral.getText().toLowerCase().trim();
        if (termo.isEmpty()) {
            if (viewBusca != null) viewBusca.setVisible(false);
            mostrarVisaoGeral();
            return;
        }

        alternarTela(viewBusca, btnNavGeral);
        btnNavGeral.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");

        containerBusca.getChildren().clear();
        String filtro = (cbFiltroBusca != null) ? cbFiltroBusca.getValue() : "Todos";
        BancoDeDados bd = BancoDeDados.getInstance();

        class Resultado {
            int id; String nome; String tipo; Object ref;
            Resultado(int i, String n, String t, Object r) { id=i; nome=n; tipo=t; ref=r; }
        }
        List<Resultado> lista = new ArrayList<>();

        if (filtro.equals("Todos") || filtro.equals("Dono")) {
            if (dono != null && (String.valueOf(dono.getId()).equals(termo) || dono.getNome().toLowerCase().contains(termo))) {
                lista.add(new Resultado(dono.getId(), dono.getNome(), "Dono", dono));
            }
        }
        if (filtro.equals("Todos") || filtro.equals("Aluno")) {
            for (Aluno a : bd.getAlunos().values()) {
                if (String.valueOf(a.getId()).equals(termo) || a.getNome().toLowerCase().contains(termo))
                    lista.add(new Resultado(a.getId(), a.getNome(), "Aluno", a));
            }
        }
        if (filtro.equals("Todos") || filtro.equals("Treinador")) {
            for (Treinador t : bd.getTreinadores().values()) {
                if (String.valueOf(t.getId()).equals(termo) || t.getNome().toLowerCase().contains(termo))
                    lista.add(new Resultado(t.getId(), t.getNome(), "Treinador", t));
            }
        }
        if (filtro.equals("Todos") || filtro.equals("Modalidade")) {
            for (Modalidade m : bd.getModalidade().values()) {
                if (String.valueOf(m.getId()).equals(termo) || m.getNome().toLowerCase().contains(termo))
                    lista.add(new Resultado(m.getId(), m.getNome(), "Modalidade", m));
            }
        }
        if (filtro.equals("Todos") || filtro.equals("Plano")) {
            for (Plano p : bd.getPlano().values()) {
                if (String.valueOf(p.getId()).equals(termo) || p.getNome().toLowerCase().contains(termo))
                    lista.add(new Resultado(p.getId(), p.getNome(), "Plano", p));
            }
        }
        if (filtro.equals("Todos") || filtro.equals("Administrativo")) {
            for (Administrativo ad : bd.getAdministrativos().values()) {
                if (String.valueOf(ad.getId()).equals(termo) || ad.getNome().toLowerCase().contains(termo))
                    lista.add(new Resultado(ad.getId(), ad.getNome(), "Administrativo", ad));
            }
        }

        lista.sort(Comparator.comparingInt(r -> r.id));

        for (Resultado r : lista) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(
                    criarTextoDestaque("[" + r.tipo + "]", "#f1ba2a", 12),
                    criarTextoDestaque("ID: " + r.id + " — " + r.nome, "#f8fafc", 16)
            );

            Button btnAcessar = null; Button btnRemover = null;

            if (r.tipo.equals("Dono")) {
                btnAcessar = criarBotao("Acessar Informações", btnAcessarStyle, () -> {
                    VBox c = novoConteudoDetalhe();
                    c.getChildren().addAll(criarSecao("Dados do Dono"), criarItem("ID: " + dono.getId()), criarItem("Nome: " + dono.getNome()), criarItem("CPF: " + dono.getCpf()));
                    mostrarJanelaDetalhe("Perfil do Dono", c);
                });
            } else if (r.tipo.equals("Aluno")) {
                btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheAluno((Aluno) r.ref));
                btnRemover = criarBotao("Expulsar", btnDangerStyle, () -> { if(confirmarExclusao()){ dono.ExpulsarAluno(r.id); executarBusca();} });
            } else if (r.tipo.equals("Treinador")) {
                btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheTreinador((Treinador) r.ref));
                btnRemover = criarBotao("Demitir", btnDangerStyle, () -> { if(confirmarExclusao()){ dono.demitirTreinadorCompleto(r.id); executarBusca();} });
            } else if (r.tipo.equals("Modalidade")) {
                btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheModalidade((Modalidade) r.ref));
                btnRemover = criarBotao("Apagar", btnDangerStyle, () -> { if(confirmarExclusao()){ bd.modalidades.remove(r.id); executarBusca();} });
            } else if (r.tipo.equals("Plano")) {
                btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalhePlano((Plano) r.ref));
                btnRemover = criarBotao("Apagar", btnDangerStyle, () -> { if(confirmarExclusao()){ dono.removerPlano(r.id); executarBusca();} });
            } else if (r.tipo.equals("Administrativo")) {
                btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheAdministrativo((Administrativo) r.ref));
                btnRemover = criarBotao("Demitir", btnDangerStyle, () -> { if(confirmarExclusao()){ dono.demitirAdministrativo(r.id); executarBusca();} });
            }

            HBox acoes = new HBox(8); acoes.setAlignment(Pos.CENTER_LEFT);
            if (btnRemover != null) acoes.getChildren().add(btnRemover);
            Region esp = new Region(); HBox.setHgrow(esp, Priority.ALWAYS); acoes.getChildren().add(esp);
            if (btnAcessar != null) acoes.getChildren().add(btnAcessar);

            card.getChildren().add(acoes);
            containerBusca.getChildren().add(card);
        }
    }

    // ==========================================
    // NAVEGAÇÃO E ATUALIZAÇÃO
    // ==========================================
    private void alternarTela(javafx.scene.Node telaAtiva, Button btnAtivo) {
        if (viewDashboard != null) viewDashboard.setVisible(false);
        if (viewAlunos != null) viewAlunos.setVisible(false);
        if (viewPlanos != null) viewPlanos.setVisible(false);
        if (viewTreinadores != null) viewTreinadores.setVisible(false);
        if (viewModalidades != null) viewModalidades.setVisible(false);
        if (viewAdmin != null) viewAdmin.setVisible(false);
        if (viewCustos != null) viewCustos.setVisible(false);
        if (viewBusca != null) viewBusca.setVisible(false);

        btnNavGeral.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");
        btnNavAlunos.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");
        btnNavPlanos.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");
        btnNavTreinadores.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");
        btnNavModalidades.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");
        btnNavAdmin.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");
        btnNavCustos.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");

        if (telaAtiva != null) telaAtiva.setVisible(true);
        if (btnAtivo != null) btnAtivo.setStyle("-fx-background-color: #f1ba2a; -fx-text-fill: #04070d; -fx-font-weight: bold;");
    }

    @FXML public void mostrarVisaoGeral() { alternarTela(viewDashboard, btnNavGeral); atualizarDashboard(); }
    @FXML public void mostrarTelaAlunos() { alternarTela(viewAlunos, btnNavAlunos); renderizarAlunos(); }
    @FXML public void mostrarTelaPlanos() { alternarTela(viewPlanos, btnNavPlanos); renderizarPlanos(); }
    @FXML public void mostrarTelaTreinadores() { alternarTela(viewTreinadores, btnNavTreinadores); renderizarTreinadores(); }
    @FXML public void mostrarTelaModalidades() { alternarTela(viewModalidades, btnNavModalidades); renderizarModalidades(); }
    @FXML public void mostrarTelaAdmin() { alternarTela(viewAdmin, btnNavAdmin); renderizarAdmin(); }
    @FXML public void sair() { Main.mostrarLogin(); }
    @FXML public void mostrarTelaCustos() { alternarTela(viewCustos, btnNavCustos); renderizarCustos(); }

    // ==========================================
    // RENDERIZAÇÃO DE CARTÕES
    // ==========================================
    private void renderizarAlunos() {
        containerAlunos.getChildren().clear();
        for (Aluno a : BancoDeDados.getInstance().getAlunos().values()) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(criarTextoDestaque(a.getNome(), "#f8fafc", 18), criarTextoNormal("CPF: " + a.getCpf()), criarTextoNormal("Intensidade: " + a.getIntensidade()), criarTextoNormal("Idade: " + a.getIdade() + " anos"));
            alinharCardEsquerda(card);
            Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheAluno(a));
            Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoAluno(a));
            Button btnRemover = criarBotao("Expulsar", btnDangerStyle, () -> { if(confirmarExclusao()) { dono.ExpulsarAluno(a.getId()); renderizarAlunos(); atualizarDashboard(); } });
            card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar)); containerAlunos.getChildren().add(card);
        }
    }

    private void renderizarPlanos() {
        containerPlanos.getChildren().clear();
        for (Plano p : BancoDeDados.getInstance().getPlano().values()) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(criarTextoDestaque(p.getNome(), "#f1ba2a", 18), criarTextoNormal(String.format("Valor: R$ %.2f", p.getValor())), criarTextoNormal("Validade: " + p.getMesesValidade() + " meses"), criarTextoNormal("Modalidades: " + p.getModalidade().size()), criarTextoNormal(p.getDescricao()));
            alinharCardEsquerda(card);
            Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalhePlano(p));
            Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoPlano(p));
            Button btnRemover = criarBotao("Apagar", btnDangerStyle, () -> { if(confirmarExclusao()) { dono.removerPlano(p.getId()); renderizarPlanos(); } });
            card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar)); containerPlanos.getChildren().add(card);
        }
    }

    private void renderizarTreinadores() {
        containerTreinadores.getChildren().clear();
        for (Treinador t : BancoDeDados.getInstance().getTreinadores().values()) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(criarTextoDestaque(t.getNome(), "#f8fafc", 18), criarTextoNormal("Especialidade: " + t.getEspecialidade()), criarTextoNormal("Modalidades: " + dono.modalidadesDoTreinador(t.getId()).size()), criarTextoNormal(String.format("Salário: R$ %.2f", t.getSalario())));
            alinharCardEsquerda(card);
            Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheTreinador(t));
            Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoTreinador(t));
            Button btnRemover = criarBotao("Demitir", btnDangerStyle, () -> { if(confirmarExclusao()) { dono.demitirTreinadorCompleto(t.getId()); renderizarTreinadores(); atualizarDashboard(); } });
            card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar)); containerTreinadores.getChildren().add(card);
        }
    }

    private void renderizarModalidades() {
        containerModalidades.getChildren().clear();
        for (Modalidade m : BancoDeDados.getInstance().getModalidade().values()) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(criarTextoDestaque(m.getNome(), "#f8fafc", 18), criarTextoNormal(m.getDescricao()), criarTextoNormal("Treinadores Locados: " + m.getTreinadores().size()));
            alinharCardEsquerda(card);
            Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheModalidade(m));
            Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoModalidade(m));
            Button btnRemover = criarBotao("Apagar", btnDangerStyle, () -> { if(confirmarExclusao()) { BancoDeDados.getInstance().modalidades.remove(m.getId()); renderizarModalidades(); } });
            card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar)); containerModalidades.getChildren().add(card);
        }
    }

    private void renderizarAdmin() {
        containerAdmin.getChildren().clear();
        for (Administrativo a : BancoDeDados.getInstance().getAdministrativos().values()) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(criarTextoDestaque(a.getNome(), "#f8fafc", 18), criarTextoNormal("CPF: " + a.getCpf()), criarTextoNormal(String.format("Salário: R$ %.2f", a.getSalario())));
            alinharCardEsquerda(card);
            Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheAdministrativo(a));
            Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoAdministrativo(a));
            Button btnRemover = criarBotao("Demitir", btnDangerStyle, () -> { if(confirmarExclusao()) { dono.demitirAdministrativo(a.getId()); renderizarAdmin(); atualizarDashboard(); } });
            card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar)); containerAdmin.getChildren().add(card);
        }
    }

    private void renderizarCustos() {
        containerCustos.getChildren().clear();
        List<Custo> custos = new ArrayList<>(BancoDeDados.getInstance().getCustos().values());
        custos.sort((c1, c2) -> c2.getDataInstant().compareTo(c1.getDataInstant()));
        for (Custo c : custos) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(criarTextoDestaque(c.getNome(), "#f8fafc", 18), criarTextoDestaque(String.format("R$ %.2f", c.getValor()), "#ef4444", 16), criarTextoNormal("Data: " + c.getData()));
            alinharCardEsquerda(card);
            Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoCusto(c));
            Button btnRemover = criarBotao("Apagar", btnDangerStyle, () -> { if(confirmarExclusao()) { dono.removerCusto(c.getId()); renderizarCustos(); atualizarDashboard(); } });
            card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar)); containerCustos.getChildren().add(card);
        }
    }

    @FXML
    private void atualizarDashboard() {
        int qtdAlunos = BancoDeDados.getInstance().getAlunos().size();
        int qtdFunc = BancoDeDados.getInstance().getTreinadores().size() + BancoDeDados.getInstance().getAdministrativos().size();
        if(lblTotalAlunos != null) lblTotalAlunos.setText(String.valueOf(qtdAlunos));
        if(lblTotalFunc != null) lblTotalFunc.setText(String.valueOf(qtdFunc));

        BancoDeDados.getInstance().grafico.somarDespesas();
        double r = BancoDeDados.getInstance().grafico.getRenda(); double c = BancoDeDados.getInstance().grafico.getCusto(); double t = BancoDeDados.getInstance().grafico.getTotal();
        if(lblRenda != null) lblRenda.setText(String.format("R$ %.2f", r));
        if(lblCusto != null) lblCusto.setText(String.format("R$ %.2f", c));
        if(lblLucro != null) {
            lblLucro.setText(String.format("R$ %.2f", t));
            lblLucro.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + (t >= 0 ? "#22c55e" : "#ef4444") + ";");
        }
        if(boxRegistrosRecentes != null) {
            boxRegistrosRecentes.getChildren().clear();
            Optional<Gerente> ger = BancoDeDados.getInstance().gerente;
            if(ger.isPresent()) {
                VBox cGerente = new VBox(5); cGerente.setStyle(cardStyle);
                cGerente.getChildren().addAll(criarTextoDestaque(ger.get().getNome(), "#f1ba2a", 16), criarTextoNormal("Gerente Geral"), criarTextoNormal(ger.get().getCpf()), criarTextoNormal(String.format("Salário: R$ %.2f", ger.get().getSalario())));
                alinharCardEsquerda(cGerente); boxRegistrosRecentes.getChildren().add(cGerente);
            }
            BancoDeDados.getInstance().getAlunos().values().stream().limit(3).forEach(a -> {
                VBox card = new VBox(5); card.setStyle(cardStyle); card.getChildren().addAll(criarTextoDestaque(a.getNome(), "#3b82f6", 16), criarTextoNormal("Aluno - " + a.getIntensidade()));
                alinharCardEsquerda(card); boxRegistrosRecentes.getChildren().add(card);
            });
            BancoDeDados.getInstance().getTreinadores().values().stream().limit(3).forEach(tr -> {
                VBox card = new VBox(5); card.setStyle(cardStyle); card.getChildren().addAll(criarTextoDestaque(tr.getNome(), "#10b981", 16), criarTextoNormal("Treinador - " + tr.getEspecialidade()), criarTextoNormal(String.format("Salário: R$ %.2f", tr.getSalario())));
                alinharCardEsquerda(card); boxRegistrosRecentes.getChildren().add(card);
            });
            BancoDeDados.getInstance().getAdministrativos().values().stream().limit(3).forEach(adm -> {
                VBox card = new VBox(5); card.setStyle(cardStyle); card.getChildren().addAll(criarTextoDestaque(adm.getNome(), "#8b5cf6", 16), criarTextoNormal("Administrativo"), criarTextoNormal(String.format("Salário: R$ %.2f", adm.getSalario())));
                alinharCardEsquerda(card); boxRegistrosRecentes.getChildren().add(card);
            });
        }
    }

    private Label criarTextoDestaque(String texto, String cor, int tamanho) {
        Label l = new Label(texto); l.setStyle("-fx-text-fill: " + cor + "; -fx-font-weight: bold; -fx-font-size: " + tamanho + "px;"); return l;
    }

    private Label criarTextoNormal(String texto) {
        Label l = new Label(texto); l.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 13px;"); return l;
    }

    // ==========================================
    // OPERAÇÕES DE CADASTRO
    // ==========================================
    private Button criarBotaoSalvar(String texto, Runnable acao) {
        Button btnSalvar = new Button(texto); btnSalvar.setStyle(btnSalvarStyle); btnSalvar.setOnAction(e -> acao.run()); return btnSalvar;
    }

    private Label criarLabelErro() {
        Label lbl = new Label(); lbl.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold; -fx-font-size: 14px;");
        lbl.setVisible(true); lbl.setText(""); lbl.setWrapText(true); lbl.setMinHeight(25); lbl.setAlignment(Pos.CENTER); return lbl;
    }

    private void forcarFocoEEnter(Stage stage, Button btnSalvar) {
        stage.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), btnSalvar::fire);
        Platform.runLater(() -> {
            stage.requestFocus();
            btnSalvar.requestFocus();
        });
    }

    private void estilizarComboBox(ComboBox<?> cb) {
        cb.setStyle("-fx-background-color: #04070d; -fx-text-fill: white; -fx-border-color: #141923; -fx-padding: 4px;");
        cb.setButtonCell(new ListCell() {
            @Override protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(""); }
                else { setText(item.toString()); setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-background-color: transparent;"); }
            }
        });
    }

    @FXML
    private void cadastrarAluno() {
        if (BancoDeDados.getInstance().getPlano().isEmpty() || BancoDeDados.getInstance().getTreinadores().isEmpty()) { mostrarAlertaErro("Cadastre pelo menos 1 Plano e 1 Treinador antes."); return; }
        Stage stage = criarJanela("Matricular Novo Aluno"); GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Ex: Kauan Silva"); TextField txtSenha = criarInput("Mín. 8 letras, 1 Maiúscula, 1 Número, 1 Especial (@)");
        TextField txtCpf = criarInput("Formato exato: 000.000.000-00");
        TextField txtIdade = criarInput("Apenas números (Ex: 24)"); aplicarMascaraNumerica(txtIdade, false);
        TextField txtAltura = criarInput("Ex: 1.75"); aplicarMascaraNumerica(txtAltura, true);
        TextField txtPeso = criarInput("Ex: 80.5"); aplicarMascaraNumerica(txtPeso, true);

        ComboBox<Plano> cbPlano = new ComboBox<>(); cbPlano.getItems().addAll(BancoDeDados.getInstance().getPlano().values());
        cbPlano.setConverter(new StringConverter<>() { @Override public String toString(Plano p) { return p == null ? "" : p.getNome(); } @Override public Plano fromString(String s) { return null; }});
        estilizarComboBox(cbPlano);

        ComboBox<Treinador> cbTreinador = new ComboBox<>(); cbTreinador.getItems().addAll(BancoDeDados.getInstance().getTreinadores().values());
        cbTreinador.setConverter(new StringConverter<>() { @Override public String toString(Treinador t) { return t == null ? "" : t.getNome(); } @Override public Treinador fromString(String s) { return null; }});
        estilizarComboBox(cbTreinador);

        ComboBox<String> cbIntensidade = new ComboBox<>(); cbIntensidade.getItems().addAll("Leve", "Médio", "Intenso");
        estilizarComboBox(cbIntensidade);

        adicionarLinhaForm(grid, 0, "Nome Completo:", txtNome); adicionarLinhaForm(grid, 1, "Senha (Obrigatória):", txtSenha);
        adicionarLinhaForm(grid, 2, "CPF (Com Pontos e Traço):", txtCpf); adicionarLinhaForm(grid, 3, "Idade:", txtIdade);
        adicionarLinhaForm(grid, 4, "Altura (m):", txtAltura); adicionarLinhaForm(grid, 5, "Peso (kg):", txtPeso);
        adicionarLinhaForm(grid, 6, "Plano:", cbPlano); adicionarLinhaForm(grid, 7, "Treinador:", cbTreinador); adicionarLinhaForm(grid, 8, "Intensidade:", cbIntensidade);

        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 9, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Concluir Matrícula", () -> {
            try {
                if (cbPlano.getValue() == null || cbTreinador.getValue() == null || cbIntensidade.getValue() == null) { lblErro.setText("Selecione os campos nas caixas de opção."); return; }
                double altura = Double.parseDouble(txtAltura.getText().replace(",", ".")); double peso = Double.parseDouble(txtPeso.getText().replace(",", ".")); int idade = Integer.parseInt(txtIdade.getText());
                boolean sucesso = dono.cadastrarAluno(txtNome.getText(), txtSenha.getText(), txtCpf.getText(), idade, altura, peso, cbPlano.getValue(), cbIntensidade.getValue(), cbTreinador.getValue());
                if (sucesso) { stage.close(); renderizarAlunos(); atualizarDashboard(); } else { lblErro.setText("Regras Violadas! CPF incorreto ou Senha fraca."); }
            } catch (Exception ex) { lblErro.setText("Preencha todos os números corretamente."); }
        });

        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 10, 2, 1);
        forcarFocoEEnter(stage, btnSalvar);
        stage.showAndWait();
    }

    @FXML
    private void cadastrarPlano() {
        if (BancoDeDados.getInstance().getModalidade().isEmpty()) { mostrarAlertaErro("Crie uma Modalidade antes."); return; }
        Stage stage = criarJanela("Novo Plano de Acesso"); GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Ex: VIP Anual"); TextField txtDesc = criarInput("Ex: Acesso Total");
        TextField txtValor = criarInput("Ex: 150.00"); aplicarMascaraNumerica(txtValor, true);
        TextField txtValidade = criarInput("Meses. Ex: 12"); aplicarMascaraNumerica(txtValidade, false);
        TextField txtJuros = criarInput("Ex: 5.0"); aplicarMascaraNumerica(txtJuros, true);

        List<CheckBox> checkboxes = new ArrayList<>(); ScrollPane listaModalidades = criarListaModalidades(checkboxes);

        adicionarLinhaForm(grid, 0, "Nome do Plano:", txtNome); adicionarLinhaForm(grid, 1, "Descrição:", txtDesc);
        adicionarLinhaForm(grid, 2, "Valor Mensal (R$):", txtValor); adicionarLinhaForm(grid, 3, "Validade (Meses):", txtValidade);
        adicionarLinhaForm(grid, 4, "Juros Atraso (%):", txtJuros); adicionarLinhaForm(grid, 5, "Modalidades (1 ou mais):", listaModalidades);

        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 6, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Plano", () -> {
            try {
                List<Integer> idsModalidades = idsSelecionados(checkboxes);
                if (idsModalidades.isEmpty()) { lblErro.setText("Selecione ao menos uma modalidade."); return; }
                double valor = Double.parseDouble(txtValor.getText().replace(",", ".")); double juros = Double.parseDouble(txtJuros.getText().replace(",", "."));
                boolean sucesso = dono.cadastrarPlano(txtNome.getText(), txtDesc.getText(), valor, idsModalidades, Integer.parseInt(txtValidade.getText()), juros);
                if(sucesso) { stage.close(); renderizarPlanos(); } else { lblErro.setText("Erro ao cadastrar plano. Verifique os dados."); }
            } catch (Exception ex) { lblErro.setText("Preencha Valor, Validade e Juros apenas com números."); }
        });

        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 7, 2, 1);
        forcarFocoEEnter(stage, btnSalvar);
        stage.showAndWait();
    }

    @FXML
    private void cadastrarModalidade() {
        Stage stage = criarJanela("Nova Modalidade"); GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Ex: Musculação"); TextField txtDesc = criarInput("Ex: Área de peso livre");
        adicionarLinhaForm(grid, 0, "Nome:", txtNome); adicionarLinhaForm(grid, 1, "Descrição:", txtDesc);

        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 2, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Modalidade", () -> {
            if (txtNome.getText().isBlank() || txtDesc.getText().isBlank()) { lblErro.setText("Preencha todos os campos."); return; }
            dono.escolherModalidade(txtNome.getText(), txtDesc.getText()); stage.close(); renderizarModalidades();
        });

        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 3, 2, 1);
        forcarFocoEEnter(stage, btnSalvar);
        stage.showAndWait();
    }

    @FXML
    private void cadastrarTreinador() {
        if (BancoDeDados.getInstance().getModalidade().isEmpty()) { mostrarAlertaErro("Crie uma Modalidade antes."); return; }
        Stage stage = criarJanela("Novo Treinador"); GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Nome Completo"); TextField txtSenha = criarInput("Mín. 8, 1 Maiúscula, 1 Número, 1 Especial (@)");
        TextField txtCpf = criarInput("000.000.000-00"); TextField txtSalario = criarInput("Ex: 3500.00"); aplicarMascaraNumerica(txtSalario, true);
        TextField txtEsp = criarInput("Ex: Hipertrofia");

        List<CheckBox> checkboxes = new ArrayList<>(); ScrollPane listaModalidades = criarListaModalidades(checkboxes);

        adicionarLinhaForm(grid, 0, "Nome:", txtNome); adicionarLinhaForm(grid, 1, "Senha (Estrita):", txtSenha);
        adicionarLinhaForm(grid, 2, "CPF (Com Pontos):", txtCpf); adicionarLinhaForm(grid, 3, "Salário (R$):", txtSalario);
        adicionarLinhaForm(grid, 4, "Especialidade:", txtEsp); adicionarLinhaForm(grid, 5, "Modalidades (1 ou mais):", listaModalidades);

        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 6, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Contratar", () -> {
            try {
                List<Integer> idsModalidades = idsSelecionados(checkboxes);
                if (idsModalidades.isEmpty()) { lblErro.setText("Selecione ao menos uma modalidade."); return; }
                double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                boolean sucesso = dono.CadastrarTreinador(idsModalidades, txtNome.getText(), txtSenha.getText(), txtCpf.getText(), salario, txtEsp.getText());
                if(sucesso) { stage.close(); renderizarTreinadores(); atualizarDashboard(); } else { lblErro.setText("Erro! Verifique as regras exatas de CPF e Senha."); }
            } catch (Exception ex) { lblErro.setText("Verifique os números do salário."); }
        });

        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 7, 2, 1);
        forcarFocoEEnter(stage, btnSalvar);
        stage.showAndWait();
    }

    @FXML
    private void cadastrarAdmin() {
        Stage stage = criarJanela("Novo Administrativo"); GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Nome Completo"); TextField txtSenha = criarInput("Mín. 8, 1 Maiúscula, 1 Número, 1 Especial (@)");
        TextField txtCpf = criarInput("000.000.000-00"); TextField txtSalario = criarInput("Ex: 2500.00"); aplicarMascaraNumerica(txtSalario, true);

        adicionarLinhaForm(grid, 0, "Nome:", txtNome); adicionarLinhaForm(grid, 1, "Senha:", txtSenha);
        adicionarLinhaForm(grid, 2, "CPF (Com Pontos):", txtCpf); adicionarLinhaForm(grid, 3, "Salário (R$):", txtSalario);

        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 4, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Contratar", () -> {
            try {
                double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                boolean sucesso = dono.cadastrarAdministrativo(txtNome.getText(), txtSenha.getText(), txtCpf.getText(), salario);
                if(sucesso) { stage.close(); renderizarAdmin(); atualizarDashboard(); } else { lblErro.setText("Erro! O CPF ou Senha informados não cumprem as regras."); }
            } catch (Exception ex) { lblErro.setText("Salário inválido."); }
        });

        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 5, 2, 1);
        forcarFocoEEnter(stage, btnSalvar);
        stage.showAndWait();
    }

    @FXML
    private void cadastrarCusto() {
        Stage stage = criarJanela("Novo Custo"); GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Ex: Conta de Luz"); TextField txtValor = criarInput("Ex: 1200.00"); aplicarMascaraNumerica(txtValor, true);
        adicionarLinhaForm(grid, 0, "Nome do Custo:", txtNome); adicionarLinhaForm(grid, 1, "Valor (R$):", txtValor);

        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 2, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Custo", () -> {
            try {
                double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
                boolean sucesso = dono.cadastrarCusto(txtNome.getText(), valor);
                if(sucesso) { stage.close(); renderizarCustos(); atualizarDashboard(); } else { lblErro.setText("Informe um nome e um valor maior que zero."); }
            } catch (Exception ex) { lblErro.setText("Valor inválido."); }
        });

        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 3, 2, 1);
        forcarFocoEEnter(stage, btnSalvar);
        stage.showAndWait();
    }

    // ==========================================
    // ACESSAR (JANELAS DE DETALHE) E EDITAR
    // ==========================================
    private void abrirDetalheAluno(Aluno a) {
        VBox conteudo = novoConteudoDetalhe();
        conteudo.getChildren().addAll(criarSecao("Informações"), criarItem("ID: " + a.getId()), criarItem("CPF: " + a.getCpf()), criarItem("Idade: " + a.getIdade() + " anos"), criarItem("Altura: " + a.getAltura() + " m"), criarItem("Peso: " + a.getPeso() + " kg"), criarItem("Intensidade: " + a.getIntensidade()), criarSecao("Planos Assinados"));
        if (a.getListaAssinaturaPlano().isEmpty()) conteudo.getChildren().add(criarTextoNormal("Nenhum plano assinado."));
        else for (AssinaturaPlano ap : a.getListaAssinaturaPlano().values()) { conteudo.getChildren().add(criarItem("• " + ap.getPlano().getNome() + " — " + String.format("R$ %.2f", ap.getPlano().getValor()))); }
        conteudo.getChildren().add(criarSecao("Treinadores"));
        if (a.getTreinadores().isEmpty()) conteudo.getChildren().add(criarTextoNormal("Nenhum treinador vinculado."));
        else for (Treinador t : a.getTreinadores().values()) conteudo.getChildren().add(criarItem("• " + t.getNome() + " — " + t.getEspecialidade()));
        mostrarJanelaDetalhe("Aluno: " + a.getNome(), conteudo);
    }

    private void abrirDetalhePlano(Plano p) {
        VBox conteudo = novoConteudoDetalhe();
        conteudo.getChildren().addAll(criarSecao("Informações"), criarItem("ID: " + p.getId()), criarItem(String.format("Valor: R$ %.2f", p.getValor())), criarItem("Validade: " + p.getMesesValidade() + " meses"), criarItem(String.format("Juros por atraso: %.2f%%", p.getJuros())), criarItem("Descrição: " + p.getDescricao()), criarSecao("Modalidades do Plano"));
        for (Modalidade m : p.getModalidade().values()) conteudo.getChildren().add(criarItem("• " + m.getNome()));
        conteudo.getChildren().add(criarSecao("Alunos que Assinaram"));
        for (Aluno a : dono.alunosDoPlano(p.getId())) conteudo.getChildren().add(criarItem("• " + a.getNome() + " (ID " + a.getId() + ")"));
        mostrarJanelaDetalhe("Plano: " + p.getNome(), conteudo);
    }

    private void abrirDetalheTreinador(Treinador t) {
        VBox conteudo = novoConteudoDetalhe();
        conteudo.getChildren().addAll(criarSecao("Informações"), criarItem("ID: " + t.getId()), criarItem("CPF: " + t.getCpf()), criarItem("Especialidade: " + t.getEspecialidade()), criarItem(String.format("Salário: R$ %.2f", t.getSalario())), criarSecao("Modalidades que Leciona"));
        for (Modalidade m : dono.modalidadesDoTreinador(t.getId())) conteudo.getChildren().add(criarItem("• " + m.getNome()));
        conteudo.getChildren().add(criarSecao("Alunos Atendidos"));
        for (Aluno a : dono.alunosDoTreinador(t.getId())) conteudo.getChildren().add(criarItem("• " + a.getNome() + " (ID " + a.getId() + ")"));
        mostrarJanelaDetalhe("Treinador: " + t.getNome(), conteudo);
    }

    private void abrirDetalheModalidade(Modalidade m) {
        VBox conteudo = novoConteudoDetalhe();
        conteudo.getChildren().addAll(criarSecao("Informações"), criarItem("ID: " + m.getId()), criarItem("Descrição: " + m.getDescricao()), criarSecao("Treinadores"));
        if (m.getTreinadores().isEmpty()) conteudo.getChildren().add(criarTextoNormal("Nenhum treinador cadastrado."));
        else for (Treinador t : m.getTreinadores().values()) conteudo.getChildren().add(criarItem("• ID: " + t.getId() + " - " + t.getNome()));
        conteudo.getChildren().add(criarSecao("Alunos"));
        for (Aluno a : dono.alunosDaModalidade(m.getId())) conteudo.getChildren().add(criarItem("• " + a.getNome() + " (ID " + a.getId() + ")"));
        mostrarJanelaDetalhe("Modalidade: " + m.getNome(), conteudo);
    }

    private void abrirDetalheAdministrativo(Administrativo a) {
        VBox conteudo = novoConteudoDetalhe();
        conteudo.getChildren().addAll(criarSecao("Informações"), criarItem("ID: " + a.getId()), criarItem("Nome: " + a.getNome()), criarItem("CPF: " + a.getCpf()), criarItem(String.format("Salário: R$ %.2f", a.getSalario())));
        mostrarJanelaDetalhe("Administrativo: " + a.getNome(), conteudo);
    }

    private void abrirEdicaoAluno(Aluno a) {
        Stage stage = criarJanela("Editar Aluno — " + a.getNome()); GridPane grid = (GridPane) stage.getUserData();
        TextField txtAltura = criarInput("Ex: 1.75"); txtAltura.setText(String.valueOf(a.getAltura())); aplicarMascaraNumerica(txtAltura, true);
        TextField txtPeso = criarInput("Ex: 80.5"); txtPeso.setText(String.valueOf(a.getPeso())); aplicarMascaraNumerica(txtPeso, true);
        ComboBox<String> cbIntensidade = new ComboBox<>(); cbIntensidade.getItems().addAll("Leve", "Médio", "Intenso"); cbIntensidade.setValue(a.getIntensidade());
        estilizarComboBox(cbIntensidade);

        List<CheckBox> checkboxesPlanos = new ArrayList<>(); VBox caixaPlanos = new VBox(8); caixaPlanos.setStyle("-fx-padding: 8;");
        for (Plano p : BancoDeDados.getInstance().getPlano().values()) {
            CheckBox cb = new CheckBox(p.getNome()); cb.setUserData(p.getId()); cb.setStyle("-fx-text-fill: #f8fafc; -fx-cursor: hand;");
            if(a.getListaAssinaturaPlano().values().stream().anyMatch(ap -> ap.getPlano().getId() == p.getId())) cb.setSelected(true);
            checkboxesPlanos.add(cb); caixaPlanos.getChildren().add(cb);
        }
        ScrollPane scrollPlanos = new ScrollPane(caixaPlanos); scrollPlanos.setPrefHeight(100); scrollPlanos.setStyle("-fx-background-color: #04070d; -fx-background: #04070d; -fx-border-color: #141923;");

        Label lblErro = criarLabelErro();
        adicionarLinhaForm(grid, 0, "Nome:", criarTextoDestaque(a.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Altura (m):", txtAltura);
        adicionarLinhaForm(grid, 2, "Peso (kg):", txtPeso); adicionarLinhaForm(grid, 3, "Intensidade:", cbIntensidade); adicionarLinhaForm(grid, 4, "Planos:", scrollPlanos);
        grid.add(lblErro, 0, 5, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Alterações", () -> {
            double alturaAnterior = a.getAltura(); double pesoAnterior = a.getPeso();
            try {
                double altura = Double.parseDouble(txtAltura.getText().replace(",", ".")); double peso = Double.parseDouble(txtPeso.getText().replace(",", "."));
                if (!a.setAltura(altura)) { lblErro.setText("Altura inválida."); return; }
                if (!a.setPeso(peso)) { a.setAltura(alturaAnterior); lblErro.setText("Peso inválido."); return; }
                if (!a.setIntensidade(cbIntensidade.getValue())) { lblErro.setText("Selecione a intensidade."); return; }
                List<Integer> ids = idsSelecionados(checkboxesPlanos);
                if (ids.isEmpty()) { lblErro.setText("Selecione pelo menos um plano."); return; }

                a.getListaAssinaturaPlano().entrySet().removeIf(entry -> !ids.contains(entry.getValue().getPlano().getId()));
                for (Integer idPlano : ids) {
                    if (a.getListaAssinaturaPlano().values().stream().noneMatch(ap -> ap.getPlano().getId() == idPlano)) {
                        Plano p = BancoDeDados.getInstance().getPlano().get(idPlano); AssinaturaPlano novaAp = new AssinaturaPlano(p.getId(), p);
                        BancoDeDados.getInstance().assinaturaPlano.put(novaAp.getId(), novaAp); a.getListaAssinaturaPlano().put(novaAp.getId(), novaAp);
                    }
                }
                stage.close(); renderizarAlunos(); atualizarDashboard();
            } catch (Exception ex) { lblErro.setText("Preencha Altura e Peso corretamente."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 6, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    private void abrirEdicaoTreinador(Treinador t) {
        Stage stage = criarJanela("Editar Treinador — " + t.getNome()); GridPane grid = (GridPane) stage.getUserData();
        TextField txtSalario = criarInput("Ex: 3500.00"); txtSalario.setText(String.valueOf(t.getSalario())); aplicarMascaraNumerica(txtSalario, true);
        TextField txtEsp = criarInput("Ex: Hipertrofia"); txtEsp.setText(t.getEspecialidade());

        List<CheckBox> checkboxesMod = new ArrayList<>(); VBox caixaMod = new VBox(8); caixaMod.setStyle("-fx-padding: 8;");
        for (Modalidade m : BancoDeDados.getInstance().getModalidade().values()) {
            CheckBox cb = new CheckBox(m.getNome()); cb.setUserData(m.getId()); cb.setStyle("-fx-text-fill: #f8fafc; -fx-cursor: hand;");
            if (m.getTreinadores().containsKey(t.getId())) cb.setSelected(true);
            checkboxesMod.add(cb); caixaMod.getChildren().add(cb);
        }
        ScrollPane scrollMod = new ScrollPane(caixaMod); scrollMod.setPrefHeight(100); scrollMod.setStyle("-fx-background-color: #04070d; -fx-background: #04070d; -fx-border-color: #141923;");

        Label lblErro = criarLabelErro();
        adicionarLinhaForm(grid, 0, "Nome:", criarTextoDestaque(t.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Salário (R$):", txtSalario);
        adicionarLinhaForm(grid, 2, "Especialidade:", txtEsp); adicionarLinhaForm(grid, 3, "Modalidades Ativas:", scrollMod);
        grid.add(lblErro, 0, 4, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Alterações", () -> {
            try {
                double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                if (salario <= 0) { lblErro.setText("O salário deve ser maior que zero."); return; }
                if (txtEsp.getText().isBlank()) { lblErro.setText("Informe a especialidade."); return; }
                List<Integer> ids = idsSelecionados(checkboxesMod);
                if (ids.isEmpty()) { lblErro.setText("Selecione pelo menos uma modalidade."); return; }

                t.setSalario(salario); t.setEspecialidade(txtEsp.getText());
                for (Modalidade m : BancoDeDados.getInstance().getModalidade().values()) {
                    if (!ids.contains(m.getId())) m.removeTreinador(m.getId(), t.getId());
                    else if (!m.getTreinadores().containsKey(t.getId())) m.addTreinador(t.getId(), t);
                }
                stage.close(); renderizarTreinadores(); atualizarDashboard();
            } catch (Exception ex) { lblErro.setText("Verifique os números do salário."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 5, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    private void abrirEdicaoPlano(Plano p) {
        Stage stage = criarJanela("Editar Plano — " + p.getNome()); GridPane grid = (GridPane) stage.getUserData();
        TextField txtDescricao = criarInput("Descrição do plano"); txtDescricao.setText(p.getDescricao());
        TextField txtValidade = criarInput("Dias de validade"); txtValidade.setText(String.valueOf(p.getMesesValidade())); aplicarMascaraNumerica(txtValidade, false);
        TextField txtValor = criarInput("Valor (R$)"); txtValor.setText(String.valueOf(p.getValor())); aplicarMascaraNumerica(txtValor, true);

        List<CheckBox> checkboxes = new ArrayList<>(); VBox caixaMod = new VBox(8); caixaMod.setStyle("-fx-padding: 8;");
        for (Modalidade m : BancoDeDados.getInstance().getModalidade().values()) {
            CheckBox cb = new CheckBox(m.getNome()); cb.setUserData(m.getId()); cb.setStyle("-fx-text-fill: #f8fafc; -fx-cursor: hand;");
            if (p.getModalidade().containsKey(m.getId())) cb.setSelected(true);
            checkboxes.add(cb); caixaMod.getChildren().add(cb);
        }
        ScrollPane scrollMod = new ScrollPane(caixaMod); scrollMod.setPrefHeight(100); scrollMod.setStyle("-fx-background-color: #04070d; -fx-background: #04070d; -fx-border-color: #141923;");

        Label lblErro = criarLabelErro();
        adicionarLinhaForm(grid, 0, "Nome:", criarTextoDestaque(p.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Descrição:", txtDescricao);
        adicionarLinhaForm(grid, 2, "Validade (meses):", txtValidade); adicionarLinhaForm(grid, 3, "Valor (R$):", txtValor); adicionarLinhaForm(grid, 4, "Modalidades:", scrollMod);
        grid.add(lblErro, 0, 5, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Plano", () -> {
            try {
                if (txtDescricao.getText().isBlank()) { lblErro.setText("A descrição é obrigatória."); return; }
                double novoValor = Double.parseDouble(txtValor.getText().replace(",", "."));
                p.setDescricao(txtDescricao.getText()); p.setValor(novoValor); p.setMesesValidade(Integer.parseInt(txtValidade.getText()));
                p.getModalidade().clear(); for (Integer id : idsSelecionados(checkboxes)) p.addModalidade(id);
                stage.close(); renderizarPlanos(); atualizarDashboard();
            } catch (Exception e) { lblErro.setText("Verifique os números digitados."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 6, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    private void abrirEdicaoAdministrativo(Administrativo a) {
        Stage stage = criarJanela("Editar Administrativo — " + a.getNome()); GridPane grid = (GridPane) stage.getUserData();
        TextField txtSalario = criarInput("Ex: 2500.00"); txtSalario.setText(String.valueOf(a.getSalario())); aplicarMascaraNumerica(txtSalario, true);
        Label lblErro = criarLabelErro();
        adicionarLinhaForm(grid, 0, "Nome (não editável):", criarTextoDestaque(a.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Salário (R$):", txtSalario);
        grid.add(lblErro, 0, 2, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Alterações", () -> {
            try {
                if (!a.setSalario(Double.parseDouble(txtSalario.getText().replace(",", ".")))) { lblErro.setText("O salário deve ser maior que zero."); return; }
                stage.close(); renderizarAdmin(); atualizarDashboard();
            } catch (Exception ex) { lblErro.setText("Salário inválido."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 3, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    private void abrirEdicaoCusto(Custo c) {
        Stage stage = criarJanela("Editar Custo — " + c.getNome()); GridPane grid = (GridPane) stage.getUserData();
        TextField txtValor = criarInput("Novo valor (R$)"); txtValor.setText(String.valueOf(c.getValor())); aplicarMascaraNumerica(txtValor, true);
        Label lblErro = criarLabelErro();
        adicionarLinhaForm(grid, 0, "Nome:", criarTextoDestaque(c.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Data Original:", criarTextoDestaque(c.getData(), "#94a3b8", 14)); adicionarLinhaForm(grid, 2, "Novo Valor (R$):", txtValor);
        grid.add(lblErro, 0, 3, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Custo", () -> {
            try {
                double novoValor = Double.parseDouble(txtValor.getText().replace(",", "."));
                if (novoValor <= 0) { lblErro.setText("O valor deve ser maior que zero."); return; }
                c.setValor(novoValor); stage.close(); renderizarCustos(); atualizarDashboard();
            } catch (Exception e) { lblErro.setText("Formato de valor inválido."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 4, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    private void abrirEdicaoModalidade(Modalidade m) {
        Stage stage = criarJanela("Editar Modalidade — " + m.getNome()); GridPane grid = (GridPane) stage.getUserData();
        TextField txtDesc = criarInput("Ex: Área de peso livre"); txtDesc.setText(m.getDescricao());
        Label lblErro = criarLabelErro();
        adicionarLinhaForm(grid, 0, "Nome:", criarTextoDestaque(m.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Descrição:", txtDesc);
        grid.add(lblErro, 0, 2, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar", () -> {
            if(txtDesc.getText().isBlank()){ lblErro.setText("Descrição não pode ser vazia"); return; }
            m.setDescricao(txtDesc.getText()); stage.close(); renderizarModalidades();
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 3, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    // ==========================================
    // UTILITÁRIOS
    // ==========================================
    private void aplicarMascaraNumerica(TextField txt, boolean permiteDecimal) {
        txt.textProperty().addListener((obs, velho, novo) -> {
            if (novo == null || novo.isEmpty()) return;
            String regex = permiteDecimal ? "\\d*([.,]\\d*)?" : "\\d*";
            if (!novo.matches(regex)) txt.setText(velho);
        });
    }

    private Stage criarJanela(String titulo) {
        Stage stage = new Stage(); stage.initOwner(Main.getStage()); stage.initModality(Modality.WINDOW_MODAL); stage.initStyle(StageStyle.UNDECORATED);
        VBox root = new VBox(); root.setStyle("-fx-background-color: #06090e; -fx-border-color: #06090e; -fx-border-width: 1px;");
        HBox header = new HBox(); header.setStyle("-fx-background-color: #06090e; -fx-padding: 10px 16px;"); header.setAlignment(Pos.CENTER_LEFT);
        Label lblTitulo = new Label(titulo); lblTitulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");
        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);
        Button btnFechar = new Button("X"); btnFechar.setStyle("-fx-background-color: transparent; -fx-text-fill: #ef4444; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 16px;");
        btnFechar.setOnAction(e -> stage.close());
        header.getChildren().addAll(lblTitulo, spacer, btnFechar);

        final double[] xOffset = new double[1]; final double[] yOffset = new double[1];
        header.setOnMousePressed(event -> { xOffset[0] = event.getSceneX(); yOffset[0] = event.getSceneY(); });
        header.setOnMouseDragged(event -> { stage.setX(event.getScreenX() - xOffset[0]); stage.setY(event.getScreenY() - yOffset[0]); });

        GridPane grid = new GridPane(); grid.setHgap(16); grid.setVgap(16); grid.setStyle("-fx-padding: 32;");
        root.getChildren().addAll(header, grid); Scene scene = new Scene(root); stage.setScene(scene); stage.setUserData(grid);
        return stage;
    }

    private void mostrarJanelaDetalhe(String titulo, VBox conteudo) {
        Stage stage = criarJanela(titulo); GridPane grid = (GridPane) stage.getUserData();
        ScrollPane scroll = new ScrollPane(conteudo); scroll.setFitToWidth(true); scroll.setPrefViewportWidth(420); scroll.setPrefViewportHeight(380); scroll.setStyle("-fx-background-color: transparent; -fx-background: #06090e;");
        Button btnFechar = new Button("Fechar"); btnFechar.setStyle(btnEditarStyle); btnFechar.setOnAction(e -> stage.close());
        HBox boxFechar = new HBox(btnFechar); boxFechar.setAlignment(Pos.CENTER_LEFT);
        grid.add(scroll, 0, 0, 2, 1); grid.add(boxFechar, 0, 1, 2, 1);

        stage.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), btnFechar::fire);
        stage.showAndWait();
    }

    private VBox novoConteudoDetalhe() { VBox c = new VBox(8); c.setStyle("-fx-padding: 0 16 0 0;"); return c; }
    private Label criarSecao(String texto) { Label l = criarTextoDestaque(texto, "#f1ba2a", 15); l.setStyle(l.getStyle() + " -fx-padding: 8 0 0 0;"); return l; }
    private Label criarItem(String texto) { Label l = new Label(texto); l.setStyle("-fx-text-fill: #f8fafc; -fx-font-size: 13px;"); l.setWrapText(true); return l; }

    private void alinharCardEsquerda(VBox card) {
        card.setAlignment(Pos.CENTER_LEFT);
        for (javafx.scene.Node n : card.getChildren()) { if (n instanceof Label l) { l.setMaxWidth(Double.MAX_VALUE); l.setAlignment(Pos.CENTER_LEFT); l.setTextAlignment(TextAlignment.LEFT); l.setWrapText(true); } }
    }

    private HBox criarLinhaAcoes(Button esquerda, Button... direita) {
        HBox linha = new HBox(8); linha.setAlignment(Pos.CENTER_LEFT); Region esp = new Region(); HBox.setHgrow(esp, Priority.ALWAYS);
        linha.getChildren().addAll(esquerda, esp); linha.getChildren().addAll(direita); return linha;
    }

    private Button criarBotao(String texto, String estilo, Runnable acao) { Button b = new Button(texto); b.setStyle(estilo); b.setOnAction(e -> acao.run()); return b; }

    private ScrollPane criarListaModalidades(List<CheckBox> checkboxes) {
        VBox caixa = new VBox(8); caixa.setStyle("-fx-padding: 8;");
        for (Modalidade m : BancoDeDados.getInstance().getModalidade().values()) {
            CheckBox cb = new CheckBox(m.getNome()); cb.setUserData(m.getId()); cb.setStyle("-fx-text-fill: #f8fafc; -fx-cursor: hand;");
            checkboxes.add(cb); caixa.getChildren().add(cb);
        }
        ScrollPane scroll = new ScrollPane(caixa); scroll.setFitToWidth(true); scroll.setPrefWidth(300); scroll.setPrefHeight(120);
        scroll.setStyle("-fx-background-color: #04070d; -fx-background: #04070d; -fx-border-color: #141923;"); return scroll;
    }

    private List<Integer> idsSelecionados(List<CheckBox> checkboxes) {
        List<Integer> ids = new ArrayList<>(); for (CheckBox cb : checkboxes) if (cb.isSelected()) ids.add((Integer) cb.getUserData()); return ids;
    }

    private boolean confirmarExclusao() {
        final boolean[] confirmado = {false}; Stage stage = criarJanela("Atenção: Ação Destrutiva"); GridPane grid = (GridPane) stage.getUserData();
        Label lblMsg = new Label("Você tem certeza que deseja excluir?\nQualquer informação associada a este registro será excluída permanentemente.");
        lblMsg.setStyle("-fx-text-fill: #f8fafc; -fx-font-size: 15px;"); lblMsg.setWrapText(true); lblMsg.setPrefWidth(380);
        Button btnConfirmar = criarBotao("Sim, Excluir", "-fx-background-color: #ef4444; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand; -fx-background-radius: 6;", () -> { confirmado[0] = true; stage.close(); });
        Button btnCancelar = criarBotao("Cancelar", btnEditarStyle, () -> stage.close()); btnCancelar.setCancelButton(true);
        HBox boxBotoes = new HBox(16, btnCancelar, btnConfirmar); boxBotoes.setAlignment(Pos.CENTER);
        grid.add(lblMsg, 0, 0); Region espaco = new Region(); espaco.setMinHeight(10); grid.add(espaco, 0, 1); grid.add(boxBotoes, 0, 2);

        stage.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), btnConfirmar::fire);
        Platform.runLater(btnConfirmar::requestFocus); stage.showAndWait(); return confirmado[0];
    }

    private TextField criarInput(String prompt) {
        TextField t = new TextField(); t.setPromptText(prompt); t.setPrefWidth(300);
        t.setStyle("-fx-background-color: #04070d; -fx-text-fill: white; -fx-border-color: #141923; -fx-padding: 10px; -fx-background-radius: 6;"); return t;
    }

    private void adicionarLinhaForm(GridPane grid, int linha, String label, javafx.scene.Node controle) {
        Label lbl = new Label(label); lbl.setStyle("-fx-text-fill: #94a3b8; -fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(lbl, 0, linha); grid.add(controle, 1, linha);
    }

    private void mostrarAlertaErro(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg); a.setHeaderText("Aviso do Sistema");
        DialogPane dp = a.getDialogPane(); dp.setStyle("-fx-background-color: #06090e; -fx-border-color: #141923;");
        dp.lookupAll(".label").forEach(n -> n.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;"));
        Button okButton = (Button) dp.lookupButton(ButtonType.OK);
        if (okButton != null) { okButton.setStyle("-fx-background-color: #f1ba2a; -fx-text-fill: #04070d; -fx-font-weight: bold; -fx-cursor: hand;"); }
        a.getDialogPane().getScene().setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER && okButton != null) okButton.fire(); }); a.showAndWait();
    }
}