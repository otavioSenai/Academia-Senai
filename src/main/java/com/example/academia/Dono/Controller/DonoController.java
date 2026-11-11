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
import java.util.Map;
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

    // NOVO: Adicionado um min-height robusto (240px) aos cards para garantir alinhamento perfeito dos botões
    private final String cardStyle = "-fx-background-color: #06090e; -fx-border-color: #141923; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 16; -fx-min-width: 300; -fx-max-width: 320; -fx-min-height: 240;";
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
        Platform.runLater(() -> { if (viewDashboard != null) viewDashboard.requestFocus(); });
    }

    // ==========================================
    // SISTEMA DE PESQUISA PADRONIZADO
    // ==========================================
    private void configurarPesquisa() {
        if (txtBuscaGeral != null && containerBusca != null) {
            if (cbFiltroBusca != null) {
                cbFiltroBusca.getItems().addAll("Todos", "Dono", "Modalidade", "Treinador", "Aluno", "Plano", "Custo", "Administrativo", "Gerente");
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
        if (termo.isEmpty()) { viewBusca.setVisible(false); mostrarVisaoGeral(); return; }

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
            if (dono != null && (String.valueOf(dono.getId()).equals(termo) || dono.getNome().toLowerCase().contains(termo) || dono.getCpf().equals(termo))) {
                lista.add(new Resultado(dono.getId(), dono.getNome(), "Dono", dono));
            }
        }
        if (filtro.equals("Todos") || filtro.equals("Aluno")) {
            for (Aluno a : bd.getAlunos().values()) {
                if (String.valueOf(a.getId()).equals(termo) || a.getNome().toLowerCase().contains(termo) || a.getCpf().equals(termo))
                    lista.add(new Resultado(a.getId(), a.getNome(), "Aluno", a));
            }
        }
        if (filtro.equals("Todos") || filtro.equals("Treinador")) {
            for (Treinador t : bd.getTreinadores().values()) {
                if (String.valueOf(t.getId()).equals(termo) || t.getNome().toLowerCase().contains(termo) || t.getCpf().equals(termo))
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
                if (String.valueOf(ad.getId()).equals(termo) || ad.getNome().toLowerCase().contains(termo) || ad.getCpf().equals(termo))
                    lista.add(new Resultado(ad.getId(), ad.getNome(), "Administrativo", ad));
            }
        }
        if (filtro.equals("Todos") || filtro.equals("Gerente")) {
            Optional<Gerente> ger = bd.gerente;
            if (ger.isPresent() && (String.valueOf(ger.get().getId()).equals(termo) || ger.get().getNome().toLowerCase().contains(termo) || ger.get().getCpf().equals(termo))) {
                lista.add(new Resultado(ger.get().getId(), ger.get().getNome(), "Gerente", ger.get()));
            }
        }
        if (filtro.equals("Todos") || filtro.equals("Custo")) {
            for (Custo c : bd.getCustos().values()) {
                if (String.valueOf(c.getId()).equals(termo) || c.getNome().toLowerCase().contains(termo))
                    lista.add(new Resultado(c.getId(), c.getNome(), "Custo", c));
            }
        }

        lista.sort(Comparator.comparingInt(r -> r.id));

        for (Resultado r : lista) {
            if (r.tipo.equals("Dono")) {
                VBox card = new VBox(8); card.setStyle(cardStyle);
                card.getChildren().addAll( criarTextoDestaque("[Dono]", "#f1ba2a", 12), criarTextoDestaque("ID: " + dono.getId() + " — " + dono.getNome(), "#f8fafc", 18), criarTextoNormal("Gestor Geral"));
                Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> {
                    VBox c = novoConteudoDetalhe();
                    c.getChildren().addAll(criarSecao("Dados do Dono"), criarItem("ID: " + dono.getId()), criarItem("Nome: " + dono.getNome()), criarItem("CPF: " + dono.getCpf()), criarCampoSenhaComOlho(dono.getSenhaOriginal()));
                    mostrarJanelaDetalhe("Perfil do Dono", c);
                });

                Region spacerVertical = new Region(); VBox.setVgrow(spacerVertical, Priority.ALWAYS);
                card.getChildren().add(spacerVertical);
                card.getChildren().add(criarLinhaAcoes(null, null, btnAcessar));
                containerBusca.getChildren().add(card);
            } else if (r.tipo.equals("Aluno")) {
                containerBusca.getChildren().add(criarCardAluno((Aluno) r.ref, true));
            } else if (r.tipo.equals("Treinador")) {
                containerBusca.getChildren().add(criarCardTreinador((Treinador) r.ref, true));
            } else if (r.tipo.equals("Modalidade")) {
                containerBusca.getChildren().add(criarCardModalidade((Modalidade) r.ref, true));
            } else if (r.tipo.equals("Plano")) {
                containerBusca.getChildren().add(criarCardPlano((Plano) r.ref, true));
            } else if (r.tipo.equals("Administrativo")) {
                containerBusca.getChildren().add(criarCardAdmin((Administrativo) r.ref, true));
            } else if (r.tipo.equals("Gerente")) {
                containerBusca.getChildren().add(criarCardGerente((Gerente) r.ref, true));
            } else if (r.tipo.equals("Custo")) {
                containerBusca.getChildren().add(criarCardCusto((Custo) r.ref, true));
            }
        }
    }

    // ==========================================
    // CRIAÇÃO UNIFICADA DE CARDS COM BOTÕES FIXOS
    // ==========================================
    private VBox criarCardAluno(Aluno a, boolean isPesquisa) {
        VBox card = new VBox(8); card.setStyle(cardStyle);
        boolean emDia = true;
        for (AssinaturaPlano ap : a.getListaAssinaturaPlano().values()) {
            if (!ap.tempoPagar()) { emDia = false; break; }
        }
        if (isPesquisa) card.getChildren().add(criarTextoDestaque("[Aluno]", "#f1ba2a", 12));
        HBox cabecalho = new HBox(12); cabecalho.setAlignment(Pos.CENTER_LEFT);
        Region espaco = new Region(); HBox.setHgrow(espaco, Priority.ALWAYS);
        cabecalho.getChildren().addAll(criarTextoDestaque(a.getNome(), "#f8fafc", 18), espaco, criarBadgeStatus(emDia));

        card.getChildren().addAll(cabecalho, criarTextoNormal("CPF: " + a.getCpf()), criarTextoNormal("Intensidade: " + a.getIntensidade()), criarTextoNormal("Idade: " + a.getIdade() + " anos"));
        alinharCardEsquerda(card);

        Region spacerVertical = new Region(); VBox.setVgrow(spacerVertical, Priority.ALWAYS);
        card.getChildren().add(spacerVertical);

        Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheAluno(a));
        Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoAluno(a));
        Button btnRemover = criarBotao("Expulsar", btnDangerStyle, () -> { if(expulsarAlunoSeguro(a.getId())) { atualizarListasAtuais(); } });
        card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar)); return card;
    }

    private VBox criarCardPlano(Plano p, boolean isPesquisa) {
        VBox card = new VBox(8); card.setStyle(cardStyle);
        if (isPesquisa) card.getChildren().add(criarTextoDestaque("[Plano]", "#f1ba2a", 12));
        card.getChildren().addAll(criarTextoDestaque(p.getNome(), "#f1ba2a", 18), criarTextoNormal(String.format("Valor: R$ %.2f", p.getValor())), criarTextoNormal("Validade: " + p.getMesesValidade() + " meses"), criarTextoNormal("Modalidades: " + p.getModalidade().size()), criarTextoNormal(p.getDescricao()));
        alinharCardEsquerda(card);

        Region spacerVertical = new Region(); VBox.setVgrow(spacerVertical, Priority.ALWAYS);
        card.getChildren().add(spacerVertical);

        Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalhePlano(p));
        Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoPlano(p));
        Button btnRemover = criarBotao("Apagar", btnDangerStyle, () -> { if(apagarPlanoSeguro(p.getId())) { atualizarListasAtuais(); } });
        card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar)); return card;
    }

    private VBox criarCardTreinador(Treinador t, boolean isPesquisa) {
        VBox card = new VBox(8); card.setStyle(cardStyle);
        if (isPesquisa) card.getChildren().add(criarTextoDestaque("[Treinador]", "#f1ba2a", 12));
        card.getChildren().addAll(criarTextoDestaque(t.getNome(), "#f8fafc", 18), criarTextoNormal("CPF: " + t.getCpf()), criarTextoNormal("Especialidade: " + t.getEspecialidade()), criarTextoNormal("Modalidades: " + dono.modalidadesDoTreinador(t.getId()).size()), criarTextoNormal(String.format("Salário: R$ %.2f", t.getSalario())));
        alinharCardEsquerda(card);

        Region spacerVertical = new Region(); VBox.setVgrow(spacerVertical, Priority.ALWAYS);
        card.getChildren().add(spacerVertical);

        Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheTreinador(t));
        Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoTreinador(t));
        Button btnRemover = criarBotao("Demitir", btnDangerStyle, () -> { if(demitirTreinadorSeguro(t.getId())) { atualizarListasAtuais(); } });
        card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar)); return card;
    }

    private VBox criarCardModalidade(Modalidade m, boolean isPesquisa) {
        VBox card = new VBox(8); card.setStyle(cardStyle);
        if (isPesquisa) card.getChildren().add(criarTextoDestaque("[Modalidade]", "#f1ba2a", 12));
        card.getChildren().addAll(criarTextoDestaque(m.getNome(), "#f8fafc", 18), criarTextoNormal(m.getDescricao()), criarTextoNormal("Treinadores Locados: " + m.getTreinadores().size()));
        alinharCardEsquerda(card);

        Region spacerVertical = new Region(); VBox.setVgrow(spacerVertical, Priority.ALWAYS);
        card.getChildren().add(spacerVertical);

        Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheModalidade(m));
        Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoModalidade(m));
        Button btnRemover = criarBotao("Apagar", btnDangerStyle, () -> { if(apagarModalidadeSegura(m.getId())) { atualizarListasAtuais(); } });
        card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar)); return card;
    }

    private VBox criarCardAdmin(Administrativo a, boolean isPesquisa) {
        VBox card = new VBox(8); card.setStyle(cardStyle);
        if (isPesquisa) card.getChildren().add(criarTextoDestaque("[Administrativo]", "#f1ba2a", 12));
        card.getChildren().addAll(criarTextoDestaque(a.getNome(), "#f8fafc", 18), criarTextoNormal("CPF: " + a.getCpf()), criarTextoNormal(String.format("Salário: R$ %.2f", a.getSalario())));
        alinharCardEsquerda(card);

        Region spacerVertical = new Region(); VBox.setVgrow(spacerVertical, Priority.ALWAYS);
        card.getChildren().add(spacerVertical);

        Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheAdministrativo(a));
        Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoAdministrativo(a));
        Button btnRemover = criarBotao("Demitir", btnDangerStyle, () -> { if(demitirAdminSeguro(a.getId())) { atualizarListasAtuais(); } });
        card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar)); return card;
    }

    private VBox criarCardGerente(Gerente g, boolean isPesquisa) {
        VBox card = new VBox(8); card.setStyle(cardStyle + " -fx-border-color: #f1ba2a;");
        if (isPesquisa) card.getChildren().add(criarTextoDestaque("[Gerente]", "#f1ba2a", 12));
        card.getChildren().addAll(criarTextoDestaque("👑 " + g.getNome(), "#f1ba2a", 18), criarTextoNormal("CPF: " + g.getCpf()), criarTextoNormal(String.format("Salário: R$ %.2f", g.getSalario())));
        alinharCardEsquerda(card);

        Region spacerVertical = new Region(); VBox.setVgrow(spacerVertical, Priority.ALWAYS);
        card.getChildren().add(spacerVertical);

        Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> {
            VBox c = novoConteudoDetalhe();
            c.getChildren().addAll(criarSecao("Informações (Gerente)"), criarItem("ID: " + g.getId()), criarItem("Nome: " + g.getNome()), criarItem("CPF: " + g.getCpf()), criarCampoSenhaComOlho(g.getSenhaOriginal()), criarItem(String.format("Salário: R$ %.2f", g.getSalario())));
            mostrarJanelaDetalhe("Gerente Geral: " + g.getNome(), c);
        });
        Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoGerente(g));
        Button btnRemover = criarBotao("Demitir", btnDangerStyle, () -> {
            if(confirmarExclusao()) { dono.demitirGerente(); atualizarListasAtuais(); }
        });
        card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar)); return card;
    }

    private VBox criarCardCusto(Custo c, boolean isPesquisa) {
        VBox card = new VBox(8); card.setStyle(cardStyle);
        if (isPesquisa) card.getChildren().add(criarTextoDestaque("[Custo]", "#f1ba2a", 12));
        card.getChildren().addAll(criarTextoDestaque(c.getNome(), "#f8fafc", 18), criarTextoDestaque(String.format("R$ %.2f", c.getValor()), "#ef4444", 16), criarTextoNormal("Data: " + c.getData()));
        alinharCardEsquerda(card);

        Region spacerVertical = new Region(); VBox.setVgrow(spacerVertical, Priority.ALWAYS);
        card.getChildren().add(spacerVertical);

        Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheCusto(c));
        Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoCusto(c));
        Button btnRemover = criarBotao("Apagar", btnDangerStyle, () -> { if(apagarCustoSeguro(c.getId())) { atualizarListasAtuais(); } });
        card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar)); return card;
    }

    private void atualizarListasAtuais() {
        if (viewBusca.isVisible()) executarBusca();
        if (viewAlunos.isVisible()) renderizarAlunos();
        if (viewPlanos.isVisible()) renderizarPlanos();
        if (viewTreinadores.isVisible()) renderizarTreinadores();
        if (viewModalidades.isVisible()) renderizarModalidades();
        if (viewAdmin.isVisible()) renderizarAdmin();
        if (viewCustos.isVisible()) renderizarCustos();
        atualizarDashboard();
    }

    // ==========================================
    // EXCLUSÃO SEGURA
    // ==========================================
    private boolean apagarModalidadeSegura(int id) {
        BancoDeDados bd = BancoDeDados.getInstance();
        for (Plano p : bd.getPlano().values()) {
            if (p.getModalidade().containsKey(id)) {
                mostrarAlertaErro("Bloqueado!\nNão é possível apagar esta Modalidade pois ela faz parte do Plano: '" + p.getNome() + "'. Retire-a do plano primeiro.");
                return false;
            }
        }
        for (Treinador t : bd.getTreinadores().values()) {
            boolean temModalidade = false;
            for (Modalidade mod : dono.modalidadesDoTreinador(t.getId())) {
                if (mod.getId() == id) { temModalidade = true; break; }
            }
            if (temModalidade) {
                mostrarAlertaErro("Bloqueado!\nO treinador '" + t.getNome() + "' está vinculado a esta modalidade. Edite o treinador primeiro.");
                return false;
            }
        }
        if (confirmarExclusao()) { bd.getModalidade().remove(id); return true; }
        return false;
    }

    private boolean apagarPlanoSeguro(int id) {
        List<Aluno> alunos = dono.alunosDoPlano(id);
        if (!alunos.isEmpty()) { mostrarAlertaErro("Bloqueado!\nExistem " + alunos.size() + " aluno(s) a usar este plano. Transfira os alunos para outro plano antes de apagar."); return false; }
        if (confirmarExclusao()) { dono.removerPlano(id); return true; }
        return false;
    }

    private boolean demitirTreinadorSeguro(int id) {
        List<Aluno> alunos = dono.alunosDoTreinador(id);
        if (!alunos.isEmpty()) { mostrarAlertaErro("Bloqueado!\nEste treinador possui " + alunos.size() + " aluno(s) associado(s). Transfira os alunos para outro treinador antes da demissão."); return false; }
        if (confirmarExclusao()) { dono.demitirTreinadorCompleto(id); return true; }
        return false;
    }

    private boolean expulsarAlunoSeguro(int id) {
        if (confirmarExclusao()) { dono.ExpulsarAluno(id); return true; }
        return false;
    }

    private boolean demitirAdminSeguro(int id) {
        if (confirmarExclusao()) { dono.demitirAdministrativo(id); return true; }
        return false;
    }

    private boolean apagarCustoSeguro(int id) {
        if (confirmarExclusao()) { dono.removerCusto(id); return true; }
        return false;
    }

    // ==========================================
    // NAVEGAÇÃO
    // ==========================================
    private void alternarTela(javafx.scene.Node telaAtiva, Button btnAtivo) {
        viewDashboard.setVisible(false); viewAlunos.setVisible(false); viewPlanos.setVisible(false);
        viewTreinadores.setVisible(false); viewModalidades.setVisible(false); viewAdmin.setVisible(false);
        viewCustos.setVisible(false); viewBusca.setVisible(false);
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
    // RENDERIZAÇÃO
    // ==========================================
    private void renderizarAlunos() {
        containerAlunos.getChildren().clear();
        for (Aluno a : BancoDeDados.getInstance().getAlunos().values()) containerAlunos.getChildren().add(criarCardAluno(a, false));
    }
    private void renderizarPlanos() {
        containerPlanos.getChildren().clear();
        for (Plano p : BancoDeDados.getInstance().getPlano().values()) containerPlanos.getChildren().add(criarCardPlano(p, false));
    }
    private void renderizarTreinadores() {
        containerTreinadores.getChildren().clear();
        for (Treinador t : BancoDeDados.getInstance().getTreinadores().values()) containerTreinadores.getChildren().add(criarCardTreinador(t, false));
    }
    private void renderizarModalidades() {
        containerModalidades.getChildren().clear();
        for (Modalidade m : BancoDeDados.getInstance().getModalidade().values()) containerModalidades.getChildren().add(criarCardModalidade(m, false));
    }
    private void renderizarAdmin() {
        containerAdmin.getChildren().clear();
        Optional<Gerente> ger = BancoDeDados.getInstance().gerente;
        if(ger.isPresent()) containerAdmin.getChildren().add(criarCardGerente(ger.get(), false));
        for (Administrativo a : BancoDeDados.getInstance().getAdministrativos().values()) containerAdmin.getChildren().add(criarCardAdmin(a, false));
    }
    private void renderizarCustos() {
        containerCustos.getChildren().clear();
        List<Custo> custos = new ArrayList<>(BancoDeDados.getInstance().getCustos().values());
        custos.sort((c1, c2) -> c2.getDataInstant().compareTo(c1.getDataInstant()));
        for (Custo c : custos) containerCustos.getChildren().add(criarCardCusto(c, false));
    }

    @FXML
    public void atualizarDashboard() {
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
            if(ger.isPresent()) boxRegistrosRecentes.getChildren().add(criarCardGerente(ger.get(), false));

            int countA = 0;
            for (Aluno a : BancoDeDados.getInstance().getAlunos().values()) {
                if (countA >= 3) break;
                boxRegistrosRecentes.getChildren().add(criarCardAluno(a, false));
                countA++;
            }
            int countT = 0;
            for (Treinador tr : BancoDeDados.getInstance().getTreinadores().values()) {
                if (countT >= 3) break;
                boxRegistrosRecentes.getChildren().add(criarCardTreinador(tr, false));
                countT++;
            }
        }
    }

    private Label criarTextoDestaque(String texto, String cor, int tamanho) {
        Label l = new Label(texto); l.setStyle("-fx-text-fill: " + cor + "; -fx-font-weight: bold; -fx-font-size: " + tamanho + "px;"); return l;
    }
    private Label criarTextoNormal(String texto) {
        Label l = new Label(texto); l.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 13px;"); return l;
    }
    private Label criarBadgeStatus(boolean emDia) {
        Label lbl = new Label(emDia ? "Em Dia" : "Atrasado");
        String cor = emDia ? "#22c55e" : "#ef4444";
        lbl.setStyle("-fx-text-fill: " + cor + "; -fx-border-color: " + cor + "; -fx-border-radius: 4; -fx-padding: 2 6; -fx-font-size: 11px; -fx-font-weight: bold;");
        return lbl;
    }

    // ==========================================
    // OPERAÇÕES DE CADASTRO E EDIÇÃO
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
        Platform.runLater(() -> { stage.requestFocus(); btnSalvar.requestFocus(); });
    }
    private <T> void estilizarComboBox(ComboBox<T> cb) {
        cb.setStyle("-fx-background-color: #04070d; -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-border-color: #141923; -fx-padding: 4px; -fx-opacity: 1;");
        cb.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText("");
                else {
                    setText(cb.getConverter() != null ? cb.getConverter().toString(item) : item.toString());
                    setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-background-color: transparent; -fx-opacity: 1;");
                }
            }
        });
    }

    @FXML
    private void cadastrarAluno() {
        if (BancoDeDados.getInstance().getPlano().isEmpty() || BancoDeDados.getInstance().getTreinadores().isEmpty()) { mostrarAlertaErro("Cadastre pelo menos 1 Plano e 1 Treinador antes."); return; }
        Stage stage = criarJanela("Matricular Novo Aluno", true, true); GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Ex: Kauan Silva"); TextField txtSenha = criarInput("Mín. 8 letras, 1 Maiúscula, 1 Número, 1 Especial (@)");
        TextField txtCpf = criarInputCpf();
        TextField txtIdade = criarInput("Apenas números (Ex: 24)"); aplicarMascaraNumerica(txtIdade, false);
        TextField txtAltura = criarInput("Ex: 1.75"); aplicarMascaraNumerica(txtAltura, true);
        TextField txtPeso = criarInput("Ex: 80.5"); aplicarMascaraNumerica(txtPeso, true);
        ComboBox<String> cbIntensidade = new ComboBox<>(); cbIntensidade.getItems().addAll("Leve", "Médio", "Intenso"); estilizarComboBox(cbIntensidade);

        ComboBox<Plano> cbPlano = new ComboBox<>(); cbPlano.getItems().addAll(BancoDeDados.getInstance().getPlano().values());
        cbPlano.setConverter(new StringConverter<Plano>() {
            @Override public String toString(Plano p) { return p == null ? "" : p.getNome(); }
            @Override public Plano fromString(String s) { return null; }
        });
        estilizarComboBox(cbPlano);

        VBox boxTreinadoresDinâmicos = new VBox(8);
        List<ComboBox<Treinador>> comboboxesTreinadores = new ArrayList<>();

        cbPlano.valueProperty().addListener((obs, pAntigo, planoSelecionado) -> {
            boxTreinadoresDinâmicos.getChildren().clear();
            comboboxesTreinadores.clear();
            if (planoSelecionado != null) {
                for (Modalidade mod : planoSelecionado.getModalidade().values()) {
                    Label lblMod = criarTextoNormal("Treinador de " + mod.getNome() + ":");
                    ComboBox<Treinador> cbT = new ComboBox<>();
                    cbT.setPrefWidth(300);
                    cbT.setConverter(new StringConverter<Treinador>() {
                        @Override public String toString(Treinador t) { return t == null ? "" : t.getNome() + " (" + t.getEspecialidade() + ")"; }
                        @Override public Treinador fromString(String s) { return null; }
                    });
                    estilizarComboBox(cbT);
                    for (Treinador t : mod.getTreinadores().values()) cbT.getItems().add(t);
                    if (cbT.getItems().isEmpty()) { cbT.setDisable(true); cbT.setPromptText("Sem treinadores"); } else { cbT.setPromptText("Selecione..."); }

                    comboboxesTreinadores.add(cbT);
                    boxTreinadoresDinâmicos.getChildren().addAll(lblMod, cbT);
                }
            }
        });

        adicionarLinhaForm(grid, 0, "Nome Completo:", txtNome); adicionarLinhaForm(grid, 1, "Senha:", txtSenha);
        adicionarLinhaForm(grid, 2, "CPF:", txtCpf); adicionarLinhaForm(grid, 3, "Idade:", txtIdade);
        adicionarLinhaForm(grid, 4, "Altura (m):", txtAltura); adicionarLinhaForm(grid, 5, "Peso (kg):", txtPeso);
        adicionarLinhaForm(grid, 6, "Intensidade:", cbIntensidade); adicionarLinhaForm(grid, 7, "Plano:", cbPlano);
        adicionarLinhaForm(grid, 8, "Equipe:", boxTreinadoresDinâmicos);

        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 9, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Concluir Matrícula", () -> {
            try {
                if (cbPlano.getValue() == null || cbIntensidade.getValue() == null) { lblErro.setText("Selecione os campos nas caixas de opção."); return; }
                List<Treinador> equipeEscolhida = new ArrayList<>();
                for (ComboBox<Treinador> cb : comboboxesTreinadores) {
                    if (cb.getValue() == null && !cb.isDisabled()) { lblErro.setText("Selecione os treinadores obrigatórios."); return; }
                    if (cb.getValue() != null && !equipeEscolhida.contains(cb.getValue())) equipeEscolhida.add(cb.getValue());
                }
                double altura = Double.parseDouble(txtAltura.getText().replace(",", ".")); double peso = Double.parseDouble(txtPeso.getText().replace(",", ".")); int idade = Integer.parseInt(txtIdade.getText());

                boolean sucesso = dono.cadastrarAluno(txtNome.getText(), txtSenha.getText(), txtCpf.getText(), idade, altura, peso, cbPlano.getValue(), cbIntensidade.getValue(), equipeEscolhida);
                if (sucesso) { fecharSemAviso(stage); atualizarListasAtuais(); } else { lblErro.setText("Regras Violadas! CPF incorreto ou Senha fraca."); }
            } catch (Exception ex) { lblErro.setText("Preencha todos os números corretamente."); }
        });

        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 10, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    @FXML
    private void cadastrarPlano() {
        if (BancoDeDados.getInstance().getModalidade().isEmpty()) { mostrarAlertaErro("Crie uma Modalidade antes."); return; }
        Stage stage = criarJanela("Novo Plano de Acesso", true, true); GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Ex: VIP Anual"); TextField txtDesc = criarInput("Ex: Acesso Total");
        TextField txtValor = criarInput("Ex: 150.00"); aplicarMascaraNumerica(txtValor, true);
        TextField txtValidade = criarInput("Meses. Ex: 12"); aplicarMascaraNumerica(txtValidade, false);
        TextField txtJuros = criarInput("Ex: 5.0"); aplicarMascaraNumerica(txtJuros, true);
        List<CheckBox> checkboxes = new ArrayList<>(); ScrollPane listaModalidades = criarListaModalidades(checkboxes);

        adicionarLinhaForm(grid, 0, "Nome do Plano:", txtNome); adicionarLinhaForm(grid, 1, "Descrição:", txtDesc);
        adicionarLinhaForm(grid, 2, "Valor Mensal (R$):", txtValor); adicionarLinhaForm(grid, 3, "Validade (Meses):", txtValidade);
        adicionarLinhaForm(grid, 4, "Juros Atraso (%):", txtJuros); adicionarLinhaForm(grid, 5, "Modalidades:", listaModalidades);
        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 6, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Plano", () -> {
            try {
                List<Integer> idsModalidades = idsSelecionados(checkboxes);
                if (idsModalidades.isEmpty()) { lblErro.setText("Selecione ao menos uma modalidade."); return; }
                double valor = Double.parseDouble(txtValor.getText().replace(",", ".")); double juros = Double.parseDouble(txtJuros.getText().replace(",", "."));
                boolean sucesso = dono.cadastrarPlano(txtNome.getText(), txtDesc.getText(), valor, idsModalidades, Integer.parseInt(txtValidade.getText()), juros);
                if(sucesso) { fecharSemAviso(stage); atualizarListasAtuais(); } else { lblErro.setText("Erro ao cadastrar plano. Verifique os dados."); }
            } catch (Exception ex) { lblErro.setText("Preencha Valor, Validade e Juros apenas com números."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 7, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    @FXML
    private void cadastrarModalidade() {
        Stage stage = criarJanela("Nova Modalidade", true, true); GridPane grid = (GridPane) stage.getUserData();
        TextField txtNome = criarInput("Ex: Musculação"); TextField txtDesc = criarInput("Ex: Área de peso livre");
        adicionarLinhaForm(grid, 0, "Nome:", txtNome); adicionarLinhaForm(grid, 1, "Descrição:", txtDesc);
        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 2, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Modalidade", () -> {
            if (txtNome.getText().isBlank() || txtDesc.getText().isBlank()) { lblErro.setText("Preencha todos os campos."); return; }
            dono.escolherModalidade(txtNome.getText(), txtDesc.getText()); fecharSemAviso(stage); atualizarListasAtuais();
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 3, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    @FXML
    private void cadastrarTreinador() {
        if (BancoDeDados.getInstance().getModalidade().isEmpty()) { mostrarAlertaErro("Crie uma Modalidade antes."); return; }
        Stage stage = criarJanela("Novo Treinador", true, true); GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Nome Completo"); TextField txtSenha = criarInput("Mín. 8, 1 Maiúscula, 1 Número, 1 Especial (@)");
        TextField txtCpf = criarInputCpf(); TextField txtSalario = criarInput("Ex: 3500.00"); aplicarMascaraNumerica(txtSalario, true);
        TextField txtEsp = criarInput("Ex: Hipertrofia");
        List<CheckBox> checkboxes = new ArrayList<>(); ScrollPane listaModalidades = criarListaModalidades(checkboxes);

        adicionarLinhaForm(grid, 0, "Nome:", txtNome); adicionarLinhaForm(grid, 1, "Senha:", txtSenha);
        adicionarLinhaForm(grid, 2, "CPF:", txtCpf); adicionarLinhaForm(grid, 3, "Salário (R$):", txtSalario);
        adicionarLinhaForm(grid, 4, "Especialidade:", txtEsp); adicionarLinhaForm(grid, 5, "Modalidades:", listaModalidades);
        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 6, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Contratar", () -> {
            try {
                List<Integer> idsModalidades = idsSelecionados(checkboxes);
                if (idsModalidades.isEmpty()) { lblErro.setText("Selecione ao menos uma modalidade."); return; }
                double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                boolean sucesso = dono.CadastrarTreinador(idsModalidades, txtNome.getText(), txtSenha.getText(), txtCpf.getText(), salario, txtEsp.getText());
                if(sucesso) { fecharSemAviso(stage); atualizarListasAtuais(); } else { lblErro.setText("Erro! Verifique as regras exatas de CPF e Senha."); }
            } catch (Exception ex) { lblErro.setText("Verifique os números do salário."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 7, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    @FXML
    private void cadastrarGerente() {
        if (BancoDeDados.getInstance().gerente.isPresent()) { mostrarAlertaErro("Bloqueado!\nA academia já possui um Gerente Geral ativo."); return; }
        Stage stage = criarJanela("Nomear Gerente Geral", true, true); GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Nome Completo"); TextField txtSenha = criarInput("Mín. 8, 1 Maiúscula, 1 Número, 1 Especial (@)");
        TextField txtCpf = criarInputCpf(); TextField txtSalario = criarInput("Ex: 5000.00"); aplicarMascaraNumerica(txtSalario, true);

        adicionarLinhaForm(grid, 0, "Nome:", txtNome); adicionarLinhaForm(grid, 1, "Senha:", txtSenha);
        adicionarLinhaForm(grid, 2, "CPF:", txtCpf); adicionarLinhaForm(grid, 3, "Salário (R$):", txtSalario);
        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 4, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Nomear", () -> {
            try {
                double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                boolean sucesso = dono.cadastrarGerente(txtNome.getText(), txtSenha.getText(), txtCpf.getText(), salario);
                if(sucesso) { fecharSemAviso(stage); atualizarListasAtuais(); } else { lblErro.setText("Erro! O CPF ou Senha informados não cumprem as regras."); }
            } catch (Exception ex) { lblErro.setText("Salário inválido."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 5, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    @FXML
    private void cadastrarAdmin() {
        Stage stage = criarJanela("Novo Administrativo", true, true); GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Nome Completo"); TextField txtSenha = criarInput("Mín. 8, 1 Maiúscula, 1 Número, 1 Especial (@)");
        TextField txtCpf = criarInputCpf(); TextField txtSalario = criarInput("Ex: 2500.00"); aplicarMascaraNumerica(txtSalario, true);

        adicionarLinhaForm(grid, 0, "Nome:", txtNome); adicionarLinhaForm(grid, 1, "Senha:", txtSenha);
        adicionarLinhaForm(grid, 2, "CPF:", txtCpf); adicionarLinhaForm(grid, 3, "Salário (R$):", txtSalario);
        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 4, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Contratar", () -> {
            try {
                double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                boolean sucesso = dono.cadastrarAdministrativo(txtNome.getText(), txtSenha.getText(), txtCpf.getText(), salario);
                if(sucesso) { fecharSemAviso(stage); atualizarListasAtuais(); } else { lblErro.setText("Erro! O CPF ou Senha informados não cumprem as regras."); }
            } catch (Exception ex) { lblErro.setText("Salário inválido."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 5, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    @FXML
    private void cadastrarCusto() {
        Stage stage = criarJanela("Novo Custo", true, true); GridPane grid = (GridPane) stage.getUserData();
        TextField txtNome = criarInput("Ex: Conta de Luz"); TextField txtValor = criarInput("Ex: 1200.00"); aplicarMascaraNumerica(txtValor, true);
        adicionarLinhaForm(grid, 0, "Nome do Custo:", txtNome); adicionarLinhaForm(grid, 1, "Valor (R$):", txtValor);
        Label lblErro = criarLabelErro(); grid.add(lblErro, 0, 2, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Custo", () -> {
            try {
                double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
                boolean sucesso = dono.cadastrarCusto(txtNome.getText(), valor);
                if(sucesso) { fecharSemAviso(stage); atualizarListasAtuais(); } else { lblErro.setText("Informe um nome e um valor maior que zero."); }
            } catch (Exception ex) { lblErro.setText("Valor inválido."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 3, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    // ==========================================
    // ACESSAR E EDITAR
    // ==========================================
    private HBox criarCampoSenhaComOlho(String senhaOriginal) {
        HBox box = new HBox(8); box.setAlignment(Pos.CENTER_LEFT);
        Label lblOculta = criarItem("Senha: ••••••••");
        Label lblVisivel = criarItem("Senha: " + senhaOriginal);
        lblVisivel.setVisible(false); lblVisivel.setManaged(false);
        Button btnOlho = criarBotao("👁", "-fx-background-color: transparent; -fx-text-fill: #94a3b8; -fx-cursor: hand; -fx-padding: 0 4;", () -> {
            boolean isOculta = lblOculta.isVisible();
            lblOculta.setVisible(!isOculta); lblOculta.setManaged(!isOculta);
            lblVisivel.setVisible(isOculta); lblVisivel.setManaged(isOculta);
        });
        box.getChildren().addAll(lblOculta, lblVisivel, btnOlho);
        return box;
    }

    private void abrirDetalheAluno(Aluno a) {
        VBox conteudo = novoConteudoDetalhe();
        conteudo.getChildren().addAll(criarSecao("Informações"), criarItem("ID: " + a.getId()), criarItem("CPF: " + a.getCpf()), criarCampoSenhaComOlho(a.getSenhaOriginal()), criarItem("Idade: " + a.getIdade() + " anos"), criarItem("Altura: " + a.getAltura() + " m"), criarItem("Peso: " + a.getPeso() + " kg"), criarItem("Intensidade: " + a.getIntensidade()), criarSecao("Planos Assinados"));
        if (a.getListaAssinaturaPlano().isEmpty()) conteudo.getChildren().add(criarTextoNormal("Nenhum plano assinado."));
        else for (AssinaturaPlano ap : a.getListaAssinaturaPlano().values()) { conteudo.getChildren().add(criarItem("• " + ap.getPlano().getNome() + " — " + String.format("R$ %.2f", ap.getPlano().getValor()))); }
        conteudo.getChildren().add(criarSecao("Equipe de Treinadores"));
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
        conteudo.getChildren().addAll(criarSecao("Informações"), criarItem("ID: " + t.getId()), criarItem("CPF: " + t.getCpf()), criarCampoSenhaComOlho(t.getSenhaOriginal()), criarItem("Especialidade: " + t.getEspecialidade()), criarItem(String.format("Salário: R$ %.2f", t.getSalario())), criarSecao("Modalidades que Leciona"));
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
        conteudo.getChildren().addAll(criarSecao("Informações"), criarItem("ID: " + a.getId()), criarItem("Nome: " + a.getNome()), criarItem("CPF: " + a.getCpf()), criarCampoSenhaComOlho(a.getSenhaOriginal()), criarItem(String.format("Salário: R$ %.2f", a.getSalario())));
        mostrarJanelaDetalhe("Administrativo: " + a.getNome(), conteudo);
    }

    private void abrirDetalheCusto(Custo c) {
        VBox conteudo = novoConteudoDetalhe();
        conteudo.getChildren().addAll(criarSecao("Detalhes do Custo"), criarItem("ID: " + c.getId()), criarItem("Nome: " + c.getNome()), criarItem(String.format("Valor: R$ %.2f", c.getValor())), criarItem("Data de Lançamento: " + c.getData()));
        mostrarJanelaDetalhe("Custo: " + c.getNome(), conteudo);
    }

    private void abrirEdicaoAluno(Aluno a) {
        Stage stage = criarJanela("Editar Aluno — " + a.getNome(), false, true); GridPane grid = (GridPane) stage.getUserData();
        TextField txtAltura = criarInput("Ex: 1.75"); txtAltura.setText(String.valueOf(a.getAltura())); aplicarMascaraNumerica(txtAltura, true);
        TextField txtPeso = criarInput("Ex: 80.5"); txtPeso.setText(String.valueOf(a.getPeso())); aplicarMascaraNumerica(txtPeso, true);
        TextField txtSenha = criarInput("Nova senha (deixe vazio para manter)");

        ComboBox<String> cbIntensidade = new ComboBox<>(); cbIntensidade.getItems().addAll("Leve", "Médio", "Intenso"); cbIntensidade.setValue(a.getIntensidade());
        estilizarComboBox(cbIntensidade);

        VBox boxTreinadoresDinâmicos = new VBox(8);
        List<ComboBox<Treinador>> comboboxesTreinadores = new ArrayList<>();
        List<CheckBox> checkboxesPlanos = new ArrayList<>(); VBox caixaPlanos = new VBox(8); caixaPlanos.setStyle("-fx-padding: 8;");

        Runnable atualizarTreinadores = () -> {
            boxTreinadoresDinâmicos.getChildren().clear();
            comboboxesTreinadores.clear();
            List<Integer> ids = idsSelecionados(checkboxesPlanos);

            for(Integer idPlano : ids) {
                Plano p = BancoDeDados.getInstance().getPlano().get(idPlano);
                for(Modalidade mod : p.getModalidade().values()) {
                    boolean jaTemComboParaEstaModalidade = false;
                    for (javafx.scene.Node n : boxTreinadoresDinâmicos.getChildren()) {
                        if (n instanceof Label && ((Label) n).getText().contains(mod.getNome())) { jaTemComboParaEstaModalidade = true; break; }
                    }
                    if (jaTemComboParaEstaModalidade) continue;

                    Label lblMod = criarTextoNormal("Treinador de " + mod.getNome() + ":");
                    ComboBox<Treinador> cbT = new ComboBox<>();
                    cbT.setPrefWidth(300);
                    cbT.setConverter(new StringConverter<Treinador>() {
                        @Override public String toString(Treinador t) { return t == null ? "" : t.getNome() + " (" + t.getEspecialidade() + ")"; }
                        @Override public Treinador fromString(String s) { return null; }
                    });
                    estilizarComboBox(cbT);

                    for (Treinador t : mod.getTreinadores().values()) {
                        cbT.getItems().add(t);
                        if (a.getTreinadores().containsKey(t.getId())) cbT.setValue(t);
                    }
                    if (cbT.getItems().isEmpty()) { cbT.setDisable(true); cbT.setPromptText("Sem treinadores"); } else { cbT.setPromptText("Selecione..."); }

                    comboboxesTreinadores.add(cbT);
                    boxTreinadoresDinâmicos.getChildren().addAll(lblMod, cbT);
                }
            }
        };

        for (Plano p : BancoDeDados.getInstance().getPlano().values()) {
            CheckBox cb = new CheckBox(p.getNome()); cb.setUserData(p.getId()); cb.setStyle("-fx-text-fill: #f8fafc; -fx-cursor: hand;");
            boolean assinando = false;
            for (AssinaturaPlano ap : a.getListaAssinaturaPlano().values()) { if (ap.getPlano().getId() == p.getId()) { assinando = true; break; } }
            if(assinando) cb.setSelected(true);
            cb.setOnAction(e -> atualizarTreinadores.run());
            checkboxesPlanos.add(cb); caixaPlanos.getChildren().add(cb);
        }
        atualizarTreinadores.run();

        ScrollPane scrollPlanos = new ScrollPane(caixaPlanos); scrollPlanos.setPrefHeight(100); scrollPlanos.setStyle("-fx-background-color: #04070d; -fx-background: #04070d; -fx-border-color: #141923;");

        Label lblErro = criarLabelErro();
        adicionarLinhaForm(grid, 0, "Nome:", criarTextoDestaque(a.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Nova Senha:", txtSenha); adicionarLinhaForm(grid, 2, "Altura (m):", txtAltura);
        adicionarLinhaForm(grid, 3, "Peso (kg):", txtPeso); adicionarLinhaForm(grid, 4, "Intensidade:", cbIntensidade); adicionarLinhaForm(grid, 5, "Planos:", scrollPlanos); adicionarLinhaForm(grid, 6, "Equipe:", boxTreinadoresDinâmicos);
        grid.add(lblErro, 0, 7, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Alterações", () -> {
            double alturaAnterior = a.getAltura(); double pesoAnterior = a.getPeso();
            try {
                if(!txtSenha.getText().isBlank()) {
                    if(Verificacao.verificarSenha(txtSenha.getText())) {
                        a.setSenha(CriptografarSenha.SenhaGeral(txtSenha.getText()));
                        a.setSenhaOriginal(txtSenha.getText());
                    } else { lblErro.setText("A nova senha é fraca!"); return; }
                }
                double altura = Double.parseDouble(txtAltura.getText().replace(",", ".")); double peso = Double.parseDouble(txtPeso.getText().replace(",", "."));
                if (!a.setAltura(altura)) { lblErro.setText("Altura inválida."); return; }
                if (!a.setPeso(peso)) { a.setAltura(alturaAnterior); lblErro.setText("Peso inválido."); return; }
                if (!a.setIntensidade(cbIntensidade.getValue())) { lblErro.setText("Selecione a intensidade."); return; }
                List<Integer> ids = idsSelecionados(checkboxesPlanos);
                if (ids.isEmpty()) { lblErro.setText("Selecione pelo menos um plano."); return; }

                List<Treinador> equipeEscolhida = new ArrayList<>();
                for (ComboBox<Treinador> cb : comboboxesTreinadores) {
                    if (cb.getValue() == null && !cb.isDisabled()) { lblErro.setText("Selecione os treinadores obrigatórios para cada modalidade."); return; }
                    if (cb.getValue() != null && !equipeEscolhida.contains(cb.getValue())) equipeEscolhida.add(cb.getValue());
                }

                List<Integer> idsRemover = new ArrayList<>();
                for (Integer idAntigo : a.getListaAssinaturaPlano().keySet()) {
                    int idDoPlanoDestaAssinatura = a.getListaAssinaturaPlano().get(idAntigo).getPlano().getId();
                    boolean aindaTem = false;
                    for (Integer idNovo : ids) { if (idNovo == idDoPlanoDestaAssinatura) { aindaTem = true; break; } }
                    if (!aindaTem) idsRemover.add(idAntigo);
                }
                for (Integer idRem : idsRemover) a.getListaAssinaturaPlano().remove(idRem);

                for (Integer idPlano : ids) {
                    boolean jaTem = false;
                    for (AssinaturaPlano ap : a.getListaAssinaturaPlano().values()) { if (ap.getPlano().getId() == idPlano) { jaTem = true; break; } }
                    if (!jaTem) {
                        Plano p = BancoDeDados.getInstance().getPlano().get(idPlano); AssinaturaPlano novaAp = new AssinaturaPlano(p.getId(), p);
                        BancoDeDados.getInstance().assinaturaPlano.put(novaAp.getId(), novaAp); a.getListaAssinaturaPlano().put(novaAp.getId(), novaAp);
                    }
                }

                a.getTreinadores().clear();
                for (Treinador t : equipeEscolhida) a.getTreinadores().put(t.getId(), t);

                fecharSemAviso(stage); atualizarListasAtuais();
            } catch (Exception ex) { lblErro.setText("Preencha Altura e Peso corretamente."); }
        });

        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER);
        boxBotao.setStyle("-fx-padding: 0 32 32 32;");

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #06090e; -fx-padding: 0;");
        scroll.setPrefViewportHeight(450);

        VBox root = (VBox) stage.getScene().getRoot();
        javafx.scene.Node header = root.getChildren().get(0);
        root.getChildren().clear();
        root.getChildren().addAll(header, scroll, boxBotao);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    private void abrirEdicaoTreinador(Treinador t) {
        Stage stage = criarJanela("Editar Treinador — " + t.getNome(), false, true); GridPane grid = (GridPane) stage.getUserData();
        TextField txtSalario = criarInput("Ex: 3500.00"); txtSalario.setText(String.valueOf(t.getSalario())); aplicarMascaraNumerica(txtSalario, true);
        TextField txtEsp = criarInput("Ex: Hipertrofia"); txtEsp.setText(t.getEspecialidade());
        TextField txtSenha = criarInput("Nova senha (deixe vazio para manter)");

        List<CheckBox> checkboxesMod = new ArrayList<>(); VBox caixaMod = new VBox(8); caixaMod.setStyle("-fx-padding: 8;");
        for (Modalidade m : BancoDeDados.getInstance().getModalidade().values()) {
            CheckBox cb = new CheckBox(m.getNome()); cb.setUserData(m.getId()); cb.setStyle("-fx-text-fill: #f8fafc; -fx-cursor: hand;");
            if (m.getTreinadores().containsKey(t.getId())) cb.setSelected(true);
            checkboxesMod.add(cb); caixaMod.getChildren().add(cb);
        }
        ScrollPane scrollMod = new ScrollPane(caixaMod); scrollMod.setPrefHeight(100); scrollMod.setStyle("-fx-background-color: #04070d; -fx-background: #04070d; -fx-border-color: #141923;");

        Label lblErro = criarLabelErro();
        adicionarLinhaForm(grid, 0, "Nome:", criarTextoDestaque(t.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Nova Senha:", txtSenha);
        adicionarLinhaForm(grid, 2, "Salário (R$):", txtSalario); adicionarLinhaForm(grid, 3, "Especialidade:", txtEsp); adicionarLinhaForm(grid, 4, "Modalidades Ativas:", scrollMod);
        grid.add(lblErro, 0, 5, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Alterações", () -> {
            try {
                if(!txtSenha.getText().isBlank()) {
                    if(Verificacao.verificarSenha(txtSenha.getText())) {
                        t.setSenha(CriptografarSenha.SenhaGeral(txtSenha.getText()));
                        t.setSenhaOriginal(txtSenha.getText());
                    } else { lblErro.setText("A nova senha é fraca!"); return; }
                }
                double salarioNovo = Double.parseDouble(txtSalario.getText().replace(",", "."));
                if (salarioNovo <= 0) { lblErro.setText("O salário deve ser maior que zero."); return; }
                if (txtEsp.getText().isBlank()) { lblErro.setText("Informe a especialidade."); return; }
                List<Integer> ids = idsSelecionados(checkboxesMod);
                if (ids.isEmpty()) { lblErro.setText("Selecione pelo menos uma modalidade."); return; }

                List<Integer> modAntigas = new ArrayList<>();
                for (Modalidade mod : dono.modalidadesDoTreinador(t.getId())) modAntigas.add(mod.getId());

                List<Integer> removidas = new ArrayList<>();
                for (Integer antiga : modAntigas) {
                    boolean mantida = false;
                    for (Integer novoId : ids) { if (novoId.equals(antiga)) { mantida = true; break; } }
                    if (!mantida) removidas.add(antiga);
                }

                for (Integer idRem : removidas) {
                    for (Aluno a : dono.alunosDoTreinador(t.getId())) {
                        boolean alunoUsaEstaModalidade = false;
                        for (AssinaturaPlano ap : a.getListaAssinaturaPlano().values()) {
                            if (ap.getPlano().getModalidade().containsKey(idRem)) { alunoUsaEstaModalidade = true; break; }
                        }
                        if (alunoUsaEstaModalidade) {
                            lblErro.setText("Bloqueado!\nO aluno " + a.getNome() + " estuda essa modalidade com este treinador. Transfira o aluno na aba Edição de Aluno primeiro.");
                            return;
                        }
                    }
                }

                double diferenca = salarioNovo - t.getSalario();
                BancoDeDados.getInstance().grafico.addCustoSalario(diferenca);

                t.setSalario(salarioNovo); t.setEspecialidade(txtEsp.getText());
                for (Modalidade m : BancoDeDados.getInstance().getModalidade().values()) {
                    boolean foiSelecionada = false;
                    for (Integer i : ids) { if (i == m.getId()) { foiSelecionada = true; break; } }
                    if (!foiSelecionada) { m.removeTreinador(m.getId(), t.getId()); }
                    else if (!m.getTreinadores().containsKey(t.getId())) { m.addTreinador(t.getId(), t); }
                }
                fecharSemAviso(stage); atualizarListasAtuais();
            } catch (Exception ex) { lblErro.setText("Verifique os números do salário."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 6, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    private void abrirEdicaoPlano(Plano p) {
        Stage stage = criarJanela("Editar Plano — " + p.getNome(), false, true); GridPane grid = (GridPane) stage.getUserData();
        TextField txtDescricao = criarInput("Descrição do plano"); txtDescricao.setText(p.getDescricao());
        TextField txtValidade = criarInput("Dias de validade"); txtValidade.setText(String.valueOf(p.getMesesValidade())); aplicarMascaraNumerica(txtValidade, false);
        TextField txtValor = criarInput("Valor (R$)"); txtValor.setText(String.valueOf(p.getValor())); aplicarMascaraNumerica(txtValor, true);
        TextField txtJuros = criarInput("Juros %"); txtJuros.setText(String.valueOf(p.getJuros())); aplicarMascaraNumerica(txtJuros, true);

        List<CheckBox> checkboxes = new ArrayList<>(); VBox caixaMod = new VBox(8); caixaMod.setStyle("-fx-padding: 8;");
        for (Modalidade m : BancoDeDados.getInstance().getModalidade().values()) {
            CheckBox cb = new CheckBox(m.getNome()); cb.setUserData(m.getId()); cb.setStyle("-fx-text-fill: #f8fafc; -fx-cursor: hand;");
            if (p.getModalidade().containsKey(m.getId())) cb.setSelected(true);
            checkboxes.add(cb); caixaMod.getChildren().add(cb);
        }
        ScrollPane scrollMod = new ScrollPane(caixaMod); scrollMod.setPrefHeight(100); scrollMod.setStyle("-fx-background-color: #04070d; -fx-background: #04070d; -fx-border-color: #141923;");

        Label lblErro = criarLabelErro();
        adicionarLinhaForm(grid, 0, "Nome:", criarTextoDestaque(p.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Descrição:", txtDescricao);
        adicionarLinhaForm(grid, 2, "Validade (meses):", txtValidade); adicionarLinhaForm(grid, 3, "Valor (R$):", txtValor); adicionarLinhaForm(grid, 4, "Juros (%):", txtJuros); adicionarLinhaForm(grid, 5, "Modalidades:", scrollMod);
        grid.add(lblErro, 0, 6, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Plano", () -> {
            try {
                if (txtDescricao.getText().isBlank()) { lblErro.setText("A descrição é obrigatória."); return; }
                double novoValor = Double.parseDouble(txtValor.getText().replace(",", "."));
                double novosJuros = Double.parseDouble(txtJuros.getText().replace(",", "."));
                p.setDescricao(txtDescricao.getText()); p.setValor(novoValor); p.setMesesValidade(Integer.parseInt(txtValidade.getText()));
                p.setJuros(novosJuros);
                p.getModalidade().clear();
                List<Integer> selecionados = idsSelecionados(checkboxes);
                for (Integer id : selecionados) p.addModalidade(id);
                fecharSemAviso(stage); atualizarListasAtuais();
            } catch (Exception e) { lblErro.setText("Verifique os números digitados."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 7, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    private void abrirEdicaoAdministrativo(Administrativo a) {
        Stage stage = criarJanela("Editar Administrativo — " + a.getNome(), false, true); GridPane grid = (GridPane) stage.getUserData();
        TextField txtSalario = criarInput("Ex: 2500.00"); txtSalario.setText(String.valueOf(a.getSalario())); aplicarMascaraNumerica(txtSalario, true);
        TextField txtSenha = criarInput("Nova senha (deixe vazio para manter)");
        Label lblErro = criarLabelErro();

        adicionarLinhaForm(grid, 0, "Nome (não editável):", criarTextoDestaque(a.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Nova Senha:", txtSenha); adicionarLinhaForm(grid, 2, "Salário (R$):", txtSalario);
        grid.add(lblErro, 0, 3, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Alterações", () -> {
            try {
                if(!txtSenha.getText().isBlank()) {
                    if(Verificacao.verificarSenha(txtSenha.getText())) {
                        a.setSenha(CriptografarSenha.SenhaGeral(txtSenha.getText()));
                        a.setSenhaOriginal(txtSenha.getText());
                    } else { lblErro.setText("A nova senha é fraca!"); return; }
                }
                double novoSalario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                if (novoSalario <= 0) { lblErro.setText("O salário deve ser maior que zero."); return; }

                double diferenca = novoSalario - a.getSalario();
                BancoDeDados.getInstance().grafico.addCustoSalario(diferenca);

                a.setSalario(novoSalario);
                fecharSemAviso(stage); atualizarListasAtuais();
            } catch (Exception ex) { lblErro.setText("Salário inválido."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 4, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    private void abrirEdicaoGerente(Gerente g) {
        Stage stage = criarJanela("Editar Gerente — " + g.getNome(), false, true); GridPane grid = (GridPane) stage.getUserData();
        TextField txtSalario = criarInput("Ex: 2500.00"); txtSalario.setText(String.valueOf(g.getSalario())); aplicarMascaraNumerica(txtSalario, true);
        TextField txtSenha = criarInput("Nova senha (deixe vazio para manter)");
        Label lblErro = criarLabelErro();

        adicionarLinhaForm(grid, 0, "Nome (não editável):", criarTextoDestaque(g.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Nova Senha:", txtSenha); adicionarLinhaForm(grid, 2, "Salário (R$):", txtSalario);
        grid.add(lblErro, 0, 3, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Alterações", () -> {
            try {
                if(!txtSenha.getText().isBlank()) {
                    if(Verificacao.verificarSenha(txtSenha.getText())) {
                        g.setSenha(CriptografarSenha.SenhaGeral(txtSenha.getText()));
                        g.setSenhaOriginal(txtSenha.getText());
                    } else { lblErro.setText("A nova senha é fraca!"); return; }
                }
                double novoSalario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                if (novoSalario <= 0) { lblErro.setText("O salário deve ser maior que zero."); return; }

                double diferenca = novoSalario - g.getSalario();
                BancoDeDados.getInstance().grafico.addCustoSalario(diferenca);

                g.setSalario(novoSalario);
                fecharSemAviso(stage); atualizarListasAtuais();
            } catch (Exception ex) { lblErro.setText("Salário inválido."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER);
        grid.add(boxBotao, 0, 4, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    private void abrirEdicaoCusto(Custo c) {
        Stage stage = criarJanela("Editar Custo — " + c.getNome(), false, true); GridPane grid = (GridPane) stage.getUserData();
        TextField txtValor = criarInput("Novo valor (R$)"); txtValor.setText(String.valueOf(c.getValor())); aplicarMascaraNumerica(txtValor, true);
        Label lblErro = criarLabelErro();
        adicionarLinhaForm(grid, 0, "Nome:", criarTextoDestaque(c.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Data Original:", criarTextoDestaque(c.getData(), "#94a3b8", 14)); adicionarLinhaForm(grid, 2, "Novo Valor (R$):", txtValor);
        grid.add(lblErro, 0, 3, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar Custo", () -> {
            try {
                double novoValor = Double.parseDouble(txtValor.getText().replace(",", "."));
                if (novoValor <= 0) { lblErro.setText("O valor deve ser maior que zero."); return; }

                double diferenca = novoValor - c.getValor();
                BancoDeDados.getInstance().grafico.addCustoSalario(diferenca);

                c.setValor(novoValor);
                fecharSemAviso(stage); atualizarListasAtuais();
            } catch (Exception e) { lblErro.setText("Formato de valor inválido."); }
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 4, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    private void abrirEdicaoModalidade(Modalidade m) {
        Stage stage = criarJanela("Editar Modalidade — " + m.getNome(), false, true); GridPane grid = (GridPane) stage.getUserData();
        TextField txtDesc = criarInput("Ex: Área de peso livre"); txtDesc.setText(m.getDescricao());
        Label lblErro = criarLabelErro();
        adicionarLinhaForm(grid, 0, "Nome:", criarTextoDestaque(m.getNome(), "#f8fafc", 14)); adicionarLinhaForm(grid, 1, "Descrição:", txtDesc);
        grid.add(lblErro, 0, 2, 2, 1);

        Button btnSalvar = criarBotaoSalvar("Salvar", () -> {
            if(txtDesc.getText().isBlank()){ lblErro.setText("Descrição não pode ser vazia"); return; }
            dono.setDescricao(m.getId(), txtDesc.getText()); fecharSemAviso(stage); atualizarListasAtuais();
        });
        HBox boxBotao = new HBox(btnSalvar); boxBotao.setAlignment(Pos.CENTER); grid.add(boxBotao, 0, 3, 2, 1);
        forcarFocoEEnter(stage, btnSalvar); stage.showAndWait();
    }

    // ==========================================
    // UTILITÁRIOS E MODAIS DE SISTEMA
    // ==========================================
    private void fecharSemAviso(Stage stage) {
        stage.setOnCloseRequest(null);
        stage.close();
    }

    private void aplicarMascaraNumerica(TextField txt, boolean permiteDecimal) {
        txt.textProperty().addListener((obs, velho, novo) -> {
            if (novo == null || novo.isEmpty()) return;
            boolean valido = true; int contadorPonto = 0;
            for (int i = 0; i < novo.length(); i++) {
                char c = novo.charAt(i);
                if (c == '.' || c == ',') { contadorPonto++; if (!permiteDecimal || contadorPonto > 1) { valido = false; break; } }
                else if (!Character.isDigit(c)) { valido = false; break; }
            }
            if (!valido) txt.setText(velho);
        });
    }

    private TextField criarInputCpf() {
        TextField txt = criarInput("000.000.000-00");
        txt.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;
            String digits = newVal.replaceAll("\\D", "");
            if (digits.length() > 11) digits = digits.substring(0, 11);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digits.length(); i++) {
                if (i == 3 || i == 6) sb.append(".");
                if (i == 9) sb.append("-");
                sb.append(digits.charAt(i));
            }
            Platform.runLater(() -> { txt.setText(sb.toString()); txt.positionCaret(sb.length()); });
        });
        return txt;
    }

    // A JANELA BASE (Sem scroll global para manter proporções)
    private Stage criarJanela(String titulo, boolean isCadastro, boolean mostrarX) {
        Stage stage = new Stage(); stage.initOwner(Main.getStage()); stage.initModality(Modality.WINDOW_MODAL); stage.initStyle(StageStyle.UNDECORATED);
        VBox root = new VBox(); root.setStyle("-fx-background-color: #06090e; -fx-border-color: #06090e; -fx-border-width: 1px;");

        HBox header = new HBox(); header.setStyle("-fx-background-color: #06090e; -fx-padding: 10px 16px;"); header.setAlignment(Pos.CENTER_LEFT);
        Label lblTitulo = new Label(titulo); lblTitulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");
        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(lblTitulo, spacer);

        Button btnFechar = new Button("X");
        btnFechar.setStyle("-fx-background-color: transparent; -fx-text-fill: #ef4444; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 16px;");

        if (mostrarX) {
            btnFechar.setOnAction(e -> { if (confirmarSaidaJanela(isCadastro, stage)) stage.close(); });
        } else {
            btnFechar.setOpacity(0);
            btnFechar.setDisable(true);
        }
        header.getChildren().add(btnFechar);

        final double[] xOffset = new double[1]; final double[] yOffset = new double[1];
        header.setOnMousePressed(event -> { xOffset[0] = event.getSceneX(); yOffset[0] = event.getSceneY(); });
        header.setOnMouseDragged(event -> { stage.setX(event.getScreenX() - xOffset[0]); stage.setY(event.getScreenY() - yOffset[0]); });

        GridPane grid = new GridPane(); grid.setHgap(16); grid.setVgap(16); grid.setStyle("-fx-padding: 32;");

        root.getChildren().addAll(header, grid);
        Scene scene = new Scene(root); stage.setScene(scene); stage.setUserData(grid);
        return stage;
    }

    private boolean confirmarSaidaJanela(boolean isCadastro, Stage parentStage) {
        final boolean[] confirmado = {false};
        Stage stage = new Stage(); stage.initOwner(parentStage); stage.initModality(Modality.WINDOW_MODAL); stage.initStyle(StageStyle.UNDECORATED);

        VBox root = new VBox(16);
        root.setStyle("-fx-background-color: #06090e; -fx-border-color: #141923; -fx-border-width: 1px; -fx-padding: 32;");
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(450, 220);
        root.setMinSize(450, 220);

        String txtMsg = isCadastro ? "Tem certeza que deseja sair?\nTodas as informações inseridas serão apagadas." : "Tem certeza que deseja sair?\nTodas as informações alteradas serão apagadas.";
        Label lblMsg = new Label(txtMsg); lblMsg.setStyle("-fx-text-fill: #f8fafc; -fx-font-size: 15px;"); lblMsg.setWrapText(true); lblMsg.setPrefWidth(380);

        Button btnConfirmar = criarBotao("Confirmar", "-fx-background-color: #ef4444; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand; -fx-background-radius: 6;", () -> { confirmado[0] = true; stage.close(); });
        Button btnCancelar = criarBotao("Cancelar", "-fx-background-color: transparent; -fx-border-color: #94a3b8; -fx-text-fill: #94a3b8; -fx-border-radius: 6; -fx-cursor: hand; -fx-padding: 8 16;", stage::close); btnCancelar.setCancelButton(true);
        HBox boxBotoes = new HBox(16, btnCancelar, btnConfirmar); boxBotoes.setAlignment(Pos.CENTER);

        root.getChildren().addAll(lblMsg, new Region(), boxBotoes);
        stage.setScene(new Scene(root)); stage.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), btnConfirmar::fire); stage.showAndWait(); return confirmado[0];
    }

    private void mostrarJanelaDetalhe(String titulo, VBox conteudo) {
        Stage stage = criarJanela(titulo, false, false); GridPane grid = (GridPane) stage.getUserData();
        Button btnFechar = new Button("Fechar"); btnFechar.setStyle("-fx-background-color: transparent; -fx-border-color: #94a3b8; -fx-text-fill: #94a3b8; -fx-border-radius: 6; -fx-cursor: hand; -fx-padding: 8 16;"); btnFechar.setOnAction(e -> stage.close());
        HBox boxFechar = new HBox(btnFechar); boxFechar.setAlignment(Pos.CENTER_LEFT);

        ScrollPane scroll = new ScrollPane(conteudo);
        scroll.setFitToWidth(true); scroll.setPrefViewportWidth(420); scroll.setPrefViewportHeight(380); scroll.setStyle("-fx-background-color: transparent; -fx-background: #06090e;");

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

    private HBox criarLinhaAcoes(Button esquerda, Button centro, Button direita) {
        HBox linha = new HBox(8); linha.setAlignment(Pos.CENTER_LEFT); Region esp = new Region(); HBox.setHgrow(esp, Priority.ALWAYS);
        if(esquerda != null) linha.getChildren().add(esquerda);
        linha.getChildren().add(esp);
        if(centro != null) linha.getChildren().add(centro);
        if(direita != null) linha.getChildren().add(direita);
        return linha;
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
        List<Integer> ids = new ArrayList<>();
        for (CheckBox cb : checkboxes) { if (cb.isSelected()) { ids.add((Integer) cb.getUserData()); } }
        return ids;
    }

    private boolean confirmarExclusao() {
        final boolean[] confirmado = {false};
        Stage stage = new Stage(); stage.initOwner(Main.getStage()); stage.initModality(Modality.WINDOW_MODAL); stage.initStyle(StageStyle.UNDECORATED);

        VBox root = new VBox(16);
        root.setStyle("-fx-background-color: #06090e; -fx-border-color: #141923; -fx-border-width: 1px; -fx-padding: 32;");
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(450, 220);
        root.setMinSize(450, 220);

        Label lblMsg = new Label("Você tem certeza que deseja excluir?\nQualquer informação associada a este registro será excluída permanentemente.");
        lblMsg.setStyle("-fx-text-fill: #f8fafc; -fx-font-size: 15px;"); lblMsg.setWrapText(true); lblMsg.setPrefWidth(380);

        Button btnConfirmar = criarBotao("Sim, Excluir", "-fx-background-color: #ef4444; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand; -fx-background-radius: 6;", () -> { confirmado[0] = true; stage.close(); });
        Button btnCancelar = criarBotao("Cancelar", "-fx-background-color: transparent; -fx-border-color: #94a3b8; -fx-text-fill: #94a3b8; -fx-border-radius: 6; -fx-cursor: hand; -fx-padding: 8 16;", stage::close); btnCancelar.setCancelButton(true);
        HBox boxBotoes = new HBox(16, btnCancelar, btnConfirmar); boxBotoes.setAlignment(Pos.CENTER);

        root.getChildren().addAll(lblMsg, new Region(), boxBotoes);
        stage.setScene(new Scene(root)); stage.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), btnConfirmar::fire); stage.showAndWait(); return confirmado[0];
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
        Stage stage = new Stage(); stage.initOwner(Main.getStage()); stage.initModality(Modality.WINDOW_MODAL); stage.initStyle(StageStyle.UNDECORATED);

        VBox root = new VBox(16);
        root.setStyle("-fx-background-color: #06090e; -fx-border-color: #141923; -fx-border-width: 1px; -fx-padding: 32;");
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(450, 220);
        root.setMinSize(450, 220);

        Label lblMsg = new Label(msg); lblMsg.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 15px;"); lblMsg.setWrapText(true); lblMsg.setMaxWidth(380);
        Button btnOk = criarBotao("OK Entendi", "-fx-background-color: #f1ba2a; -fx-text-fill: #04070d; -fx-font-weight: bold; -fx-padding: 8 16; -fx-cursor: hand; -fx-background-radius: 6;", stage::close);

        root.getChildren().addAll(lblMsg, new Region(), btnOk);
        stage.setScene(new Scene(root)); stage.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), btnOk::fire); Platform.runLater(btnOk::requestFocus); stage.showAndWait();
    }
}