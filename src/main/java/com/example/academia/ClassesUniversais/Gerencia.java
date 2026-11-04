package com.example.academia.ClassesUniversais;

import com.example.academia.Treinador.Treinador;
import com.example.academia.aluno.Aluno;

import java.util.ArrayList;
import java.util.List;

public abstract class Gerencia extends Funcionario{

    public Gerencia(String nome, String senha, String cpf, double salario) {
        super(nome, senha, cpf, salario);
    }

    //Escolher Modalidade
    public boolean escolherModalidade(String m, String descricao){
        if(m.isBlank() || descricao.isBlank()){
            return false;
        }
        Modalidade modalidade = new Modalidade(m, descricao);
        BancoDeDados.getInstance().modalidades.put(modalidade.getId(), modalidade);
        return true;
    }

    //Alterar Descrição Da Modalidade
    public boolean setDescricao(int idModalidade, String descricao){
        if(BancoDeDados.getInstance().validarIdModalidade(idModalidade) && !descricao.isBlank()){
            BancoDeDados.getInstance().modalidades.get(idModalidade).setDescricao(descricao);
            return true;
        }
        return false;
    }

    //Cadastrar Custo
    public boolean cadastrarCusto(String nome, double valor){
        if(nome.isBlank() || valor <= 0){
            return false;
        }
        else{
            Custo custo = new Custo(nome, valor);
            BancoDeDados.getInstance().custos.put(custo.getId(), custo);
            BancoDeDados.getInstance().grafico.addCusto(custo.getId());
            return true;
        }
    }

    //Remover Custo (desconta do gráfico antes de remover do mapa)
    public boolean removerCusto(int id){
        if(BancoDeDados.getInstance().validarIdCusto(id)){
            Custo custo = BancoDeDados.getInstance().custos.get(id);
            BancoDeDados.getInstance().grafico.removerCusto(custo.getValor());
            BancoDeDados.getInstance().custos.remove(id);
            return true;
        }
        else{
            return false;
        }
    }

    //Cadastra o Treinador
    public boolean CadastrarTreinador(int idModalidade, String nome, String senha, String cpf, double salario, String especialidade) {
        if(!BancoDeDados.getInstance().validarIdModalidade(idModalidade) ||nome.isBlank() || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf) || salario <= 0 || especialidade.isBlank()){
            return false;
        }
        // Se passou nas verificações, chama a classe para criptografar em SHA-512
        String senhaCriptografada = CriptografarSenha.SenhaGeral(senha);
        Treinador treinador = new Treinador(nome, senhaCriptografada, cpf, salario, especialidade);
        BancoDeDados.getInstance().treinadores.put(treinador.getId(), treinador);
        BancoDeDados.getInstance().modalidades.get(idModalidade).addTreinador(treinador.getId(), treinador);
        return true;
    }

    //Cadastra o Treinador em Uma ou Mais Modalidades
    public boolean CadastrarTreinador(List<Integer> idsModalidades, String nome, String senha, String cpf, double salario, String especialidade) {
        if(idsModalidades == null || idsModalidades.isEmpty() || nome.isBlank() || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf) || salario <= 0 || especialidade.isBlank()){
            return false;
        }
        for(Integer id : idsModalidades){
            if(id == null || !BancoDeDados.getInstance().validarIdModalidade(id)){
                return false;
            }
        }
        // Se passou nas verificações, chama a classe para criptografar em SHA-512
        String senhaCriptografada = CriptografarSenha.SenhaGeral(senha);
        Treinador treinador = new Treinador(nome, senhaCriptografada, cpf, salario, especialidade);
        BancoDeDados.getInstance().treinadores.put(treinador.getId(), treinador);
        for(Integer id : idsModalidades){
            BancoDeDados.getInstance().modalidades.get(id).addTreinador(treinador.getId(), treinador);
        }
        return true;
    }

    //Verificar Feedback do Treinador
    public boolean verificarFeedbackTreinador(int idTreinador){
        if(BancoDeDados.getInstance().validarIdTreinador(idTreinador)) {
            for (FeedbackTreinador f : BancoDeDados.getInstance().treinadores.get(idTreinador).getFeedback().values()) {
                f.getFeedback();
            }
            return true;
        }
        return false;
    }

    //Verificar Solicitações Para Trocar de Treinador
    public void VerificarSolicitacoesTrocarTreinador(int idTreinador){
        if(BancoDeDados.getInstance().validarIdTreinador(idTreinador)){
            for(TrocarTreinador t : BancoDeDados.getInstance().treinadores.get(idTreinador).getSolicitacaoTroca().values()){
                t.getSolicitacaoTroca(idTreinador);
            }
        }
    }

    //Transferir Treinador de Modalidade
    public boolean transferirTreinador(Treinador treinador, int idModalidadeInicial, int idModalidadeFinal){
        if(treinador != null && BancoDeDados.getInstance().validarIdModalidade(idModalidadeFinal) && BancoDeDados.getInstance().validarIdModalidade(idModalidadeInicial)) {
            BancoDeDados.getInstance().modalidades.get(idModalidadeInicial).removeTreinador(idModalidadeInicial, treinador.getId());
            BancoDeDados.getInstance().modalidades.get(idModalidadeFinal).addTreinador(treinador.getId(), treinador);
            return true;
        }
        return false;
    }

    //Demitir o Treinador
    public boolean demitirTreinador(int idTreinador, int idModalidade) {
        if(BancoDeDados.getInstance().validarIdTreinador(idTreinador) && BancoDeDados.getInstance().validarIdModalidade(idModalidade)){
            BancoDeDados.getInstance().treinadores.remove(idTreinador);
            BancoDeDados.getInstance().modalidades.get(idModalidade).removeTreinador(idModalidade, idTreinador);
            return true;
        }
        else{
            return false;
        }
    }

    //Demitir o Treinador removendo-o de TODAS as modalidades em que atua
    public boolean demitirTreinadorCompleto(int idTreinador) {
        BancoDeDados bd = BancoDeDados.getInstance();
        if(!bd.validarIdTreinador(idTreinador)){
            return false;
        }
        for(Modalidade m : bd.modalidades.values()){
            m.getTreinadores().remove(idTreinador);
        }
        bd.treinadores.remove(idTreinador);
        return true;
    }

    //Cadastrar o Aluno
    public boolean cadastrarAluno(String nome, String senha, String cpf, int idade, double altura, double peso, Plano plano, String intensidade, Treinador treinador) {
        if (nome == null || nome.isBlank() || idade < 13 || altura <= 0 || peso <= 0 || intensidade == null || intensidade.isBlank()
                || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf)
                || treinador == null || !BancoDeDados.getInstance().validarIdTreinador(treinador.getId())
                || plano == null || !BancoDeDados.getInstance().validarIdPlano(plano.getId())){
            return false;
        }
        else{
            // Se passou nas verificações, chama a classe para criptografar em SHA-512
            String senhaCriptografada = CriptografarSenha.SenhaGeral(senha);
            // A assinatura é criada AGORA e registrada no banco (antes o mapa nunca era preenchido e o cadastro sempre falhava)
            AssinaturaPlano assinaturaPlano = new AssinaturaPlano(plano.getId(), plano);
            BancoDeDados.getInstance().assinaturaPlano.put(assinaturaPlano.getId(), assinaturaPlano);
            Aluno aluno = new Aluno(nome, senhaCriptografada, cpf, idade, altura, peso, assinaturaPlano, intensidade, treinador);
            BancoDeDados.getInstance().alunos.put(aluno.getId(), aluno);
            // O aluno paga o valor do plano no ato da matrícula: entra na Renda (Visão Geral).
            // A validade inicial da assinatura já cobre esse primeiro pagamento.
            BancoDeDados.getInstance().grafico.addRenda(aluno.getId(), assinaturaPlano.getId());
            return true;
        }
    }

    //Mudar Treinador do Aluno
    public void setTreinadorAluno(int idAluno, int idModalidade, int idTreinadorAntigo, int idTreinadorNovo){
        BancoDeDados.getInstance().alunos.get(idAluno).setTreinador(idModalidade, idTreinadorAntigo, idTreinadorNovo);
    }

    //Ver as Assinaturas do Aluno
    public void verAssinaturas(int id){
        BancoDeDados.getInstance().alunos.get(id).showAssinaturaPlano();
    }

    //Mostrar Alunos
    public void verAlunos(){
        for(Aluno a : BancoDeDados.getInstance().alunos.values()){
            System.out.println(a.getId() + " - " + a.getNome());
        }
    }

    //Expulsar Aluno
    public boolean ExpulsarAluno(int id) {
        if(BancoDeDados.getInstance().validIdAluno(id)){
            BancoDeDados.getInstance().alunos.remove(id);
            return true;
        }
        else{
            return false;
        }
    }

    //Carteirinha Aluno
    public boolean carteirinhaAluno(int id){
        if(BancoDeDados.getInstance().validIdAluno(id)){
            BancoDeDados.getInstance().alunos.get(id).mostrarCarteirinhaAluno();
            return true;
        }
        return false;
    }

    //Carteirinha Treinador
    public boolean carteirinhaTreinador(int id){
        if(BancoDeDados.getInstance().validarIdTreinador(id)){
            BancoDeDados.getInstance().treinadores.get(id).mostrarCarteirinhaTreinador();
            return true;
        }
        return false;
    }

    //Cadastrar Plano
    public boolean cadastrarPlano(String nome, String descricao, double valor, int idModalidade, int mesesValidade, double juros){
        if(nome.isBlank() || descricao.isBlank() || valor <= 0 || mesesValidade <= 0 || idModalidade < 0 || juros < 0){
            return false;
        }
        Plano plano = new Plano(nome,descricao,valor, mesesValidade, juros);
        if(plano.addModalidade(idModalidade)) {
            BancoDeDados.getInstance().planos.put(plano.getId(), plano);
            return true;
        }
        return false;
    }

    //Cadastrar Plano com Uma ou Mais Modalidades
    public boolean cadastrarPlano(String nome, String descricao, double valor, List<Integer> idsModalidades, int mesesValidade, double juros){
        if(nome.isBlank() || descricao.isBlank() || valor <= 0 || mesesValidade <= 0 || juros < 0 || idsModalidades == null || idsModalidades.isEmpty()){
            return false;
        }
        for(Integer id : idsModalidades){
            if(id == null || !BancoDeDados.getInstance().validarIdModalidade(id)){
                return false;
            }
        }
        Plano plano = new Plano(nome, descricao, valor, mesesValidade, juros);
        for(Integer id : idsModalidades){
            plano.addModalidade(id);
        }
        BancoDeDados.getInstance().planos.put(plano.getId(), plano);
        return true;
    }

    //Definir as Modalidades de um Plano (botão "+ Modalidades"): adiciona as marcadas e retira as desmarcadas
    public boolean definirModalidadesPlano(int idPlano, List<Integer> idsModalidades){
        if(!BancoDeDados.getInstance().validarIdPlano(idPlano) || idsModalidades == null || idsModalidades.isEmpty()){
            return false;
        }
        for(Integer id : idsModalidades){
            if(id == null || !BancoDeDados.getInstance().validarIdModalidade(id)){
                return false;
            }
        }
        Plano plano = BancoDeDados.getInstance().planos.get(idPlano);
        plano.getModalidade().keySet().removeIf(idAtual -> !idsModalidades.contains(idAtual));
        for(Integer id : idsModalidades){
            if(!plano.getModalidade().containsKey(id)){
                plano.addModalidade(id);
            }
        }
        return true;
    }

    //Remover Plano
    public boolean removerPlano(int id){
        if(BancoDeDados.getInstance().validarIdPlano(id)){
            BancoDeDados.getInstance().planos.remove(id);
            return true;
        }
        else{
            return false;
        }
    }

    //Verificar Custos
    public boolean verificarCusto(int id){
        if(BancoDeDados.getInstance().validarIdCusto(id)){
            BancoDeDados.getInstance().custos.get(id).mostrarCusto();
            return true;
        }
        else{
            return false;
        }
    }

    //Ver o Gráfico
    public void verGrafico(){
        GraficoRenda g = BancoDeDados.getInstance().grafico;
        g.somarDespesas();
        g.verGrafico();
    }

    // ===== Consultas usadas pelos botões "Acessar" =====

    //Alunos que praticam uma modalidade (pelos planos que assinaram)
    public List<Aluno> alunosDaModalidade(int idModalidade){
        List<Aluno> lista = new ArrayList<>();
        for(Aluno a : BancoDeDados.getInstance().alunos.values()){
            for(AssinaturaPlano ap : a.getListaAssinaturaPlano().values()){
                if(ap.getPlano() != null && ap.getPlano().getModalidade().containsKey(idModalidade)){
                    lista.add(a);
                    break;
                }
            }
        }
        return lista;
    }

    //Alunos que assinaram um plano
    public List<Aluno> alunosDoPlano(int idPlano){
        List<Aluno> lista = new ArrayList<>();
        for(Aluno a : BancoDeDados.getInstance().alunos.values()){
            for(AssinaturaPlano ap : a.getListaAssinaturaPlano().values()){
                if(ap.getPlano() != null && ap.getPlano().getId() == idPlano){
                    lista.add(a);
                    break;
                }
            }
        }
        return lista;
    }

    //Modalidades em que um treinador atua
    public List<Modalidade> modalidadesDoTreinador(int idTreinador){
        List<Modalidade> lista = new ArrayList<>();
        for(Modalidade m : BancoDeDados.getInstance().modalidades.values()){
            if(m.getTreinadores().containsKey(idTreinador)){
                lista.add(m);
            }
        }
        return lista;
    }

    //Alunos atendidos por um treinador
    public List<Aluno> alunosDoTreinador(int idTreinador){
        List<Aluno> lista = new ArrayList<>();
        for(Aluno a : BancoDeDados.getInstance().alunos.values()){
            if(a.getTreinadores().containsKey(idTreinador)){
                lista.add(a);
            }
        }
        return lista;
    }
}