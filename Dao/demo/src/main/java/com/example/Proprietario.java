package com.example;

import com.example.abstrata.Propriedade;
import com.example.abstrata.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Proprietario extends Usuario {

    private List<Propriedade> propriedades;

    public Proprietario(int id, String nome, String email, String senha) {
        super(id, nome, email, senha);
        this.propriedades = new ArrayList<>();
    }

    public Proprietario(String nome, String email, String senha) {
        super(nome, email, senha);
        this.propriedades = new ArrayList<>();
    }

    public void cadastrarPropriedade(Propriedade propriedade) {
        if (propriedade != null) {
            propriedades.add(propriedade);
            System.out.println("Propriedade adicionada com sucesso!");
        } else {
            System.out.println("Erro: A propriedade não pode ser nula ao cadastrar!");
        }
    }

    public void listarPropriedades() {
        if (propriedades.isEmpty()) {
            System.out.println("Nenhuma propriedade cadastrada!");
        } else {
            System.out.println("\n--- Propriedades Cadastradas por " + getNome() + " ---");
            for (Propriedade p : propriedades) {
                p.exibirPropriedade();
                System.out.println("------------------");
            }
        }
    }

    public void listarPropriedadesAlugadas() {
        boolean encontrouAlugada = false;
        System.out.println("\n--- Propriedades Alugadas de " + getNome() + " ---");
        for (Propriedade p : propriedades) {
            if (!p.isDisponivel()) {
                p.exibirPropriedade();
                encontrouAlugada = true;
                System.out.println("------------------");
            }
        }
        if (!encontrouAlugada) {
            System.out.println("Nenhuma propriedade alugada no momento.");
        }
    }

    @Override
    public void imprimirDados() {
        System.out.println("Tipo de Usuário: Proprietário");
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("Email: " + getEmail());
        System.out.println("Número de propriedades cadastradas: " + propriedades.size());
    }

    public List<Propriedade> getPropriedades() {
        return propriedades;
    }

    public void setPropriedades(List<Propriedade> propriedades) {
        this.propriedades = propriedades;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}