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

    public void exibirRelatorioDePeso() {
        System.out.println("\nRelatório de Peso por Endereço - Rua " + identificador + ":\n");

        for (Endereco e : enderecos) {
            String codigo = e.getCodigoEndereco();
            double pesoTotal = e.getPesoTotal();
            int totalProdutos = e.getProdutos().size();

            System.out.printf("Endereço: %s | Produtos: %d | Peso total: %.2f kg\n",codigo, totalProdutos, pesoTotal);
        }
    }
}
