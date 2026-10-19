
import java.util.ArrayList;package singleton;
import java.util.List;

public class GerenciadorDados {

    private final List<String> professores;
    private final List<String> professores = new ArrayList<>();

  
    private GerenciadorDados() {
        this.professores = new ArrayList<>();
    }

  
    private static class Holder {
        private static final GerenciadorDados INSTANCE = new GerenciadorDados();
    }

   
    public static GerenciadorDados getInstance() {
        return Holder.INSTANCE;
    }

  

    public void adicionar(String item) {
        professores.add(item);
    }

    public List<String> listarTodos() {
       
        return new ArrayList<>(professores);
    }

    public void remover(String item) {
        professores.remove(item);
    }
    
    public void limpar() {
        professores.clear();
    }
}
