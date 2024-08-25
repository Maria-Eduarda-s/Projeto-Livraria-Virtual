import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VendaDAO {

    private Connection connection;

    public VendaDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarVenda(Venda venda) throws SQLException {
        String sql = "INSERT INTO venda (numero, cliente, valor) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, venda.getNumero());
            stmt.setString(2, venda.getCliente());
            stmt.setDouble(3, venda.getValor());
            stmt.executeUpdate();
        }

        for (Livro livro : venda.getLivros()) {
            if (livro != null) {
                cadastrarLivroVenda(venda.getNumero(), livro);
            }
        }
    }

    private void cadastrarLivroVenda(int numeroVenda, Livro livro) throws SQLException {
        String sql = "INSERT INTO venda_livro (venda_numero, livro_titulo, livro_preco) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numeroVenda);
            stmt.setString(2, livro.getTitulo());
            stmt.setDouble(3, livro.getPreco());
            stmt.executeUpdate();
        }
    }
}
