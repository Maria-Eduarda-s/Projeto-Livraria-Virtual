import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class LivroDAO {

    private Connection connection;

    public LivroDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Livro livro) {
        String sql = "INSERT INTO livro (titulo, autores, editora, preco) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, String.join(", ", livro.getAutores()));
            stmt.setString(3, livro.getEditora());
            stmt.setDouble(4, livro.getPreco());

            stmt.executeUpdate();
            System.out.println("Livro salvo com sucesso no banco de dados.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao salvar o livro no banco de dados.");
        }
    }
}
