package com.example;

import com.example.Proprietario;
import com.example.IProprietarioDAO;
import com.example.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDao implements IProprietarioDAO {

    @Override
    public void inserir(Proprietario proprietario) throws SQLException {
        String sql = "INSERT INTO PROPRIETARIO (NOME, EMAIL, SENHA) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, proprietario.getNome());
            stmt.setString(2, proprietario.getEmail());
            stmt.setString(3, proprietario.getSenha());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                proprietario.setId(rs.getInt(1));
            }
            System.out.println("Proprietário " + proprietario.getNome() + " inserido com sucesso! ID: " + proprietario.getId());
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
    }

    @Override 
    public Proprietario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM PROPRIETARIO WHERE ID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Proprietario proprietario = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                proprietario = new Proprietario (
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")  
                );
            }
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
        return proprietario;
    }

    @Override 
    public Proprietario buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM PROPRIETARIO WHERE NOME ILIKE ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Proprietario proprietario = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            rs = stmt.executeQuery();

            if (rs.next()) {
                proprietario = new Proprietario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                );
            }
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn); 
        }
        return proprietario;
    }

    @Override 
    public List<Proprietario> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM PROPRIETARIO";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Proprietario> proprietarios = new ArrayList<>();

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Proprietario proprietario = new Proprietario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                );
                proprietarios.add(proprietario);
            }
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
        return proprietarios;
    }

    @Override 
    public void atualizar(Proprietario proprietario) throws SQLException {
        String sql = "UPDATE PROPRIETARIO SET NOME = ?, EMAIL = ?, SENHA = ? WHERE ID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, proprietario.getNome());
            stmt.setString(2, proprietario.getEmail());
            stmt.setString(3, proprietario.getSenha());
            stmt.setInt(4, proprietario.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Proprietário com ID " + proprietario.getId() + " atualizado com sucesso.");
            } else {
                System.out.println("Nenhum proprietário encontrado com ID " + proprietario.getId() + " para atualização.");
            }
        } finally {
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
    }

    @Override
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM PROPRIETARIO WHERE ID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Proprietário com ID " + id + " deletado com sucesso.");
            } else {
                System.out.println("Nenhum proprietário encontrado com ID " + id + " para deleção.");
            }
        } finally {
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
    }
}