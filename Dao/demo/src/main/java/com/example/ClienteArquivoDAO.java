package com.example;

import java.util.ArrayList;
import java.util.List;

public class ClienteArquivoDAO implements IClienteDAO {
    private static List<Cliente> clientes = new ArrayList<>();
    private static int idCounter = 1;

    @Override
    public void inserir(Cliente cliente) {
        cliente.setId(idCounter++);
        clientes.add(cliente);
        System.out.println("[ARQUIVO] Cliente inserido: " + cliente.getNome());
    }

    @Override
    public Cliente buscarPorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Cliente> buscarTodos() {
        return new ArrayList<>(clientes);
    }

    @Override
    public void atualizar(Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == cliente.getId()) {
                clientes.set(i, cliente);
                break;
            }
        }
    }

    @Override
    public void deletar(Cliente cliente) {
        clientes.removeIf(c -> c.getId() == cliente.getId());
    }
}
