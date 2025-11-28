package br.edu.ifpi.Factory;

import br.edu.ifpi.Model.Pagamento;
import br.edu.ifpi.Model.Boleto;

/**
 * Factory concreta para criação de Boletos
 * Implementa o Factory Method da classe PagamentoFactory
 */
public class BoletoFactory extends PagamentoFactory {
    
    private String codigoBoleto;
    private String vencimento;
    private Double valor;
    
    /**
     * Construtor básico - apenas código
     */
    public BoletoFactory(String codigoBoleto) {
        this.codigoBoleto = codigoBoleto;
    }
    
    /**
     * Construtor com código e vencimento
     */
    public BoletoFactory(String codigoBoleto, String vencimento) {
        this.codigoBoleto = codigoBoleto;
        this.vencimento = vencimento;
    }
    
    /**
     * Construtor completo
     */
    public BoletoFactory(String codigoBoleto, String vencimento, Double valor) {
        this.codigoBoleto = codigoBoleto;
        this.vencimento = vencimento;
        this.valor = valor;
    }
    
    /**
     * Factory Method - cria um Boleto
     * @return Boleto criado com os parâmetros fornecidos
     */
    @Override
    public Pagamento criarPagamento() {
        if (valor != null) {
            return new Boleto(codigoBoleto, vencimento, valor);
        } else if (vencimento != null) {
            return new Boleto(codigoBoleto, vencimento);
        } else {
            return new Boleto(codigoBoleto);
        }
    }
    
    /**
     * Validações específicas para boletos
     */
    @Override
    protected void validarPagamento(Pagamento pagamento) {
        // Primeiro executa validações da classe pai
        super.validarPagamento(pagamento);
        
        // Depois validações específicas do boleto
        Boleto boleto = (Boleto) pagamento;
        
        if (boleto.getCodigoBoleto() == null || boleto.getCodigoBoleto().trim().isEmpty()) {
            throw new IllegalArgumentException("Código do boleto é obrigatório");
        }
        
        // Validação do formato do código do boleto (deve ter pelo menos 10 caracteres)
        if (boleto.getCodigoBoleto().length() < 10) {
            throw new IllegalArgumentException("Código do boleto deve ter pelo menos 10 caracteres");
        }
        
        // Se tem vencimento, valida formato básico
        if (boleto.getVencimento() != null && !boleto.getVencimento().trim().isEmpty()) {
            String venc = boleto.getVencimento().trim();
            // Validação básica de formato DD/MM/YYYY
            if (!venc.matches("\\d{2}/\\d{2}/\\d{4}")) {
                throw new IllegalArgumentException("Vencimento deve estar no formato DD/MM/YYYY");
            }
        }
    }
    
    /**
     * Configurações específicas para boletos
     */
    @Override
    protected void configurarPagamento(Pagamento pagamento) {
        super.configurarPagamento(pagamento);
        
        Boleto boleto = (Boleto) pagamento;
        
        // Se não tem vencimento, define vencimento padrão (30 dias)
        if (boleto.getVencimento() == null || boleto.getVencimento().trim().isEmpty()) {
            java.time.LocalDate dataVencimento = java.time.LocalDate.now().plusDays(30);
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            boleto.setVencimento(dataVencimento.format(formatter));
        }
        
        // Se tem valor, define no pagamento
        if (valor != null) {
            boleto.setValor(valor);
        }
    }
    
    /**
     * Log específico para boletos
     */
    @Override
    protected void logCriacao(Pagamento pagamento) {
        super.logCriacao(pagamento);
        Boleto boleto = (Boleto) pagamento;
        String codigoExibido = boleto.getCodigoBoleto().length() > 20 ? 
            boleto.getCodigoBoleto().substring(0, 20) + "..." : boleto.getCodigoBoleto();
        System.out.println("   🔢 Código: " + codigoExibido + " | Vencimento: " + boleto.getVencimento());
    }
    
    // Setters para permitir modificação após construção
    public BoletoFactory setVencimento(String vencimento) {
        this.vencimento = vencimento;
        return this;
    }
    
    public BoletoFactory setValor(Double valor) {
        this.valor = valor;
        return this;
    }
}