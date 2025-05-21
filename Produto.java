public class Produto {
    private String nome;
    private double precoCusto;
    private double taxaLucro;
    private Data validade;
    private int quantidade;
    private String tipo;
    private double peso;

    public Produto(String nome, double precoCusto, double taxaLucro, Data validade, int quantidade, String tipo, double peso) {
        this.nome = nome;
        if (precoCusto > 0) {
            this.precoCusto = precoCusto;
        } else {
            this.precoCusto = 0;
            System.out.println("Preço de custo precisa ser maior que 0!");
        }

        if (taxaLucro > 0) {
            this.taxaLucro = taxaLucro;
            System.out.println(("Taxa de lucro precisa ser maior que 0!"));
        } else {
            this.taxaLucro = 0;
        }

        this.validade = validade;

        if (quantidade >= 0) {
            this.quantidade = quantidade;
        } else {
            this.quantidade = 0;
            System.out.println("Quantidade informada não pode ser menor que 0!");
        }

        this.tipo = tipo;

        if (peso > 0) {
            this.peso = peso;
        } else {
            this.peso = 0;
            System.out.println("Peso informado invalido!");
        }
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public double getTaxaLucro() {
        return taxaLucro;
    }

    public Data getValidade() {
        return validade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPeso() {
        return peso;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoVenda() {
        return precoCusto + (precoCusto * taxaLucro);
    }

    public boolean estaVencido() {
        return validade.isAnterior(Data.hoje());
    }
}