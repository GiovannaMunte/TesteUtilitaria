
package codigos;
// Giovanna Dias de Almeida Munte

public class TesteUtilitaria {


    public static void main(String[] args) {
       System.out.println("CPF valido: " + validarCPF(""));
        System.out.println("CNPJ valido: " + validarCNPJ(""));
        System.out.println("RG valido: " + validarRG(""));
        System.out.println("IE valido: " + validarIE(""));
        
    }
     public static boolean validarCNPJ(String cnpj) {
        // O comando replaceALL remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^0-9]", "");
        
        // Verificar o tamanho do CNPJ e o matches reconhce como é a escrita do CNPJ
        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false; 
        }

        // Calcula os dígitos verificadores
        int primeiroDigito = calcularDigitoVerificador(cnpj, 5);
        int segundoDigito = calcularDigitoVerificador(cnpj, 6, primeiroDigito);

        // Verificar se os dígitos calculados são iguais aos dígitos do CNPJ
        return primeiroDigito == Character.getNumericValue(cnpj.charAt(12)) &&
               segundoDigito == Character.getNumericValue(cnpj.charAt(13));
    }

    // Método para calcular o dígito verificador do CNPJ
    private static int calcularDigitoVerificador(String cnpj, int peso) {
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            // getNumericValue traz o cnpj como um int para poder verificar como um valor de conta
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso--;
            if (peso < 2) {
                peso = 9; // Reinicia o peso para 9
            }
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    private static int calcularDigitoVerificador(String cnpj, int peso, int primeiroDigito) {
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso--;
            if (peso < 2) {
                peso = 9; // Reinicia o peso para 9
            }
        }
        soma += primeiroDigito * 2; // Adiciona o primeiro dígito verificador
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
     public static boolean validarCPF(String cpf) {
         // O comando replaceALL remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        // Cálculo do primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            // getNumericValue traz o cpf como um int para poder verificar como um valor de conta
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int resto = soma % 11;
        int primeiroDigito = (resto < 2) ? 0 : 11 - resto;

        // Cálculo do segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        soma += primeiroDigito * 2;
        resto = soma % 11;
        int segundoDigito = (resto < 2) ? 0 : 11 - resto;

        // Verifica se os dígitos calculados correspondem aos do CPF
        return primeiroDigito == Character.getNumericValue(cpf.charAt(9)) &&
               segundoDigito == Character.getNumericValue(cpf.charAt(10));
    }
    // Método para calcular o dígito de controle
    public static int calcularDigitoControle(String rg) {
        // Remove caracteres não numéricos
        rg = rg.replaceAll("[^0-9]", "");
        
        // Verifica se o RG possui 8 dígitos
        if (rg.length() != 8) {
            
        }

        int soma = 0;
        // Soma os produtos do dígito pela posição
        for (int i = 0; i < rg.length(); i++) {
            // getNumericValue traz o rg como um int para poder verificar como um valor de conta
            int digito = Character.getNumericValue(rg.charAt(rg.length() - 1 - i));
            soma += digito * (9 - i);
        }

        // Encontra o dígito de controle
        for (int dc = 0; dc <= 9; dc++) {
            if ((soma + dc * 100) % 11 == 0) {
                return dc;
            }
        }

        return -1; // Este caso não deve ocorrer se o RG for válido
    }

    // Método para validar o RG completo
    public static boolean validarRG(String rg) {
        
        if (rg == null || rg.isEmpty()) { // para verificar se o rg não está vazio
     // uma exceção quando a entrada fornecida ao método não atende a certos critérios de validade.
            throw new IllegalArgumentException("O RG deve ser uma string válida.");
        }

        rg = rg.replaceAll("[^0-9]", "");

        // Verifica se o RG possui 9 dígitos
        if (rg.length() != 9) {
            return false; // O RG deve ter 9 dígitos incluindo o dígito de controle
        }

        int digitoControleInformado = Character.getNumericValue(rg.charAt(8));
        String rgSemDc = rg.substring(0, 8);

        int digitoControleCalculado = calcularDigitoControle(rgSemDc);

        return digitoControleInformado == digitoControleCalculado;
    }
       public static char calcularDigitoVerificador(String ie) {
        // Remove caracteres não numéricos
        ie = ie.replaceAll("[^0-9]", "");

        // Verifica se a ie possui 8 dígitos
        if (ie.length() != 8) {
          
        }

        // Pesos para multiplicação
        int[] pesos = {9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;

        // Calcula a soma ponderada
        for (int i = 0; i < ie.length(); i++) {
            // getNumericValue traz o ie como um int para poder verificar como um valor de conta
            int digito = Character.getNumericValue(ie.charAt(i));
            soma += digito * pesos[i];
        }

        // Calcula o resto da divisão por 11
        int resto = soma % 11;

        // Define o dígito verificador
        char digitoVerificador;
        if (resto == 0 || resto == 1) {
            digitoVerificador = '0';
        } else {
            digitoVerificador = Character.forDigit(11 - resto, 10);
        }

        return digitoVerificador;
    }

    // Método para validar a ie completa
    public static boolean validarIE(String ie) {
        if (ie == null || ie.isEmpty()) { // para verificar se o ie não está vazio
            // uma exceção quando a entrada fornecida ao método não atende a certos critérios de validade.
            throw new IllegalArgumentException("A Inscrição Estadual deve ser uma string válida.");
        }

        // Remove caracteres não numéricos
        ie = ie.replaceAll("[^0-9]", "");

        // Verifica se a ie possui 9 dígitos
        if (ie.length() != 9) {
            return false; // A IE deve ter 9 dígitos incluindo o dígito verificador
        }

        // Obtém o dígito verificador informado
        char digitoVerificadorInformado = ie.charAt(8);
        String ieSemDv = ie.substring(0, 8);

        // Calcula o dígito verificador esperado
        char digitoVerificadorCalculado = calcularDigitoVerificador(ieSemDv);

        return digitoVerificadorInformado == digitoVerificadorCalculado;
    }  
}