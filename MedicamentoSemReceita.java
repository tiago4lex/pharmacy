public abstract class MedicamentoSemReceita extends Medicamento {

    public MedicamentoSemReceita(String nome, double precoCusto, double taxaLucro, Data validade, int quantidade, String tipo,
            double peso) {
        super(nome, precoCusto, taxaLucro, validade, quantidade, tipo, peso);
    }

    @Override
    public boolean precisaReceita(){
        return false;
    }
}