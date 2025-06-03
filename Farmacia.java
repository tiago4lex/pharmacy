import java.util.*;
import java.time.LocalDateTime;

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
        if (produto.estaVencido()) {
            System.out.println("Produto vencido! Não pode ser vendido.");
            return null;
        }

        if (produto.precisaReceita()) {
            if (receita == null ||
                receita.getCrm() == null || receita.getCrm().isEmpty() ||
                receita.getNomeMedico() == null || receita.getNomeMedico().isEmpty() ||
                receita.getNomePaciente() == null || receita.getNomePaciente().isEmpty()) {
                System.out.println("Receita inválida ou incompleta. Venda não liberada!");
                return null;
            }
        }

        int estoqueAtual = estoque.getOrDefault(produto, 0);
        if (estoqueAtual < quantidade) {
            System.out.println("Estoque insuficiente para o produto: " + produto.getNome());
            return null;
        }

        estoque.put(produto, estoqueAtual - quantidade);

        Produto[] produtosVendidos = new Produto[quantidade];
        for (int i = 0; i < quantidade; i++) {
            produtosVendidos[i] = produto;
        }

        double aliquota = (galpaoPrincipal != null) ? galpaoPrincipal.getAliquota() : 0;

        Venda venda = new Venda(
            historicoVendas.size() + 1,
            produtosVendidos,
            LocalDateTime.now(),
            funcionario,
            receita,
            aliquota
        );

        historicoVendas.add(venda);
        return venda;
    }

    public void setGalpaoPrincipal(Galpao galpao) {
        this.galpaoPrincipal = galpao;
    }

    public void reporEstoque(Produto produto, int quantidade) {
        estoque.put(produto, estoque.getOrDefault(produto, 0) + quantidade);
        System.out.println("Produto '" + produto.getNome() + "' reabastecido com " + quantidade + " unidades.");
    }

    public Map<Produto, Integer> getEstoque() {
        return estoque;
    }

    public void solicitarReposicao(String nomeProduto, int quantidade) {
        solicitarReposicaoDoGalpao(nomeProduto, quantidade);
    }


    public boolean solicitarReposicaoDoGalpao(String nomeProduto, int quantidadeSolicitada) {
        if (galpaoPrincipal == null) {
            System.out.println("Galpão não definido para esta farmácia.");
            return false;
        }

        for (Rua rua : galpaoPrincipal.getRuas()) {
            for (Endereco endereco : rua.getEnderecos()) {
                for (Produto produto : endereco.getProdutos()) {
                    if (produto.getNome().equalsIgnoreCase(nomeProduto)) {
                        Produto copia = produto.clonarComQuantidade(quantidadeSolicitada);
                        this.reporEstoque(copia, quantidadeSolicitada);
                        System.out.println("Produto solicitado ao galpão com sucesso.");
                        return true;
                    }
                }
            }
        }

        System.out.println("Produto não encontrado no galpão.");
        return false;
    }

    public List<Venda> getHistoricoVendas() {
        return historicoVendas;
    }

    public String getFarmaciaId() {
        return this.id;
    }

    public String getFarmaciaNome() {
        return this.nome;
    }

    public void exibirHistoricoVendas() {
        System.out.println("\n -- Histórico de Vendas -- \n");
        for (Venda venda : historicoVendas) {
            System.out.println("Venda ID: " + venda.getId()
                + " | Funcionário: " + venda.getFuncionario()
                + " | Data: " + venda.getData()
                + " | Valor : R$ " + String.format("%.2f", venda.getValorTotal()));
        }
    }

    public String getFarmaceuticoResponsavel() {
        return this.farmaceuticoResponsavel;
    }

    public void mostrarEstoque() {
        System.out.println("=== Estoque da Farmácia ===");

        if (estoque.isEmpty()) {
            System.out.println("Estoque vazio.");
            return;
        }

        for (Map.Entry<Produto, Integer> entry : estoque.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            System.out.printf("- %s | Quantidade: %d | Vencimento: %s | Receita: %s\n",
                produto.getNome(),
                quantidade,
                produto.getValidade().toString(),
                produto.precisaReceita() ? "Sim" : "Não"
            );
        }
    }

    public Produto buscarProdutoPorNome(String nome) {
        for (Produto produto : estoque.keySet()) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                return produto;
            }
        }
        return null;
    }

    public boolean processarVenda(Produto produto, int quantidade) {
        if (produto.estaVencido()) {
            System.out.println("Produto vencido. Venda não permitida.");
            return false;
        }

        int estoqueAtual = estoque.getOrDefault(produto, 0);
        if (estoqueAtual < quantidade) {
            System.out.println("Estoque insuficiente para o produto: " + produto.getNome());
            return false;
        }

        estoque.put(produto, estoqueAtual - quantidade);
        System.out.println("Venda processada: " + quantidade + " unidades de " + produto.getNome());
        return true;
    }
}
