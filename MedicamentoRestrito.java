import java.time.LocalDate;

public class MedicamentoRestrito extends Medicamento {

    public MedicamentoRestrito(String nome, double precoCusto, double taxaLucro,
                               LocalDate validade, int quantidade, double peso) {
        super(nome, precoCusto, taxaLucro, validade, quantidade, peso);
    }

    @Override
    public boolean precisaReceita() {
        return true;
    }

    @Override
    public Produto clonarComQuantidade(int quantidade) {
        return new MedicamentoRestrito(
            this.getNome(),
            this.getPrecoCusto(),
            this.getTaxaLucro(),
            this.getValidade(),
            quantidade,
            this.getPeso()
        );
    }
}
