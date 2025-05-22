import java.util.*;

public class Rua {
    private String identificador;
    private List<Endereco> enderecos;

    public Rua(String identificador) {
        this.identificador = identificador;
        this.enderecos = new ArrayList<>();
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void adicionarEndereco(Endereco e) {
        enderecos.add(e);
    }
}
