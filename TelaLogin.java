// Importações de bibliotecas Swing e AWT para a interface gráfica
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TelaLogin herda de JFrame, ou seja, é uma janela gráfica
public class TelaLogin extends JFrame {

    // Componentes da tela
    private JTextField campoNome;         // Campo para digitar o RA
    private JPasswordField campoSenha;  // Campo para digitar a senha (oculta)
    private JButton botaoLogin;         // Botão que aciona o login

    // Construtor da tela
    public TelaLogin() {
        // Define o título da janela
        setTitle("Login do Cliente");

        // Define o tamanho da janela
        setSize(300, 200);

        // Centraliza a janela na tela
        setLocationRelativeTo(null);

        // Define que o programa será encerrado ao fechar a janela
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Define o layout da janela: 3 linhas, 2 colunas, com espaçamento de 5px
        setLayout(new GridLayout(3, 2, 5, 5));

        // Criação dos rótulos e campos de entrada
        JLabel labelNome = new JLabel("Nome:");
        campoNome = new JTextField(); // Campo de texto para o cpf

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField(); // Campo de senha (oculta os caracteres)

        // Botão para realizar o login
        botaoLogin = new JButton("Entrar");

        // Adiciona os componentes na tela (ordem importa para o GridLayout)
        add(labelNome);
        add(campoNome);
        add(labelSenha);
        add(campoSenha);
        add(new JLabel()); // Cria um espaço vazio (preenchendo o layout 3x2)
        add(botaoLogin);

        // Adiciona um ouvinte de evento ao botão
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtém os valores digitados nos campos
                String nome = campoNome.getText();
                String senha = new String(campoSenha.getPassword());

                // Cria um objeto Aluno com os dados inseridos
                Cliente cliente = new Cliente(nome, senha);

                // Cria uma instância do DAO para acessar o banco de dados
                ClienteDAO dao = new ClienteDAO();

                // Chama o método de autenticação
                if (dao.autenticar(cliente)) {
                    // Mensagem de sucesso
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido!");

                    // Fecha a tela de login
                    dispose();

                    // Abre a próxima tela (ex: dashboard ou tela de usuários)
                    new TelaLogin();
                } else {
                    // Mensagem de erro
                    JOptionPane.showMessageDialog(null, "Nome ou senha inválidos.");
                }
            }
        });

        // Torna a janela visível
        setVisible(true);
    }
}
