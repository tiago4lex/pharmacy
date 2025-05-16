public class MedicamentoComReceita extends Medicamento {

    public MedicamentoComReceita(String nome, double precoCusto, double taxaLucro, Data validade,
            int quantidade, String tipo,
            double peso) {
        super(nome, precoCusto, taxaLucro, validade, quantidade, tipo, peso);
    }

    @Override
    public boolean precisaReceita() {
        return true;
    }

    @Override
    public boolean liberarVenda(){
        // Confirma se todos os dados de reecita estão preenchidos
        return getCrmMedico() != null && !getCrmMedico().isEmpty()
            && getNomeMedico() != null && !getNomeMedico().isEmpty()
            && getNomePaciente() != null && !getNomePaciente().isEmpty();
    }
}
