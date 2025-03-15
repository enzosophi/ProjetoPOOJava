class Propriedade {
    int disponivel;
    String titulo;
    String descricao;
    String localizacao;
    int capacidade;
    double precoPorNoite;
    Usuario proprietario;
    
    public Propriedade(String titulo, String descricao, String localizacao, int capacidade, double precoPorNoite, Usuario proprietario) {
        
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título da propriedade não pode ser vazio ou conter apenas espaços em branco.");
        }
        
        if (localizacao == null || localizacao.trim().isEmpty()) {
            throw new IllegalArgumentException("A localização da propriedade não pode ser vazia ou conter apenas espaços em branco.");
        }
        
        if (capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade não pode ser igual ou menor que zero.");
        }
    
        if (precoPorNoite <= 0) {
            throw new IllegalArgumentException("O preço por noite não pode ser menor ou igual a zero.");
        }
        
        this.disponivel = 1;
        this.titulo = titulo;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.capacidade = capacidade;
        this.precoPorNoite = precoPorNoite;
        this.proprietario = proprietario;
    }
    
    public void exibirPropriedade() {
        if (disponivel == 1) {
            System.out.println("Está disponível?: Sim!");
        }
        else {
            System.out.println("Está disponível?: Não!");
        }
        System.out.println("Título: " + titulo);
        System.out.println("Descrição: " + descricao);
        System.out.println("Localização: " + localizacao);
        System.out.println("Capacidade: " + capacidade);
        System.out.println("Preço por noite: " + precoPorNoite);
        System.out.println("O proprietário se chama: " + proprietario.nome);
    }

    
    public String getTitulo() {
        return titulo;
    }
    
    public int getDisponivel() {
        return disponivel;
    }

    public void verificarDisponibilidade() {
        if(disponivel == 1) {
            System.out.println("Está propriedade está disponível para alugar!");
        }
        
        else {
            System.out.println("Está propriedade não está disponível para alugar!");
        }
    }

    public void alugarPropriedade(){
        if(disponivel ==1){
            disponivel = 0;
            System.out.println("Propriedade " + titulo + " foi alugada com sucesso!");
        }
        else{
            System.out.println("Erro: Esta propriedade já está alugada!");  
        }
    }

    public void verificarAlugada() {
        if(disponivel == 0) {
            System.out.println("Está propriedade está alugada!");
        }
        
        else {
            System.out.println("Está propriedade não está alugada!");
        }
    }
}
