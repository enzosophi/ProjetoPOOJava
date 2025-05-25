package com.example;

import com.example.abstrata.Propriedade;

public class Casa extends Propriedade {
    private boolean possuiPiscina;
    private double precoPorPessoa;

    // Construtor para quando o ID é conhecido (e.g., vindo do banco de dados)
    public Casa(int id, String titulo, String descricao, String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario, String imagem, boolean possuiPiscina, double precoPorPessoa) {
        super(id, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem);
        this.possuiPiscina = possuiPiscina;
        if (precoPorPessoa < 0) {
            throw new IllegalArgumentException("O preço por pessoa não pode ser negativo.");
        }
        this.precoPorPessoa = precoPorPessoa;
    }

    // Construtor para quando o ID ainda não é conhecido (e.g., nova criação)
    public Casa(String titulo, String descricao, String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario, String imagem, boolean possuiPiscina, double precoPorPessoa) {
        super(titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem);
        this.possuiPiscina = possuiPiscina;
        if (precoPorPessoa < 0) {
            throw new IllegalArgumentException("O preço por pessoa não pode ser negativo.");
        }
        this.precoPorPessoa = precoPorPessoa;
    }

    public boolean isPossuiPiscina() {
        return possuiPiscina;
    }

    public void setPossuiPiscina(boolean possuiPiscina) {
        this.possuiPiscina = possuiPiscina;
    }

    public double getPrecoPorPessoa() {
        return precoPorPessoa;
    }

    public void setPrecoPorPessoa(double precoPorPessoa) {
        if (precoPorPessoa < 0) {
            throw new IllegalArgumentException("O preço por pessoa não pode ser negativo.");
        }
        this.precoPorPessoa = precoPorPessoa;
    }

    @Override
    public void exibirPropriedade() {
        super.exibirPropriedade();
        System.out.println("Tipo: Casa");
        System.out.println("Possui Piscina: " + (possuiPiscina ? "Sim" : "Não"));
        System.out.println("Preço por Pessoa: " + String.format("%.2f", precoPorPessoa));
    }

    @Override
    public double calcularPrecoTotal(int dias) {
        return (getPrecoPorNoite() + (precoPorPessoa * getCapacidade())) * dias;
    }
}