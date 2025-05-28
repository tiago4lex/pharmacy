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
}
