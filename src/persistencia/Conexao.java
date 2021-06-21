//  Classe de conexão com o banco de dados e Java
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Camila_Camera
 */
public class Conexao {
    //variáveis constantes (static) serão sempre em MAIÚSCULO
    //se houver mais de uma palavra ELA_SAO_SEPARADAS_POR_UNDERLINE 
    
    //cria uma constante com o endereçamento da base de dados no conector com mysql
    private static String URL = "jdbc:mysql://localhost:3306/loja_produto";
    //cria uma constante para determinar usuário pré-definido do banco de dados (na instalação)
    private static String USUARIO = "root";
    //cria uma constante para determinar a senha do usuário
    private static String SENHA = "";
    
    //método que estabele a conexão com o banco de dados, incluindo excessões
    public static Connection getConexao() throws SQLException {
        //diz que ainda não foi estabelecida a conexão com BD
        Connection c = null;
        //tenta estabelecer conexão
        try{
            //objeto c (Connection) fazendo a tentativa de conectar com o .JAR (jdbc connector) puxando as constantes como parâmetro
            c = DriverManager.getConnection(URL, USUARIO, SENHA);
        //caso a tentativa tenha sido falha, gera um excessão    
        }catch (SQLException e) {
            throw new SQLException ("Erro ao conectar", e.getMessage());
        }
        return c;
    }
   
}
