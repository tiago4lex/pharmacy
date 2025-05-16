public abstract class Medicamento extends Produto {
    private String crmMedico;
    private String nomeMedico;
    private String nomePaciente;

    public Medicamento(String nome, double precoCusto, double taxaLucro, Data validade, int quantidade, String tipo,
            double peso) {
        super(nome, precoCusto, taxaLucro, validade, quantidade, tipo, peso);
    }

    public String getCrmMedico() {
        return crmMedico;
    }

    public void setCrmMedico(String crmMedico) {
        this.crmMedico = crmMedico;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getNomePacienteI() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    // Indica se precisa receita
    public abstract boolean precisaReceita();

    // Validação padrão para liberação de venda
    public boolean liberarVenda(){
        return true;
    }
}


