package com.example;

import com.example.Reserva;
import java.sql.SQLException;
import java.util.List;

public interface IReservaDAO {
    void inserir(Reserva reserva) throws SQLException;
    Reserva buscarPorId(int id) throws SQLException;
    List<Reserva> buscarTodas() throws SQLException;
    List<Reserva> buscarPorCliente(int clienteId) throws SQLException;
    List<Reserva> buscarPorPropriedade(int propriedadeId) throws SQLException;
    void atualizar(Reserva reserva) throws SQLException;
    void deletar(int id) throws SQLException;
}