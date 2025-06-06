public class Cliente {
       // Atributo que representa o ID único do aluno (gerado pelo banco de dados)
    private int idCliente;
    // Nome do aluno
    private String nome;
    // RA (Registro Acadêmico) do aluno, usado como identificador acadêmico
    private double cpf;
    // Telefone de contato do aluno
    private String telefone;
    // Senha de acesso do aluno (em um sistema real, deveria estar criptografada)
    private String senha;

    // Construtor completo - usado quando todos os dados já estão disponíveis
    public Cliente(int idCliente, String nome, double cpf, String telefone, String senha) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.senha = senha;
    }

    // Construtor sem ID - usado para novos cadastros (ID será gerado pelo banco)
    public Cliente(String nome, double cpf, String telefone, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.senha = senha;
    }

    // Construtor apenas com RA e senha - usado para login/autenticação
    public Cliente(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    // Métodos getters - usados para acessar os valores dos atributos

    public int getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public double getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getSenha() {
        return senha;
    }

    // Métodos setters - usados para modificar os valores dos atributos

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public void setcpf(double cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
