package com.example.academia.Dono.Controller.Teste;

public class DonoController {
/*
    @FXML private Label lblMensagem;
    @FXML private Label lblTitulo;
    @FXML private TextField txtPesquisa;
    @FXML private GridPane gridOperacoes;

    private Dono dono;
    private List<Node> cardsOriginais = new ArrayList<>();

    @FXML
    private void initialize() {
        // Dono temporário para testar a interface. No sistema final,
        // substitua este objeto pelo Dono que fez login.
        dono = new Dono(
                1,
                "Dono de Teste",
                "Senha@123",
                "52998224725",
                5000.0
        );

        if (gridOperacoes != null) {
            cardsOriginais = new ArrayList<>(gridOperacoes.getChildren());
        }

        if (txtPesquisa != null) {
            txtPesquisa.textProperty().addListener((obs, antigo, novo) -> filtrarCards(novo));
        }

        if (lblMensagem != null) {
            lblMensagem.setText("Selecione uma operação para gerenciar a Academia Ícaro.");
        }
    }

    // Ação do botão amarelo "Cadastrar" no cabeçalho.
    @FXML
    private void abrirCadastro() {
        List<String> opcoes = List.of(
                "Treinador",
                "Aluno",
                "Administrativo",
                "Plano"
        );
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Aluno", opcoes);
        dialog.setTitle("Academia Ícaro");
        dialog.setHeaderText("O que deseja cadastrar?");
        dialog.setContentText("Tipo de cadastro:");

        Optional<String> escolha = dialog.showAndWait();
        if (escolha.isEmpty()) return;

        switch (escolha.get()) {
            case "Treinador" -> cadastrarTreinador();
            case "Aluno" -> cadastrarAluno();
            case "Administrativo" -> cadastrarAdministrativo();
            case "Plano" -> cadastrarPlano();
            default -> aviso("Tipo de cadastro não reconhecido.");
        }
    }

    // Pesquisa as categorias mostradas nos cartões.
    @FXML
    private void pesquisar() {
        filtrarCards(txtPesquisa == null ? "" : txtPesquisa.getText());
    }

    private void filtrarCards(String pesquisa) {
        if (gridOperacoes == null) return;

        String termo = pesquisa == null ? "" : pesquisa.trim().toLowerCase();
        List<Node> encontrados = new ArrayList<>();

        for (Node card : cardsOriginais) {
            String palavras = card.getAccessibleText();
            if (palavras == null) palavras = "";
            boolean mostrar = termo.isBlank() || palavras.toLowerCase().contains(termo);
            card.setVisible(mostrar);
            card.setManaged(mostrar);
            if (mostrar) encontrados.add(card);
        }

        // Reorganiza os cartões visíveis para não deixar espaços grandes na pesquisa.
        for (int i = 0; i < encontrados.size(); i++) {
            GridPane.setColumnIndex(encontrados.get(i), i % 3);
            GridPane.setRowIndex(encontrados.get(i), i / 3);
        }
    }

    @FXML
    private void mostrarDepartamentos() {
        if (lblTitulo != null) lblTitulo.setText("Departamentos");
        if (txtPesquisa != null) txtPesquisa.clear();
        if (lblMensagem != null) {
            lblMensagem.setText(
                    "Esta seção aparece na apresentação, mas a classe Dono enviada ainda não contém métodos para cadastrar ou remover departamentos."
            );
        }
    }

    @FXML
    private void mostrarPlanos() {
        selecionarCategoria("Planos", "planos", "Cadastre ou remova planos de assinatura.");
    }

    @FXML
    private void mostrarTreinadores() {
        selecionarCategoria("Treinadores", "treinadores", "Gerencie cadastros, demissões e carteirinhas dos treinadores.");
    }

    @FXML
    private void mostrarAlunos() {
        selecionarCategoria("Alunos", "alunos", "Gerencie matrículas, carteirinhas e remoção de alunos.");
    }

    @FXML
    private void mostrarAdministrativos() {
        selecionarCategoria("Administrativo", "administrativo", "Gerencie os funcionários administrativos.");
    }

    @FXML
    private void mostrarGerente() {
        selecionarCategoria("Relatórios", "financeiro", "Consulte renda e custos. Feedback e troca de treinador ainda precisam da lógica correspondente.");
    }

    private void selecionarCategoria(String titulo, String filtro, String mensagem) {
        if (lblTitulo != null) lblTitulo.setText(titulo);
        if (lblMensagem != null) lblMensagem.setText(mensagem);
        if (txtPesquisa != null) txtPesquisa.setText(filtro);
    }

    // =========================
    // TREINADORES
    // =========================

    @FXML
    private void cadastrarTreinador() {
        String[] v = formulario("Cadastrar treinador", "ID", "Nome", "Senha", "CPF", "Salário");
        if (v == null || !camposPreenchidos(v)) return;

        Integer id = inteiro(v[0], "ID");
        Double salario = decimal(v[4], "Salário");
        if (id == null || salario == null) return;

        dono.CadastrarTreinador(id, v[1], v[2], v[3], salario);
        resultado("Método de cadastro de treinador chamado. Confira o console para ver o resultado da validação.");
    }

    @FXML
    private void demitirTreinador() {
        String[] v = formulario("Demitir treinador", "ID do treinador");
        if (v == null || !camposPreenchidos(v)) return;

        Integer id = inteiro(v[0], "ID do treinador");
        if (id == null) return;

        dono.demitirTreinador(id);
        resultado("Método de demissão chamado. Confira o console para ver se o ID existia.");
    }

    // =========================
    // ALUNOS
    // =========================

    @FXML
    private void cadastrarAluno() {
        String[] v = formulario(
                "Cadastrar aluno",
                "ID do aluno", "Nome", "Senha", "CPF", "Idade",
                "Altura em metros", "Peso em kg", "Nome do plano",
                "ID do plano", "Descrição do plano", "Valor do plano",
                "Validade do plano", "Intensidade: Leve, Médio ou Intenso"
        );
        if (v == null || !camposPreenchidos(v)) return;

        Integer id = inteiro(v[0], "ID do aluno");
        Integer idade = inteiro(v[4], "Idade");
        Double altura = decimal(v[5], "Altura");
        Double peso = decimal(v[6], "Peso");
        Integer idPlano = inteiro(v[8], "ID do plano");
        Double valorPlano = decimal(v[10], "Valor do plano");

        if (id == null || idade == null || altura == null || peso == null
                || idPlano == null || valorPlano == null) return;

        Plano plano = new Plano(v[7], idPlano, v[9], valorPlano, v[11]);
        dono.cadastrarAluno(id, v[1], v[2], v[3], idade, altura, peso, plano, v[12]);
        resultado("Método de cadastro de aluno chamado. Confira o console para ver o resultado da validação.");
    }

    @FXML
    private void expulsarAluno() {
        String[] v = formulario("Expulsar aluno", "ID do aluno");
        if (v == null || !camposPreenchidos(v)) return;

        Integer id = inteiro(v[0], "ID do aluno");
        if (id == null) return;

        dono.ExpulsarAluno(id);
        resultado("Método de expulsão chamado. Confira o console para ver se o ID existia.");
    }

    // =========================
    // ADMINISTRATIVOS
    // =========================

    @FXML
    private void cadastrarAdministrativo() {
        String[] v = formulario("Cadastrar administrativo", "ID", "Nome", "Senha", "CPF", "Salário");
        if (v == null || !camposPreenchidos(v)) return;

        Integer id = inteiro(v[0], "ID");
        Double salario = decimal(v[4], "Salário");
        if (id == null || salario == null) return;

        dono.cadastrarAdministrativo(id, v[1], v[2], v[3], salario);
        resultado("Método de cadastro de administrativo chamado. Confira o console para ver o resultado.");
    }

    @FXML
    private void demitirAdministrativo() {
        String[] v = formulario("Demitir administrativo", "ID do administrativo");
        if (v == null || !camposPreenchidos(v)) return;

        Integer id = inteiro(v[0], "ID do administrativo");
        if (id == null) return;

        dono.demitirAd(id);
        resultado("Método de demissão de administrativo chamado. Confira o console.");
    }

    // =========================
    // PLANOS
    // =========================

    @FXML
    private void cadastrarPlano() {
        String[] v = formulario("Cadastrar plano", "Nome", "ID", "Descrição", "Valor", "Validade");
        if (v == null || !camposPreenchidos(v)) return;

        Integer id = inteiro(v[1], "ID do plano");
        Double valor = decimal(v[3], "Valor do plano");
        if (id == null || valor == null) return;

        dono.cadastrarPlano(v[0], id, v[2], valor, v[4]);
        resultado("Método de cadastro de plano chamado. Confira o console.");
    }

    @FXML
    private void removerPlano() {
        String[] v = formulario("Remover plano", "ID do plano");
        if (v == null || !camposPreenchidos(v)) return;

        Integer id = inteiro(v[0], "ID do plano");
        if (id == null) return;

        dono.removerPlano(id);
        resultado("Método de remoção de plano chamado. Confira o console.");
    }

    // =========================
    // CARTEIRINHAS
    // =========================

    @FXML
    private void carteirinhaAluno() {
        String[] v = formulario("Carteirinha do aluno", "ID do aluno");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID do aluno");
        if (id == null) return;

        dono.carteirinhaAluno(id);
        resultado("Método de consulta da carteirinha chamado. Confira o console.");
    }

    @FXML
    private void carteirinhaTreinador() {
        String[] v = formulario("Carteirinha do treinador", "ID do treinador");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID do treinador");
        if (id == null) return;

        dono.carteirinhaTreinador(id);
        resultado("Método de consulta da carteirinha chamado. Confira o console.");
    }

    @FXML
    private void carteirinhaAdministrativo() {
        String[] v = formulario("Carteirinha do administrativo", "ID do administrativo");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID do administrativo");
        if (id == null) return;

        dono.carteirinhaAdministrativo(id);
        resultado("Método de consulta da carteirinha chamado. Confira o console.");
    }

    // =========================
    // RELATÓRIOS
    // =========================

    @FXML
    private void verificarGrafico() {
        dono.verificarGrafico();
        resultado("Método de consulta do gráfico chamado. Confira o console.");
    }

    @FXML
    private void verificarCusto() {
        String[] v = formulario("Verificar custo", "ID do custo");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID do custo");
        if (id == null) return;

        dono.verificarCusto(id);
        resultado("Método de consulta de custo chamado. Confira o console.");
    }

    @FXML
    private void verificarFeedback() {
        aviso("O método verificarFeedback() ainda não está implementado na classe Dono enviada.");
    }

    @FXML
    private void trocarTreinador() {
        aviso("A troca de treinador ainda precisa ser conectada à lógica do aluno.");
    }

    // =========================
    // FORMULÁRIO E VALIDAÇÃO DE ENTRADA
    // =========================

    private String[] formulario(String titulo, String... nomesCampos) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Academia Ícaro");
        dialog.setHeaderText(titulo);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);

        TextField[] campos = new TextField[nomesCampos.length];
        for (int i = 0; i < nomesCampos.length; i++) {
            campos[i] = new TextField();
            campos[i].setPromptText(nomesCampos[i]);
            grid.add(new Label(nomesCampos[i] + ":"), 0, i);
            grid.add(campos[i], 1, i);
        }

        dialog.getDialogPane().setContent(grid);
        Optional<ButtonType> resposta = dialog.showAndWait();
        if (resposta.isEmpty() || resposta.get() != ButtonType.OK) return null;

        String[] valores = new String[campos.length];
        for (int i = 0; i < campos.length; i++) {
            valores[i] = campos[i].getText().trim();
        }
        return valores;
    }

    private boolean camposPreenchidos(String... valores) {
        for (String valor : valores) {
            if (valor == null || valor.isBlank()) {
                aviso("Preencha todos os campos.");
                return false;
            }
        }
        return true;
    }

    private Integer inteiro(String valor, String campo) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            aviso("Digite um número inteiro válido para: " + campo);
            return null;
        }
    }

    private Double decimal(String valor, String campo) {
        try {
            double numero = Double.parseDouble(valor.replace(',', '.'));
            if (!Double.isFinite(numero)) {
                aviso("Digite um número válido para: " + campo);
                return null;
            }
            return numero;
        } catch (NumberFormatException e) {
            aviso("Digite um número válido para: " + campo);
            return null;
        }
    }

    private void resultado(String mensagem) {
        if (lblMensagem != null) lblMensagem.setText(mensagem);
        aviso(mensagem);
    }

    private void aviso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Academia Ícaro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }*/
}
