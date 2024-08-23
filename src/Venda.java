import java.util.ArrayList;
import java.util.List;

public class Venda {
    private static int numVendas = 0;
    private int numero;
    private String cliente;
    private float valor;
    private List<Livro> livros;

    public Venda(String cliente, float valor) {
        this.numero = ++numVendas;
        this.cliente = cliente;
        this.valor = valor;
        this.livros = new ArrayList<>();
    }

    public void addLivro(Livro l, int index) {
        if (index >= 0 && index <= livros.size()) {
            livros.add(index, l);
        } else {
            System.out.println("Índice inválido!");
        }
    }

    public void listarLivros() {
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro à venda.");
        } else {
            System.out.println("Livros à venda:");
            for (Livro livro : livros) {
                System.out.println(livro);
            }
        }
    }

    public int getNumero() {
        return numero;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
