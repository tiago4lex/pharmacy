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
        double novoPeso = produto.getPeso() * produto.getQuantidade();

        if (isPrateleira() && (pesoTotal + novoPeso > 20.0)) {
            System.out.printf("Erro: Produto '%s' excede o limite de 20kg nesta prateleira. (%.2fkg/20.00kg)%n",produto.getNome(), pesoTotal + novoPeso);
            return false;
        }

        produtos.add(produto);
        System.out.printf("Produto '%s' adicionado à posição [%s]. Peso atual: %.2fkg%n",produto.getNome(), getCodigoEndereco(), pesoTotal + novoPeso);
        return true;
    }

    public double getPesoTotal() {
        double total = 0;
        for (Produto p : produtos) {
            total += p.getPeso() * p.getQuantidade();
        }
        return total;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}
