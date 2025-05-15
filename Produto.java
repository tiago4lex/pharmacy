import java.time.LocalDate;

public class Produto{
    private String nome;
    private double precoCusto;
    private double taxaLucro;
    private Localdate validade;
    private int quantidade;
    private double peso;


    public Produto(String nome, double precoCusto, double taxaLucro, Localdate validade, int quantidade){
        this.nome = nome;
        if(precoCusto >= 0){
            this.precoCusto = precoCusto;
            System.out.println("Preço de custo precisa ser maio que 0!");
        } else{
            this.precoCusto = 0;
        }
        if(taxaLucro > 0){
            this.taxaLucro = taxaLucro;
            System.out.println(("Taxa de lucro precisa ser maior que 0!"));
        } else{
            this.taxaLucro = 0;
        }
        this.validade = validade;
        if(quantidade >= 0){
            this.quantidade = quantidade;
        } else{
            this.quantidade = 0;
            System.out.println("Quantidade informada não pode ser menor que 0!");
        }
        if(peso > 0){
            this.peso = peso;
        } else{
            this.peso = 0;
            System.out.println("Peso informado invalido!");
        }
    }

    public String getNome(){

    }

    public double getPrecoCusto(){

    }

    public double gettaxaLucro(){

    }

    public LocalDate getValidade(){

    }

    public int getQuantidade(){

    }

    public double getPeso(){

    }

    public void setQuantidade(int quantidade){

    }

    public double calcularPrecoVenda(double aliquota){

    }

    public boolean estaVencido(){
        
    }
}