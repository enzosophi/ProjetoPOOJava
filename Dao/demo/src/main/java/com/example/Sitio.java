package com.example;

import com.example.abstrata.Propriedade;

public class Sitio extends Propriedade {
    private double areaTotal;

    public Sitio(String titulo, String descricao, String localizacao, int capacidade, double precoPorNoite, Proprietario proprietario, String imagem, double areaTotal) {
        super(titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem);
        if (areaTotal <= 0) {
            throw new IllegalArgumentException("A área total não pode ser menor ou igual a zero.");
        }
        this.areaTotal = areaTotal;
    }

    public double getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(double areaTotal) {
        if (areaTotal <= 0) {
            throw new IllegalArgumentException("A área total não pode ser menor ou igual a zero.");
        }
        this.areaTotal = areaTotal;
    }

    @Override
    public void exibirPropriedade() {
        super.exibirPropriedade();
        System.out.println("Tipo: Sítio");
        System.out.println("Área Total (m²): " + String.format("%.2f", areaTotal));
    }

    @Override
    public double calcularPrecoTotal(int dias) {
        return getPrecoPorNoite() * dias;
    }
}