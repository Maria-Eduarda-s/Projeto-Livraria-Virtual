import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

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

    public void cadastrarLivro(Connection connection) {
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
            case 1:
                Impresso livroImp = new Impresso(titulo, autores.split(","), editora, preco, 0, 0);

                System.out.println("Digite o valor do frete:");
                livroImp.setFrete(input.nextDouble());
                System.out.println("Digite o estoque:");
                livroImp.setEstoque(input.nextInt());
                input.nextLine();

                ImpressoDAO impressoDAO = new ImpressoDAO(connection);

                try {
                    impressoDAO.cadastrarImpresso(livroImp);
                } catch (SQLException e) {
                    System.out.println("Erro ao cadastrar livro impresso: " + e.getMessage());
                }

                impressos[numImpressos++] = livroImp;
                break;

            case 2:
                Eletronico livroEle = new Eletronico(titulo, autores.split(","), editora, preco, 0);
                System.out.println("Digite o tamanho do arquivo (em KB):");
                livroEle.setTamanho(input.nextDouble());
                input.nextLine();

                EletronicoDAO eletronicoDAO = new EletronicoDAO(connection);

                try {
                    eletronicoDAO.cadastrarEletronico(livroEle);
                } catch (SQLException e) {
                    System.out.println("Erro ao cadastrar livro eletronico: " + e.getMessage());
                }

                eletronicos[numEletronicos++] = livroEle;
                break;

            default:
                System.out.println("Opção inválida.");
        }
    }

    public static void main(String[] args) {
        LivrariaVirtual livraria = new LivrariaVirtual();
        Scanner input = new Scanner(System.in);

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return;
        }

        int opcao;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Cadastrar livro");
            System.out.println("2. Realizar venda");
            System.out.println("3. Listar livros");
            System.out.println("4. Listar vendas");
            System.out.println("5. Sair");

            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    livraria.cadastrarLivro(connection);
                    break;
                /*case 2:
                    livraria.realizarVenda();
                    break;
                case 3:
                    livraria.listarLivros();
                    break;
                case 4:
                    livraria.listarVendas();
                    break;*/
                case 5:
                    System.out.println("Saindo do programa");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 5);

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
        }

        input.close();
    }
}
