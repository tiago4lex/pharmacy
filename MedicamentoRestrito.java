public class MedicamentoRestrito extends Medicamento {

    public MedicamentoRestrito(String nome, double precoCusto, double taxaLucro, Data validade,
                               int quantidade, String tipo, double peso) {
        super(nome, precoCusto, taxaLucro, validade, quantidade, tipo, peso);
    }

    // Método sobrescrito que indica que o medicamento exige receita
    @Override
    public boolean precisaReceita() {
        return true;
    }

    // Método específico da classe MedicamentoRestrito
    public void emitirAlerta() {
        System.out.println("Atenção: Este medicamento é de uso restrito e exige receita médica.");
    }
}
