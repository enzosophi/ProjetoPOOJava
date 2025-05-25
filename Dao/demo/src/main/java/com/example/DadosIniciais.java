package com.example;

import com.example.IProprietarioDAO;
import com.example.IPropriedadeDAO;
import com.example.Casa;
import com.example.IClienteDAO;
import com.example.IReservaDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class DadosIniciais {
    public static void carregar(IClienteDAO clienteDAO, IProprietarioDAO propDAO, IPropriedadeDAO propriedadeDAO, IReservaDAO reservaDAO) throws SQLException {
        Proprietario proprietario = new Proprietario("Ana", "ana@mail.com", "123456");
        propDAO.inserir(proprietario);

        Proprietario proprietario2 = new Proprietario("Gabriel", "gabriel@mail.com", "Soubom");
        propDAO.inserir(proprietario2);

        Casa casa = new Casa("Casa da Ana", "Casa confortável com piscina", "São Paulo", 4, 300.0, proprietario, "imagem.jpg", true,  100.0);
        propriedadeDAO.inserir(casa);

        Casa casa2 = new Casa("Casa do Gabriel", "Casa confortável com Gabriel", "Carapicuiba", 2, 350.0, proprietario, "imagem2.jpg", true,  150.0);
        propriedadeDAO.inserir(casa2);

        Cliente cliente = new Cliente("João", "joao@mail.com", "senha123");
        clienteDAO.inserir(cliente);

        Cliente cliente2 = new Cliente("Andre", "andremack@mail.com", "heapsort123RSA");
        clienteDAO.inserir(cliente2);

        Reserva r1 = new Reserva(casa, cliente, LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 5));
        reservaDAO.inserir(r1);

        Reserva r2 = new Reserva(casa2, cliente2, LocalDate.of(2025, 6, 10), LocalDate.of(2025, 6, 12));
        reservaDAO.inserir(r2);
    }
}