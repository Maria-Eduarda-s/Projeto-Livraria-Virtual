import java.util.ArrayList;
import java.util.List;

public class Venda {
    private static int numVendas = 0;
    private int numero;
    private String cliente;
    private double valor;
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

    public List<String> listarLivros() {
        List<String> resultado = new ArrayList<>();
        if (livros.isEmpty()) {
            resultado.add("Nenhum livro foi vendido.");
        } else {
            resultado.add("Livros comprados:");
            for (Livro livro : livros) {
                resultado.add(livro.toString());
            }
        }
        return resultado;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
