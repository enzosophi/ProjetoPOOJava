package com.example;

import com.example.abstrata.Propriedade;
import com.example.abstrata.Usuario; // Assumindo que Cliente herda de Usuario

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Reserva {
    private int id; // Adicionado o ID para persistência
    private Propriedade propriedade;
    private Usuario cliente;
    private LocalDate checkin;
    private LocalDate checkout;
    private double custoTotal;

    // Novos campos para armazenar IDs de FKs
    private int propriedadeId;
    private int clienteId;

    // Construtor para quando o ID é conhecido (e.g., vindo do banco de dados)
    public Reserva(int id, Propriedade propriedade, Usuario cliente, LocalDate checkin, LocalDate checkout) {
        if (propriedade == null) {
            throw new IllegalArgumentException("Propriedade não pode ser nula para reserva.");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo para reserva.");
        }
        if (checkin == null || checkout == null) {
            throw new IllegalArgumentException("As datas de check-in e check-out não podem ser nulas.");
        }
        if (checkin.isAfter(checkout)) {
            throw new IllegalArgumentException("A data de check-in deve ser anterior à data de check-out.");
        }
        if (checkin.isBefore(LocalDate.now())) { // Não permitir reservas no passado
            // Apenas um aviso, pode ser ajustado conforme a regra de negócio
            System.out.println("Atenção: A data de check-in é no passado. Esta reserva pode não ser válida para novos agendamentos.");
        }

        this.id = id;
        this.propriedade = propriedade;
        this.cliente = cliente;
        this.checkin = checkin;
        this.checkout = checkout;

        // Armazenar os IDs para persistência
        this.propriedadeId = propriedade.getId();
        this.clienteId = cliente.getId();

        calcularCustoTotal();
    }

    // Construtor para quando o ID ainda não é conhecido (e.g., nova criação)
    public Reserva(Propriedade propriedade, Usuario cliente, LocalDate checkin, LocalDate checkout) {
        this(0, propriedade, cliente, checkin, checkout); // Chama o outro construtor com ID 0
    }

    // Getter e Setter para o ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(int propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }


    public void exibirReserva() {
        System.out.println("ID da Reserva: " + id); // Adicionado o ID
        System.out.println("Reserva para a propriedade: " + propriedade.getTitulo() + " (ID: " + propriedade.getId() + ")");
        System.out.println("Cliente: " + cliente.getNome() + " (ID: " + cliente.getId() + ")");
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

    // ... (restante dos getters e setters)

    public Propriedade getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
        if (propriedade != null) {
            this.propriedadeId = propriedade.getId();
        }
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
        if (cliente != null) {
            this.clienteId = cliente.getId();
        }
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

    public void setCustoTotal(double custoTotal) {
        this.custoTotal = custoTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        // Para reservas, o ID é o identificador único no banco de dados.
        // Se o ID for 0, significa que ainda não foi persistido, então comparamos por outros campos.
        if (this.id != 0 && reserva.id != 0) {
            return id == reserva.id;
        } else {
            return Objects.equals(propriedade, reserva.propriedade) &&
                   Objects.equals(cliente, reserva.cliente) &&
                   Objects.equals(checkin, reserva.checkin) &&
                   Objects.equals(checkout, reserva.checkout);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, propriedade, cliente, checkin, checkout);
    }

    @Override
    public String toString() {
        return "Reserva ID: " + id +
               "\nCliente: " + cliente.getNome() +
               "\nPropriedade: " + propriedade.getTitulo() +
               "\nCheck-in: " + checkin +
               "\nCheck-out: " + checkout +
               "\nCusto total: R$" + String.format("%.2f", custoTotal) + "\n";
    }
}