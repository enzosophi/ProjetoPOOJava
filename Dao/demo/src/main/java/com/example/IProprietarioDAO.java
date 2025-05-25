package com.example;

import com.example.Proprietario;
import java.util.List;
import java.sql.SQLException;

public interface IProprietarioDAO {
    void inserir(Proprietario proprietario) throws SQLException;
    Proprietario buscarPorId(int id) throws SQLException;
    Proprietario buscarPorNome(String nome) throws SQLException;
    List<Proprietario> buscarTodos() throws SQLException;
    void atualizar(Proprietario proprietario) throws SQLException;
    void deletar(int id) throws SQLException; 
}