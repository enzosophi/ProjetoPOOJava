package com.example;

import java.util.ArrayList;
import java.util.List;

public class ProprietarioArquivoDAO implements IProprietarioDAO {
    private static List<Proprietario> proprietarios = new ArrayList<>();
    private static int idCounter = 1;

    @Override
    public void inserir(Proprietario proprietario) {
        proprietario.setId(idCounter++);
        proprietarios.add(proprietario);
        System.out.println("[ARQUIVO] Proprietário inserido: " + proprietario.getNome());
    }

    @Override
    public Proprietario buscarPorId(int id) {
        for (Proprietario p : proprietarios) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public Proprietario buscarPorNome(String nome) {
        for (Proprietario p : proprietarios) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }


    @Override
    public List<Proprietario> buscarTodos() {
        return new ArrayList<>(proprietarios);
    }

    @Override
    public void atualizar(Proprietario proprietario) {
        for (int i = 0; i < proprietarios.size(); i++) {
            if (proprietarios.get(i).getId() == proprietario.getId()) {
                proprietarios.set(i, proprietario);
                break;
            }
        }
    }

    @Override
    public void deletar(int id) {
        proprietarios.removeIf(p -> p.getId() == id);
    }
}
