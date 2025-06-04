import java.time.LocalDateTime;

public class Venda {
    private int id;
    private Produto[] produtosVendidos;
    private LocalDateTime data;
    private String funcionario;
    private Receita receita;
    private double valorTotal;
    private double aliquota;

    public Venda(int id, Produto[] produtosVendidos, LocalDateTime data, String funcionario, Receita receita, double aliquota) {
        this.id = id;
        this.produtosVendidos = produtosVendidos;
        this.data = data;
        this.funcionario = funcionario;
        this.receita = receita;
        this.aliquota = aliquota;
        this.valorTotal = calcularValorTotal();
    }

    private double calcularValorTotal() {
        double total = 0.0;
        for (Produto p : produtosVendidos) {
            total += p.calcularPrecoVenda(aliquota) * p.getQuantidade();
        }
        return total;
    }

    
    public int getId() {
        return this.id;
    }

    public Produto[] getProdutosVendidos() {
        return produtosVendidos;
    }

    public LocalDateTime getData() {
        return this.data;
    }

    public String getFuncionario() {
        return this.funcionario;
    }

    public Receita getReceita() {
        return this.receita;
    }

    public double getValorTotal() {
        return this.valorTotal;
    }

    public double getAliquota() {
        return this.aliquota;
    }
}
