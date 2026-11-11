package com.example.academia.ClassesUniversais;

import com.example.academia.Treinador.Treinador;
import com.example.academia.aluno.Aluno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean CadastrarTreinador(List<Integer> idsModalidades, String nome, String senha, String cpf, double salario, String especialidade) {
        if(idsModalidades == null || idsModalidades.isEmpty() || nome.isBlank() || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf) || salario <= 0 || especialidade.isBlank()){
            return false;
        }

        for(Integer id : idsModalidades){
            if(id == null || !BancoDeDados.getInstance().validarIdModalidade(id)){
                return false;
            }
        }

        Treinador treinador = new Treinador(nome, CriptografarSenha.SenhaGeral(senha), cpf, salario, especialidade);
        treinador.setSenhaOriginal(senha);
        BancoDeDados.getInstance().treinadores.put(treinador.getId(), treinador);

        for(Integer id : idsModalidades){
            BancoDeDados.getInstance().modalidades.get(id).addTreinador(treinador.getId(), treinador);
        }
        return true;
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

    public boolean cadastrarAluno(String nome, String senha, String cpf, int idade, double altura, double peso, Plano plano, String intensidade, List<Treinador> listaTreinadores) {
        if (nome == null || nome.isBlank() || idade < 13 || altura <= 0 || peso <= 0 || intensidade == null || intensidade.isBlank()
                || !Verificacao.verificarSenha(senha) || !Verificacao.verificarCpf(cpf)
                || listaTreinadores == null || listaTreinadores.isEmpty()
                || plano == null || !BancoDeDados.getInstance().validarIdPlano(plano.getId())){
            return false;
        }
        AssinaturaPlano assinaturaPlano = new AssinaturaPlano(plano.getId(), plano);
        BancoDeDados.getInstance().assinaturaPlano.put(assinaturaPlano.getId(), assinaturaPlano);

        Aluno aluno = new Aluno(nome, CriptografarSenha.SenhaGeral(senha), cpf, idade, altura, peso, assinaturaPlano, intensidade, listaTreinadores);
        aluno.setSenhaOriginal(senha);

        BancoDeDados.getInstance().alunos.put(aluno.getId(), aluno);
        BancoDeDados.getInstance().grafico.addRenda(aluno.getId(), assinaturaPlano.getId());
        return true;
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

    //Cadastrar Plano
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

    //Mostrar os treinadores das modalidades do plano
    public Map<Integer, Treinador> viewTreinador(int idPlano){
        Map<Integer, Treinador> treinadores = new HashMap<>();

        if(BancoDeDados.getInstance().planos.containsKey(idPlano)){
            Plano plano = BancoDeDados.getInstance().planos.get(idPlano);

            for(Modalidade modalidade : plano.getModalidade().values()){

                for(Treinador treinador : modalidade.getTreinadores().values()){
                    treinadores.put(treinador.getId(), treinador);
                }
            }
        }
        return treinadores;
    }

    //Definir as Modalidades de um Plano
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

    //Ver o Gráfico
    public void verGrafico(){
        GraficoRenda g = BancoDeDados.getInstance().grafico;
        g.somarDespesas();
    }

    //Alunos que praticam uma modalidade (pelos planos que assinaram)
    public List<Aluno> alunosDaModalidade(int idModalidade){
        List<Aluno> alunos = new ArrayList<>();

        for(Aluno aluno : BancoDeDados.getInstance().alunos.values()){

            for(AssinaturaPlano assinatura : aluno.getListaAssinaturaPlano().values()){

                if(assinatura.getPlano() != null && assinatura.getPlano().getModalidade().containsKey(idModalidade)){
                    alunos.add(aluno);
                    break;
                }
            }
        }
        return alunos;
    }

    //Alunos que assinaram um plano
    public List<Aluno> alunosDoPlano(int idPlano){
        List<Aluno> alunos = new ArrayList<>();

        for(Aluno aluno : BancoDeDados.getInstance().alunos.values()){

            for(AssinaturaPlano assinatura : aluno.getListaAssinaturaPlano().values()){

                if(assinatura.getPlano() != null && assinatura.getPlano().getId() == idPlano){
                    alunos.add(aluno);
                    break;
                }
            }
        }
        return alunos;
    }

    //Modalidades em que um treinador atua
    public List<Modalidade> modalidadesDoTreinador(int idTreinador){
        List<Modalidade> modalidades = new ArrayList<>();

        for(Modalidade modalidade : BancoDeDados.getInstance().modalidades.values()){

            if(modalidade.getTreinadores().containsKey(idTreinador)){
                modalidades.add(modalidade);
            }
        }
        return modalidades;
    }

    //Alunos atendidos por um treinador
    public List<Aluno> alunosDoTreinador(int idTreinador){
        List<Aluno> alunos = new ArrayList<>();

        for(Aluno aluno : BancoDeDados.getInstance().alunos.values()){

            if(aluno.getTreinadores().containsKey(idTreinador)){
                alunos.add(aluno);
            }
        }
        return alunos;
    }
}