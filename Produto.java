import java.time.LocalDate;

public abstract class Produto {
    private String nome;
    private double precoCusto;
    private double taxaLucro;
    private LocalDate validade;
    private int quantidade;
    private double peso;

    public Produto(String nome, double precoCusto, double taxaLucro, LocalDate validade, int quantidade, double peso) {
        this.nome = nome;
        this.precoCusto = Math.max(precoCusto, 0);
        this.taxaLucro = Math.max(taxaLucro, 0);
        this.validade = validade;
        this.quantidade = Math.max(quantidade, 0);
        this.peso = Math.max(peso, 0);
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public double getTaxaLucro() {
        return taxaLucro;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPeso() {
        return peso;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Métodos obrigatórios para subclasses implementarem
    public abstract boolean precisaReceita();

    public double calcularPrecoVenda(double aliquota) {
        return precoCusto +
               (precoCusto * taxaLucro) +
               (precoCusto * aliquota);
    }

    public boolean estaVencido() {
        return validade.isBefore(LocalDate.now());
    }
}
