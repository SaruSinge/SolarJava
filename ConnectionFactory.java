import java.sql.Connection;       // Representa uma conexão com o banco de dados.
import java.sql.DriverManager;    // Utilizado para obter conexões com bancos usando JDBC.
import java.sql.SQLException;     // Classe de exceção usada para tratar erros relacionados a SQL.

public class ConnectionFactory {
    // Define a URL de conexão com o banco de dados.
    // jdbc:mysql:// - protocolo JDBC para banco MySQL
    // localhost:3306 - endereço do servidor e porta
    // universidade - nome do banco de dados
    // useTimezone=true&server=UTC - configurações de fuso horário
    private static String url = 
        "jdbc:mysql://localhost:3306/universidade?useTimezone=true&server=UTC";

    // Usuário e senha do banco de dados (com privilégios para acessar o BD)
    private static String usuario = "root";
    private static String senha = "senhadobancodedados";   

    // Método estático que tenta criar e retornar uma conexão com o banco
    public static Connection getConnection() {
        try {
            // Tenta criar a conexão com o banco de dados
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            // Se conseguiu, imprime confirmação no console
            System.out.println("Conexão estabelecida com sucesso");
            return conexao;
        } catch (SQLException e) {
            // Se falhar, imprime o erro e retorna null
            System.err.println(e.getMessage());
            return null;
        }
    }
}
