/*
A classe DAOFactory é uma fábrica de DAO, ou seja, ela fabrica objetos de 
ProdutoDAO e retorna para qualquer classe que solicitar.
Quando ela retorna um objeto da classe ProdutoDAO ela está enviando o objeto com
os métodos que tem dentro dele, nesse caso o método cadastrarProduto.
Em suma, a classe Factory apenas cria um objeto da classe ProdutoDAO e retorna o
mesmo para a classe que solicitar.


O padrão Factory Method sugere que você substitua chamadas diretas de construção
de objetos (usando o operador new) por chamadas para um método fábrica especial.
Não se preocupe: os objetos ainda são criados através do operador new, mas esse 
está sendo chamado de dentro do método fábrica. Objetos retornados por um método 
fábrica geralmente são chamados de produtos.
 */
package dao;

/**
 *
 * @author Camila_Camera
 */
public class DAOFactory {
    private static ProdutoDAO produtoDAO = new ProdutoDAO();
    
    public static ProdutoDAO getProdutoDAO(){
        return produtoDAO;
    }
    
}
