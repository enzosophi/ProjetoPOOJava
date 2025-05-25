package com.example;

import com.example.abstrata.Propriedade;
import java.util.List;
import java.sql.SQLException; 

public interface IPropriedadeDAO {
    void inserir (Propriedade propriedade) throws SQLException; 
    Propriedade buscarPorId(int id) throws SQLException; 
    List<Propriedade> buscarTodas() throws SQLException;
    List<Propriedade> buscarPorProprietario(int proprietarioId) throws SQLException;
    List<Propriedade> buscarDisponiveis() throws SQLException;
    void atualizar(Propriedade propriedade) throws SQLException;
    void deletar(int id) throws SQLException;
}