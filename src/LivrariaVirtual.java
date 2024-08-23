import java.util.*;

public class LivrariaVirtual extends Livro{

    private final int MAX_IMPRESSOS = 10;
    private final int MAX_ELETRONICOS = 20;
    private final int MAX_VENDAS = 50;
    private numImpressos;
    private numEletronicos;
    private numVendas;
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

    //Codigo de repete, necessario otimizar
    public void cadastrarLivro(int opcao) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Livro Impresso");
        System.out.println("2. Livro Digital");

        // Impresso
        if (opcao == 1) {
            if (numImpressos >= MAX_IMPRESSOS) {
                System.out.println("Não há mais espaço para livros impressos.");
            } else {
                Impresso livroImp = new Impresso();
                System.out.println("Digite o título do livro:");//repetido
                livroImp.setTitulo(scanner.nextLine());//repetido
                System.out.println("Digite o(s) autor(es):");//repetido
                livroImp.setAutores(scanner.nextLine());//repetido
                System.out.println("Digite a editora:");//repetido
                livroImp.setEditora(scanner.nextLine());
                System.out.println("Digite o preço:");//repetido
                livroImp.setPreco(scanner.nextDouble());
                scanner.nextLine();

                impressos[numImpressos++] = livroImp;
            }
        }
        // Eletrônico
        else if (opcao == 2) {
            if (numEletronicos >= MAX_ELETRONICOS) {
                System.out.println("Não há mais espaço para livros eletrônicos.");
            } else {
                Eletronico livroEle = new Eletronico();
                System.out.println("Digite o título do livro:"); //repetido
                livroEle.setTitulo(scanner.nextLine());//repetido
                System.out.println("Digite o(s) autor(es):");//repetido
                livroEle.setAutores(scanner.nextLine());
                System.out.println("Digite a editora:");//repetido
                livroEle.setEditora(scanner.nextLine());
                System.out.println("Digite o preço:");//repetido
                livroEle.setPreco(scanner.nextDouble());
                System.out.println("Digite o tamanho do arquivo (em KB):");
                livroEle.setTamanho(scanner.nextDouble());
                scanner.nextLine();

                eletronicos[numEletronicos++] = livroEle;
            }
        }
    }

    public void realizarVenda() {

    }

    public void  listarLivrosImpressos(){

    }

    public void listarLivrosEletronicos(){

    }

    public void listarLivros(){

    }

    public void listarVendas(){

    }

    public class main(args: String[]{

    }

}