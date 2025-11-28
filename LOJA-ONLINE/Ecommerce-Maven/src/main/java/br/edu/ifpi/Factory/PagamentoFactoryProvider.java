package br.edu.ifpi.Factory;

/**
 * Provider que retorna a Factory apropriada baseada no tipo de pagamento
 * Implementa o padrão Abstract Factory
 */
public class PagamentoFactoryProvider {
    
    /**
     * Retorna a factory apropriada baseada no tipo de pagamento
     * @param tipoPagamento "BOLETO", etc.
     * @return PagamentoFactory apropriada
     * @throws IllegalArgumentException se tipo inválido
     */
    public static PagamentoFactory getFactory(String tipoPagamento) {
        if (tipoPagamento == null) {
            throw new IllegalArgumentException("Tipo de pagamento não pode ser nulo");
        }
        
        switch (tipoPagamento.toUpperCase().trim()) {
            case "BOLETO":
            case "BOLETO_BANCARIO":
                return new BoletoFactory("");
            
            default:
                throw new IllegalArgumentException("Tipo de pagamento inválido: " + tipoPagamento + 
                    ". Tipos válidos: BOLETO");
        }
    }
    
    /**
     * Cria factory para boleto básico
     */
    public static PagamentoFactory criarFactoryBoleto(String codigoBoleto) {
        return new BoletoFactory(codigoBoleto);
    }
    
    /**
     * Cria factory para boleto com vencimento
     */
    public static PagamentoFactory criarFactoryBoleto(String codigoBoleto, String vencimento) {
        return new BoletoFactory(codigoBoleto, vencimento);
    }
    
    /**
     * Cria factory para boleto completo
     */
    public static PagamentoFactory criarFactoryBoleto(String codigoBoleto, String vencimento, Double valor) {
        return new BoletoFactory(codigoBoleto, vencimento, valor);
    }
}