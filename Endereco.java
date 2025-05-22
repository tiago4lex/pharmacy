import java.util.*;

public class Endereco {
    private String rua;
    private int posicao;
    private int altura;
    private List<Produto> produtos;

    public Endereco(String rua, int posicao, int altura) {
        this.rua = rua;
        this.posicao = posicao;
        this.altura = altura;
        this.produtos = new ArrayList<>();
    }

    public String getCodigoEndereco() {
        return String.format("%s%02d%02d", rua, posicao, altura);
    }

    public boolean isPrateleira() {
        return altura != 0;
    }

    public boolean adicionarProduto(Produto produto) {
        double pesoTotal = getPesoTotal();
        if (isPrateleira() && (pesoTotal + produto.getPeso() > 20.0)) {
            System.out.println("Não é possível adicionar. Limite de 20kg por prateleira excedido.");
            return false;
        }
        produtos.add(produto);
        return true;
    }

    public double getPesoTotal() {
        double total = 0;
        for (Produto p : produtos) {
            total += p.getPeso();
        }
        return total;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}
