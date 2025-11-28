package br.edu.ifpi.Factory;

import br.edu.ifpi.Model.Cliente;

/**
 * Factory para criação de clientes com validações robustas
 * Implementa validações de CPF, email e outros dados
 */
public class ClienteFactory {
    
    /**
     * Cria um cliente com todas as validações necessárias
     * @param nome Nome do cliente
     * @param email Email do cliente  
     * @param senha Senha do cliente
     * @param cpf CPF do cliente
     * @param endereco Endereço do cliente
     * @return Cliente criado e validado
     * @throws IllegalArgumentException se algum dado for inválido
     */
    public static Cliente criarCliente(String nome, String email, String senha, String cpf, String endereco) {
        // Validações antes da criação
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarCPF(cpf);
        validarEndereco(endereco);
        
        // Criar o cliente
        Cliente cliente = new Cliente(nome, email, senha, cpf, endereco);
        
        // Configurações adicionais
        configurarCliente(cliente);
        
        // Log da criação
        logCriacao(cliente);
        
        return cliente;
    }
    
    /**
     * Valida o nome do cliente
     */
    private static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        if (nome.trim().length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
        }
        
        if (nome.trim().length() > 100) {
            throw new IllegalArgumentException("Nome não pode ter mais de 100 caracteres");
        }
        
        // Validar se contém apenas letras e espaços
        if (!nome.trim().matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
            throw new IllegalArgumentException("Nome deve conter apenas letras e espaços");
        }
    }
    
    /**
     * Valida o email do cliente
     */
    private static void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        
        // Regex básico para validação de email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.trim().matches(emailRegex)) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        
        if (email.trim().length() > 150) {
            throw new IllegalArgumentException("Email não pode ter mais de 150 caracteres");
        }
    }
    
    /**
     * Valida a senha do cliente
     */
    private static void validarSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        
        if (senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres");
        }
        
        if (senha.length() > 50) {
            throw new IllegalArgumentException("Senha não pode ter mais de 50 caracteres");
        }
    }
    
    /**
     * Valida o CPF do cliente
     */
    private static void validarCPF(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }
        
        // Remove formatação (pontos e hífen)
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        
        // Verifica se tem 11 dígitos
        if (cpfNumerico.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos");
        }
        
        // Verifica se não são todos iguais (ex: 11111111111)
        if (cpfNumerico.matches("(\\d)\\1{10}")) {
            throw new IllegalArgumentException("CPF inválido - todos os dígitos são iguais");
        }
        
        // Validação dos dígitos verificadores
        if (!validarDigitosCPF(cpfNumerico)) {
            throw new IllegalArgumentException("CPF inválido - dígitos verificadores incorretos");
        }
    }
    
    /**
     * Valida os dígitos verificadores do CPF
     */
    private static boolean validarDigitosCPF(String cpf) {
        try {
            // Primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int resto = 11 - (soma % 11);
            int digito1 = (resto == 10 || resto == 11) ? 0 : resto;
            
            // Segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            resto = 11 - (soma % 11);
            int digito2 = (resto == 10 || resto == 11) ? 0 : resto;
            
            return (Character.getNumericValue(cpf.charAt(9)) == digito1) && 
                   (Character.getNumericValue(cpf.charAt(10)) == digito2);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Valida o endereço do cliente
     */
    private static void validarEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço é obrigatório");
        }
        
        if (endereco.trim().length() < 10) {
            throw new IllegalArgumentException("Endereço deve ter pelo menos 10 caracteres");
        }
        
        if (endereco.trim().length() > 200) {
            throw new IllegalArgumentException("Endereço não pode ter mais de 200 caracteres");
        }
    }
    
    /**
     * Configurações adicionais no cliente
     */
    private static void configurarCliente(Cliente cliente) {
        // Normalizar nome (primeira letra de cada palavra em maiúscula)
        String nomeNormalizado = normalizarNome(cliente.getNome());
        cliente.setNome(nomeNormalizado);
        
        // Email em minúsculas
        cliente.setEmail(cliente.getEmail().toLowerCase().trim());
        
        // Remover espaços extras do endereço
        cliente.setEndereco(cliente.getEndereco().trim().replaceAll("\\s+", " "));
    }
    
    /**
     * Normaliza o nome (primeira letra maiúscula)
     */
    private static String normalizarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return nome;
        }
        
        String[] palavras = nome.trim().toLowerCase().split("\\s+");
        StringBuilder nomeNormalizado = new StringBuilder();
        
        for (int i = 0; i < palavras.length; i++) {
            String palavra = palavras[i];
            if (palavra.length() > 0) {
                if (i > 0) nomeNormalizado.append(" ");
                
                // Preposições ficam em minúscula (exceto no início)
                if (i > 0 && (palavra.equals("de") || palavra.equals("da") || palavra.equals("do") || 
                             palavra.equals("das") || palavra.equals("dos") || palavra.equals("e"))) {
                    nomeNormalizado.append(palavra);
                } else {
                    nomeNormalizado.append(Character.toUpperCase(palavra.charAt(0)))
                                  .append(palavra.substring(1));
                }
            }
        }
        
        return nomeNormalizado.toString();
    }
    
    /**
     * Log da criação do cliente
     */
    private static void logCriacao(Cliente cliente) {
        String cpfMascara = cliente.getCpf().replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", 
                                                        "$1.$2.$3-$4");
        System.out.println("👤 Factory: Cliente criado - " + cliente.getNome() + 
                          " | Email: " + cliente.getEmail() + 
                          " | CPF: " + cpfMascara);
    }
}