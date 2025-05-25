package com.example;

import com.example.Cliente;
import com.example.ConnectionHelper;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;



public class ClienteDao  implements IClienteDAO{
    
    @Override
    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, email, senha) VALUES (?, ?, ?)";
        try (
            Connection conn = ConnectionHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getSenha());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    @Override
    public Cliente buscarPorId(int id){
        String sql = "Select * From CLiente where id =?";
        Cliente cliente = null;
        try(
            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement mStatement = connection.prepareStatement(sql)){

                mStatement.setInt(1, id);
                ResultSet rs= mStatement.executeQuery();

                if(rs.next()){
                    int clienteId = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String email = rs.getString("email");
                    String senha = rs.getString("senha");

                    cliente = new Cliente(clienteId,nome, email, senha);
                }
            }catch(SQLException e){
                    System.err.println("Erro ao buscar cliente: " + e.getMessage());
                }
                return cliente;
            
        }
        @Override
        public List<Cliente> buscarTodos() {
            String sql ="Select *from Cliente";
            List<Cliente> clientes = new ArrayList<>();

            try(Connection connection = ConnectionHelper.getConnection();
            Statement mStatement = connection.createStatement();
            ResultSet rs = mStatement.executeQuery(sql)){
                
                while(rs.next()){
                    int ClienteId = rs.getInt("id");
                    String nome = rs.getString("nome"); 
                    String email = rs.getString("email");
                    String senha =  rs.getString("senha");
                
                    Cliente cliente = new Cliente(ClienteId,nome, email, senha);
                    clientes.add(cliente);
                }
            } 
                
             catch (SQLException e) {
                System.err.println("Erro ao buscar clientes: " + e.getMessage());
            }
            return clientes;
        }
        @Override 
        public void atualizar(Cliente cliente) {
           String sql ="Update cliente set nome = ?, email = ?, senha = ? where id = ?";

           try(Connection connection = ConnectionHelper.getConnection();
           PreparedStatement mStatement = connection.prepareStatement(sql)){

            mStatement.setInt(1, cliente.getId());
            mStatement.setString(2, cliente.getNome());
            mStatement.setString(3, cliente.getEmail());
            mStatement.setString(4, cliente.getSenha());
            

            mStatement.executeUpdate();
            System.out.println("Cliente atualizado com sucesso!");
           } catch(SQLException e){
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
           }
            
        }

        @Override
        public void deletar(Cliente cliente) {
         String sql = "delete  from cliente where id = ?";

         try(Connection connection = ConnectionHelper.getConnection();
         PreparedStatement mStatement = connection.prepareStatement(sql)){
            mStatement.setInt(1, cliente.getId());
            mStatement.executeUpdate();
            System.out.println("Cliente deletado com sucesso!");
         } catch(SQLException e){
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
         }
            
        }
    }   

