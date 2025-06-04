import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Inicialização do galpão com uma rua e um endereço
        Galpao galpao = new Galpao("Galpao PR", "PR", 0.18, "Dr. Joao Oliveira");
        Rua ruaAA = new Rua("AA");
        Endereco enderecoPadrao = new Endereco("AA", 1, 1);
        ruaAA.adicionarEndereco(enderecoPadrao);
        galpao.adicionarRua(ruaAA);

        // Criação da farmácia e vinculação com o galpão
        Farmacia farmacia = new Farmacia("FAR#PR01", "Farmacia PR", "Dra. Juliana Dark");
        farmacia.setGalpaoPrincipal(galpao);

        int opcao;
        do {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1 - Galpão");
            System.out.println("2 - Farmácia");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> menuGalpao(galpao, sc);
                case 2 -> menuFarmacia(farmacia, sc);
                case 0 -> System.out.println("Encerrando sistema...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        sc.close();
    }

    public static void menuGalpao(Galpao galpao, Scanner sc) {
        int opcao;
        do {
            System.out.println("\n--- Menu Galpão ---");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Visualizar estrutura de estoque");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarProduto(galpao, sc);
                case 2 -> galpao.listarProdutos();
                case 3 -> galpao.visualizarEstoque();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public static void cadastrarProduto(Galpao galpao, Scanner sc) {
        System.out.print("Nome do produto: ");
        String nome = sc.nextLine();

        System.out.print("Preço de custo: ");
        double preco = sc.nextDouble();

        System.out.print("Taxa de lucro (ex: 0.2 para 20%): ");
        double lucro = sc.nextDouble();

        System.out.print("Quantidade: ");
        int quantidade = sc.nextInt();

        System.out.print("Peso por unidade (kg): ");
        double peso = sc.nextDouble();
        sc.nextLine();

        System.out.print("Tipo (com/sem/restrito): ");
        String tipo = sc.nextLine().trim().toLowerCase();

        System.out.print("Data de validade (AAAA-MM-DD): ");
        LocalDate validade = LocalDate.parse(sc.nextLine());

        Produto produto = switch (tipo) {
            case "com" -> new MedicamentoComReceita(nome, preco, lucro, validade, quantidade, peso);
            case "sem" -> new MedicamentoSemReceita(nome, preco, lucro, validade, quantidade, peso);
            case "restrito" -> new MedicamentoRestrito(nome, preco, lucro, validade, quantidade, peso);
            default -> null;
        };

        if (produto == null) {
            System.out.println("Tipo inválido. Produto não criado.");
            return;
        }

        Endereco endereco = galpao.buscarEndereco("AA", 1, 1);
        if (endereco != null && endereco.adicionarProduto(produto)) {
            System.out.println("Produto adicionado com sucesso!");
        } else {
            System.out.println("Erro ao adicionar produto no endereço.");
        }
    }

    public static void menuFarmacia(Farmacia farmacia, Scanner sc) {
        int opcao;
        do {
            System.out.println("\n--- Menu Farmácia ---");
            System.out.println("1 - Vender produto");
            System.out.println("2 - Visualizar estoque");
            System.out.println("3 - Solicitar reposição de produto ao galpão");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> venderProduto(farmacia, sc);
                case 2 -> farmacia.mostrarEstoque();
                case 3 -> solicitarReposicao(farmacia, sc);
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public static void venderProduto(Farmacia farmacia, Scanner sc) {
        System.out.print("Nome do produto: ");
        String nome = sc.nextLine();

        System.out.print("Quantidade desejada: ");
        int quantidade = sc.nextInt();
        sc.nextLine();

        Produto produto = farmacia.buscarProdutoPorNome(nome);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        if (produto.precisaReceita()) {
            System.out.print("Possui receita? (s/n): ");
            String resposta = sc.nextLine();
            if (!resposta.equalsIgnoreCase("s")) {
                System.out.println("Venda cancelada: receita obrigatória.");
                return;
            }
        }

        boolean sucesso = farmacia.processarVenda(produto, quantidade);
        if (sucesso) {
            System.out.println("Venda realizada com sucesso!");
        } else {
            System.out.println("Falha na venda. Verifique estoque e validade.");
        }
    }

    public static void solicitarReposicao(Farmacia farmacia, Scanner sc) {
        System.out.print("Nome do produto a solicitar: ");
        String nome = sc.nextLine();

        System.out.print("Quantidade a solicitar: ");
        int quantidade = sc.nextInt();
        sc.nextLine();

        boolean sucesso = farmacia.solicitarReposicaoDoGalpao(nome, quantidade);
        if (!sucesso) {
            System.out.println("Não foi possível realizar a solicitação.");
        }
    }
}
