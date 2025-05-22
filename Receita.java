public class Receita {
    private String nomeMedico;
    private String crm;
    private String nomePaciente;

    public Receita(String nomeMedico, String crm, String nomePaciente){
        this.nomeMedico = nomeMedico;
        this.crm = crm;
        this.nomePaciente = nomePaciente;
    }

    public String getNomeMedico(){
        return this.nomeMedico;
    }

    public String getCrm(){
        return this.crm;
    }

    public String getNomePaciente(){
        return this.nomePaciente;
    }

}
