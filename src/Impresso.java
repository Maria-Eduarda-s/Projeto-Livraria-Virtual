

public class Impresso {
    private double frete;
    private int estoque;

    public Impresso(double frete, int estoque) {
        this.frete = frete;
        this.estoque = estoque;
    }

    public void atualizarEstoque() {
        if (estoque > 0) {
            estoque--;
        } else {
            System.out.println("Estoque zerado.");
        }
    }

    @Override
    public String toString() {
        return "Livro [Frete: " + frete + "\nEstoque:" + estoque + "]";
    }

    public double getFrete() {
        return frete;
    }

    public void setFrete(double frete) {
        this.frete = frete;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

}