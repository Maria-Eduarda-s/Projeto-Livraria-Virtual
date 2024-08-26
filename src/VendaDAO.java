import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendaDAO {

    private Connection connection;

    public VendaDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarVenda(Venda venda) throws SQLException {
        String sql = "INSERT INTO venda (numVendas, cliente, valor) VALUES (?, ?, ?)";

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
        String sql = "INSERT INTO vendalivro (venda_id, livro_id) VALUES (?, ?)";

        // Obtendo o ID do livro pra inserir na tabela
        String sqlLivro = "SELECT id FROM livro WHERE titulo = ? AND preco = ?";

        try (PreparedStatement stmtLivro = connection.prepareStatement(sqlLivro)) {
            stmtLivro.setString(1, livro.getTitulo());
            stmtLivro.setDouble(2, livro.getPreco());

            ResultSet rs = stmtLivro.executeQuery();

            if (rs.next()) {
                int livroId = rs.getInt("id");

                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, numeroVenda);
                    stmt.setInt(2, livroId);
                    stmt.executeUpdate();
                }
            } else {
                throw new SQLException("Livro não encontrado no banco de dados.");
            }
        }
    }

    public void listarVendas(){

    }
}
