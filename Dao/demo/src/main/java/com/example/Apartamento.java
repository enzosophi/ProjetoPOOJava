package com.example;

import com.example.abstrata.Propriedade;

public class Apartamento extends Propriedade {
    private int andar;
    private double taxa; 

    public Apartamento(String titulo, String descricao, String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario, String imagem, int andar, double taxa) {
        super(titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem);
        if (andar <= 0) {
            throw new IllegalArgumentException("O andar não pode ser menor ou igual a zero.");
        }
        this.andar = andar;
        if (taxa < 0) {
            throw new IllegalArgumentException("A taxa não pode ser negativa.");
        }
        this.taxa = taxa;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        if (andar <= 0) {
            throw new IllegalArgumentException("O andar não pode ser menor ou igual a zero.");
        }
        this.andar = andar;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        if (taxa < 0) {
            throw new IllegalArgumentException("A taxa não pode ser negativa.");
        }
        this.taxa = taxa;
    }

    @Override 
    public void exibirPropriedade() {
        super.exibirPropriedade();
        System.out.println("Tipo: Apartamento");
        System.out.println("Andar: " + andar);
        System.out.println("Taxa Adicional: " + String.format("%.2f", taxa));
    }

    @Override 
    public double calcularPrecoTotal(int dias) {
        return taxa * (getPrecoPorNoite() * dias);
    }
}