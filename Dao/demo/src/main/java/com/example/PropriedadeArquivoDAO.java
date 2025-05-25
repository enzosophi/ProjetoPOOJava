package com.example;

import java.util.ArrayList;
import java.util.List;
import com.example.abstrata.Propriedade;

public class PropriedadeArquivoDAO implements IPropriedadeDAO {
    private static List<Propriedade> propriedades = new ArrayList<>();
    private static int idCounter = 1;

    @Override
    public void inserir(Propriedade propriedade) {
        propriedade.setId(idCounter++);
        propriedades.add(propriedade);
        System.out.println("[ARQUIVO] Propriedade inserida: " + propriedade.getTitulo());
    }

    @Override
    public Propriedade buscarPorId(int id) {
        for (Propriedade p : propriedades) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Propriedade> buscarTodas() {
        return new ArrayList<>(propriedades);
    }

    @Override
    public List<Propriedade> buscarPorProprietario(int proprietarioId) {
        List<Propriedade> resultado = new ArrayList<>();
        for (Propriedade p : propriedades) {
            if (p.getProprietario().getId() == proprietarioId) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    @Override
    public List<Propriedade> buscarDisponiveis() {
        List<Propriedade> disponiveis = new ArrayList<>();
        for (Propriedade p : propriedades) {
            if (p.isDisponivel()) {
                disponiveis.add(p);
            }
        }
        return disponiveis;
    }

    @Override
    public void atualizar(Propriedade propriedade) {
        for (int i = 0; i < propriedades.size(); i++) {
            if (propriedades.get(i).getId() == propriedade.getId()) {
                propriedades.set(i, propriedade);
                break;
            }
        }
    }

    @Override
    public void deletar(int id) {
        propriedades.removeIf(p -> p.getId() == id);
    }
}