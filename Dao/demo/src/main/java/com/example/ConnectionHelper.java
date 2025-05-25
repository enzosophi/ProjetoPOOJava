package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHelper {
    
    private static final String URL = "jdbc:postgresql://aws-0-us-east-2.pooler.supabase.com:5432/postgres";
    private static final String USER = "postgres.jotthwehovoredmjykmr";
    private static final String PASSWORD = "mackenziepoo2025";

    public static Connection getConnection() throws SQLException {
      try {
          Class.forName("org.postgresql.Driver");
          return DriverManager.getConnection(URL, USER, PASSWORD);
      } catch (ClassNotFoundException e) {
          System.err.println("Driver JDBC do PostgreSQL não encontrado.");
          throw new SQLException("Driver JDBC não encontrado", e);
      }
    }

    public static void closeConnection(Connection conn) {
      if (conn != null) {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
      }
    }

    public static void closeStatement(Statement stmt) {
      if (stmt != null) {
        try {
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar o statement: " + e.getMessage());
        }
      }
    }

    public static void closeResultSet(ResultSet rs) {
      if (rs != null) {
          try {
              rs.close();
          } catch (SQLException e) {
              System.err.println("Erro ao fechar o result set: " + e.getMessage());
          }
      }
    }
}