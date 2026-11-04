package com.example.academia.Dono.Controller;

import com.example.academia.ClassesUniversais.*;
import com.example.academia.Main;
import com.example.academia.Dono.Dono;
import com.example.academia.Gerente.Gerente;
import com.example.academia.Treinador.Treinador;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DonoController {

    @FXML private Button btnNavGeral, btnNavAlunos, btnNavPlanos, btnNavTreinadores, btnNavModalidades, btnNavAdmin, btnNavCustos;
    @FXML private ScrollPane viewDashboard;
    @FXML private VBox viewAlunos, viewPlanos, viewTreinadores, viewModalidades, viewAdmin, viewCustos;
    @FXML private FlowPane containerAlunos, containerPlanos, containerTreinadores, containerModalidades, containerAdmin, containerCustos;
    @FXML private Label lblTotalAlunos, lblTotalFunc, lblRenda, lblCusto, lblLucro;
    @FXML private FlowPane boxRegistrosRecentes;

    private Dono dono;

    private final String cardStyle = "-fx-background-color: #06090e; -fx-border-color: #141923; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 16; -fx-min-width: 280; -fx-max-width: 320;";
    private final String btnDangerStyle = "-fx-background-color: transparent; -fx-border-color: #ef4444; -fx-text-fill: #ef4444; -fx-border-radius: 6; -fx-cursor: hand; -fx-padding: 8;";
    // Novos estilos (mesma família do btnDangerStyle) para os botões Acessar / Editar / + Modalidades
    private final String btnAcessarStyle = "-fx-background-color: transparent; -fx-border-color: #f1ba2a; -fx-text-fill: #f1ba2a; -fx-border-radius: 6; -fx-cursor: hand; -fx-padding: 8;";
    private final String btnEditarStyle = "-fx-background-color: transparent; -fx-border-color: #94a3b8; -fx-text-fill: #94a3b8; -fx-border-radius: 6; -fx-cursor: hand; -fx-padding: 8;";
    private final String btnSalvarStyle = "-fx-background-color: #f1ba2a; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand; -fx-background-radius: 6;";

    @FXML
    private void initialize() {
        dono = LoginController.obterDono();   // o mesmo Dono que fez login
        mostrarVisaoGeral();
    }

    // ==========================================
    // NAVEGAÇÃO E ATUALIZAÇÃO
    // ==========================================
    @FXML private void mostrarVisaoGeral() { alternarTela(viewDashboard, btnNavGeral); atualizarDashboard(); }
    @FXML private void mostrarTelaAlunos() { alternarTela(viewAlunos, btnNavAlunos); renderizarAlunos(); }
    @FXML private void mostrarTelaPlanos() { alternarTela(viewPlanos, btnNavPlanos); renderizarPlanos(); }
    @FXML private void mostrarTelaTreinadores() { alternarTela(viewTreinadores, btnNavTreinadores); renderizarTreinadores(); }
    @FXML private void mostrarTelaModalidades() { alternarTela(viewModalidades, btnNavModalidades); renderizarModalidades(); }
    @FXML private void mostrarTelaAdmin() { alternarTela(viewAdmin, btnNavAdmin); renderizarAdmin(); }
    @FXML private void sair() { Main.mostrarLogin(); }
    @FXML private void mostrarTelaCustos() { alternarTela(viewCustos, btnNavCustos); renderizarCustos(); }

    private void alternarTela(javafx.scene.Node telaAtiva, Button btnAtivo) {
        viewDashboard.setVisible(false); viewAlunos.setVisible(false); viewPlanos.setVisible(false);
        viewTreinadores.setVisible(false); viewModalidades.setVisible(false); viewAdmin.setVisible(false);
        viewCustos.setVisible(false);

        btnNavGeral.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");
        btnNavAlunos.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");
        btnNavPlanos.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");
        btnNavTreinadores.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");
        btnNavModalidades.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");
        btnNavAdmin.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");
        btnNavCustos.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8;");

        telaAtiva.setVisible(true);
        btnAtivo.setStyle("-fx-background-color: #f1ba2a; -fx-text-fill: #04070d; -fx-font-weight: bold;");
    }

    // ==========================================
    // RENDERIZAÇÃO DE CARTÕES
    // ==========================================
    private void renderizarAlunos() {
        containerAlunos.getChildren().clear();
        for (Aluno a : BancoDeDados.getInstance().getAlunos().values()) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(
                    criarTextoDestaque(a.getNome(), "#f8fafc", 18),
                    criarTextoNormal("CPF: " + a.getCpf()),
                    criarTextoNormal("Intensidade: " + a.getIntensidade()),
                    criarTextoNormal("Idade: " + a.getIdade() + " anos")
            );
            centralizarCard(card);

            Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheAluno(a));
            Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoAluno(a));
            Button btnRemover = criarBotao("Expulsar", btnDangerStyle, () -> {
                if(confirmarExclusao()) { dono.ExpulsarAluno(a.getId()); renderizarAlunos(); atualizarDashboard(); }
            });
            card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar));
            containerAlunos.getChildren().add(card);
        }
    }

    private void renderizarPlanos() {
        containerPlanos.getChildren().clear();
        for (Plano p : BancoDeDados.getInstance().getPlano().values()) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(
                    criarTextoDestaque(p.getNome(), "#f1ba2a", 18),
                    criarTextoNormal(String.format("Valor: R$ %.2f", p.getValor())),
                    criarTextoNormal("Validade: " + p.getMesesValidade() + " meses"),
                    criarTextoNormal("Modalidades: " + p.getModalidade().size()),
                    criarTextoNormal(p.getDescricao())
            );
            centralizarCard(card);

            Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalhePlano(p));
            Button btnModalidades = criarBotao("+ Modalidades", btnEditarStyle, () -> abrirAdicionarModalidades(p));
            Button btnRemover = criarBotao("Apagar", btnDangerStyle, () -> {
                if(confirmarExclusao()) { dono.removerPlano(p.getId()); renderizarPlanos(); }
            });
            card.getChildren().add(criarLinhaAcoes(btnRemover, btnModalidades, btnAcessar));
            containerPlanos.getChildren().add(card);
        }
    }

    private void renderizarTreinadores() {
        containerTreinadores.getChildren().clear();
        for (Treinador t : BancoDeDados.getInstance().getTreinadores().values()) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(
                    criarTextoDestaque(t.getNome(), "#f8fafc", 18),
                    criarTextoNormal("Especialidade: " + t.getEspecialidade()),
                    criarTextoNormal("Modalidades: " + dono.modalidadesDoTreinador(t.getId()).size()),
                    criarTextoNormal(String.format("Salário: R$ %.2f", t.getSalario()))
            );
            centralizarCard(card);

            Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheTreinador(t));
            Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoTreinador(t));
            Button btnRemover = criarBotao("Demitir", btnDangerStyle, () -> {
                if(confirmarExclusao()) { dono.demitirTreinadorCompleto(t.getId()); renderizarTreinadores(); atualizarDashboard(); }
            });
            card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar));
            containerTreinadores.getChildren().add(card);
        }
    }

    private void renderizarModalidades() {
        containerModalidades.getChildren().clear();
        for (Modalidade m : BancoDeDados.getInstance().getModalidade().values()) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(
                    criarTextoDestaque(m.getNome(), "#f8fafc", 18),
                    criarTextoNormal(m.getDescricao()),
                    criarTextoNormal("Treinadores Locados: " + m.getTreinadores().size())
            );
            centralizarCard(card);

            Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheModalidade(m));
            Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoModalidade(m));
            Button btnRemover = criarBotao("Apagar", btnDangerStyle, () -> {
                if(confirmarExclusao()) { BancoDeDados.getInstance().modalidades.remove(m.getId()); renderizarModalidades(); }
            });
            card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar));
            containerModalidades.getChildren().add(card);
        }
    }

    private void renderizarAdmin() {
        containerAdmin.getChildren().clear();
        for (Administrativo a : BancoDeDados.getInstance().getAdministrativos().values()) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(
                    criarTextoDestaque(a.getNome(), "#f8fafc", 18),
                    criarTextoNormal("CPF: " + a.getCpf()),
                    criarTextoNormal(String.format("Salário: R$ %.2f", a.getSalario()))
            );
            centralizarCard(card);

            Button btnAcessar = criarBotao("Acessar", btnAcessarStyle, () -> abrirDetalheAdministrativo(a));
            Button btnEditar = criarBotao("Editar", btnEditarStyle, () -> abrirEdicaoAdministrativo(a));
            Button btnRemover = criarBotao("Demitir", btnDangerStyle, () -> {
                if(confirmarExclusao()) { dono.demitirAdministrativo(a.getId()); renderizarAdmin(); atualizarDashboard(); }
            });
            card.getChildren().add(criarLinhaAcoes(btnRemover, btnEditar, btnAcessar));
            containerAdmin.getChildren().add(card);
        }
    }

    // TELA CUSTOS (mais recentes primeiro)
    private void renderizarCustos() {
        containerCustos.getChildren().clear();
        List<Custo> custos = new ArrayList<>(BancoDeDados.getInstance().getCustos().values());
        custos.sort((c1, c2) -> c2.getDataInstant().compareTo(c1.getDataInstant()));

        for (Custo c : custos) {
            VBox card = new VBox(8); card.setStyle(cardStyle);
            card.getChildren().addAll(
                    criarTextoDestaque(c.getNome(), "#f8fafc", 18),
                    criarTextoDestaque(String.format("R$ %.2f", c.getValor()), "#ef4444", 16),
                    criarTextoNormal("Data: " + c.getData())
            );
            centralizarCard(card);

            Button btnRemover = criarBotao("Apagar", btnDangerStyle, () -> {
                if(confirmarExclusao()) { dono.removerCusto(c.getId()); renderizarCustos(); atualizarDashboard(); }
            });
            card.getChildren().add(criarLinhaAcoes(btnRemover));
            containerCustos.getChildren().add(card);
        }
    }

    @FXML
    private void atualizarDashboard() {
        int qtdAlunos = BancoDeDados.getInstance().getAlunos().size();
        int qtdFunc = BancoDeDados.getInstance().getTreinadores().size() + BancoDeDados.getInstance().getAdministrativos().size();

        lblTotalAlunos.setText(String.valueOf(qtdAlunos));
        lblTotalFunc.setText(String.valueOf(qtdFunc));

        // Financeiro
        BancoDeDados.getInstance().grafico.somarDespesas();
        double r = BancoDeDados.getInstance().grafico.getRenda();
        double c = BancoDeDados.getInstance().grafico.getCusto();
        double t = BancoDeDados.getInstance().grafico.getTotal();

        lblRenda.setText(String.format("R$ %.2f", r));
        lblCusto.setText(String.format("R$ %.2f", c));
        lblLucro.setText(String.format("R$ %.2f", t));
        lblLucro.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + (t >= 0 ? "#22c55e" : "#ef4444") + ";");

        boxRegistrosRecentes.getChildren().clear();

        // Gerente
        Optional<Gerente> ger = BancoDeDados.getInstance().gerente;
        if(ger.isPresent()) {
            VBox cGerente = new VBox(5); cGerente.setStyle(cardStyle);
            cGerente.getChildren().addAll(
                    criarTextoDestaque(ger.get().getNome(), "#f1ba2a", 16),
                    criarTextoNormal("Gerente Geral"),
                    criarTextoNormal(ger.get().getCpf()),
                    criarTextoNormal(String.format("Salário: R$ %.2f", ger.get().getSalario()))
            );
            centralizarCard(cGerente);
            boxRegistrosRecentes.getChildren().add(cGerente);
        }

        // 3 Alunos, 3 Treinadores, 3 Admins
        BancoDeDados.getInstance().getAlunos().values().stream().limit(3).forEach(a -> {
            VBox card = new VBox(5); card.setStyle(cardStyle);
            card.getChildren().addAll(criarTextoDestaque(a.getNome(), "#3b82f6", 16), criarTextoNormal("Aluno - " + a.getIntensidade()));
            centralizarCard(card);
            boxRegistrosRecentes.getChildren().add(card);
        });

        BancoDeDados.getInstance().getTreinadores().values().stream().limit(3).forEach(tr -> {
            VBox card = new VBox(5); card.setStyle(cardStyle);
            card.getChildren().addAll(
                    criarTextoDestaque(tr.getNome(), "#10b981", 16),
                    criarTextoNormal("Treinador - " + tr.getEspecialidade()),
                    criarTextoNormal(String.format("Salário: R$ %.2f", tr.getSalario()))
            );
            centralizarCard(card);
            boxRegistrosRecentes.getChildren().add(card);
        });

        BancoDeDados.getInstance().getAdministrativos().values().stream().limit(3).forEach(adm -> {
            VBox card = new VBox(5); card.setStyle(cardStyle);
            card.getChildren().addAll(
                    criarTextoDestaque(adm.getNome(), "#8b5cf6", 16),
                    criarTextoNormal("Administrativo"),
                    criarTextoNormal(String.format("Salário: R$ %.2f", adm.getSalario()))
            );
            centralizarCard(card);
            boxRegistrosRecentes.getChildren().add(card);
        });
    }

    private Label criarTextoDestaque(String texto, String cor, int tamanho) {
        Label l = new Label(texto);
        l.setStyle("-fx-text-fill: " + cor + "; -fx-font-weight: bold; -fx-font-size: " + tamanho + "px;");
        return l;
    }

    private Label criarTextoNormal(String texto) {
        Label l = new Label(texto);
        l.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 13px;");
        return l;
    }

    // ==========================================
    // OPERAÇÕES DE CADASTRO COM JANELAS CUSTOMIZADAS
    // ==========================================
    @FXML
    private void cadastrarAluno() {
        if (BancoDeDados.getInstance().getPlano().isEmpty() || BancoDeDados.getInstance().getTreinadores().isEmpty()) {
            mostrarAlertaErro("Cadastre pelo menos 1 Plano e 1 Treinador antes de matricular alunos."); return;
        }

        Stage stage = criarJanela("Matricular Novo Aluno");
        GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Ex: Kauan Silva");
        TextField txtSenha = criarInput("Mín. 8 letras, 1 Maiúscula, 1 Número, 1 Especial (@)");
        TextField txtCpf = criarInput("Formato exato: 000.000.000-00");
        TextField txtIdade = criarInput("Apenas números (Ex: 24)");
        TextField txtAltura = criarInput("Ex: 1.75");
        TextField txtPeso = criarInput("Ex: 80.5");

        ComboBox<Plano> cbPlano = new ComboBox<>();
        cbPlano.getItems().addAll(BancoDeDados.getInstance().getPlano().values());
        cbPlano.setConverter(new StringConverter<>() {
            @Override public String toString(Plano p) { return p == null ? "" : p.getNome(); }
            @Override public Plano fromString(String s) { return null; }
        });

        ComboBox<Treinador> cbTreinador = new ComboBox<>();
        cbTreinador.getItems().addAll(BancoDeDados.getInstance().getTreinadores().values());
        cbTreinador.setConverter(new StringConverter<>() {
            @Override public String toString(Treinador t) { return t == null ? "" : t.getNome(); }
            @Override public Treinador fromString(String s) { return null; }
        });

        ComboBox<String> cbIntensidade = new ComboBox<>();
        cbIntensidade.getItems().addAll("Leve", "Médio", "Intenso");

        adicionarLinhaForm(grid, 0, "Nome Completo:", txtNome);
        adicionarLinhaForm(grid, 1, "Senha (Obrigatória):", txtSenha);
        adicionarLinhaForm(grid, 2, "CPF (Com Pontos e Traço):", txtCpf);
        adicionarLinhaForm(grid, 3, "Idade:", txtIdade);
        adicionarLinhaForm(grid, 4, "Altura (m):", txtAltura);
        adicionarLinhaForm(grid, 5, "Peso (kg):", txtPeso);
        adicionarLinhaForm(grid, 6, "Plano:", cbPlano);
        adicionarLinhaForm(grid, 7, "Treinador:", cbTreinador);
        adicionarLinhaForm(grid, 8, "Intensidade:", cbIntensidade);

        Button btnSalvar = new Button("Concluir Matrícula");
        btnSalvar.setStyle("-fx-background-color: #f1ba2a; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand; -fx-background-radius: 6;");

        btnSalvar.setOnAction(e -> {
            try {
                if (cbPlano.getValue() == null || cbTreinador.getValue() == null || cbIntensidade.getValue() == null) {
                    mostrarAlertaErro("Selecione os campos nas caixas de opção (Plano, Treinador, Intensidade)."); return;
                }
                double altura = Double.parseDouble(txtAltura.getText().replace(",", "."));
                double peso = Double.parseDouble(txtPeso.getText().replace(",", "."));
                int idade = Integer.parseInt(txtIdade.getText());

                boolean sucesso = dono.cadastrarAluno(
                        txtNome.getText(), txtSenha.getText(), txtCpf.getText(),
                        idade, altura, peso,
                        cbPlano.getValue(), cbIntensidade.getValue(), cbTreinador.getValue()
                );

                if (sucesso) { stage.close(); renderizarAlunos(); atualizarDashboard(); }
                else mostrarAlertaErro("Regras Violadas!\n1. O CPF deve estar em '000.000.000-00'.\n2. Senha precisa de maiúscula, número e símbolo (@).\n3. O aluno deve ter mais de 12 anos.");
            } catch (Exception ex) { mostrarAlertaErro("Preencha Idade, Altura e Peso informando apenas números (Ex: 1.75)."); }
        });

        grid.add(btnSalvar, 1, 9);
        stage.showAndWait();
    }

    @FXML
    private void cadastrarPlano() {
        if (BancoDeDados.getInstance().getModalidade().isEmpty()) { mostrarAlertaErro("Crie uma Modalidade antes."); return; }

        Stage stage = criarJanela("Novo Plano de Acesso");
        GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Ex: VIP Anual"); TextField txtDesc = criarInput("Ex: Acesso Total");
        TextField txtValor = criarInput("Ex: 150.00"); TextField txtValidade = criarInput("Meses. Ex: 12"); TextField txtJuros = criarInput("Ex: 5.0");

        // Agora é possível marcar UMA OU MAIS modalidades
        List<CheckBox> checkboxes = new ArrayList<>();
        ScrollPane listaModalidades = criarListaModalidades(checkboxes, null);

        adicionarLinhaForm(grid, 0, "Nome do Plano:", txtNome);
        adicionarLinhaForm(grid, 1, "Descrição:", txtDesc);
        adicionarLinhaForm(grid, 2, "Valor Mensal (R$):", txtValor);
        adicionarLinhaForm(grid, 3, "Validade (Meses):", txtValidade);
        adicionarLinhaForm(grid, 4, "Juros Atraso (%):", txtJuros);
        adicionarLinhaForm(grid, 5, "Modalidades (1 ou mais):", listaModalidades);

        Button btnSalvar = new Button("Salvar Plano");
        btnSalvar.setStyle(btnSalvarStyle);

        btnSalvar.setOnAction(e -> {
            try {
                List<Integer> idsModalidades = idsSelecionados(checkboxes);
                if (idsModalidades.isEmpty()) { mostrarAlertaErro("Selecione ao menos uma modalidade."); return; }
                double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
                double juros = Double.parseDouble(txtJuros.getText().replace(",", "."));
                boolean sucesso = dono.cadastrarPlano(txtNome.getText(), txtDesc.getText(), valor, idsModalidades, Integer.parseInt(txtValidade.getText()), juros);
                if(sucesso) { stage.close(); renderizarPlanos(); }
                else mostrarAlertaErro("Erro ao cadastrar plano. Verifique os dados.");
            } catch (Exception ex) { mostrarAlertaErro("Preencha Valor, Validade e Juros apenas com números."); }
        });

        grid.add(btnSalvar, 1, 6);
        stage.showAndWait();
    }

    @FXML
    private void cadastrarModalidade() {
        Stage stage = criarJanela("Nova Modalidade");
        GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Ex: Musculação"); TextField txtDesc = criarInput("Ex: Área de peso livre");
        adicionarLinhaForm(grid, 0, "Nome:", txtNome); adicionarLinhaForm(grid, 1, "Descrição:", txtDesc);

        Button btnSalvar = new Button("Salvar Modalidade");
        btnSalvar.setStyle("-fx-background-color: #f1ba2a; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand; -fx-background-radius: 6;");

        btnSalvar.setOnAction(e -> {
            dono.escolherModalidade(txtNome.getText(), txtDesc.getText());
            stage.close(); renderizarModalidades();
        });

        grid.add(btnSalvar, 1, 2);
        stage.showAndWait();
    }

    @FXML
    private void cadastrarTreinador() {
        if (BancoDeDados.getInstance().getModalidade().isEmpty()) { mostrarAlertaErro("Crie uma Modalidade antes."); return; }

        Stage stage = criarJanela("Novo Treinador");
        GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Nome Completo"); TextField txtSenha = criarInput("Mín. 8, 1 Maiúscula, 1 Número, 1 Especial (@)");
        TextField txtCpf = criarInput("000.000.000-00"); TextField txtSalario = criarInput("Ex: 3500.00"); TextField txtEsp = criarInput("Ex: Hipertrofia");

        // Agora o treinador pode ser cadastrado em UMA OU MAIS modalidades (igual ao Novo Plano)
        List<CheckBox> checkboxes = new ArrayList<>();
        ScrollPane listaModalidades = criarListaModalidades(checkboxes, null);

        adicionarLinhaForm(grid, 0, "Nome:", txtNome); adicionarLinhaForm(grid, 1, "Senha (Estrita):", txtSenha);
        adicionarLinhaForm(grid, 2, "CPF (Com Pontos):", txtCpf); adicionarLinhaForm(grid, 3, "Salário (R$):", txtSalario);
        adicionarLinhaForm(grid, 4, "Especialidade:", txtEsp); adicionarLinhaForm(grid, 5, "Modalidades (1 ou mais):", listaModalidades);

        Button btnSalvar = new Button("Contratar");
        btnSalvar.setStyle("-fx-background-color: #f1ba2a; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand; -fx-background-radius: 6;");

        btnSalvar.setOnAction(e -> {
            try {
                List<Integer> idsModalidades = idsSelecionados(checkboxes);
                if (idsModalidades.isEmpty()) { mostrarAlertaErro("Selecione ao menos uma modalidade."); return; }
                double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                boolean sucesso = dono.CadastrarTreinador(idsModalidades, txtNome.getText(), txtSenha.getText(), txtCpf.getText(), salario, txtEsp.getText());
                if(sucesso) { stage.close(); renderizarTreinadores(); atualizarDashboard(); }
                else mostrarAlertaErro("Erro! Verifique as regras exatas de CPF (com pontos e traço) e Senha.");
            } catch (Exception ex) { mostrarAlertaErro("Verifique os números do salário."); }
        });

        grid.add(btnSalvar, 1, 6);
        stage.showAndWait();
    }

    @FXML
    private void cadastrarAdmin() {
        Stage stage = criarJanela("Novo Administrativo");
        GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Nome Completo"); TextField txtSenha = criarInput("Mín. 8, 1 Maiúscula, 1 Número, 1 Especial (@)");
        TextField txtCpf = criarInput("000.000.000-00"); TextField txtSalario = criarInput("Ex: 2500.00");

        adicionarLinhaForm(grid, 0, "Nome:", txtNome); adicionarLinhaForm(grid, 1, "Senha:", txtSenha);
        adicionarLinhaForm(grid, 2, "CPF (Com Pontos):", txtCpf); adicionarLinhaForm(grid, 3, "Salário (R$):", txtSalario);

        Button btnSalvar = new Button("Contratar");
        btnSalvar.setStyle("-fx-background-color: #f1ba2a; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand; -fx-background-radius: 6;");

        btnSalvar.setOnAction(e -> {
            try {
                double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                boolean sucesso = dono.cadastrarAdministrativo(txtNome.getText(), txtSenha.getText(), txtCpf.getText(), salario);
                if(sucesso) { stage.close(); renderizarAdmin(); atualizarDashboard(); }
                else mostrarAlertaErro("Erro! O CPF ou Senha informados não cumprem os requisitos do sistema.");
            } catch (Exception ex) { mostrarAlertaErro("Salário inválido."); }
        });

        grid.add(btnSalvar, 1, 4);
        stage.showAndWait();
    }

    @FXML
    private void cadastrarCusto() {
        Stage stage = criarJanela("Novo Custo");
        GridPane grid = (GridPane) stage.getUserData();

        TextField txtNome = criarInput("Ex: Conta de Luz"); TextField txtValor = criarInput("Ex: 1200.00");
        adicionarLinhaForm(grid, 0, "Nome do Custo:", txtNome); adicionarLinhaForm(grid, 1, "Valor (R$):", txtValor);

        Button btnSalvar = new Button("Salvar Custo");
        btnSalvar.setStyle(btnSalvarStyle);

        btnSalvar.setOnAction(e -> {
            try {
                double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
                boolean sucesso = dono.cadastrarCusto(txtNome.getText(), valor);
                if(sucesso) { stage.close(); renderizarCustos(); atualizarDashboard(); }
                else mostrarAlertaErro("Informe um nome e um valor maior que zero.");
            } catch (Exception ex) { mostrarAlertaErro("Valor inválido. Use apenas números (Ex: 1200.00)."); }
        });

        grid.add(btnSalvar, 1, 2);
        stage.showAndWait();
    }

    // ==========================================
    // "+ MODALIDADES" DO PLANO
    // ==========================================
    private void abrirAdicionarModalidades(Plano p) {
        Stage stage = criarJanela("Modalidades — " + p.getNome());
        GridPane grid = (GridPane) stage.getUserData();

        // Já começa com as modalidades atuais do plano marcadas
        List<CheckBox> checkboxes = new ArrayList<>();
        ScrollPane listaModalidades = criarListaModalidades(checkboxes, p.getModalidade().keySet());

        adicionarLinhaForm(grid, 0, "Modalidades do Plano:", listaModalidades);

        Button btnSalvar = new Button("Salvar Modalidades");
        btnSalvar.setStyle(btnSalvarStyle);

        btnSalvar.setOnAction(e -> {
            List<Integer> idsModalidades = idsSelecionados(checkboxes);
            if (dono.definirModalidadesPlano(p.getId(), idsModalidades)) { stage.close(); renderizarPlanos(); }
            else mostrarAlertaErro("O plano precisa ter ao menos uma modalidade.");
        });

        grid.add(btnSalvar, 1, 1);
        stage.showAndWait();
    }

    // ==========================================
    // ACESSAR (JANELAS DE DETALHE)
    // ==========================================
    private void abrirDetalheAluno(Aluno a) {
        VBox conteudo = novoConteudoDetalhe();
        conteudo.getChildren().addAll(
                criarSecao("Informações"),
                criarItem("ID: " + a.getId()),
                criarItem("CPF: " + a.getCpf()),
                criarItem("Idade: " + a.getIdade() + " anos"),
                criarItem("Altura: " + a.getAltura() + " m"),
                criarItem("Peso: " + a.getPeso() + " kg"),
                criarItem("Intensidade: " + a.getIntensidade()),
                criarSecao("Planos Assinados")
        );
        if (a.getListaAssinaturaPlano().isEmpty()) {
            conteudo.getChildren().add(criarTextoNormal("Nenhum plano assinado."));
        } else {
            for (AssinaturaPlano ap : a.getListaAssinaturaPlano().values()) {
                Plano p = ap.getPlano();
                conteudo.getChildren().add(criarItem("• " + p.getNome() + " — " + String.format("R$ %.2f", p.getValor())));
                for (Modalidade m : p.getModalidade().values()) {
                    conteudo.getChildren().add(criarTextoNormal("      - " + m.getNome()));
                }
            }
        }
        conteudo.getChildren().add(criarSecao("Treinadores"));
        if (a.getTreinadores().isEmpty()) {
            conteudo.getChildren().add(criarTextoNormal("Nenhum treinador vinculado."));
        } else {
            for (Treinador t : a.getTreinadores().values()) {
                conteudo.getChildren().add(criarItem("• " + t.getNome() + " — " + t.getEspecialidade()));
            }
        }
        mostrarJanelaDetalhe("Aluno: " + a.getNome(), conteudo);
    }

    private void abrirDetalhePlano(Plano p) {
        VBox conteudo = novoConteudoDetalhe();
        conteudo.getChildren().addAll(
                criarSecao("Informações"),
                criarItem("ID: " + p.getId()),
                criarItem(String.format("Valor: R$ %.2f", p.getValor())),
                criarItem("Validade: " + p.getMesesValidade() + " meses"),
                criarItem(String.format("Juros por atraso: %.2f%%", p.getJuros())),
                criarItem("Descrição: " + p.getDescricao()),
                criarSecao("Modalidades do Plano")
        );
        if (p.getModalidade().isEmpty()) {
            conteudo.getChildren().add(criarTextoNormal("Nenhuma modalidade vinculada."));
        } else {
            for (Modalidade m : p.getModalidade().values()) {
                conteudo.getChildren().add(criarItem("• " + m.getNome()));
            }
        }
        conteudo.getChildren().add(criarSecao("Alunos que Assinaram"));
        List<Aluno> alunos = dono.alunosDoPlano(p.getId());
        if (alunos.isEmpty()) {
            conteudo.getChildren().add(criarTextoNormal("Nenhum aluno assinou este plano."));
        } else {
            for (Aluno a : alunos) {
                conteudo.getChildren().add(criarItem("• " + a.getNome() + " (ID " + a.getId() + ")"));
            }
        }
        mostrarJanelaDetalhe("Plano: " + p.getNome(), conteudo);
    }

    private void abrirDetalheTreinador(Treinador t) {
        VBox conteudo = novoConteudoDetalhe();
        conteudo.getChildren().addAll(
                criarSecao("Informações"),
                criarItem("ID: " + t.getId()),
                criarItem("CPF: " + t.getCpf()),
                criarItem("Especialidade: " + t.getEspecialidade()),
                criarItem(String.format("Salário: R$ %.2f", t.getSalario())),
                criarSecao("Modalidades que Leciona")
        );
        List<Modalidade> modalidades = dono.modalidadesDoTreinador(t.getId());
        if (modalidades.isEmpty()) {
            conteudo.getChildren().add(criarTextoNormal("Nenhuma modalidade vinculada."));
        } else {
            for (Modalidade m : modalidades) {
                conteudo.getChildren().add(criarItem("• " + m.getNome()));
            }
        }
        conteudo.getChildren().add(criarSecao("Alunos Atendidos"));
        List<Aluno> alunos = dono.alunosDoTreinador(t.getId());
        if (alunos.isEmpty()) {
            conteudo.getChildren().add(criarTextoNormal("Nenhum aluno atendido."));
        } else {
            for (Aluno a : alunos) {
                conteudo.getChildren().add(criarItem("• " + a.getNome() + " (ID " + a.getId() + ")"));
            }
        }
        mostrarJanelaDetalhe("Treinador: " + t.getNome(), conteudo);
    }

    private void abrirDetalheModalidade(Modalidade m) {
        VBox conteudo = novoConteudoDetalhe();
        conteudo.getChildren().addAll(
                criarSecao("Descrição"),
                criarItem(m.getDescricao()),
                criarSecao("Treinadores")
        );
        if (m.getTreinadores().isEmpty()) {
            conteudo.getChildren().add(criarTextoNormal("Nenhum treinador cadastrado."));
        } else {
            for (Treinador t : m.getTreinadores().values()) {
                conteudo.getChildren().add(criarItem("• " + t.getNome() + " — " + t.getEspecialidade()));
            }
        }
        conteudo.getChildren().add(criarSecao("Alunos"));
        List<Aluno> alunos = dono.alunosDaModalidade(m.getId());
        if (alunos.isEmpty()) {
            conteudo.getChildren().add(criarTextoNormal("Nenhum aluno matriculado."));
        } else {
            for (Aluno a : alunos) {
                conteudo.getChildren().add(criarItem("• " + a.getNome() + " (ID " + a.getId() + ")"));
            }
        }
        mostrarJanelaDetalhe("Modalidade: " + m.getNome(), conteudo);
    }

    private void abrirDetalheAdministrativo(Administrativo a) {
        VBox conteudo = novoConteudoDetalhe();
        conteudo.getChildren().addAll(
                criarSecao("Informações"),
                criarItem("ID: " + a.getId()),
                criarItem("Nome: " + a.getNome()),
                criarItem("CPF: " + a.getCpf()),
                criarItem(String.format("Salário: R$ %.2f", a.getSalario()))
        );
        mostrarJanelaDetalhe("Administrativo: " + a.getNome(), conteudo);
    }

    // ==========================================
    // EDITAR
    // ==========================================
    private void abrirEdicaoAluno(Aluno a) {
        Stage stage = criarJanela("Editar Aluno — " + a.getNome());
        GridPane grid = (GridPane) stage.getUserData();

        TextField txtAltura = criarInput("Ex: 1.75"); txtAltura.setText(String.valueOf(a.getAltura()));
        TextField txtPeso = criarInput("Ex: 80.5"); txtPeso.setText(String.valueOf(a.getPeso()));
        ComboBox<String> cbIntensidade = new ComboBox<>();
        cbIntensidade.getItems().addAll("Leve", "Médio", "Intenso");
        cbIntensidade.setValue(a.getIntensidade());

        adicionarLinhaForm(grid, 0, "Nome (não editável):", criarTextoDestaque(a.getNome(), "#f8fafc", 14));
        adicionarLinhaForm(grid, 1, "Altura (m):", txtAltura);
        adicionarLinhaForm(grid, 2, "Peso (kg):", txtPeso);
        adicionarLinhaForm(grid, 3, "Intensidade:", cbIntensidade);

        Button btnSalvar = new Button("Salvar Alterações");
        btnSalvar.setStyle(btnSalvarStyle);

        btnSalvar.setOnAction(e -> {
            double alturaAnterior = a.getAltura();
            double pesoAnterior = a.getPeso();
            try {
                double altura = Double.parseDouble(txtAltura.getText().replace(",", "."));
                double peso = Double.parseDouble(txtPeso.getText().replace(",", "."));
                if (!a.setAltura(altura)) { mostrarAlertaErro("Altura inválida (entre 0.50 m e 2.50 m)."); return; }
                if (!a.setPeso(peso)) { a.setAltura(alturaAnterior); mostrarAlertaErro("Peso inválido (entre 30 kg e 350 kg)."); return; }
                if (!a.setIntensidade(cbIntensidade.getValue())) { a.setAltura(alturaAnterior); a.setPeso(pesoAnterior); mostrarAlertaErro("Selecione uma intensidade."); return; }
                stage.close(); renderizarAlunos();
            } catch (Exception ex) { mostrarAlertaErro("Altura e Peso precisam ser números (Ex: 1.75)."); }
        });

        grid.add(btnSalvar, 1, 4);
        stage.showAndWait();
    }

    private void abrirEdicaoTreinador(Treinador t) {
        Stage stage = criarJanela("Editar Treinador — " + t.getNome());
        GridPane grid = (GridPane) stage.getUserData();

        TextField txtSalario = criarInput("Ex: 3500.00"); txtSalario.setText(String.valueOf(t.getSalario()));
        TextField txtEsp = criarInput("Ex: Hipertrofia"); txtEsp.setText(t.getEspecialidade());

        adicionarLinhaForm(grid, 0, "Nome (não editável):", criarTextoDestaque(t.getNome(), "#f8fafc", 14));
        adicionarLinhaForm(grid, 1, "Salário (R$):", txtSalario);
        adicionarLinhaForm(grid, 2, "Especialidade:", txtEsp);

        Button btnSalvar = new Button("Salvar Alterações");
        btnSalvar.setStyle(btnSalvarStyle);

        btnSalvar.setOnAction(e -> {
            try {
                double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                if (salario <= 0) { mostrarAlertaErro("O salário deve ser maior que zero."); return; }
                if (txtEsp.getText() == null || txtEsp.getText().isBlank()) { mostrarAlertaErro("Informe a especialidade."); return; }
                t.setSalario(salario);
                t.setEspecialidade(txtEsp.getText());
                stage.close(); renderizarTreinadores(); atualizarDashboard();
            } catch (Exception ex) { mostrarAlertaErro("Verifique os números do salário."); }
        });

        grid.add(btnSalvar, 1, 3);
        stage.showAndWait();
    }

    private void abrirEdicaoModalidade(Modalidade m) {
        Stage stage = criarJanela("Editar Modalidade — " + m.getNome());
        GridPane grid = (GridPane) stage.getUserData();

        TextField txtDesc = criarInput("Ex: Área de peso livre"); txtDesc.setText(m.getDescricao());

        adicionarLinhaForm(grid, 0, "Nome (não editável):", criarTextoDestaque(m.getNome(), "#f8fafc", 14));
        adicionarLinhaForm(grid, 1, "Descrição:", txtDesc);

        Button btnSalvar = new Button("Salvar Alterações");
        btnSalvar.setStyle(btnSalvarStyle);

        btnSalvar.setOnAction(e -> {
            if (dono.setDescricao(m.getId(), txtDesc.getText())) { stage.close(); renderizarModalidades(); }
            else mostrarAlertaErro("A descrição não pode ficar vazia.");
        });

        grid.add(btnSalvar, 1, 2);
        stage.showAndWait();
    }

    private void abrirEdicaoAdministrativo(Administrativo a) {
        Stage stage = criarJanela("Editar Administrativo — " + a.getNome());
        GridPane grid = (GridPane) stage.getUserData();

        TextField txtSalario = criarInput("Ex: 2500.00"); txtSalario.setText(String.valueOf(a.getSalario()));

        adicionarLinhaForm(grid, 0, "Nome (não editável):", criarTextoDestaque(a.getNome(), "#f8fafc", 14));
        adicionarLinhaForm(grid, 1, "Salário (R$):", txtSalario);

        Button btnSalvar = new Button("Salvar Alterações");
        btnSalvar.setStyle(btnSalvarStyle);

        btnSalvar.setOnAction(e -> {
            try {
                double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                if (!a.setSalario(salario)) { mostrarAlertaErro("O salário deve ser maior que zero."); return; }
                stage.close(); renderizarAdmin(); atualizarDashboard();
            } catch (Exception ex) { mostrarAlertaErro("Salário inválido."); }
        });

        grid.add(btnSalvar, 1, 2);
        stage.showAndWait();
    }

    // ==========================================
    // ESTRUTURAS DE JANELAS (STAGES) "DARK MODE" OTIMIZADAS
    // ==========================================
    private Stage criarJanela(String titulo) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        VBox root = new VBox();
        // Garantindo que a borda seja da mesma cor que o fundo e o titulo branco
        root.setStyle("-fx-background-color: #06090e; -fx-border-color: #06090e; -fx-border-width: 1px;");

        HBox header = new HBox();
        header.setStyle("-fx-background-color: #06090e; -fx-padding: 10px 16px;");
        header.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnFechar = new Button("X");
        btnFechar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 14px;");
        btnFechar.setOnAction(e -> stage.close());

        header.getChildren().addAll(lblTitulo, spacer, btnFechar);

        final double[] xOffset = new double[1]; final double[] yOffset = new double[1];
        header.setOnMousePressed(event -> { xOffset[0] = event.getSceneX(); yOffset[0] = event.getSceneY(); });
        header.setOnMouseDragged(event -> { stage.setX(event.getScreenX() - xOffset[0]); stage.setY(event.getScreenY() - yOffset[0]); });

        GridPane grid = new GridPane();
        grid.setHgap(16); grid.setVgap(16);
        grid.setStyle("-fx-padding: 32;");

        root.getChildren().addAll(header, grid);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setUserData(grid);
        return stage;
    }

    // Janela somente leitura do "Acessar": conteúdo com rolagem + botão Fechar
    private void mostrarJanelaDetalhe(String titulo, VBox conteudo) {
        Stage stage = criarJanela(titulo);
        GridPane grid = (GridPane) stage.getUserData();

        ScrollPane scroll = new ScrollPane(conteudo);
        scroll.setFitToWidth(true);
        scroll.setPrefViewportWidth(420);
        scroll.setPrefViewportHeight(380);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #06090e;");

        Button btnFechar = new Button("Fechar");
        btnFechar.setStyle(btnEditarStyle);
        btnFechar.setOnAction(e -> stage.close());

        grid.add(scroll, 0, 0, 2, 1);
        grid.add(btnFechar, 1, 1);
        stage.showAndWait();
    }

    private VBox novoConteudoDetalhe() {
        VBox conteudo = new VBox(8);
        conteudo.setStyle("-fx-padding: 0 16 0 0;");
        return conteudo;
    }

    private Label criarSecao(String texto) {
        Label l = criarTextoDestaque(texto, "#f1ba2a", 15);
        l.setStyle(l.getStyle() + " -fx-padding: 8 0 0 0;");
        return l;
    }

    private Label criarItem(String texto) {
        Label l = new Label(texto);
        l.setStyle("-fx-text-fill: #f8fafc; -fx-font-size: 13px;");
        l.setWrapText(true);
        return l;
    }

    // Centraliza o card: textos e conteúdo no meio do quadrado
    private void centralizarCard(VBox card) {
        card.setAlignment(Pos.CENTER);
        for (javafx.scene.Node n : card.getChildren()) {
            if (n instanceof Label) {
                Label l = (Label) n;
                l.setMaxWidth(Double.MAX_VALUE);
                l.setAlignment(Pos.CENTER);
                l.setTextAlignment(TextAlignment.CENTER);
                l.setWrapText(true);
            }
        }
    }

    // Linha de botões do card: Apagar/Demitir à esquerda; os demais à direita (o último fica no canto direito)
    private HBox criarLinhaAcoes(Button esquerda, Button... direita) {
        HBox linha = new HBox(8);
        linha.setAlignment(Pos.CENTER_LEFT);
        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);
        linha.getChildren().addAll(esquerda, espaco);
        linha.getChildren().addAll(direita);
        return linha;
    }

    private Button criarBotao(String texto, String estilo, Runnable acao) {
        Button b = new Button(texto);
        b.setStyle(estilo);
        b.setOnAction(e -> acao.run());
        return b;
    }

    // Lista de CheckBox com todas as modalidades (usada em Novo Plano, Novo Treinador e + Modalidades)
    private ScrollPane criarListaModalidades(List<CheckBox> checkboxes, Set<Integer> marcadas) {
        VBox caixa = new VBox(8);
        caixa.setStyle("-fx-padding: 8;");
        for (Modalidade m : BancoDeDados.getInstance().getModalidade().values()) {
            CheckBox cb = new CheckBox(m.getNome());
            cb.setUserData(m.getId());
            cb.setStyle("-fx-text-fill: #f8fafc; -fx-cursor: hand;");
            if (marcadas != null && marcadas.contains(m.getId())) cb.setSelected(true);
            checkboxes.add(cb);
            caixa.getChildren().add(cb);
        }
        ScrollPane scroll = new ScrollPane(caixa);
        scroll.setFitToWidth(true);
        scroll.setPrefWidth(300);
        scroll.setPrefHeight(120);
        scroll.setStyle("-fx-background-color: #04070d; -fx-background: #04070d; -fx-border-color: #141923;");
        return scroll;
    }

    private List<Integer> idsSelecionados(List<CheckBox> checkboxes) {
        List<Integer> ids = new ArrayList<>();
        for (CheckBox cb : checkboxes) {
            if (cb.isSelected()) ids.add((Integer) cb.getUserData());
        }
        return ids;
    }

    private boolean confirmarExclusao() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Atenção: Ação Destrutiva");
        a.setContentText("Você tem certeza que deseja excluir?\nQualquer informação associada a este registro será excluída permanentemente do sistema da academia.");

        try { a.getDialogPane().getStylesheets().add(getClass().getResource("/VisaoGeral.css").toExternalForm()); } catch(Exception e){}
        a.getDialogPane().setStyle("-fx-background-color: #06090e; -fx-border-color: #141923;");
        a.getDialogPane().lookupAll(".label").forEach(n -> n.setStyle("-fx-text-fill: white;"));

        Optional<ButtonType> result = a.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private TextField criarInput(String prompt) {
        TextField t = new TextField();
        t.setPromptText(prompt);
        t.setStyle("-fx-background-color: #04070d; -fx-text-fill: white; -fx-border-color: #141923; -fx-padding: 10px; -fx-background-radius: 6;");
        t.setPrefWidth(300);
        return t;
    }

    private void adicionarLinhaForm(GridPane grid, int linha, String label, javafx.scene.Node controle) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: #94a3b8; -fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(lbl, 0, linha);
        grid.add(controle, 1, linha);

        if (controle instanceof ComboBox) {
            controle.setStyle("-fx-background-color: #04070d; -fx-text-fill: white; -fx-border-color: #141923; -fx-padding: 4px;");
            ((ComboBox<?>) controle).setPrefWidth(300);
        }
    }

    private void mostrarAlertaErro(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.setHeaderText("Aviso do Sistema");
        a.getDialogPane().setStyle("-fx-background-color: #06090e; -fx-border-color: #141923;");
        a.getDialogPane().lookupAll(".label").forEach(n -> n.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;"));
        a.showAndWait();
    }
}