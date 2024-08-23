public class Eletronico extendes Livro{
    private double tamanho;

    public Eletronico(String titulo, String[] autores, String editora, double preco, double tamanho) {
        super(titulo, autores, editora, preco);
        this.tamanho = tamanho;
    }

    public double getTamanho() {
        return tamanho;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public String toString() {
        return super.toString() + ", O tamanho do arquivo desse livro é : " + tamanho + " KB";
    }
}
