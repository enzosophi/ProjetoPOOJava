package com.example;

import java.sql.Connection;
import com.example.ClienteDao;
import com.example.PropriedadeDao;

public class DAOFactory {
    private static final String MODO = "banco"; 

    public static IClienteDAO getClienteDAO() {
        if (MODO.equals("banco")) {
            return new ClienteDao();
        } else {
            return new ClienteArquivoDAO(); 
        }
    }

    public static IProprietarioDAO getProprietarioDAO() {
        if (MODO.equals("banco")) {
            return new ProprietarioDao();
        } else {
            return new ProprietarioArquivoDAO(); 
        }
    }

    public static IPropriedadeDAO getPropriedadeDAO() {
        if (MODO.equals("banco")) {
            IProprietarioDAO proprietarioDAO = getProprietarioDAO();
            return new PropriedadeDao(proprietarioDAO);
        } else {
            return new PropriedadeArquivoDAO();
        }
    }

    public static IReservaDAO getReservaDAO() {
        if (MODO.equals("banco")) {
            IClienteDAO clienteDAO = getClienteDAO();
            IPropriedadeDAO propriedadeDAO = getPropriedadeDAO();
            return new ReservaDao(clienteDAO, propriedadeDAO);
        } else {
            return new ReservaArquivoDAO();
        }
    }
}
