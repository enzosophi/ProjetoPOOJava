package com.example;

import com.example.abstrata.Propriedade;
import com.example.abstrata.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Importe Objects para o equals e hashCode

public class Cliente extends Usuario {

    private List<Reserva> reservasRealizadas;

    // Construtor para quando o ID é conhecido (e.g., vindo do banco de dados)
    public Cliente(int id, String nome, String email, String senha) {
        super(id, nome, email, senha); // Chama o construtor de Usuario com ID
        this.reservasRealizadas = new ArrayList<>();
    }

    // Construtor para quando o ID ainda não é conhecido (e.g., nova criação)
    public Cliente(String nome, String email, String senha) {
        super(nome, email, senha); // Chama o construtor de Usuario sem ID (que internamente chama com ID 0)
        this.reservasRealizadas = new ArrayList<>();
    }

    public void realizarReserva(Propriedade p, LocalDate checkin, LocalDate checkOut) {
        if (p == null) {
            throw new IllegalArgumentException("A propriedade não pode ser nula para reserva.");
        }
        if (checkin == null || checkOut == null) {
            throw new IllegalArgumentException("As datas de check-in e check-out não podem ser nulas.");
        }
        if (checkin.isAfter(checkOut)) {
            throw new IllegalArgumentException("A data de check-in deve ser anterior à data de check-out.");
        }

        if (p.isDisponivel()) {
            Reserva novaReserva = new Reserva(p, this, checkin, checkOut); // 'this' refere-se ao próprio objeto Cliente
            novaReserva.alugarPropriedade(); // Marca a propriedade como indisponível
            this.reservasRealizadas.add(novaReserva);
            System.out.println("Reserva realizada com sucesso para a propriedade: " + p.getTitulo());
            System.out.println("Custo total da reserva: R$ " + String.format("%.2f", novaReserva.getCustoTotal()));
        } else {
            System.out.println("A propriedade " + p.getTitulo() + " não está disponível para reserva.");
        }
    }

    public void listarReservas() {
        if (reservasRealizadas.isEmpty()) {
            System.out.println("Nenhuma reserva realizada por " + getNome() + ".");
        } else {
            System.out.println("\n--- Reservas de " + getNome() + " ---");
            for (Reserva r : reservasRealizadas) {
                r.exibirReserva();
                System.out.println("------------------");
            }
        }
    }

    public void listarPropriedadesDisponiveis(List<Propriedade> todasPropriedades) {
        boolean encontrouDisponivel = false;
        System.out.println("\n--- Propriedades Disponíveis para Aluguel ---");
        for (Propriedade p : todasPropriedades) {
            if (p.isDisponivel()) {
                p.exibirPropriedade();
                encontrouDisponivel = true;
                System.out.println("------------------");
            }
        }
        if (!encontrouDisponivel) {
            System.out.println("Nenhuma propriedade disponível no momento.");
        }
    }

    // Sobrescrevendo o método abstrato de Usuario
    @Override
    public void imprimirDados() {
        System.out.println("Tipo de Usuário: Cliente");
        System.out.println("ID: " + getId()); // getId() agora vem da classe Usuario
        System.out.println("Nome: " + getNome());
        System.out.println("Email: " + getEmail());
        System.out.println("Número de reservas realizadas: " + reservasRealizadas.size());
    }

    public List<Reserva> getReservasRealizadas() {
        return reservasRealizadas;
    }

    public void setReservasRealizadas(List<Reserva> reservasRealizadas) {
        this.reservasRealizadas = reservasRealizadas;
    }

    @Override
    public boolean equals(Object o) {
        // Chama o equals da superclasse Usuario que agora compara IDs
        // Isso é suficiente se o ID for a chave primária e o identificador único.
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        // Chama o hashCode da superclasse Usuario que agora inclui o ID
        return super.hashCode();
    }
}