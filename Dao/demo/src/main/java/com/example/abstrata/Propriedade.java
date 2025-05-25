
package com.example.abstrata;

import com.example.Proprietario;
import java.util.Objects;

public abstract class Propriedade {
    private int id;
    private int disponivel;
    private String titulo;
    private String descricao;
    private String localizacao;
    private int capacidade;
    private double precoPorNoite;
    private String imagem;
    private Proprietario proprietario;

    public Propriedade(int id, String titulo, String descricao, String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario, String imagem) {
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
        if (proprietario == null) {
            throw new IllegalArgumentException("O proprietário não pode ser nulo.");
        }

        this.id = id;
        this.disponivel = 1; // Padrão: disponível
        this.titulo = titulo;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.capacidade = capacidade;
        this.precoPorNoite = precoPorNoite;
        this.proprietario = proprietario;
        this.imagem = imagem;
    }

    public Propriedade(String titulo, String descricao, String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario, String imagem) {
        this(0, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void exibirPropriedade() {
        System.out.println("ID da Propriedade: " + id);
        System.out.println("Está disponível?: " + (disponivel == 1 ? "Sim!" : "Não!"));
        System.out.println("Título: " + titulo);
        System.out.println("Descrição: " + descricao);
        System.out.println("Localização: " + localizacao);
        System.out.println("Capacidade: " + capacidade);
        System.out.println("Preço por noite: " + String.format("%.2f", precoPorNoite));
        if (proprietario != null) {
            System.out.println("O proprietário se chama: " + proprietario.getNome());
        } else {
            System.out.println("Proprietário: Não atribuído");
        }
        System.out.println("Imagem: " + (imagem != null && !imagem.isEmpty() ? imagem : "N/A"));
        System.out.println();
    }

    // Getters e Setters
    public boolean isDisponivel() {
        return disponivel == 1;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel ? 1 : 0;
    }
   
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título da propriedade não pode ser vazio ou conter apenas espaços em branco.");
        }
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        if (localizacao == null || localizacao.trim().isEmpty()) {
            throw new IllegalArgumentException("A localização da propriedade não pode ser vazia ou conter apenas espaços em branco.");
        }
        this.localizacao = localizacao;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade não pode ser igual ou menor que zero.");
        }
        this.capacidade = capacidade;
    }

    public double getPrecoPorNoite() {
        return precoPorNoite;
    }

    public void setPrecoPorNoite(double precoPorNoite) {
        if (precoPorNoite <= 0) {
            throw new IllegalArgumentException("O preço por noite não pode ser menor ou igual a zero.");
        }
        this.precoPorNoite = precoPorNoite;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }
    
    public void setProprietario(Proprietario proprietario) {
        if (proprietario == null) {
            throw new IllegalArgumentException("O proprietário não pode ser nulo.");
        }
        this.proprietario = proprietario;
    }

    // Métodos auxiliares para verificar disponibilidade e aluguel
    public void verificarDisponibilidade() {
        if (disponivel == 1) {
            System.out.println("Esta propriedade está disponível para alugar!");
        } else {
            System.out.println("Esta propriedade não está disponível para alugar!");
        }
    }

    public void verificarAlugada() {
        if (disponivel == 0) {
            System.out.println("Esta propriedade está alugada!");
        } else {
            System.out.println("Esta propriedade não está alugada!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; 
        if (o == null || getClass() != o.getClass()) return false; 

        Propriedade that = (Propriedade) o;

        if (this.id != 0 && that.id != 0) {
            return id == that.id;
        } else {
            return Objects.equals(titulo, that.titulo) &&
                   Objects.equals(localizacao, that.localizacao) &&
                   Objects.equals(proprietario, that.proprietario);
        }
    }

    @Override
    public int hashCode() {
        // Consistente com equals: usa título, localização e proprietário
        return Objects.hash(id, titulo, localizacao, proprietario);
    }

    public abstract double calcularPrecoTotal(int dias);
}
