/*
A classe ServicosFactory apenas cria um objeto da classe ProdutoServicos
e retorna o mesmo para a classe que solicitar,
geralmente uma classe de interface.


O padrão Factory Method sugere que você substitua chamadas diretas de construção
de objetos (usando o operador new) por chamadas para um método fábrica especial.
Não se preocupe: os objetos ainda são criados através do operador new, mas esse 
está sendo chamado de dentro do método fábrica. Objetos retornados por um método 
fábrica geralmente são chamados de produtos.
 */
package servicos;



/**
 *
 * @author Camila_Camera
 */
public class ServicosFactory {
    //cria instância estática para que a mesma nunca seja modificada ou recebe interferências
    private static ProdutoServicos produtoServicos = new ProdutoServicos();
    
    public static ProdutoServicos getProdutoServicos() {
        return produtoServicos;
    }
    
   
    
}
