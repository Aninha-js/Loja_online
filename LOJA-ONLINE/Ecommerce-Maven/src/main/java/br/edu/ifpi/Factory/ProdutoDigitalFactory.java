package br.edu.ifpi.Factory;

import br.edu.ifpi.Model.Produto;
import br.edu.ifpi.Model.ProdutoDigital;

/**
 * Factory concreta para criação de Produtos Digitais
 * Implementa o Factory Method da classe ProdutoFactory
 */
public class ProdutoDigitalFactory extends ProdutoFactory {
    
    private String nome;
    private Double preco;
    private String descricao;
    private String urlDownload;
    private Integer tamanhoArquivo;
    private boolean pularValidacaoUrl = false; // Para casos onde usuário confirmou URL inválida
    
    /**
     * Construtor com parâmetros obrigatórios
     */
    public ProdutoDigitalFactory(String nome, Double preco, String descricao, String urlDownload) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.urlDownload = urlDownload;
    }
    
    /**
     * Construtor completo
     */
    public ProdutoDigitalFactory(String nome, Double preco, String descricao, 
                                String urlDownload, Integer tamanhoArquivo) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.urlDownload = urlDownload;
        this.tamanhoArquivo = tamanhoArquivo;
    }
    
    /**
     * Factory Method - cria um ProdutoDigital
     * @return ProdutoDigital criado com os parâmetros fornecidos
     */
    @Override
    public Produto criarProduto() {
        if (tamanhoArquivo != null) {
            return new ProdutoDigital(nome, preco, descricao, urlDownload, tamanhoArquivo);
        } else {
            return new ProdutoDigital(nome, preco, descricao, urlDownload);
        }
    }
    
    /**
     * Validações específicas para produtos digitais
     */
    @Override
    protected void validarProduto(Produto produto) {
        // Primeiro executa validações da classe pai
        super.validarProduto(produto);
        
        // Depois validações específicas
        ProdutoDigital pd = (ProdutoDigital) produto;
        
        if (pd.getUrlDownload() == null || pd.getUrlDownload().trim().isEmpty()) {
            throw new IllegalArgumentException("URL de download é obrigatória para produtos digitais");
        }
        
        // Só valida formato da URL se não foi explicitamente permitida pelo usuário
        if (!pularValidacaoUrl && !pd.getUrlDownload().startsWith("http://") && !pd.getUrlDownload().startsWith("https://")) {
            throw new IllegalArgumentException("URL de download deve começar com http:// ou https://");
        }
        
        // Tamanho do arquivo é OPCIONAL para produtos digitais
        // Se informado, deve ser maior que zero. Se não informado (null ou 0), é válido
        if (pd.getTamanhoArquivo() != null && pd.getTamanhoArquivo() < 0) {
            throw new IllegalArgumentException("Tamanho do arquivo não pode ser negativo");
        }
    }
    
    /**
     * Configurações específicas para produtos digitais
     */
    @Override
    protected void configurarProduto(Produto produto) {
        super.configurarProduto(produto);
        
        // Tamanho do arquivo é opcional para produtos digitais
        // Se não informado, deixa como null (mais semântico que 0)
        // Não força um valor padrão pois pode não ser necessário
    }
    
    // Setters para permitir modificação após construção
    public ProdutoDigitalFactory setTamanhoArquivo(Integer tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
        return this;
    }
    
    /**
     * Permite pular a validação de URL quando o usuário confirma que quer continuar
     * @return esta instância para method chaining
     */
    public ProdutoDigitalFactory permitirUrlInvalida() {
        this.pularValidacaoUrl = true;
        return this;
    }
}