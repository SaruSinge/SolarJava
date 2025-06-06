import java.sql.*;              // Importa todas as classes da API JDBC para conexões, comandos e resultados SQL.
import java.util.ArrayList;     // Lista dinâmica para armazenar objetos.
import java.util.List;          // Interface usada como tipo genérico para listas.

public class ClienteDAO {

    // Autentica um aluno com base em RA e senha (não usa o id_aluno aqui)
    public boolean autenticar(Cliente cliente) {
        String sql = "SELECT * FROM cliente WHERE nome = ? AND senha = ?";

        // Tenta obter conexão e preparar a consulta com parâmetros
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define os parâmetros da consulta (placeholders ?)
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSenha());

            ResultSet rs = stmt.executeQuery(); // Executa o SELECT

            return rs.next(); // Se encontrou resultado, aluno existe => autenticado
        } catch (SQLException e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
            return false; // Falha na consulta ou credenciais inválidas
        }
    }

    // Lista todos os alunos cadastrados no banco de dados
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>(); // Lista que irá armazenar os objetos Aluno
        String sql = "SELECT id_cliente, nome, cpf, telefone, senha FROM aluno";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Itera sobre os resultados e preenche a lista com objetos Aluno
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getDouble("cpf"),
                        rs.getString("telefone"),
                        rs.getString("senha")
                );
                clientes.add(cliente); // Adiciona à lista
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes; // Retorna a lista (vazia se houver erro)
    }

    // Adiciona um novo aluno no banco
    public boolean adicionar(Cliente cliente) {
        String sql = "INSERT INTO aluno (nome, cpf, telefone, senha) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define os valores que serão inseridos no banco
            stmt.setString(1, cliente.getNome());
            stmt.setDouble(3, cliente.getCpf());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getSenha());

            int rows = stmt.executeUpdate(); // Executa o comando de inserção
            return rows > 0; // Retorna true se pelo menos uma linha foi afetada
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar cliente: " + e.getMessage());
            return false;
        }
    }

    // Atualiza os dados de um aluno existente com base no id_aluno
    public boolean editar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, telefone = ?, senha = ? WHERE id_cliente = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Seta os novos valores para o aluno existente
            stmt.setString(1, cliente.getNome());
            stmt.setDouble(3, cliente.getCpf());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getSenha());
            stmt.setInt(6, cliente.getIdCliente());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao editar cliente: " + e.getMessage());
            return false;
        }
    }

    // Remove um aluno do banco com base no id_aluno
    public boolean excluir(int idCliente) {
        String sql = "DELETE FROM aluno WHERE id_aluno = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente); // Define o id que será removido

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir aluno: " + e.getMessage());
            return false;
        }
    }
}