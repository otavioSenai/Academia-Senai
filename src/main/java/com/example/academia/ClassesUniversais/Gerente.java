
import java.util.ArrayList;

public class Gerente{
private String nome;
private String senha;
private int cpf;
ArrayList<String> professores = new ArrayList<String>();//tambem será diferente

public void setNome(String nome) {
    this.nome = nome;
}
public void setSenha(String senha) {
    this.senha = senha;
}
public void setCpf(int cpf) {
    this.cpf = cpf;
}

public String getNome() {
    return nome;
}

public String getSenha() {
    return senha;
}

public int getCpf() {
    return cpf;
}

}
