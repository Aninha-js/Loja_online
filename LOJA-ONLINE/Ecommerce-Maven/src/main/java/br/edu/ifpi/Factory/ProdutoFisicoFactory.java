package br.edu.ifpi.Factory;

import br.edu.ifpi.Model.Produto;
import br.edu.ifpi.Model.ProdutoFisico;

/**
 * Factory concreta para criação de Produtos Físicos
 * Implementa o Factory Method da classe ProdutoFactory
 */
public class ProdutoFisicoFactory extends ProdutoFactory {
    
    private String nome;
    private Double preco;
    private String descricao;
    private Double peso;
    private Integer estoque;
    
    /**
     * Construtor completo
     */
    public ProdutoFisicoFactory(String nome, Double preco, String descricao, 
                               Double peso, Integer estoque) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.peso = peso;
        this.estoque = estoque;
    }
    
    /**
     * Factory Method - cria um ProdutoFisico
     * @return ProdutoFisico criado com os parâmetros fornecidos
     */
    @Override
    public Produto criarProduto() {
        return new ProdutoFisico(nome, preco, descricao, peso, estoque);
    }
    
    /**
     * Validações específicas para produtos físicos
     */
    @Override
    protected void validarProduto(Produto produto) {
        // Primeiro executa validações da classe pai
        super.validarProduto(produto);
        
        // Depois validações específicas
        ProdutoFisico pf = (ProdutoFisico) produto;
        
        if (pf.getPeso() != null && pf.getPeso() <= 0) {
            throw new IllegalArgumentException("Peso deve ser maior que zero");
        }
        
        if (pf.getEstoque() != null && pf.getEstoque() < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
    }
    
    /**
     * Configurações específicas para produtos físicos
     */
    @Override
    protected void configurarProduto(Produto produto) {
        super.configurarProduto(produto);
        
        ProdutoFisico pf = (ProdutoFisico) produto;
        
        // Se não tem peso definido, configura como padrão
        if (pf.getPeso() == null) {
            pf.setPeso(0.1); // Peso mínimo padrão
        }
        
        // Se não tem estoque definido, inicia com zero
        if (pf.getEstoque() == null) {
            pf.setEstoque(0);
        }
    }
    
    /**
     * Log específico para produtos físicos
     */
    @Override
    protected void logCriacao(Produto produto) {
        super.logCriacao(produto);
        ProdutoFisico pf = (ProdutoFisico) produto;
        System.out.println("   📦 Estoque: " + pf.getEstoque() + " unidades | Peso: " + pf.getPeso() + " kg");
    }
}