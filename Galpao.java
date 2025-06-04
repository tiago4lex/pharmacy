import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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

    public void salvarArquivo(String caminho) {
    try (FileWriter writer = new FileWriter(caminho)) {
        writer.write(this.id + ";" + this.estado + ";" + this.aliquota + ";" + this.farmaceuticoResponsavel + "\n");
        for (Rua rua : ruas) {
            for (Endereco endereco : rua.getEnderecos()) {
                for (Produto produto : endereco.getProdutos()) {
                    writer.write(rua.getIdentificador() + ";" + endereco.getSetor() + ";" + endereco.getPrateleira() + ";"
                        + produto.getNome() + ";" + produto.getPrecoCusto() + ";" + produto.getTaxaLucro() + ";"
                        + produto.getValidade() + ";" + produto.getQuantidade() + ";" + produto.getPeso() + ";"
                        + produto.getClass().getSimpleName() + "\n");
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao salvar galpão: " + e.getMessage());
    }

    }

    public void carregarArquivo(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha = reader.readLine();
            if (linha != null) {
                String[] dados = linha.split(";");
                this.id = dados[0];
                this.estado = dados[1];
                this.aliquota = Double.parseDouble(dados[2]);
                this.farmaceuticoResponsavel = dados[3];
            }

            String linhaProduto;
            while ((linhaProduto = reader.readLine()) != null) {
                String[] dados = linhaProduto.split(";");
                String ruaId = dados[0];
                int setor = Integer.parseInt(dados[1]);
                int prateleira = Integer.parseInt(dados[2]);
                String nome = dados[3];
                double preco = Double.parseDouble(dados[4]);
                double lucro = Double.parseDouble(dados[5]);
                LocalDate validade = LocalDate.parse(dados[6]);
                int quantidade = Integer.parseInt(dados[7]);
                double peso = Double.parseDouble(dados[8]);
                String tipo = dados[9];

                Produto produto = switch (tipo) {
                    case "MedicamentoComReceita" -> new MedicamentoComReceita(nome, preco, lucro, validade, quantidade, peso);
                    case "MedicamentoSemReceita" -> new MedicamentoSemReceita(nome, preco, lucro, validade, quantidade, peso);
                    case "MedicamentoRestrito" -> new MedicamentoRestrito(nome, preco, lucro, validade, quantidade, peso);
                    default -> null;
                };

                if (produto != null) {
                    Rua rua = new Rua(ruaId);
                    Endereco endereco = new Endereco(ruaId, setor, prateleira);

                    boolean novaRua = true;
                    for (Rua r : ruas) {
                        if (r.getIdentificador().equalsIgnoreCase(ruaId)) {
                            rua = r;
                            novaRua = false;
                            break;
                        }
                    }
                    if (novaRua) ruas.add(rua);

                    boolean novoEndereco = true;
                    for (Endereco e : rua.getEnderecos()) {
                        if (e.getSetor() == setor && e.getPrateleira() == prateleira) {
                            endereco = e;
                            novoEndereco = false;
                            break;
                        }
                    }
                    if (novoEndereco) rua.adicionarEndereco(endereco);

                    endereco.adicionarProduto(produto);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar galpão: " + e.getMessage());
        }
    }


}
