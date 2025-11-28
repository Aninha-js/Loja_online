package br.edu.ifpi.Factory;

import br.edu.ifpi.Model.Produto;

/**
 * Abstract Factory para criação de produtos
 * Implementa o padrão Factory Method
 */
public abstract class ProdutoFactory {
    
    /**
     * Factory Method - deve ser implementado pelas subclasses
     * @return Produto concreto criado
     */
    public abstract Produto criarProduto();
    
    /**
     * Template Method - define o processo completo de criação
     * @return Produto criado, configurado e validado
     */
    public final Produto criarProdutoCompleto() {
        // 1. Criar o produto usando o Factory Method
        Produto produto = criarProduto();
        
        // 2. Aplicar configurações comuns
        configurarProduto(produto);
        
        // 3. Validar o produto criado
        validarProduto(produto);
        
        // 4. Log da criação
        logCriacao(produto);
        
        return produto;
    }
    
    /**
     * Configurações comuns a todos os produtos
     */
    protected void configurarProduto(Produto produto) {
        // Configurações padrão que se aplicam a todos os produtos
        if (produto.getDescricao() == null || produto.getDescricao().trim().isEmpty()) {
            produto.setDescricao("Descrição não informada");
        }
    }
    
    /**
     * Validações comuns a todos os produtos
     */
    protected void validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        
        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
    }
    
    /**
     * Log da criação do produto
     */
    protected void logCriacao(Produto produto) {
        System.out.println("🏭 Factory: Produto criado - " + produto.getClass().getSimpleName() + 
                          " | Nome: " + produto.getNome() + 
                          " | Preço: R$ " + String.format("%.2f", produto.getPreco()));
    }
}