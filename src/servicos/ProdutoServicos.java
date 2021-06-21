/*
Essa classe é responsável pela exibição dos métodos para as interfaces(janelas, GUI's). 
 */
package servicos;

import dao.DAOFactory;
import dao.ProdutoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ProdutoVO;

/**
 *
 * @author Camila_Camera
 */
public class ProdutoServicos {
    
    public void cadastrarProduto(ProdutoVO pVO) throws SQLException{
        ProdutoDAO pDAO = DAOFactory.getProdutoDAO();
        pDAO.cadastrarProduto(pVO);
    }
    
    public ArrayList<ProdutoVO> buscar() throws SQLException {
        ProdutoDAO pDAO = DAOFactory.getProdutoDAO();
        return pDAO.buscar();
    }
    
    public void deletarProduto(long idProduto) throws SQLException{
        ProdutoDAO pDAO = DAOFactory.getProdutoDAO();
        pDAO.deletarProduto(idProduto);
    }
    
    public ArrayList<ProdutoVO> filtrar(String query) throws SQLException {
        ProdutoDAO pDAO = DAOFactory.getProdutoDAO();
        return pDAO.filtrar(query);
    }
    
    public void alterarProduto(ProdutoVO pVo) throws SQLException {
        ProdutoDAO pDAO = DAOFactory.getProdutoDAO();
        pDAO.alterarProduto(pVo);
    }
}
