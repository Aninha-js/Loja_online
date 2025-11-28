package br.edu.ifpi.Factory;

import br.edu.ifpi.Model.Pagamento;

/**
 * Abstract Factory para criação de pagamentos
 * Implementa o padrão Factory Method
 */
public abstract class PagamentoFactory {
    
    /**
     * Factory Method - deve ser implementado pelas subclasses
     * @return Pagamento concreto criado
     */
    public abstract Pagamento criarPagamento();
    
    /**
     * Template Method - define o processo completo de criação
     * @return Pagamento criado, configurado e validado
     */
    public final Pagamento criarPagamentoCompleto() {
        // 1. Criar o pagamento usando o Factory Method
        Pagamento pagamento = criarPagamento();
        
        // 2. Aplicar configurações comuns
        configurarPagamento(pagamento);
        
        // 3. Validar o pagamento criado
        validarPagamento(pagamento);
        
        // 4. Log da criação
        logCriacao(pagamento);
        
        return pagamento;
    }
    
    /**
     * Configurações comuns a todos os pagamentos
     */
    protected void configurarPagamento(Pagamento pagamento) {
        // Configuração padrão: status pendente se não definido
        if (pagamento.getStatusPagamento() == null || pagamento.getStatusPagamento().trim().isEmpty()) {
            pagamento.setStatusPagamento("PENDENTE");
        }
    }
    
    /**
     * Validações comuns a todos os pagamentos
     */
    protected void validarPagamento(Pagamento pagamento) {
        if (pagamento.getValor() != null && pagamento.getValor() < 0) {
            throw new IllegalArgumentException("Valor do pagamento não pode ser negativo");
        }
        
        String status = pagamento.getStatusPagamento();
        if (status != null && !status.equals("PENDENTE") && !status.equals("PAGO")) {
            throw new IllegalArgumentException("Status do pagamento deve ser PENDENTE ou PAGO");
        }
    }
    
    /**
     * Log da criação do pagamento
     */
    protected void logCriacao(Pagamento pagamento) {
        String valor = pagamento.getValor() != null ? 
            "R$ " + String.format("%.2f", pagamento.getValor()) : "Não informado";
        
        System.out.println("💳 Factory: Pagamento criado - " + pagamento.getClass().getSimpleName() + 
                          " | Valor: " + valor + 
                          " | Status: " + pagamento.getStatusPagamento());
    }
}