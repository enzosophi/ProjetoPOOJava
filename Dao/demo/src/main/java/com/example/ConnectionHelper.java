package com.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionHelper {
    

    private static final String URL = "coloque a url";
    private static final String USER="coloque o nome";
    private static final String PASSWORD="coloque a senha";

    static{
      try{
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver carregado");
      }catch(ClassNotFoundException e){
        System.err.println("Erro ao carregar o driver: " + e.getMessage());
         }
    }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }

  public static void closeConnection(Connection connection) throws SQLException {
    if (connection != null) {
      try {
        connection.close();
        System.out.println("Conexao fechada");
      } catch (SQLException e) {
        System.err.println("Erro ao fechar a conexao: " + e.getMessage());
        // TODO: handle exception
      }
    }
  }
}