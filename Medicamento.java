import java.time.LocalDate;

public abstract class Medicamento extends Produto {

    public Medicamento(String nome, double precoCusto, double taxaLucro,
                       LocalDate validade, int quantidade, double peso) {
        super(nome, precoCusto, taxaLucro, validade, quantidade, peso);
    }

    public abstract boolean precisaReceita();
}
