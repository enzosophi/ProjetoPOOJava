package com.example;

import com.example.Reserva;
import com.example.Cliente; 
import com.example.IReservaDAO;
import com.example.IClienteDAO; 
import com.example.IPropriedadeDAO; 
import com.example.abstrata.Propriedade; 
import com.example.ConnectionHelper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaDao implements IReservaDAO {

    private IClienteDAO clienteDao;
    private IPropriedadeDAO propriedadeDao;

    public ReservaDao(IClienteDAO clienteDao, IPropriedadeDAO propriedadeDao) {
        this.clienteDao = clienteDao;
        this.propriedadeDao = propriedadeDao;
    }

    @Override
    public void inserir(Reserva reserva) throws SQLException {
        List<Reserva> reservasExistentes = buscarPorPropriedade(reserva.getPropriedadeId());

        for (Reserva r : reservasExistentes) {
            if (reserva.getCheckin().isBefore(r.getCheckout()) &&
                reserva.getCheckout().isAfter(r.getCheckin())) {
                throw new SQLException("Já existe uma reserva para esta propriedade nesse período.");
            }
        }
        String sql = "INSERT INTO reserva (propriedade_id, cliente_id, checkin_data, checkout_data, custo_total) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, reserva.getPropriedadeId());
            stmt.setInt(2, reserva.getClienteId());
            stmt.setDate(3, Date.valueOf(reserva.getCheckin()));
            stmt.setDate(4, Date.valueOf(reserva.getCheckout()));
            stmt.setDouble(5, reserva.getCustoTotal());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                reserva.setId(rs.getInt(1));
            }
            System.out.println("Reserva inserida com sucesso! ID: " + reserva.getId());
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
    }

    @Override
    public Reserva buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM reserva WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Reserva reserva = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int propId = rs.getInt("propriedade_id");
                int cliId = rs.getInt("cliente_id");

                // Buscar a propriedade e o cliente associados usando os DAOs
                Propriedade propriedade = propriedadeDao.buscarPorId(propId);
                Cliente cliente = (Cliente) clienteDao.buscarPorId(cliId); // Cast para Cliente

                if (propriedade != null && cliente != null) {
                    reserva = new Reserva(
                        rs.getInt("id"),
                        propriedade,
                        cliente,
                        rs.getDate("checkin_data").toLocalDate(),
                        rs.getDate("checkout_data").toLocalDate()
                    );
                    reserva.setCustoTotal(rs.getDouble("custo_total")); // Setar o custo total do banco
                } else {
                    System.err.println("Erro: Propriedade ou Cliente não encontrados para a reserva ID: " + id);
                }
            }
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
        return reserva;
    }

    @Override
    public List<Reserva> buscarTodas() throws SQLException {
        String sql = "SELECT * FROM reserva";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Reserva> reservas = new ArrayList<>();

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int propId = rs.getInt("propriedade_id");
                int cliId = rs.getInt("cliente_id");

                Propriedade propriedade = propriedadeDao.buscarPorId(propId);
                Cliente cliente = (Cliente) clienteDao.buscarPorId(cliId);

                if (propriedade != null && cliente != null) {
                    Reserva reserva = new Reserva(
                        rs.getInt("id"),
                        propriedade,
                        cliente,
                        rs.getDate("checkin_data").toLocalDate(),
                        rs.getDate("checkout_data").toLocalDate()
                    );
                    reserva.setCustoTotal(rs.getDouble("custo_total"));
                    reservas.add(reserva);
                }
            }
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
        return reservas;
    }

    @Override
    public List<Reserva> buscarPorCliente(int clienteId) throws SQLException {
        String sql = "SELECT * FROM reserva WHERE cliente_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Reserva> reservas = new ArrayList<>();

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, clienteId);
            rs = stmt.executeQuery();

            Cliente cliente = (Cliente) clienteDao.buscarPorId(clienteId); // Buscar o cliente uma vez

            while (rs.next()) {
                int propId = rs.getInt("propriedade_id");
                Propriedade propriedade = propriedadeDao.buscarPorId(propId);

                if (propriedade != null && cliente != null) {
                    Reserva reserva = new Reserva(
                        rs.getInt("id"),
                        propriedade,
                        cliente,
                        rs.getDate("checkin_data").toLocalDate(),
                        rs.getDate("checkout_data").toLocalDate()
                    );
                    reserva.setCustoTotal(rs.getDouble("custo_total"));
                    reservas.add(reserva);
                }
            }
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
        return reservas;
    }

    @Override
    public List<Reserva> buscarPorPropriedade(int propriedadeId) throws SQLException {
        String sql = "SELECT * FROM reserva WHERE propriedade_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Reserva> reservas = new ArrayList<>();

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, propriedadeId);
            rs = stmt.executeQuery();

            Propriedade propriedade = propriedadeDao.buscarPorId(propriedadeId); // Buscar a propriedade uma vez

            while (rs.next()) {
                int cliId = rs.getInt("cliente_id");
                Cliente cliente = (Cliente) clienteDao.buscarPorId(cliId);

                if (propriedade != null && cliente != null) {
                    Reserva reserva = new Reserva(
                        rs.getInt("id"),
                        propriedade,
                        cliente,
                        rs.getDate("checkin_data").toLocalDate(),
                        rs.getDate("checkout_data").toLocalDate()
                    );
                    reserva.setCustoTotal(rs.getDouble("custo_total"));
                    reservas.add(reserva);
                }
            }
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
        return reservas;
    }

    @Override
    public void atualizar(Reserva reserva) throws SQLException {
        String sql = "UPDATE reserva SET propriedade_id = ?, cliente_id = ?, checkin_data = ?, checkout_data = ?, custo_total = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reserva.getPropriedadeId());
            stmt.setInt(2, reserva.getClienteId());
            stmt.setDate(3, Date.valueOf(reserva.getCheckin()));
            stmt.setDate(4, Date.valueOf(reserva.getCheckout()));
            stmt.setDouble(5, reserva.getCustoTotal());
            stmt.setInt(6, reserva.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Reserva com ID " + reserva.getId() + " atualizada com sucesso.");
            } else {
                System.out.println("Nenhuma reserva encontrada com ID " + reserva.getId() + " para atualização.");
            }
        } finally {
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
    }

    @Override
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM reserva WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Reserva com ID " + id + " deletada com sucesso.");
            } else {
                System.out.println("Nenhuma reserva encontrada com ID " + id + " para exclusão.");
            }
        } finally {
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
    }
}