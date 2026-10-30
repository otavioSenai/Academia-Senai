package com.example.academia.Dono.Controller;

import com.example.academia.ClassesUniversais.AssinaturaPlano;
import com.example.academia.ClassesUniversais.BancoDeDados;
import com.example.academia.ClassesUniversais.Plano;
import com.example.academia.Dono.Dono;
import com.example.academia.administrativo.Administrativo;
import com.example.academia.aluno.Aluno;
import com.example.academia.Treinador.Treinador;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DonoController {
    @FXML private Label lblMensagem;
    @FXML private Label lblTitulo;
    @FXML private TextField txtPesquisa;
    @FXML private GridPane gridOperacoes;

    private Dono dono;
    private List<Node> cardsOriginais = new ArrayList<>();

    @FXML
    private void initialize() {
        dono = new Dono("Dono Master", "Senha@123", "52998224725", 5000.0);

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
        for (int i = 0; i < encontrados.size(); i++) {
            GridPane.setColumnIndex(encontrados.get(i), i % 3);
            GridPane.setRowIndex(encontrados.get(i), i / 3);
        }
    }

    @FXML
    private void mostrarDepartamentos() {
        selecionarCategoria("Departamentos", "departamentos", "Lista de departamentos da academia");
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
        selecionarCategoria("Relatórios", "financeiro", "Consulte renda e custos.");
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
        String[] v = formulario("Cadastrar treinador", "id do Treinador","id da Modalidade", "Nome", "Senha", "CPF", "Salário", "Especialidade");
        if (v == null || !camposPreenchidos(v)) return;
        Double salario = decimal(v[6], "Salário");
        if (salario == null) return;
        String especialidade =(v[7]);
        if(especialidade.isBlank()) return;

        boolean sucesso = dono.CadastrarTreinador(v[1], v[2], v[3], v[4], v[5], salario, especialidade);
        if(sucesso) resultadoSucesso("Treinador " + v[3] + " cadastrado com sucesso.");
        else resultadoErro("Falha ao cadastrar. Verifique os requisitos de CPF, senha forte e se o ID já existe.");
    }

    @FXML
    private void demitirTreinador() {
        String[] v = formulario("Demitir treinador", "ID do treinador");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID do treinador");
        if (id == null) return;

        boolean sucesso = dono.demitirTreinador(id);
        if(sucesso) resultadoSucesso("Treinador removido com sucesso.");
        else resultadoErro("Falha: Treinador não encontrado.");
    }

    // =========================
    // ALUNOS (Com seleção de plano existente)
    // =========================
    @FXML
    private void cadastrarAluno() {
        Map<Integer, Plano> planosCadastrados = BancoDeDados.getInstance().getPlano();
        if (planosCadastrados.isEmpty()) {
            resultadoErro("Não existem planos no sistema. Cadastre um plano primeiro.");
            return;
        }

        String[] v = formulario(
                "Cadastrar aluno",
                "ID do aluno", "Nome", "Senha", "CPF", "Idade",
                "Altura (ex: 1.75)", "Peso (ex: 70.5)", "Validade (Meses)", "Intensidade (Leve, Médio, Intenso)"
        );
        if (v == null || !camposPreenchidos(v)) return;

        Integer id = inteiro(v[0], "ID do aluno");
        Integer idade = inteiro(v[4], "Idade");
        Double altura = decimal(v[5], "Altura");
        Double peso = decimal(v[6], "Peso");
        Integer mesesValidade = inteiro(v[7], "Validade (Meses)");
        if (id == null || idade == null || altura == null || peso == null || mesesValidade == null) return;

        // Caixa de diálogo para selecionar o plano
        ChoiceDialog<Plano> dialogPlano = new ChoiceDialog<>();
        dialogPlano.setTitle("Selecionar Plano");
        dialogPlano.setHeaderText("Escolha o plano para o aluno");
        dialogPlano.getItems().addAll(planosCadastrados.values());

        Optional<Plano> planoSelecionado = dialogPlano.showAndWait();
        if (planoSelecionado.isPresent()) {
            Plano planoReal = planoSelecionado.get();
            AssinaturaPlano assinatura = new AssinaturaPlano(mesesValidade, planoReal);

            boolean sucesso = dono.cadastrarAluno(id, v[1], v[2], v[3], idade, altura, peso, assinatura, v[8]);
            if(sucesso) resultadoSucesso("Aluno " + v[1] + " matriculado no plano " + planoReal.getNome());
            else resultadoErro("Falha no cadastro. Verifique a força da senha, CPF ou se o ID já existe.");
        }
    }

    @FXML
    private void expulsarAluno() {
        String[] v = formulario("Expulsar aluno", "ID do aluno");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID do aluno");
        if (id == null) return;

        boolean sucesso = dono.ExpulsarAluno(id);
        if(sucesso) resultadoSucesso("Aluno removido da base de dados.");
        else resultadoErro("Falha: Aluno não encontrado.");
    }

    // =========================
    // PLANOS
    // =========================
    @FXML
    private void cadastrarPlano() {
        // Removido o campo "Validade" pois a regra atual foi para AssinaturaPlano
        String[] v = formulario("Cadastrar plano", "Nome", "ID", "Descrição", "Valor");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[1], "ID do plano");
        Double valor = decimal(v[3], "Valor do plano");
        if (id == null || valor == null) return;

        // Passando validade fixa "N/A" apenas para satisfazer a assinatura antiga do Dono que ainda pede String validade
        boolean sucesso = dono.cadastrarPlano(v[0], id, v[2], valor, "N/A");
        if(sucesso) resultadoSucesso("Plano " + v[0] + " adicionado ao catálogo.");
        else resultadoErro("Falha ao cadastrar o plano.");
    }

    @FXML
    private void removerPlano() {
        String[] v = formulario("Remover plano", "ID do plano");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID do plano");
        if (id == null) return;

        boolean removido = dono.removerPlano(id);
        if(removido) resultadoSucesso("Plano removido do catálogo.");
        else resultadoErro("Falha: Plano não encontrado.");
    }

    // =========================
    // CARTEIRINHAS (Exibição na UI)
    // =========================
    @FXML
    private void carteirinhaAluno() {
        String[] v = formulario("Carteirinha do aluno", "ID do aluno");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID do aluno");
        if (id == null) return;

        Aluno aluno = BancoDeDados.getInstance().getAlunos().get(id);
        if (aluno != null) {
            String detalhes = "Nome: " + aluno.getNome() + "\n" +
                    "CPF: " + aluno.getCpf() + "\n" +
                    "Idade: " + aluno.getIdade() + "\n" +
                    "Altura: " + aluno.getAltura() + "m | Peso: " + aluno.getPeso() + "kg\n" +
                    "Intensidade: " + aluno.getIntensidade();
            exibirCarteirinhaVisual("Carteirinha de Aluno", detalhes);
            // Preserva a regra de negócio do back-end
            dono.carteirinhaAluno(id);
        } else {
            resultadoErro("Aluno não encontrado no banco de dados.");
        }
    }

    @FXML
    private void carteirinhaTreinador() {
        String[] v = formulario("Carteirinha do treinador", "ID do treinador");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID do treinador");
        if (id == null) return;

        Treinador prof = BancoDeDados.getInstance().getTreinadores().get(id);
        if (prof != null) {
            String detalhes = "Nome: " + prof.getNome() + "\n" +
                    "CPF: " + prof.getCpf() + "\n" +
                    "Especialidade: " + (prof.getEspecialidade() != null ? prof.getEspecialidade() : "N/D");
            exibirCarteirinhaVisual("Carteirinha do Instrutor", detalhes);
            dono.carteirinhaTreinador(id);
        } else {
            resultadoErro("Treinador não encontrado.");
        }
    }

    @FXML
    private void carteirinhaAdministrativo() {
        String[] v = formulario("Carteirinha do administrativo", "ID do administrativo");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID do administrativo");
        if (id == null) return;

        Administrativo adm = BancoDeDados.getInstance().getAdministrativos().get(id);
        if (adm != null) {
            String detalhes = "Nome: " + adm.getNome() + "\n" +
                    "CPF: " + adm.getCpf();
            exibirCarteirinhaVisual("Credencial Administrativa", detalhes);
            dono.carteirinhaAdministrativo(id);
        } else {
            resultadoErro("Colaborador não encontrado.");
        }
    }

    private void exibirCarteirinhaVisual(String titulo, String dados) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Academia Ícaro");
        alert.setHeaderText(titulo);
        alert.setContentText(dados);

        // Estilização simples do Alert para manter consistência
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #0a0e16; -fx-base: #04070d;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: #f8fafc; -fx-font-size: 14px;");
        dialogPane.lookup(".header-panel").setStyle("-fx-background-color: #f1ba2a;");

        alert.showAndWait();
    }

    // =========================
    // ADMINISTRATIVOS E FINANCEIROS
    // =========================
    @FXML
    private void cadastrarAdministrativo() {
        String[] v = formulario("Cadastrar administrativo", "ID", "Nome", "Senha", "CPF", "Salário");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID");
        Double salario = decimal(v[4], "Salário");
        if (id == null || salario == null) return;

        boolean sucesso = dono.cadastrarAdministrativo(id, v[1], v[2], v[3], salario);
        if (sucesso) resultadoSucesso("Colaborador administrativo cadastrado.");
        else resultadoErro("Falha no cadastro (verifique restrições de CPF e senha).");
    }

    @FXML
    private void demitirAdministrativo() {
        String[] v = formulario("Demitir administrativo", "ID do administrativo");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID");
        if (id == null) return;

        boolean sucesso = dono.demitirAdministrativo(id);
        if (sucesso) resultadoSucesso("Administrativo desligado.");
        else resultadoErro("Administrativo não encontrado.");
    }

    @FXML
    private void verificarGrafico() {
        dono.verificarGrafico();
        resultadoSucesso("Consulta financeira executada. Dados atualizados.");
    }

    @FXML
    private void verificarCusto() {
        String[] v = formulario("Verificar custo", "ID do custo");
        if (v == null || !camposPreenchidos(v)) return;
        Integer id = inteiro(v[0], "ID do custo");
        if (id == null) return;

        boolean existe = dono.verificarCusto(id);
        if (!existe) resultadoErro("Custo não localizado.");
    }

    @FXML
    private void verificarFeedback() {
        aviso("Módulo de Feedback em desenvolvimento na classe Dono.");
    }

    @FXML
    private void trocarTreinador() {
        aviso("Módulo de troca de treinador requer instância de Aluno.");
    }

    // =========================
    // UTILITÁRIOS DE FORMULÁRIO E FEEDBACK
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
                resultadoErro("Preencha todos os campos obrigatórios.");
                return false;
            }
        }
        return true;
    }

    private Integer inteiro(String valor, String campo) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            resultadoErro("Digite um número inteiro válido para: " + campo);
            return null;
        }
    }

    private Double decimal(String valor, String campo) {
        try {
            double numero = Double.parseDouble(valor.replace(',', '.'));
            if (!Double.isFinite(numero)) {
                resultadoErro("Digite um número decimal válido para: " + campo);
                return null;
            }
            return numero;
        } catch (NumberFormatException e) {
            resultadoErro("Digite um número decimal válido para: " + campo);
            return null;
        }
    }

    private void resultadoSucesso(String mensagem) {
        if (lblMensagem != null) lblMensagem.setText(mensagem);
        aviso(mensagem);
    }

    private void resultadoErro(String mensagem) {
        if (lblMensagem != null) lblMensagem.setText(mensagem);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Operação Falhou");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void aviso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Academia Ícaro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}