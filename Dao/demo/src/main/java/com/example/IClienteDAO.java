package com.example;

import java.util.List;
import com.example.Cliente;

public interface IClienteDAO {

    void inserir(Cliente cliente);

    Cliente buscarPorId(int id);

    List<Cliente> buscarTodos();

    void atualizar(Cliente cliente);
    
    void deletar(Cliente cliente);
}