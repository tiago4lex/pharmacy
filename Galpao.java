import java.util.*;

public class Galpao {
    private String id;
    private String estado;
    private double aliquota;
    private String farmaceuticoResponsavel;
    private List<String> colaboradores;
    private List<Rua> ruas;
    private Map<Endereco, Produto> estoque;

    public Galpao(String id, String estado, double aliquota, String farmaceuticoResponsavel) {
        this.id = id;
        this.estado = estado;
        this.aliquota = aliquota;
        this.farmaceuticoResponsavel = farmaceuticoResponsavel;
        this.colaboradores = new ArrayList<>();
        this.ruas = new ArrayList<>();
        this.estoque = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public String getFarmaceuticoResponsavel() {
        return this.farmaceuticoResponsavel;
    }

    public double getAliquota() {
        return this.aliquota;
    }

    public String getEstado() {
        return this.estado;
    }

    public List<Rua> getRuas() {
        return ruas;
    }

    public void adicionarRua(Rua rua) {
        ruas.add(rua);
    }

    public void adicionarColaborador(String nome) {
        colaboradores.add(nome);
    }

    public void adicionarProduto(Endereco endereco, Produto produto) {
        if (!produto.estaVencido()) {
            estoque.put(endereco, produto);
        } else {
            System.out.println("Produto vencido. Não pode ser adicionado ao estoque.");
        }
    }

    public void fornecerProduto(String nomeProduto, int quantidade) {
        System.out.println("ainda não implementado.");
    }

    public void verificarValidade() {
        for (Map.Entry<Endereco, Produto> entry : estoque.entrySet()) {
            if (entry.getValue().estaVencido()) {
                System.out.println("Produto vencido no endereço: " + entry.getKey().getCodigoEndereco());
            }
        }
    }

    public void exibirRelatorioGeralDePeso() {
        System.out.println("\nRelatório Geral de Peso - Galpão " + id + " (" + estado + ")\n");

        for (Rua rua : ruas) {
            System.out.println("Rua: " + rua.getIdentificador());

            for (Endereco endereco : rua.getEnderecos()) {
                String codigo = endereco.getCodigoEndereco();
                double peso = endereco.getPesoTotal();
                int totalProdutos = endereco.getProdutos().size();

                String alerta = "";
                if (endereco.isPrateleira() && peso > 20.0) {
                    alerta = " EXCEDE LIMITE!";
                }

                System.out.printf("  Endereço: %s | Produtos: %d | Peso total: %.2f kg%s\n",
                        codigo, totalProdutos, peso, alerta);
            }

            System.out.println();
        }
    }

    public void listarProdutos() {
        System.out.println("--- Produtos no Galpão ---");
        for (Rua rua : ruas) {
            for (Endereco endereco : rua.getEnderecos()) {
                List<Produto> produtos = endereco.getProdutos();
                if (!produtos.isEmpty()) {
                    System.out.println("Endereço: " + endereco.getCodigoEndereco());
                    for (Produto p : produtos) {
                        System.out.println("- " + p.getNome() + " | Quantidade: " + p.getQuantidade());
                    }
                }
            }
        }
    }

    public void visualizarEstoque() {
        System.out.println("\n--- Estrutura do Estoque do Galpão ---");
        for (Rua rua : ruas) {
            System.out.println("Rua: " + rua.getIdentificador());
            for (Endereco endereco : rua.getEnderecos()) {
                String codigo = endereco.getCodigoEndereco();
                int quantidadeProdutos = endereco.getProdutos().size();
                System.out.printf("  Endereço: %s | Produtos armazenados: %d\n", codigo, quantidadeProdutos);
            }
        }
    }

    public Endereco buscarEndereco(String ruaId, int setor, int prateleira) {
        for (Rua rua : ruas) {
            if (rua.getIdentificador().equalsIgnoreCase(ruaId)) {
                for (Endereco endereco : rua.getEnderecos()) {
                    if (endereco.getSetor() == setor && endereco.getPrateleira() == prateleira) {
                        return endereco;
                    }
                }
            }
        }
        return null;
    }
}
