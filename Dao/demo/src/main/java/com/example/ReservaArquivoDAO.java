package com.example;

import java.util.ArrayList;
import java.util.List;

public class ReservaArquivoDAO implements IReservaDAO {
    private static List<Reserva> reservas = new ArrayList<>();
    private static int idCounter = 1;

    @Override
    public void inserir(Reserva reserva) {
        reserva.setId(idCounter++);
        reservas.add(reserva);
        System.out.println("[ARQUIVO] Reserva inserida. ID: " + reserva.getId());
    }

    @Override
    public Reserva buscarPorId(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    @Override
    public List<Reserva> buscarTodas() {
        return new ArrayList<>(reservas);
    }

    @Override
    public List<Reserva> buscarPorCliente(int clienteId) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getCliente().getId() == clienteId) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    @Override
    public List<Reserva> buscarPorPropriedade(int propriedadeId) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getPropriedade().getId() == propriedadeId) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    @Override
    public void atualizar(Reserva reserva) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getId() == reserva.getId()) {
                reservas.set(i, reserva);
                break;
            }
        }
    }

    @Override
    public void deletar(int id) {
        reservas.removeIf(r -> r.getId() == id);
    }
}
