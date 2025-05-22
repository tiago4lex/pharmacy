public class Galpao {
    private String id;
    private String estado;
    private double aliquota;
    private String farmaceuticoResponsavel;
    private String colaboradores[];
    private Map estoque;

    public Galpao(String id, String estado, double aliquota, String farmaceuticoResponsavel, String colaboradores, Map estoque){
        this.id = id;
        this.estado = estado;
        this.aliquota = aliquota;
        this.farmaceuticoResponsavel = farmaceuticoResponsavel;
        this.colaboradores = colaboradores;
        this.estoque = estoque;
        }
    
    public String etId(){
        return this.id;
    }

    public double getAliquota(){
        return this.aliquota;
    }

    public String getEstado(){
        return this.estado;
    }

    public String[] getRuas(){
        
    }

    public void adiocionarProduto(Endereco endereco, Produto produto){

    }

    public void fornecerProduto(String nomeProduto, int quantidade){

    }

    public void verificarValidade(){

    }

    public void adicionarRua(Rua rua){

    }

    public void adicionarColaborador(String nome){

    }

}
