
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.ProdutoVO;
import persistencia.Conexao;

/**
 *
 * @author Camila_Camera
 */
public class ProdutoDAO {
    
    public void cadastrarProduto(ProdutoVO pVO) throws SQLException{
        //Busca conexão com o banco de dados
        Connection con = Conexao.getConexao();
        //Cria um objeto "stat" responsável por enviar os comandos de banco do java para serem executados dentro do BD
        Statement stat = con.createStatement();
        try{
            String sql;//String que receberá o comando SQL
            
            sql = "insert into produto(idproduto, nome, valorcusto, quantidade)"
                    +"values(null,'"+pVO.getNome()+"', "+pVO.getValorCusto()+", "+pVO.getQuantidade()+")";
            //executa o comando que guardamos dentro da String sql
            stat.execute(sql);
        }catch(SQLException e){
            throw new SQLException("Erro ao inserir produto!"+e.getMessage());
        }finally{
            stat.close();
            con.close();
        }        
    }
    
    public ArrayList<ProdutoVO> buscar() throws SQLException {
        Connection con = Conexao.getConexao();
        //permite a execução do sql entre java e sql
        Statement stat = con.createStatement();
        //tenta a conexão com o banco
        try {
            String sql;
            //traz todos os resultados dos produtos cadastrados em forma de tabela
            //quem permite executar esse comanod de sql é o comando Statement
            sql = "select * from produto";
            //traz todos os resultados da query do banco
            ResultSet rset = stat.executeQuery(sql);
            ArrayList<ProdutoVO> pvo = new ArrayList<>();
            
            while(rset.next()) {
                ProdutoVO prod = new ProdutoVO();
                prod.setIdproduto(rset.getLong("idproduto"));
                prod.setNome(rset.getString("nome"));
                prod.setQuantidade(rset.getInt("quantidade"));
                prod.setValorCusto(rset.getDouble("valorcusto"));
                
                pvo.add(prod);
            }
            return pvo;
        //se a primeira tentativa não der certo, ele tenta pegar as exceções    
        } catch (SQLException e) {
            throw new SQLException ("Erro ao buscar dados no banco");
            //depois de fazer a conexão, usa-se o finally para finalizar a conexão e fechar o banco de forma segura
        }finally {
            stat.close();
            con.close();
        }
    }
    public void deletarProduto(long idProduto) throws SQLException{
        Connection con = Conexao.getConexao();
        Statement stat = con.createStatement();
        try{
        String sql;
        sql = "delete from produto where idProduto = " +idProduto;
        stat.execute(sql);
    } catch (SQLException e) {
        throw new SQLException ("Erro ao deletar dados no banco");
            //depois de fazer a conexão, usa-se o finally para finalizar a conexão e fechar o banco de forma segura
        }finally {
            stat.close();
            con.close();
        }
    }
    
    public ArrayList<ProdutoVO> filtrar(String query) throws SQLException {
        Connection con = Conexao.getConexao();
        Statement stat = con.createStatement();
        try{
            String sql;
            sql = "select * from produto "+query;
            //traz todos os resultados da query do banco
            ResultSet rset = stat.executeQuery(sql);
            ArrayList<ProdutoVO> pvo = new ArrayList<>();
            
            //busca sempre o próximo dado existente no banco e retorna pro atributo existente no produtoVO
            while(rset.next()) {
                ProdutoVO prod = new ProdutoVO();
                prod.setIdproduto(rset.getLong("idproduto"));
                prod.setNome(rset.getString("nome"));
                prod.setQuantidade(rset.getInt("quantidade"));
                prod.setValorCusto(rset.getDouble("valorcusto"));
                
                pvo.add(prod);
            }
            return pvo;
        } catch (SQLException e) {
            throw new SQLException ("Erro ao pesquisar dados no banco"+e.getMessage());
        }finally {
            stat.close();
            con.close();
        }  
    }
    
    public void alterarProduto(ProdutoVO pVo) throws SQLException {
        Connection con = Conexao.getConexao();
        Statement stat = con.createStatement();
        try{
            //atualiza os dados, pegando da classe (get) aquilo que já foi adicionado anteriormente e seta para o comando update
            String sql = "update produto set nome = '"+pVo.getNome()+"',valorcusto = "+pVo.getValorCusto()+",quantidade = "+pVo.getQuantidade()+" where idproduto = "+pVo.getIdproduto();
            stat.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SQLException ("Erro ao atualizar dados no banco"+e.getMessage());
        }finally {
            stat.close();
            con.close();
        }  
    }
    
}
