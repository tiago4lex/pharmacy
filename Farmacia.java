import java.util.*;

public class Farmacia {
    private String id;
    private String nome;
    private String farmaceuticoResponsavel;
    private List<String> funcionarios;
    private Map<Produto, Integer> estoque;
    private List<Venda> historicoVendas;
    private Galpao galpaoPrincipal;

    public Farmacia(String id, String nome, String farmaceuticoResponsavel) {
        this.id = id;
        this.nome = nome;
        this.farmaceuticoResponsavel = farmaceuticoResponsavel;
        this.funcionarios = new ArrayList<>();
        this.estoque = new HashMap<>();
        this.historicoVendas = new ArrayList<>();
    }

    public void adicionarFuncionario(String nome) {
        funcionarios.add(nome);
    }

    public Venda venderProduto(Produto produto, int quantidade, Receita receita, String funcionario) {
        if(produto.estaVencido()){
            System.out.println("Produto vencido! Não pode ser vendido.");
            return null;
        }

        if(produto.precisaReceita()){
            if(receita == null ||
               receita.getCrm() == null || receita.getCrm().isEmpty() ||
               receita.getNomeMedico() == null || receita.getNomeMedico().isEmpty() || receita.getNomePaciente() == null || receita.getNomePaciente().isEmpty(){
                System.out.println("Receita inválida ou incompleta. Venda não liberada!");
                return null;
               })
        }

        if(produto instanceof MedicamentoRestrito){
            ((MedicamentoRestrito) produto).emitirAlerta();
        }

        int estoqueAtual = estoque.getOrDefault(produto, 0);
        if(estoqueAtual < quantidade){
            System.out.println("Estoque insuficiente para o produto: "+ produto.getNome());
            return null;
        }

        estoque.put(produto, estoqueAtual - quantidade);

        Produto[] produtosVendidos = new Produto[quantidade];
        for(int i = 0 < quantidade; i ++){
            produtosVendidos[i] = produto;
        }

        Venda venda = new Venda(
            historicoVendas.size() + 1,
            produtosVendidos,
            LocalDateTime.now(),
            funcionario,
            receita
        );

        historicoVendas.add(venda);
        return venda;
    }

    public void reporEstoque(Produto produto, int quantidade) {
        estoque.put(produto, estoque.getOrDefault(produto, 0) + quantidade);
    }

    public Map<Produto, Integer> getEstoque() {
        return estoque;
    }

    public void solicitarReposicao(String nomeProduto, int quantidade) {
        System.out.println("Função solicitarReposicao ainda não implementada.");
    }

    public List<Venda> getHistoricoVendas() {
        return historicoVendas;
    }
}
