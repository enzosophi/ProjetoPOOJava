public class Usuario {
    String nome;
    String email;
    String senha;

    public Usuario(String nome, String email, String senha) {
        
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia.");
        }
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        
    }

    public void exibirInfo() {
        System.out.println("Nome: " + nome + " | Email: " + email + " | Senha: " + senha);
    }
}
