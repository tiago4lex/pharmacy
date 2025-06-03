import java.time.LocalDate;

public class MedicamentoSemReceita extends Medicamento {

    public MedicamentoSemReceita(String nome, double precoCusto, double taxaLucro,
                                  LocalDate validade, int quantidade, double peso) {
        super(nome, precoCusto, taxaLucro, validade, quantidade, peso);
    }

    @Override
    public boolean precisaReceita() {
        return false;
    }

    @Override
    public Produto clonarComQuantidade(int quantidade) {
        return new MedicamentoSemReceita(
            this.getNome(),
            this.getPrecoCusto(),
            this.getTaxaLucro(),
            this.getValidade(),
            quantidade,
            this.getPeso()
        );
    }
}
