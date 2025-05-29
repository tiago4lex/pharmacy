import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Criação de galpão e rua
        Galpao galpao1 = new Galpao("Galpao PR", "PR", 0.18, "Dr. Joao Oliveira");
        Rua ruaAA = new Rua("AA");
        Endereco endereco1 = new Endereco("AA", 1, 1);
        ruaAA.adicionarEndereco(endereco1);
        galpao1.adicionarRua(ruaAA);

        // Criação de medicamentos
        Produto medComReceita = new MedicamentoComReceita("Amoxilina", 10.00, 0.3, LocalDate.of(2025, 12, 1), 20, 0.5);
        Produto medRestrito = new MedicamentoRestrito("Morfina", 50.0, 0.4, LocalDate.of(2026, 1, 25), 10, 0.3);
        Produto medSemReceita = new MedicamentoSemReceita("Dipirona", 2.0, 0.2, LocalDate.of(2026, 2, 3), 50, 0.2);

        // Vincular produto ao galpão
        endereco1.adicionarProduto(medComReceita);
        endereco1.adicionarProduto(medSemReceita);
        endereco1.adicionarProduto(medRestrito);

        // Criar farmácia e vincular ao galpão
        Farmacia farmacia1 = new Farmacia("FAR#PR01", "Farmacia PR", "Dra. Juliana Dark");
        farmacia1.setGalpaoPrincipal(galpao1);
        farmacia1.adicionarFuncionario("Pedro");

        // Repor estoque
        farmacia1.reporEstoque(medSemReceita, 10);
        farmacia1.reporEstoque(medComReceita, 10);
        farmacia1.reporEstoque(medRestrito, 2);

        // Criar receita
        Receita receita1 = new Receita("Dr Pedro Caminski", "PR-5537", "Rafael Pedroso");

        // Menu principal
        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Acessar Galpão");
            System.out.println("2 - Acessar Farmácia");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    galpao1.exibirRelatorioGeralDePeso();
                    break;

                case 2:
                    System.out.println("\n -- Realizando venda com receita -- \n");
                    farmacia1.venderProduto(medComReceita, 1, receita1, "Pedro");

                    System.out.println("\n -- Realizando venda sem receita -- \n");
                    farmacia1.venderProduto(medSemReceita, 1, null, "Pedro");

                    System.out.println("\n -- Realizando venda de produto restrito -- \n");
                    farmacia1.venderProduto(medRestrito, 1, receita1, "Pedro");

                    farmacia1.exibirHistoricoVendas();
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        sc.close();
    }
}
