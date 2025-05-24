package com.example;

import com.example.abstrata.Propriedade;
import com.example.abstrata.Usuario;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva {
    private Propriedade propriedade;
    private Usuario cliente;
    private LocalDate checkin;
    private LocalDate checkout;
    private double custoTotal;

    public Reserva(Propriedade propriedade, Usuario cliente, LocalDate checkin, LocalDate checkout) {
        if (propriedade == null) throw new IllegalArgumentException("Propriedade não pode ser nula.");
        if (cliente == null) throw new IllegalArgumentException("Cliente não pode ser nulo.");
        if (checkin == null || checkout == null) throw new IllegalArgumentException("Datas inválidas.");
        if (checkin.isAfter(checkout)) throw new IllegalArgumentException("Check-in deve ser antes do check-out.");

        this.propriedade = propriedade;
        this.cliente = cliente;
        this.checkin = checkin;
        this.checkout = checkout;

        calcularCustoTotal();
    }

    public void exibirReserva() {
        System.out.println("Reserva para a propriedade: " + propriedade.getTitulo());
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Check-in: " + checkin);
        System.out.println("Check-out: " + checkout);
        System.out.println("Custo total: R$ " + String.format("%.2f", custoTotal));
    }

    public void alugarPropriedade() {
        if (propriedade.isDisponivel()) {
            propriedade.setDisponivel(false);
            System.out.println("Propriedade '" + propriedade.getTitulo() + "' alugada com sucesso!");
        } else {
            System.out.println("A propriedade '" + propriedade.getTitulo() + "' não está disponível para alugar.");
        }
    }

    public double calcularCustoTotal() {
        long dias = ChronoUnit.DAYS.between(checkin, checkout); 
        if (dias < 1) {
            this.custoTotal = propriedade.calcularPrecoTotal(1);
        } else {
            this.custoTotal = propriedade.calcularPrecoTotal((int) dias);
        }
        return this.custoTotal;
    }

    // Getters and Setters
    public Propriedade getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public double getCustoTotal() {
        return custoTotal;
    }
}
