package com.example;

import com.example.abstrata.Propriedade;
import com.example.Casa;
import com.example.Apartamento;
import com.example.Sitio;
import com.example.Proprietario; 
import com.example.IPropriedadeDAO;
import com.example.IProprietarioDAO; 
import com.example.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropriedadeDao implements IPropriedadeDAO {
    
    private IProprietarioDAO proprietarioDAO;

    public PropriedadeDao(IProprietarioDAO proprietarioDAO) {
        this.proprietarioDAO = proprietarioDAO;
    }

    @Override 
    public void inserir(Propriedade propriedade) throws SQLException {
        String sql = "INSERT INTO PROPRIEDADE (TIPO_PROPRIEDADE, TITULO, DESCRICAO, LOCALIZACAO, CAPACIDADE, PRECO_POR_NOITE, IMAGEM, DISPONIVEL, PROPRIETARIO_ID, POSSUI_PISCINA, PRECO_POR_PESSOA, ANDAR, TAXA, AREA_TOTAL) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            String tipo = "";
            if (propriedade instanceof Casa) {
                tipo = "Casa";
            } else if (propriedade instanceof Apartamento) {
                tipo = "Apartamento";
            } else if (propriedade instanceof Sitio) {
                tipo = "Sitio";
            }

            stmt.setString(1, tipo);
            stmt.setString(2, propriedade.getTitulo());
            stmt.setString(3, propriedade.getDescricao());
            stmt.setString(4, propriedade.getLocalizacao());
            stmt.setInt(5, propriedade.getCapacidade());
            stmt.setDouble(6, propriedade.getPrecoPorNoite());
            stmt.setString(7, propriedade.getImagem());
            stmt.setBoolean(8, propriedade.isDisponivel());
            stmt.setInt(9, propriedade.getProprietario().getId());

            if (propriedade instanceof Casa casa) {
                stmt.setBoolean(10, casa.isPossuiPiscina());
                stmt.setDouble(11, casa.getPrecoPorPessoa());
                stmt.setNull(12, Types.INTEGER); 
                stmt.setNull(13, Types.NUMERIC); 
                stmt.setNull(14, Types.NUMERIC);
            } else if (propriedade instanceof Apartamento apto) {
                stmt.setNull(10, Types.BOOLEAN);
                stmt.setNull(11, Types.NUMERIC);
                stmt.setInt(12, apto.getAndar());
                stmt.setDouble(13, apto.getTaxa());
                stmt.setNull(14, Types.NUMERIC);
            } else if (propriedade instanceof Sitio sitio) {
                stmt.setNull(10, Types.BOOLEAN);
                stmt.setNull(11, Types.NUMERIC); 
                stmt.setNull(12, Types.INTEGER); 
                stmt.setNull(13, Types.NUMERIC); 
                stmt.setDouble(14, sitio.getAreaTotal());
            } else {
                stmt.setNull(10, Types.BOOLEAN);
                stmt.setNull(11, Types.NUMERIC);
                stmt.setNull(12, Types.INTEGER);
                stmt.setNull(13, Types.NUMERIC);
                stmt.setNull(14, Types.NUMERIC);
            }

            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                propriedade.setId(rs.getInt(1));
            }
            System.out.println("Propriedade '" + propriedade.getTitulo() + "' inserida com sucesso! ID: " + propriedade.getId());
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
    }

    @Override
    public Propriedade buscarPorId(int id) throws SQLException {
        String sql = "SELECT p.*, pr.nome as proprietario_nome, pr.email as proprietario_email, pr.senha as proprietario_senha FROM propriedade p JOIN proprietario pr ON p.proprietario_id = pr.id WHERE p.id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Propriedade propriedade = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Proprietario proprietario = new Proprietario(
                    rs.getInt("proprietario_id"),
                    rs.getString("proprietario_nome"),
                    rs.getString("proprietario_email"),
                    rs.getString("proprietario_senha")
                );

                String tipo = rs.getString("tipo_propriedade");
                int propId = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                String localizacao = rs.getString("localizacao");
                int capacidade = rs.getInt("capacidade");
                double precoPorNoite = rs.getDouble("preco_por_noite");
                String imagem = rs.getString("imagem");
                boolean disponivel = rs.getBoolean("disponivel");

                switch (tipo) {
                    case "Casa":
                        boolean possuiPiscina = rs.getBoolean("possui_piscina");
                        double precoPorPessoa = rs.getDouble("preco_por_pessoa");
                        propriedade = new Casa(propId, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, possuiPiscina, precoPorPessoa);
                        break;
                    case "Apartamento":
                        int andar = rs.getInt("andar");
                        double taxa = rs.getDouble("taxa");
                        propriedade = new Apartamento(propId, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, andar, taxa);
                        break;
                    case "Sitio":
                        double areaTotal = rs.getDouble("area_total");
                        propriedade = new Sitio(propId, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, areaTotal);
                        break;
                    default:
                        System.err.println("Tipo de propriedade desconhecido: " + tipo);
                        break;
                }
                if (propriedade != null) {
                    propriedade.setDisponivel(disponivel);
                }
            }
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
        return propriedade;
    }

    @Override
    public List<Propriedade> buscarTodas() throws SQLException {
        String sql = "SELECT p.*, pr.nome as proprietario_nome, pr.email as proprietario_email, pr.senha as proprietario_senha FROM propriedade p JOIN proprietario pr ON p.proprietario_id = pr.id";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Propriedade> propriedades = new ArrayList<>();

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Proprietario proprietario = new Proprietario(
                    rs.getInt("proprietario_id"),
                    rs.getString("proprietario_nome"),
                    rs.getString("proprietario_email"),
                    rs.getString("proprietario_senha")
                );

                String tipo = rs.getString("tipo_propriedade");
                int propId = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                String localizacao = rs.getString("localizacao");
                int capacidade = rs.getInt("capacidade");
                double precoPorNoite = rs.getDouble("preco_por_noite");
                String imagem = rs.getString("imagem");
                boolean disponivel = rs.getBoolean("disponivel");

                Propriedade propriedade = null;
                switch (tipo) {
                    case "Casa":
                        boolean possuiPiscina = rs.getBoolean("possui_piscina");
                        double precoPorPessoa = rs.getDouble("preco_por_pessoa");
                        propriedade = new Casa(propId, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, possuiPiscina, precoPorPessoa);
                        break;
                    case "Apartamento":
                        int andar = rs.getInt("andar");
                        double taxa = rs.getDouble("taxa");
                        propriedade = new Apartamento(propId, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, andar, taxa);
                        break;
                    case "Sitio":
                        double areaTotal = rs.getDouble("area_total");
                        propriedade = new Sitio(propId, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, areaTotal);
                        break;
                    default:
                        System.err.println("Tipo de propriedade desconhecido ao carregar: " + tipo);
                        break;
                }
                if (propriedade != null) {
                    propriedade.setDisponivel(disponivel);
                    propriedades.add(propriedade);
                }
            }
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
        return propriedades;
    }

    @Override
    public List<Propriedade> buscarPorProprietario(int proprietarioId) throws SQLException {
        String sql = "SELECT p.*, pr.nome as proprietario_nome, pr.email as proprietario_email, pr.senha as proprietario_senha FROM propriedade p JOIN proprietario pr ON p.proprietario_id = pr.id WHERE p.proprietario_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Propriedade> propriedades = new ArrayList<>();

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, proprietarioId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Proprietario proprietario = new Proprietario(
                    rs.getInt("proprietario_id"),
                    rs.getString("proprietario_nome"),
                    rs.getString("proprietario_email"),
                    rs.getString("proprietario_senha")
                );

                String tipo = rs.getString("tipo_propriedade");
                int propId = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                String localizacao = rs.getString("localizacao");
                int capacidade = rs.getInt("capacidade");
                double precoPorNoite = rs.getDouble("preco_por_noite");
                String imagem = rs.getString("imagem");
                boolean disponivel = rs.getBoolean("disponivel");

                Propriedade propriedade = null;
                switch (tipo) {
                    case "Casa":
                        boolean possuiPiscina = rs.getBoolean("possui_piscina");
                        double precoPorPessoa = rs.getDouble("preco_por_pessoa");
                        propriedade = new Casa(propId, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, possuiPiscina, precoPorPessoa);
                        break;
                    case "Apartamento":
                        int andar = rs.getInt("andar");
                        double taxa = rs.getDouble("taxa");
                        propriedade = new Apartamento(propId, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, andar, taxa);
                        break;
                    case "Sitio":
                        double areaTotal = rs.getDouble("area_total");
                        propriedade = new Sitio(propId, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, areaTotal);
                        break;
                    default:
                        System.err.println("Tipo de propriedade desconhecido ao carregar: " + tipo);
                        break;
                }
                if (propriedade != null) {
                    propriedade.setDisponivel(disponivel);
                    propriedades.add(propriedade);
                }
            }
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
        return propriedades;
    }

    @Override
    public List<Propriedade> buscarDisponiveis() throws SQLException {
        String sql = "SELECT p.*, pr.nome as proprietario_nome, pr.email as proprietario_email, pr.senha as proprietario_senha FROM propriedade p JOIN proprietario pr ON p.proprietario_id = pr.id WHERE p.disponivel = TRUE";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Propriedade> propriedadesDisponiveis = new ArrayList<>();

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Proprietario proprietario = new Proprietario(
                    rs.getInt("proprietario_id"),
                    rs.getString("proprietario_nome"),
                    rs.getString("proprietario_email"),
                    rs.getString("proprietario_senha")
                );

                String tipo = rs.getString("tipo_propriedade");
                int propId = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                String localizacao = rs.getString("localizacao");
                int capacidade = rs.getInt("capacidade");
                double precoPorNoite = rs.getDouble("preco_por_noite");
                String imagem = rs.getString("imagem");
                boolean disponivel = rs.getBoolean("disponivel");

                Propriedade propriedade = null;
                switch (tipo) {
                    case "Casa":
                        boolean possuiPiscina = rs.getBoolean("possui_piscina");
                        double precoPorPessoa = rs.getDouble("preco_por_pessoa");
                        propriedade = new Casa(propId, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, possuiPiscina, precoPorPessoa);
                        break;
                    case "Apartamento":
                        int andar = rs.getInt("andar");
                        double taxa = rs.getDouble("taxa");
                        propriedade = new Apartamento(propId, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, andar, taxa);
                        break;
                    case "Sitio":
                        double areaTotal = rs.getDouble("area_total");
                        propriedade = new Sitio(propId, titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, areaTotal);
                        break;
                    default:
                        System.err.println("Tipo de propriedade desconhecido ao carregar: " + tipo);
                        break;
                }
                if (propriedade != null) {
                    propriedade.setDisponivel(disponivel);
                    propriedadesDisponiveis.add(propriedade);
                }
            }
        } finally {
            ConnectionHelper.closeResultSet(rs);
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
        return propriedadesDisponiveis;
    }


    @Override
    public void atualizar(Propriedade propriedade) throws SQLException {
        String sql = "UPDATE propriedade SET titulo = ?, descricao = ?, localizacao = ?, capacidade = ?, preco_por_noite = ?, imagem = ?, disponivel = ?, proprietario_id = ?, possui_piscina = ?, preco_por_pessoa = ?, andar = ?, taxa = ?, area_total = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, propriedade.getTitulo());
            stmt.setString(2, propriedade.getDescricao());
            stmt.setString(3, propriedade.getLocalizacao());
            stmt.setInt(4, propriedade.getCapacidade());
            stmt.setDouble(5, propriedade.getPrecoPorNoite());
            stmt.setString(6, propriedade.getImagem());
            stmt.setBoolean(7, propriedade.isDisponivel());
            stmt.setInt(8, propriedade.getProprietario().getId());

            if (propriedade instanceof Casa casa) {
                stmt.setBoolean(9, casa.isPossuiPiscina());
                stmt.setDouble(10, casa.getPrecoPorPessoa());
                stmt.setNull(11, Types.INTEGER); 
                stmt.setNull(12, Types.NUMERIC); 
                stmt.setNull(13, Types.NUMERIC); 
            } else if (propriedade instanceof Apartamento apto) {
                stmt.setNull(9, Types.BOOLEAN); 
                stmt.setNull(10, Types.NUMERIC); 
                stmt.setInt(11, apto.getAndar());
                stmt.setDouble(12, apto.getTaxa());
                stmt.setNull(13, Types.NUMERIC); 
            } else if (propriedade instanceof Sitio sitio) {
                stmt.setNull(9, Types.BOOLEAN); 
                stmt.setNull(10, Types.NUMERIC); 
                stmt.setNull(11, Types.INTEGER); 
                stmt.setNull(12, Types.NUMERIC); 
                stmt.setDouble(13, sitio.getAreaTotal());
            } else {
                stmt.setNull(9, Types.BOOLEAN);
                stmt.setNull(10, Types.NUMERIC);
                stmt.setNull(11, Types.INTEGER);
                stmt.setNull(12, Types.NUMERIC);
                stmt.setNull(13, Types.NUMERIC);
            }
            stmt.setInt(14, propriedade.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Propriedade com ID " + propriedade.getId() + " atualizada com sucesso.");
            } else {
                System.out.println("Nenhuma propriedade encontrada com ID " + propriedade.getId() + " para atualização.");
            }
        } finally {
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
    }

    @Override
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM propriedade WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Propriedade com ID " + id + " deletada com sucesso.");
            } else {
                System.out.println("Nenhuma propriedade encontrada com ID " + id + " para exclusão.");
            }
        } finally {
            ConnectionHelper.closeStatement(stmt);
            ConnectionHelper.closeConnection(conn);
        }
    }
}

