import java.time.LocalDate;

public class Main{
    public static void main(String[] args){
        
        //Criação de galpão e rua
        Galpao galpao1 = new Galpao("Galpao PR", "PR", 0.18, "Dr. Joao Oliveira");
        Rua ruaAA = new Rua("AA");
        Endereco endereco1 = new Endereco("AA", 1, 1);
        ruaAA.adicionarEndereco(endereco1);
        galpao1.adicionarRua(ruaAA);

        //Criação de medicamentos
        Produto medComReceita = new MedicamentoComReceita("Amoxilina", 10.00, 0.3, LocalDate.of(2025, 12, 1), 20, 0.5);
        Produto medRestrito = new MedicamentoRestrito("Morfina", 50.0, 0.4, LocalDate.of(2026, 1, 25), 10, 0.3);
        Produto medSemReceita = new MedicamentoSemReceita("Dipirona", 2.0, 0.2, LocalDate.of(2026, 2, 3), 50, 0.2);

        //Vincular produto ao galpão
        endereco1.adicionarProduto(medComReceita);
        endereco1.adicionarProduto(medSemReceita);
        endereco1.adicionarProduto(medRestrito);

        //Criar farmacia e vincular ao galpão
        Farmacia farmacia1 = new Farmacia("FAR#PR01", "Farmacia PR", "Dra. Juliana Dark");
        farmacia1.setGalpaoPrincipal(galpao1);
        farmacia1.adicionarFuncionario("Pedro");

        //Repor estoque
        farmacia1.reporEstoque(medSemReceita, 10);
        farmacia1.reporEstoque(medComReceita, 10);
        farmacia1.reporEstoque(medRestrito, 2);

        //Criar receita
        Receita receita1 = new Receita("Dr Pedro Caminski", "PR-5537", "Rafael Pedroso");


        //Realizar venda
        System.out.println("\n -- Realizando venda com receita -- \n");
        farmacia1.venderProduto(medComReceita, 1, receita1, "Pedro");

        System.out.println("\n -- Realizando venda sem receita -- \n");
        farmacia1.venderProduto(medSemReceita, 1, null, "Pedro");

        System.out.println("\n -- Realizando vende de produto restrito -- \n");
        farmacia1.venderProduto(medRestrito, 1, receita1, "Pedro");


        //Historico de Vendas
        farmacia1.exibirHistoricoVendas();
    }
}