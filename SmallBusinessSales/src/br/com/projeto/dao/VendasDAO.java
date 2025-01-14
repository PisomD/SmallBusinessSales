package br.com.projeto.dao;

import br.com.projeto.Resources.ConnectionFactory;
import br.com.projeto.model.Clientes;
import br.com.projeto.model.Vendas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Classe criada para 
 * @author Adilson Luz
 * @since Classe Criada em 08/07/2021, 15:08:57
 */
public class VendasDAO {
    
    private Connection conn;

    public VendasDAO() {
        this.conn = new ConnectionFactory().getConnection();
    }
    
    //Cadastrar venda
    public void cadastrarVenda(Vendas obj){
        try {
            //1 Passo - criar comando SQL
            String sql = "INSERT INTO tb_vendas (cliente_id, data_venda, total_venda, observacoes)"
                       + "VALUES (?,?,?,?)";

            //2 Passo - Conectar no banco da dados e organizar o comando SQL
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, obj.getCliente().getId());
            stmt.setString(2, obj.getData_venda());
            stmt.setDouble(3, obj.getTotal_venda());
            stmt.setString(4, obj.getObs());

            //3 Passo - Executar o comando SQL
            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ops, aconteceu o erro " + e);
        }
        
    }    
    
    //Retorna ultima venda
    public int retornaUltimaVenda(){
        try {
            int idvenda = 0;
            
            String query = "SELECT MAX(id) id FROM tb_vendas";
            
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                Vendas p = new Vendas();
                p.setId(rs.getInt("id"));
                idvenda = p.getId();
            }
            
            return idvenda;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    //Metodo que filtra vendas por data
    public List<Vendas> listarVendasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        try {
            //1 Passo criar a lista
            List<Vendas> lista = new ArrayList<>();

            //2 Passo - Criar o SQL, organizar e executar
            String sql = "SELECT v.id, DATE_FORMAT(v.data_venda,'%d/%m/%Y') AS data_formatada, c.nome, v.total_venda, v.observacoes FROM tb_vendas AS v "
                       + "INNER JOIN tb_clientes AS c ON (v.cliente_id = c.id) WHERE v.data_venda BETWEEN ? AND ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dataInicio.toString());
            stmt.setString(2, dataFim.toString());
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vendas obj = new Vendas();
                Clientes c = new Clientes();

                obj.setId(rs.getInt("v.id"));
                obj.setData_venda(rs.getString("data_formatada"));
                c.setNome(rs.getString("c.nome"));
                obj.setTotal_venda(rs.getDouble("v.total_venda"));
                obj.setObs(rs.getString("v.observacoes"));
                
                obj.setCliente(c);
                
                
                lista.add(obj);

            }
            
                return lista;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ops, aconteceu o erro " + e);
            return null;
        }
    }
    
    //Metodo que calcula o total de vendas por data
    public double retornaTotalVendaPorData(LocalDate data_venda){
        try {
            double totalvenda = 0;
            
            String sql = "SELECT SUM(total_venda) AS Total FROM tb_vendas WHERE data_venda = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, data_venda.toString());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                totalvenda = rs.getDouble("total");
            }
            
            return totalvenda;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }   
    
}//fim da classe
