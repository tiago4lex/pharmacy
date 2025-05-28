import java.time.LocalDate;

public class MedicamentoComReceita extends Medicamento {

    public MedicamentoComReceita(String nome, double precoCusto, double taxaLucro,
                                  LocalDate validade, int quantidade, double peso) {
        super(nome, precoCusto, taxaLucro, validade, quantidade, peso);
    }

    @Override
    public boolean precisaReceita() {
        return true;
    }
}
