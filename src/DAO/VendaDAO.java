package DAO;

import model.Livro;
import model.Venda;


import java.sql.*;

public class VendaDAO {

    private Connection connection;

    public VendaDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarVenda(Venda venda) throws SQLException {
        String sql = "INSERT INTO venda (numVendas, cliente, valor) VALUES (?, ?, ?)";
        int vendaId;

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, venda.getNumero());
            stmt.setString(2, venda.getCliente());
            stmt.setDouble(3, venda.getValor());
            stmt.executeUpdate();

            // Capturar o ID gerado automaticamente
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                vendaId = rs.getInt(1);
            } else {
                throw new SQLException("Erro ao obter o ID da venda.");
            }
        }

        for (Livro livro : venda.getLivros()) {
            if (livro != null) {
                cadastrarLivroVenda(vendaId, livro);
            }
        }
    }

    private void cadastrarLivroVenda(int vendaId, Livro livro) throws SQLException {
        // Consulta para verificar o estoque do livro
        String sqlVerificarEstoque = "SELECT i.id, i.estoque FROM impresso i " +
                "JOIN livro l ON i.id = l.id " +
                "WHERE l.titulo = ? AND l.preco = ?";

        // Inserir na tabela vendalivro
        String sqlInserirVendaLivro = "INSERT INTO vendalivro (venda_id, livro_id) VALUES (?, ?)";

        // Obter o ID do livro
        String sqlLivro = "SELECT id FROM livro WHERE titulo = ? AND preco = ?";

        try (PreparedStatement stmtVerificarEstoque = connection.prepareStatement(sqlVerificarEstoque)) {
            stmtVerificarEstoque.setString(1, livro.getTitulo());
            stmtVerificarEstoque.setDouble(2, livro.getPreco());

            ResultSet rs = stmtVerificarEstoque.executeQuery();

            if (rs.next()) {
                int livroId = rs.getInt("id");
                int estoque = rs.getInt("estoque");

                if (estoque > 0) {
                    // Inserir na tabela vendalivro
                    try (PreparedStatement stmtInserirVenda = connection.prepareStatement(sqlInserirVendaLivro)) {
                        stmtInserirVenda.setInt(1, vendaId);
                        stmtInserirVenda.setInt(2, livroId);
                        stmtInserirVenda.executeUpdate();
                    }
                } else {
                    System.out.println("Estoque insuficiente para o livro: " + livro.getTitulo());
                }
            } else {
                throw new SQLException("Livro não encontrado na tabela 'impresso'.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar livro na venda: " + e.getMessage());
        }
    }


    public void listarVendas() {
        String sql = "SELECT v.id AS venda_id, v.cliente, v.valor, l.id AS livro_id, l.titulo, l.preco " +
                "FROM venda v " +
                "JOIN vendalivro vl ON v.id = vl.venda_id " +
                "JOIN livro l ON vl.livro_id = l.id";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            int currentVendaId = -1;

            while (rs.next()) {
                int vendaId = rs.getInt("venda_id");
                String cliente = rs.getString("cliente");
                double valor = rs.getDouble("valor");

                if (vendaId != currentVendaId) {
                    if (currentVendaId != -1) {
                        System.out.println(); // Linha em branco para separar vendas
                    }

                    System.out.println("Venda ID: " + vendaId);
                    System.out.println("Cliente: " + cliente);
                    System.out.println("Valor: R$ " + valor);
                    System.out.println("Livros:");

                    currentVendaId = vendaId;
                }

                int livroId = rs.getInt("livro_id");
                String titulo = rs.getString("titulo");
                double preco = rs.getDouble("preco");

                System.out.println(" - " + titulo + " - R$ " + preco);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar vendas: " + e.getMessage());
        }
    }
}
