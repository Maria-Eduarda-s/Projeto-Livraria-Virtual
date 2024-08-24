import java.util.Scanner;

public class LivrariaVirtual {

    private final int MAX_IMPRESSOS = 10;
    private final int MAX_ELETRONICOS = 20;
    private final int MAX_VENDAS = 50;
    private int numImpressos;
    private int numEletronicos;
    private int numVendas;
    private Impresso[] impressos;
    private Eletronico[] eletronicos;
    private Venda[] vendas;

    public LivrariaVirtual() {
        this.impressos = new Impresso[MAX_IMPRESSOS];
        this.eletronicos = new Eletronico[MAX_ELETRONICOS];
        this.vendas = new Venda[MAX_VENDAS];
        this.numImpressos = 0;
        this.numEletronicos = 0;
        this.numVendas = 0;
    }

    public void cadastrarLivro() {
        Scanner input = new Scanner(System.in);

        System.out.println("Qual o tipo de livro que você deseja cadastrar?");
        System.out.println("1. Impresso");
        System.out.println("2. Eletrônico");
        int opcao = input.nextInt();
        input.nextLine();

        if (opcao == 1 && numImpressos >= MAX_IMPRESSOS) {
            System.out.println("Não há mais espaço para livros impressos.");
            return;
        }
        if (opcao == 2 && numEletronicos >= MAX_ELETRONICOS) {
            System.out.println("Não há mais espaço para livros eletrônicos.");
            return;
        }

        // Atributos comuns a ambos os tipos
        System.out.println("Digite o título do livro:");
        String titulo = input.nextLine();
        System.out.println("Digite o autor(a):");
        String autores = input.nextLine();
        System.out.println("Digite a editora:");
        String editora = input.nextLine();
        System.out.println("Digite o preço:");
        double preco = input.nextDouble();
        input.nextLine();

        switch (opcao) {
            case 1: // Impresso
                Impresso livroImp = new Impresso(titulo, autores.split(","), editora, preco, 0, 0);
                System.out.println("Digite o valor do frete:");
                livroImp.setFrete(input.nextDouble());
                System.out.println("Digite o estoque:");
                livroImp.setEstoque(input.nextInt());
                input.nextLine();

                impressos[numImpressos++] = livroImp;
                break;

            case 2: // Eletrônico
                Eletronico livroEle = new Eletronico(titulo, autores.split(","), editora, preco, 0);
                System.out.println("Digite o tamanho do arquivo (em KB):");
                livroEle.setTamanho(input.nextDouble());
                input.nextLine();

                eletronicos[numEletronicos++] = livroEle;
                break;

            default:
                System.out.println("Opção inválida.");
        }
    }

    public void realizarVenda() {
        Scanner input = new Scanner(System.in);
        System.out.println("Digite o nome do cliente:");
        String nomeCliente = input.nextLine();
        System.out.println("Digite a quantidade de livros que o cliente deseja comprar:");
        int quantidade = input.nextInt();
        input.nextLine();

        Venda venda = new Venda(nomeCliente, 0); // O valor de 0 será ajustado depois

        for (int i = 0; i < quantidade; i++) {
            System.out.println("Qual o tipo de livro que você deseja adquirir?");
            System.out.println("1. Impresso");
            System.out.println("2. Eletrônico");
            int opcao = input.nextInt();
            input.nextLine();

            if (opcao == 1) {
                listarLivrosImpressos();
                System.out.println("Digite o ID do livro impresso que deseja comprar:");
                int indice = input.nextInt() - 1;  // Subtrai 1 para ajustar ao índice do array
                if (indice >= 0 && indice < numImpressos) {
                    Impresso livroSelecionado = impressos[indice];
                    if (livroSelecionado.getEstoque() > 0) {
                        venda.addLivro(livroSelecionado, i);
                        venda.setValor(venda.getValor() + livroSelecionado.getPreco());
                        livroSelecionado.atualizarEstoque(); // Reduz o estoque do livro impresso
                    } else {
                        System.out.println("Estoque insuficiente para o livro selecionado.");
                        i--;
                    }
                }
                else {
                    System.out.println("Índice inválido.");
                    i--;
                }
            } else if (opcao == 2) {
                listarLivrosEletronicos();
                System.out.println("Digite o número do livro eletrônico que deseja comprar:");
                int indice = input.nextInt() - 1;
                input.nextLine();
                if (indice >= 0 && indice < numEletronicos) {
                    Eletronico livroSelecionado = eletronicos[indice];
                    venda.addLivro(livroSelecionado, i);
                    venda.setValor(venda.getValor() + livroSelecionado.getPreco()); // Atualiza o valor total
                } else {
                    System.out.println("Índice inválido.");
                    i--;
                }
            } else {
                System.out.println("Opção inválida.");
                i--;
            }
        }

        if (numVendas < vendas.length) {
            vendas[numVendas] = venda;
            numVendas++; // Incrementa o contador de vendas
        } else {
            System.out.println("Não há mais espaço para novas vendas.");
        }

        System.out.println("Resumo da compra:");
        venda.listarLivros();
        System.out.println("Valor total da venda: R$ " + venda.getValor());
        System.out.println("Venda concluída para " + nomeCliente + "! Agradecemos pela compra!");
    }


    public void listarLivrosImpressos() {
        if (numImpressos == 0) {
            System.out.println("Não há livros impressos cadastrados");
            return;
        }

        // Cabeçalhos da tabela
        System.out.printf("%-4s %-60s%n", "ID", "Detalhes");

        // Exibindo dados dos livros
        for (int i = 0; i < numImpressos; i++) {
            Impresso livro = impressos[i];
            System.out.printf("%-4d %-60s%n", i + 1, livro.toString());
        }
    }


    public void listarLivrosEletronicos() {
        if (numEletronicos == 0) {
            System.out.println("Não há livros eletrônicos cadastrados");
            return;
        }

        // Cabeçalhos da tabela
        System.out.printf("%-4s %-60s%n", "ID", "Detalhes");

        // Exibindo dados dos livros
        for (int i = 0; i < numEletronicos; i++) {
            Eletronico livro = eletronicos[i];
            System.out.printf("%-4d %-60s%n", i + 1, livro.toString());
        }
    }

    public void listarLivros(){

        if(numEletronicos == 0 && numImpressos == 0)
            System.out.println("Não há livros cadastrados");
        else if (numEletronicos !=0 && numImpressos == 0)
            listarLivrosEletronicos();
        else if (numEletronicos == 0 && numImpressos !=0)
            listarLivrosImpressos();
        else {
            //Impressos
            System.out.println("Impressos:");
            listarLivrosImpressos();
            //Eletronicos
            System.out.println("Eletrônicos:");
            listarLivrosEletronicos();
        }
    }

    public void listarVendas() {
        if (numVendas == 0) {
            System.out.println("Nenhuma venda foi realizada até o momento.");
            return;
        }

        // Cabeçalhos da tabela
        String[] headers = { "Nome do Cliente", "Quantidade de Livros", "Valor Total (R$)" };

        // Dados das vendas
        String[][] data = new String[numVendas][3];

        for (int i = 0; i < numVendas; i++) {
            Venda venda = vendas[i];
            data[i][0] = venda.getCliente();
            data[i][1] = String.valueOf(venda.listarLivros());


            data[i][2] = String.format("%.2f", venda.getValor());
        }

        // Exibindo cabeçalhos
        System.out.printf("%-20s %-20s %-20s%n", headers[0], headers[1], headers[2]);
        System.out.println("---------------------- -------------------- --------------------");

        // Exibindo dados
        for (String[] row : data) {
            System.out.printf("%-20s %-20s %-20s%n", row[0], row[1], row[2]);
        }
    }


    public static void main(String[] args) {
        LivrariaVirtual livraria = new LivrariaVirtual();
        Scanner input = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Cadastrar livro");
            System.out.println("2. Realizar venda");
            System.out.println("3. Listar livros impressos");
            System.out.println("4. Listar livros eletrônicos");
            System.out.println("5. Listar livros");
            System.out.println("6. Listar vendas");
            System.out.println("7. Sair");

            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    livraria.cadastrarLivro();
                    break;
                case 2:
                    livraria.realizarVenda();
                    break;
                case 3:
                    livraria.listarLivrosImpressos();
                    break;
                case 4:
                    livraria.listarLivrosEletronicos();
                    break;
                case 5:
                    livraria.listarLivros();
                    break;
                case 6:
                    livraria.listarVendas();
                    break;
                case 7:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 7);

        input.close();
    }
}
