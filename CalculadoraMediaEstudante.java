package br.com.educacao.calculadora;

import java.util.Scanner;
import java.text.DecimalFormat;

/**
 * Classe responsável por calcular a média de um estudante
 * com base em três notas (N1, N2, N3).
 * 
 * @author Mauro de Lima
 * @version 1.0
 * @since 2025-07-07
 */
public class CalculadoraMediaEstudante {
    
    // === CONSTANTES ===
    
    /** Nota mínima permitida */
    private static final double NOTA_MINIMA = 0.0;
    
    /** Nota máxima permitida */
    private static final double NOTA_MAXIMA = 10.0;
    
    /** Média mínima para aprovação */
    private static final double MEDIA_APROVACAO = 7.0;
    
    /** Número de notas para calcular a média */
    private static final int QUANTIDADE_NOTAS = 3;
    
    // === ATRIBUTOS DA CLASSE ===
    
    /** Scanner para leitura de dados do usuário */
    private static Scanner scanner = new Scanner(System.in);
    
    /** Formatador para exibir números com 2 casas decimais */
    private static DecimalFormat formatadorDecimal = new DecimalFormat("#0.00");
    
    // === MÉTODO PRINCIPAL ===
    
    /**
     * Método principal que executa o programa de cálculo de média
     * 
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        try {
            // Exibe cabeçalho do programa
            exibirCabecalho();
            
            // Coleta dados do estudante
            String nomeEstudante = obterNomeEstudante();
            
            // Coleta as três notas
            double primeiraNota = obterNota("primeira nota (N1)");
            double segundaNota = obterNota("segunda nota (N2)");
            double terceiraNota = obterNota("terceira nota (N3)");
            
            // Calcula a média
            double mediaFinal = calcularMedia(primeiraNota, segundaNota, terceiraNota);
            
            // Determina a situação do estudante
            String situacaoEstudante = determinarSituacao(mediaFinal);
            
            // Exibe o resultado
            exibirResultado(nomeEstudante, primeiraNota, segundaNota, 
                           terceiraNota, mediaFinal, situacaoEstudante);
            
        } catch (Exception excecao) {
            System.err.println(" Erro inesperado: " + excecao.getMessage());
        } finally {
            // Fecha o scanner para liberar recursos
            fecharRecursos();
        }
    }
    
    // === MÉTODOS DE ENTRADA DE DADOS ===
    
    /**
     * Obtém o nome do estudante
     * 
     * @return nome do estudante válido (não vazio)
     */
    private static String obterNomeEstudante() {
        String nomeEstudante;
        
        do {
            System.out.print("👤 Digite o nome do estudante: ");
            nomeEstudante = scanner.nextLine().trim();
            
            if (nomeEstudante.isEmpty()) {
                System.out.println("⚠️  Nome não pode estar vazio. Tente novamente.");
            }
            
        } while (nomeEstudante.isEmpty());
        
        return nomeEstudante;
    }
    
    /**
     * Obtém uma nota válida do usuário
     * 
     * @param descricaoNota descrição da nota sendo solicitada
     * @return nota válida entre 0.0 e 10.0
     */
    private static double obterNota(String descricaoNota) {
        double nota;
        boolean notaValida;
        
        do {
            System.out.printf("Digite a %s (0.0 a 10.0): ", descricaoNota);
            
            // Verifica se a entrada é um número válido
            if (scanner.hasNextDouble()) {
                nota = scanner.nextDouble();
                notaValida = validarNota(nota);
                
                if (!notaValida) {
                    System.out.printf(" Nota inválida! Digite um valor entre %.1f e %.1f%n", 
                                    NOTA_MINIMA, NOTA_MAXIMA);
                }
            } else {
                System.out.println("  Por favor, digite um número válido!");
                scanner.next(); // Limpa entrada inválida
                nota = -1; // Valor inválido para continuar o loop
                notaValida = false;
            }
            
        } while (!notaValida);
        
        // Limpa o buffer do scanner
        scanner.nextLine();
        
        return nota;
    }
    
    // === MÉTODOS DE VALIDAÇÃO ===
    
    /**
     * Valida se uma nota está dentro dos limites permitidos
     * 
     * @param nota nota a ser validada
     * @return true se a nota é válida, false caso contrário
     */
    private static boolean validarNota(double nota) {
        return nota >= NOTA_MINIMA && nota <= NOTA_MAXIMA;
    }
    
    // === MÉTODOS DE CÁLCULO ===
    
    /**
     * Calcula a média aritmética de três notas
     * 
     * @param primeiraNota primeira nota (N1)
     * @param segundaNota segunda nota (N2)
     * @param terceiraNota terceira nota (N3)
     * @return média aritmética das três notas
     */
    private static double calcularMedia(double primeiraNota, double segundaNota, double terceiraNota) {
        // Validação das notas antes do cálculo
        if (!validarNota(primeiraNota) || !validarNota(segundaNota) || !validarNota(terceiraNota)) {
            throw new IllegalArgumentException("Uma ou mais notas são inválidas para o cálculo da média");
        }
        
        // Cálculo da média aritmética
        double somaTotal = primeiraNota + segundaNota + terceiraNota;
        double mediaCalculada = somaTotal / QUANTIDADE_NOTAS;
        
        return mediaCalculada;
    }
    
    /**
     * Determina a situação do estudante com base na média
     * 
     * @param mediaFinal média final calculada
     * @return situação do estudante (Aprovado/Reprovado)
     */
    private static String determinarSituacao(double mediaFinal) {
        if (mediaFinal >= MEDIA_APROVACAO) {
            return "APROVADO";
        } else {
            return "REPROVADO";
        }
    }
    
    // === MÉTODOS DE EXIBIÇÃO ===
    
    /**
     * Exibe o cabeçalho do programa
     */
    private static void exibirCabecalho() {
        System.out.println("=" .repeat(60));
        System.out.println("          CALCULADORA DE MÉDIA ESTUDANTIL ");
        System.out.println("=" .repeat(60));
        System.out.println("Este programa calcula a média de um estudante com 3 notas.");
        System.out.println("Média para aprovação: " + formatadorDecimal.format(MEDIA_APROVACAO));
        System.out.println("-" .repeat(60));
    }
    
    /**
     * Exibe o resultado completo do cálculo
     * 
     * @param nomeEstudante nome do estudante
     * @param primeiraNota primeira nota
     * @param segundaNota segunda nota
     * @param terceiraNota terceira nota
     * @param mediaFinal média calculada
     * @param situacaoEstudante situação final
     */
    private static void exibirResultado(String nomeEstudante, double primeiraNota, 
                                      double segundaNota, double terceiraNota, 
                                      double mediaFinal, String situacaoEstudante) {
        
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("                    RELATÓRIO FINAL");
        System.out.println("=" .repeat(60));
        
        System.out.printf(" Estudante: %s%n", nomeEstudante);
        System.out.println("-" .repeat(60));
        
        System.out.printf(" Primeira Nota (N1): %s%n", formatadorDecimal.format(primeiraNota));
        System.out.printf(" Segunda Nota (N2):  %s%n", formatadorDecimal.format(segundaNota));
        System.out.printf(" Terceira Nota (N3): %s%n", formatadorDecimal.format(terceiraNota));
        
        System.out.println("-" .repeat(60));
        System.out.printf(" MÉDIA FINAL: %s%n", formatadorDecimal.format(mediaFinal));
        System.out.printf(" SITUAÇÃO: %s%n", situacaoEstudante);
        System.out.println("=" .repeat(60));
        
        // Exibe informações adicionais baseadas na situação
        exibirInformacoesAdicionais(mediaFinal);
    }
    
    /**
     * Exibe informações adicionais baseadas na média do estudante
     * 
     * @param mediaFinal média final calculada
     */
    private static void exibirInformacoesAdicionais(double mediaFinal) {
        System.out.println("\n INFORMAÇÕES ADICIONAIS:");
        
        if (mediaFinal >= 9.0) {
            System.out.println(" Excelente desempenho! Parabéns!");
        } else if (mediaFinal >= MEDIA_APROVACAO) {
            System.out.println(" Bom desempenho! Continue assim!");
        } else if (mediaFinal >= 5.0) {
            System.out.println("  Desempenho regular. Considere estudar mais.");
        } else {
            System.out.println("É necessário melhorar os estudos.");
        }
        
        // Calcula quantos pontos faltam para aprovação (se reprovado)
        if (mediaFinal < MEDIA_APROVACAO) {
            double pontosFaltantes = MEDIA_APROVACAO - mediaFinal;
            System.out.printf(" Faltaram %.2f pontos para aprovação.%n", pontosFaltantes);
        }
        
        System.out.println("\n🎓 Obrigado por usar a Calculadora de Média Estudantil!");
    }
    
    // === MÉTODOS DE LIMPEZA ===
    
    /**
     * Fecha recursos utilizados pelo programa
     */
    private static void fecharRecursos() {
        if (scanner != null) {
            scanner.close();
        }
    }
}