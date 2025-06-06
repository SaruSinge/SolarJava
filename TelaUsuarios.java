import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Classe principal da janela gráfica para gerenciar alunos
public class TelaUsuarios extends JFrame {

    // Componentes da tabela
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ClienteDAO dao;

    // Construtor da janela
    public TelaUsuarios() {
        setTitle("Lista de Alunos");           // Título da janela
        setSize(800, 400);                     // Tamanho da janela
        setLocationRelativeTo(null);           // Centraliza a janela na tela
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Fecha a aplicação ao fechar a janela
        dao = new ClienteDAO();                  // Cria instância para acesso ao banco

        // Define os nomes das colunas da tabela
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Senha"};
        modeloTabela = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede edição direta das células da tabela
            }
        };

        // Cria a tabela com o modelo definido
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela); // Adiciona rolagem

        // Painel com botões de ação
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(4, 1, 5, 5)); // 4 linhas, 1 coluna, espaçamento 5px

        // Criação dos botões
        JButton botaoAdicionar = new JButton("Adicionar");
        JButton botaoEditar = new JButton("Editar");
        JButton botaoExcluir = new JButton("Excluir");
        JButton botaoAtualizar = new JButton("Atualizar");

        // Adiciona botões ao painel
        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoAtualizar);

        // Define layout principal da janela
        setLayout(new BorderLayout(10, 10));
        add(scroll, BorderLayout.CENTER);         // Tabela ao centro
        add(painelBotoes, BorderLayout.EAST);     // Botões à direita

        atualizarTabela(); // Carrega dados da tabela

        // Define ação para botão "Adicionar"
        botaoAdicionar.addActionListener(e -> abrirDialogoCliente(null));

        // Define ação para botão "Editar"
        botaoEditar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.");
                return;
            }
            Cliente clienteSelecionado = getClienteDaLinha(linhaSelecionada);
            abrirDialogoCliente(clienteSelecionado);
        });

        // Define ação para botão "Excluir"
        botaoExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão?", "Excluir", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Cliente clienteSelecionado = getClienteDaLinha(linhaSelecionada);
                if (dao.excluir(clienteSelecionado.getIdCliente())) {
                    JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso.");
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir cliente.");
                }
            }
        });

        // Define ação para botão "Atualizar"
        botaoAtualizar.addActionListener(e -> atualizarTabela());

        setVisible(true); // Exibe a janela
    }

    // Atualiza os dados exibidos na tabela
    private void atualizarTabela() {
        List<Cliente> clientes = dao.listar();     // Busca todos os clientes
        modeloTabela.setRowCount(0);           // Limpa tabela atual

        for (Cliente cliente : clientes) {
            Object[] linha = {
                    cliente.getIdCliente(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getTelefone(),
                    cliente.getSenha()
            };
            modeloTabela.addRow(linha); // Adiciona linha na tabela
        }
    }

    // Cria um objeto Aluno com base nos dados da linha selecionada
    private Cliente getClienteDaLinha(int linha) {
        int id = (int) modeloTabela.getValueAt(linha, 0);
        String nome = (String) modeloTabela.getValueAt(linha, 1);
        double cpf = (double) modeloTabela.getValueAt(linha, 3);
        String telefone = (String) modeloTabela.getValueAt(linha, 4);
        String senha = (String) modeloTabela.getValueAt(linha, 5);

        return new Cliente(id, nome, cpf, telefone, senha);
    }

    // Abre um diálogo de cadastro ou edição de aluno
    private void abrirDialogoCliente(Cliente cliente) {
        boolean editar = cliente != null; // Se tem cliente → é edição

        // Campos de entrada
        JTextField campoNome = new JTextField();
        JTextField campoCpf = new JTextField();
        JTextField campoTelefone = new JTextField();
        JTextField campoSenha = new JTextField();

        if (editar) {
            campoNome.setText(cliente.getNome());
            campoCpf.setText(String.valueOf(cliente.getCpf()));
            campoTelefone.setText(cliente.getTelefone());
            campoSenha.setText(cliente.getSenha());
        }

        Object[] campos = {
                "Nome:", campoNome,
                "CPF:", campoCpf,
                "Telefone:", campoTelefone,
                "Senha:", campoSenha
        };

        int opcao = JOptionPane.showConfirmDialog(this, campos, editar ? "Editar Cliente" : "Adicionar Cliente", JOptionPane.OK_CANCEL_OPTION);

        if (opcao == JOptionPane.OK_OPTION) {
            try {
                String nome = campoNome.getText();
                double cpf = Double.parseDouble(campoCpf.getText());
                String telefone = campoTelefone.getText();
                String senha = campoSenha.getText();

                // Validação básica
                if (nome.isEmpty() ||  telefone.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
                    return;
                }

                if (editar) {
                    Cliente clienteEditado = new Cliente(cliente.getIdCliente(), nome, cpf, telefone, senha);
                    if (dao.editar(clienteEditado)) {
                        JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso.");
                        atualizarTabela();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente.");
                    }
                } else {
                    Cliente novoCliente = new Cliente(nome, cpf, telefone, senha);
                    if (dao.adicionar(novoCliente)) {
                        JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso.");
                        atualizarTabela();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao adicionar cliente.");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cpf deve ser um número válido.");
            }
        }
    }

    // Método principal para execução da aplicação
    public static void main(String[] args) {
        // Ajusta aparência da interface conforme o sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Cria e mostra a janela principal
        SwingUtilities.invokeLater(TelaUsuarios::new);
    }
}