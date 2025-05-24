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

        long dias = ChronoUnit.DAYS.between(checkin, checkout);
        this.custoTotal = propriedade.getPrecoPorNoite() * dias;
    }

    public void exibirReserva() {
        System.out.println("Reserva para a propriedade: " + propriedade.getTitulo());
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Check-in: " + checkin);
        System.out.println("Check-out: " + checkout);
        System.out.println("Custo total: R$ " + custoTotal);
    }

    public void alugar() {
        if (propriedade.isDisponivel()) {
            propriedade.setDisponivel(false);
            System.out.println("Propriedade alugada com sucesso!");
        } else {
            System.out.println("Erro: Propriedade já está alugada.");
        }
    }
}
