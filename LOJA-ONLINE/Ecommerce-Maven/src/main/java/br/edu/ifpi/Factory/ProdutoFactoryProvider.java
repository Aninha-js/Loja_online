package br.edu.ifpi.Factory;

/**
 * Provider que retorna a Factory apropriada baseada no tipo de produto
 * Implementa o padrão Abstract Factory
 */
public class ProdutoFactoryProvider {
    
    /**
     * Retorna a factory apropriada baseada no tipo de produto
     * @param tipoProduto "DIGITAL" ou "FISICO"
     * @return ProdutoFactory apropriada
     * @throws IllegalArgumentException se tipo inválido
     */
    public static ProdutoFactory getFactory(String tipoProduto) {
        if (tipoProduto == null) {
            throw new IllegalArgumentException("Tipo de produto não pode ser nulo");
        }
        
        switch (tipoProduto.toUpperCase().trim()) {
            case "DIGITAL":
            case "PRODUTO_DIGITAL":
            case "PD":
                return new ProdutoDigitalFactory("", 0.0, "", "");
            
            case "FISICO":
            case "FÍSICO":
            case "PRODUTO_FISICO":
            case "PF":
                return new ProdutoFisicoFactory("", 0.0, "", 0.0, 0);
            
            default:
                throw new IllegalArgumentException("Tipo de produto inválido: " + tipoProduto + 
                    ". Tipos válidos: DIGITAL, FISICO");
        }
    }
    
    /**
     * Cria factory para produto digital com parâmetros
     */
    public static ProdutoFactory criarFactoryProdutoDigital(String nome, Double preco, 
                                                           String descricao, String urlDownload) {
        return new ProdutoDigitalFactory(nome, preco, descricao, urlDownload);
    }
    
    /**
     * Cria factory para produto digital completa
     */
    public static ProdutoFactory criarFactoryProdutoDigital(String nome, Double preco, 
                                                           String descricao, String urlDownload, 
                                                           Integer tamanhoArquivo) {
        return new ProdutoDigitalFactory(nome, preco, descricao, urlDownload, tamanhoArquivo);
    }
    
    /**
     * Cria factory para produto físico com parâmetros
     */
    public static ProdutoFactory criarFactoryProdutoFisico(String nome, Double preco, 
                                                          String descricao, Double peso, 
                                                          Integer estoque) {
        return new ProdutoFisicoFactory(nome, preco, descricao, peso, estoque);
    }
}