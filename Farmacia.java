import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.io.FileReader;

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
            if (receita == null || receita.getCrm() == null || receita.getCrm().isEmpty() || receita.getNomeMedico() == null || receita.getNomeMedico().isEmpty() || receita.getNomePaciente() == null || receita.getNomePaciente().isEmpty()) {
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

    public String getFarmaceuticoResponsavel() {
        return this.farmaceuticoResponsavel;
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

    public void salvarArquivo(String caminho) {
        try (FileWriter writer = new FileWriter(caminho)) {
            writer.write(this.id + ";" + this.nome + ";" + this.farmaceuticoResponsavel + "\n");
            for (Map.Entry<Produto, Integer> entry : estoque.entrySet()) {
                Produto p = entry.getKey();
                writer.write(p.getNome() + ";" + p.getPrecoCusto() + ";" + p.getTaxaLucro() + ";" + p.getValidade() + ";" + p.getQuantidade() + ";" + p.getPeso() + ";" + entry.getValue() + ";" + p.getClass().getSimpleName() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo Farmácia: " + e.getMessage());
        }
    }

    public void carregarArquivo(String caminho) {
    try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
        String linha = reader.readLine();

        if (linha != null) {
            String[] dadosFarmacia = linha.split(";");
            if (dadosFarmacia.length >= 3) {
                this.id = dadosFarmacia[0];
                this.nome = dadosFarmacia[1];
                this.farmaceuticoResponsavel = dadosFarmacia[2];
            }
        }

        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split(";");
            if (partes.length >= 8) {
                String nome = partes[0];
                double preco = Double.parseDouble(partes[1]);
                double lucro = Double.parseDouble(partes[2]);
                LocalDate validade = LocalDate.parse(partes[3]);
                int quantidadeProduto = Integer.parseInt(partes[4]);
                double peso = Double.parseDouble(partes[5]);
                int quantidadeEstoque = Integer.parseInt(partes[6]);
                String tipoClasse = partes[7];

                Produto produto = null;
                switch (tipoClasse) {
                    case "MedicamentoComReceita":
                        produto = new MedicamentoComReceita(nome, preco, lucro, validade, quantidadeProduto, peso);
                        break;
                    case "MedicamentoSemReceita":
                        produto = new MedicamentoSemReceita(nome, preco, lucro, validade, quantidadeProduto, peso);
                        break;
                    case "MedicamentoRestrito":
                        produto = new MedicamentoRestrito(nome, preco, lucro, validade, quantidadeProduto, peso);
                        break;
                    default:
                        System.out.println("Tipo de produto desconhecido: " + tipoClasse);
                        continue;
                }

                this.estoque.put(produto, quantidadeEstoque);
            }
        }

        System.out.println("Dados da farmácia carregados com sucesso.");
    } catch (IOException e) {
        System.out.println("Erro ao carregar o arquivo Farmácia: " + e.getMessage());
    }
    }
}
