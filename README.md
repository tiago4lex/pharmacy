# pharmacy
---

## Etapa 1: Entendendendo o domínio e modelando as classes principais:

Antes de escrever qualquer código é necessário pensar nas **entidades** envolvidas no problema.

**Desafio:** Identificar as classes principais e pensar nos atributos que cada uma deve ter

---

### Análise do enunciado
Temos os seguintes elementos importantes

1. **Produto** com subclasses ou tipos: medicamentos sem receita, com receita, restritos, pericíveis, higiene

2. **Endereço de Armazenamento**: rua, posição, altura ou pallet

3. **Galpão**: com localização, alíquota, farmacêutic responsável, etc.

4. **Farmácia**: estoque reduzido, pedidos ao galpão

5. **Funcionários e Farmacêuticos**

6. **Clientes e vendas**: para medicamentos restritos, CRM, nome do médico e paciente

7. **Venda**: com agrupamento de produtos e cálculo de lucro

8. **Regras de validade e restrição de saída**

---

## Etapa 2: Criar a estrutura básica das classes

Vamos começar com as classes de produto e pensar em herança para facilitar o controle dos tipos.

### 1. Classe abstrata `Produto`

**Por que?**
Todos os produtos compartilham atributos como nome, peso, validade, preço, etc. Mas alguns têm regras específicas (como exigência de receita).

**Atributos da classe `Produto`(privados):**

- ``nome: String``
- ``precoCusto: double``
- ``taxaLucro: double``
- ``validade: LocalDate``
- `` quantidade: int``    
- ``peso: double``

**Métodos esperados (públicos):**

- ``Produto(nome, precoCusto, taxaLucro, validade, quantidade, peso)``
- ``getNome(): String``
- ``getPrecoCusto(): double``
- ``getTaxaLucro(): double``
- ``getValidade(): LocalDate``
- ``getQuantidade(): int``
- ``getPeso(): double``
- ``setQuantidade(int quantidade): void``
- ``calcularPrecoVenda(aliquota: double): double``
- ``estaVencido(): boolean``

> _Obs: `precoVenda` não é armazenado diretamente, ele é calculado._ 

### 2. Subclasse para medicamentos

#### 1. `MedicamentoSemReceita` (herda de `Produto`)

**Descrição:**
Produto normal, sem necessidade de receita. Nenhum atributo ou método adicional é necessário.

**Atributos**

- Nenhum novo atributo

**Métodos esperados(públicos):**

- Construtor herdade de `Produto`
- Não possui métodos específicos

#### 2. `Medicamento com Receita` (herda de `Produto`)

**Descrição:**
Venda exige receita, com dados do médico, CRM e paciente.
Portanto, precisamos de uma classe ``Receita`` com esses dados, e associá-la ao produto no momento da venda.

**Atributos:**

- Nenhum novo atributo (receita é tratada na venda)

**Métodos:**

- ``exigeReceita(): boolean`` → retorna ``true``

#### 3. Classe `MedicamentoRestrito` (herda de `Produto`)

**Descrição:**
Além da **receita obrigatória**, é necessário **emitir alerta** antes da saída do galpão.

**Atributo:**

- Nenhum novo atributo

**Métodos esperados (públicos):**

- ``exigeReceita(): boolean`` → retorna ``true``
- ``emitirAlerta():void`` → imprime ou dispara alerta de uso restrito

#### 4. Classe `Receita`

**Atributos (privados):**

- ``nomeMedico: String``
- ``crm: String``
- ``nomePaciente: String``

**Métodos esperados (públicos):**

- ``Receita(nomeMedico, crm, nomePaciente)``
- ``getNomeMedico(): String``
- ``getCrm(): String``
- ``getNomePaciente(): String``

> _Note que a associação com `Receita` é feita na VENDA, e não no produto_

---

## Etapa 3: Estrutura dos galpões, ruas, endereços e prateleiras

De acordo com o contrato, o estoque é fisicamente organizado da seguinte forma:

> Os galpões estão em estados diferentes. Dentros dos galpoes há ruas, e em cada rua há endereços.
> Endereços podem ser:
> - "Com  prateleiras (máximo 20kg)"
> - "Sem prateleiras (guardam pallets)"

### Modelagem Proposta

#### 1. Galpão

- Localizado em um **estado diferente**
- Possui:
    - Código ou nome identificador
    - Lista de **ruas**
    - **Alíquota estadua**
    - Farmacêutico responsável
    - Lista de colaboradores

#### 2. Rua

- Representa uma rua dentro do galpão
- Identificador com **duas letras** (ex: "AA")
- Possui:
    - Lista de **endereços**

#### 3. Endereço

- Representa um ponto de armazenemento
- Padrão: **\[Rua\]\[Posição\]\[Altura\]** (Ex: AA0101 = rua AA, posição 01, altura 01)
- Pode ser:
    - **Com prateleira** (suporta no máximo 20kg)
    - **Sem prateleira** (usado para pallets, sem limitação)

### Criação das Classes

#### 1. Classe `Galpao`

**Atributos (privados):**

- ``ìd: String`` (ex: "GalpaoRS)
- ``estado: String``
- ``aliquota: double``
- ``farmaceuticoResponsavel: String``
- ``colaboradores: String[]``
- ``ruas: String[]``

**Métodos esperado (públicos):**

- ``Galpao(id, estado, aliquota, farmaceuticoResponsavel)``
- ``getId(): String``
- ``getAliquota(): double``
- ``getEstado(): String``
- ``getRuas(): Rua[]``
- ``adicionarRua(Rua rua): void``
- ``adicionarColaborador(String nome): void``

#### 2. Classe `Rua`

**Atributos (privados):**

- ``identificador: String`` (ex: "AA")
- ``enderecos: Endereco[]``

**Métodos esperados (públicos):**

- ``Rua(identificador)``
- ``getIdentificador(): String``
- ``getEnderecos(): Endereco[]``
- ``adicionarEndereco(Endereco e): void``

#### 3. Classe `Endereco`

**Atributos (privados):**

- ``rua: String (letras)``
- ``posicao: int (2 dígitos)``
- ``altura: int (2 dígitos)``
- ``possuiPrateleira: boolean``
- ``produtos: Produto[]``

**Métodos esperados (públicos):**

- ``Endereco(rua, posicao, altura, possuiPrateleira)``
- ``getCodigoEndereco(): String`` → retorna ex: "AA0101"
- ``adicionarProduto(Produto p): boolean``
    - (retorna `false` se ultrapassar 20kg em prateleiras)
- ``getPesoTotal(): double``
- ``getProdutos(): Produto[]``

## Validações importantes

- Quando um produto é **armazenado** em um endereço com prateleira, o sistema deve somar o peso total dos produtos. Se ultrapassar 20kg, **não permite**.

- Produtos **vencidos não devem sair** do galpão.

- Produtos **restritos disparam alerta** ao sair.

---

## Etapa 4: Modelagem da **Farmácia** e do **processo de Venda**

### O que o contrato exige?

1. Farmácias têm:
- Estoque reduzido de medicamentos
- Farmacêutico responsável
- Funcionários que realizam vendas
- Devem solicitar **reposição ao galpão** quando o estoque estiver zerado

2. Vendas de medicamentos **restritos ou com receita** exigem:
- Cadastro de receita (CRM, nome do médico e nome do paciente)

3. O sistema deve ter:
- **Calcular taxa de lucro**, que varia por produto
- Agrupar os produtos de uma venda (como no caixa)
- Registrar a venda (com data, cliente, funcionario, produtos vendidos)

### Modelagem das Classes

#### 1. Classe `Farmácia`:

**Atributos (privados):**

- ``ìd: String``
- ``nome: String``
- ``farmaceuticoResponsavel: String``
- ``funcionarios: String[]``
- ``estoque: map.Produto Integer`` 

**Métodos esperados (públicos):**

- ``Farmacia(id, nome, farmaceuticoResponsavel)``
- ``adicionarFuncionario(String nome): void``
- ``adicionarProduto(Produto produto, int quantidade): void``
- ``venderProduto(Produto produto, int quantidade, Receita receita, String funcionario): Venda``
- ``reporEstoque(Produto produto, int quantidade): void``
- ``getEstoque(): map.Produto Integer``

#### 2. Classe `Venda`

**Atributos (privados):**

- ``id: int``
- ``produtosVendidos: Produto[]``
- ``data: LocalDateTime``
- ``receita: Receita (opcional, pode ser null)``
- ``valorTotal: double``

**Métodos esperados (públicos):**

- ``Venda(id, produtos, datam funcionario, receita)``
- ``calcularValorTotal(): double``
- ``getData(): LocalDateTime``
- ``getProdutosVendidos: Produto[]``
- ``getFuncionario(): String``
- ``getReceita(): Receita``

### Validações no precesso de venda

- Se o produto for vencido, **não pode vender**
- Se for **medicamento com receita** ou **restrito**, deve **fornecer Receita válida**
- **Restritos disparam alerta**
- Após a venda, o estoque da farmácia é atualizado
- Se o estoque chegar a zero, é possível chamar `reporEstoque()` (futura integração com galpão)

### Sobre o lucro

- o lucro de cada produto é calculado por:

```
precoVenda = precoCusto + (precoCusto * taxaLucro) + (precoCusto * aliquataEstado)
```

### Sobre a receita

Já temos a classe `Receita` com:

- ``nomeMedico: String``
- ``crm: String``
- ``nomePaciente: String``

Ela será passada como argumento apenas se o produto exigir receita.