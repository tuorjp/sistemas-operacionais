import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Atividades {
    public static void main(String[] args) {
        int simulacao = -1;

        System.out.println("Selecione uma simulação: \n");
        System.out.println(
                """
                1 - Execução monoprogramada
                2 - Execução multiprogramada
                3 - Simulação de interrupções de um processo
                4 - Comparar leitura de arquivos com e sem buffer
                """
        );

        Scanner scan = new Scanner(System.in);

        while(true) {
            while (simulacao < 0 || simulacao > 4) {
                System.out.print("Digite o número da simulação (0 a 4): ");
                if (scan.hasNextInt()) {
                    simulacao = scan.nextInt();

                    if (simulacao < 0 || simulacao > 4) {
                        System.out.println("Número inválido. Por favor, escolha entre 0 e 4.");
                    }
                } else {
                    System.out.println("Por favor, insira um número inteiro válido.");
                    scan.next();
                }
            }

            switch (simulacao) {
                case 0:
                    System.out.println("Encerrando o programa...");
                    scan.close();
                    return;
                case 1:
                    Simulador.simularMonoProgramavel();
                    break;
                case 2:
                    Simulador.simularMultiProgramavel();
                    break;
                case 3:
                    Simulador.simularInterrupcoesDeUmProcesso();
                    break;
                case 4:
                    Simulador.compararLeituraComESemBuffer();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + simulacao);
            }

            simulacao = -1;
        }
    }

    public static void simularTempoDeExecucao(Runnable runnable, int delay) {
        try {
            Thread.sleep(delay * 1000);
            runnable.run();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }
}

class Processo {
    public String nome;
    public Integer seconds;

    public Processo(String nome, Integer seconds) {
        this.nome = nome;
        this.seconds = seconds;
    }
}

class ProcessoParalelo extends Thread {
    private final String nome;
    private final int duracao;

    public ProcessoParalelo(String nome, int duracao) {
        this.nome = nome;
        this.duracao = duracao;
    }

    @Override
    public void run() {
        System.out.println(nome + " iniciado.");
        try {
            for (int i = 0; i < duracao; i++) {
                System.out.println(nome + " executando... " + i + "s");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println(nome + " foi interrompido.");
        }
        System.out.println(nome + " finalizado");
    }
}

class Simulador {
    public static void simularMonoProgramavel() {
        List<Processo> processosMonoProgramaveis = new ArrayList<>();
        processosMonoProgramaveis.add(new Processo("Processo 1 - Processar cálculo", 1));
        processosMonoProgramaveis.add(new Processo("Processo 2 - Ler arquivo", 2));
        processosMonoProgramaveis.add(new Processo("Processo 3 - Gravar arquivo", 2));

        System.out.println("Iniciando processamento mono:");

        processosMonoProgramaveis.forEach((processo) -> {
            Atividades.simularTempoDeExecucao(
                    () -> System.out.println(processo.nome),
                    processo.seconds
            );
        });
    }

    public static void simularMultiProgramavel() {
        System.out.println("Iniciando processamento multi:");

        Thread processoA = new ProcessoParalelo("Processo A - multi", 5);
        Thread processoB = new ProcessoParalelo("Processo B - multi", 3);
        Thread processoC = new ProcessoParalelo("Processo C - multi", 4);

        processoA.start();
        processoB.start();
        processoC.start();

        try {
            processoA.join();
            processoB.join();
            processoC.join();
        } catch (InterruptedException e) {
            System.out.println("Erro ao aguardar término das Threads: " + e.getMessage());
        }
    }

    public static void simularInterrupcoesDeUmProcesso() {
        Thread processo = new ProcessoParalelo("Processo Principal", 10);
        processo.start();

        Thread interrupcao = new Thread(() -> {
            try {
                Thread.sleep(3000);
                processo.interrupt();
                System.out.println("Processo interrompido externamente!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        interrupcao.start();

        try {
            processo.join();
            interrupcao.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void compararLeituraComESemBuffer() {
        File arquivo = new File("arquivo_grande.txt");
        gerarArquivoDeTeste(arquivo, 10_000_000); // 10 milhões de linhas

        System.out.println("Iniciando leitura SEM buffer:");
        long inicioSemBuffer = System.currentTimeMillis();
        try (FileReader fr = new FileReader(arquivo)) {
            while (fr.read() != -1) { }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long fimSemBuffer = System.currentTimeMillis();
        System.out.println("Tempo sem buffer: " + (fimSemBuffer - inicioSemBuffer) + "ms");

        System.out.println("Iniciando leitura COM buffer:");
        long inicioComBuffer = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            while (br.readLine() != null) { }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long fimComBuffer = System.currentTimeMillis();
        System.out.println("Tempo com buffer: " + (fimComBuffer - inicioComBuffer) + "ms");
    }

    private static void gerarArquivoDeTeste(File arquivo, int linhas) {
        if (arquivo.exists()) return;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (int i = 0; i < linhas; i++) {
                writer.write("Linha " + i + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
