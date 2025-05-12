package atividades1a5;

import java.io.*;
import java.util.*;

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
                5 - Simular Spooling de impressão
                6 - Teste de funções reentrantes e não reentrantes
                """
        );

        Scanner scan = new Scanner(System.in);

        while(true) {
            while (simulacao < 0 || simulacao > 6) {
                System.out.print("Digite o número da simulação (0 a 6): ");
                if (scan.hasNextInt()) {
                    simulacao = scan.nextInt();

                    if (simulacao < 0 || simulacao > 6) {
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
                case 5:
                    Simulador.simularSpoolingDeImpressao();
                    break;
                case 6:
                    Simulador.simularFuncoesReentrantes();
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
        processosMonoProgramaveis.add(new Processo("atividades1a5.Processo 1 - Processar cálculo", 1));
        processosMonoProgramaveis.add(new Processo("atividades1a5.Processo 2 - Ler arquivo", 2));
        processosMonoProgramaveis.add(new Processo("atividades1a5.Processo 3 - Gravar arquivo", 2));

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

        Thread processoA = new ProcessoParalelo("atividades1a5.Processo A - multi", 5);
        Thread processoB = new ProcessoParalelo("atividades1a5.Processo B - multi", 3);
        Thread processoC = new ProcessoParalelo("atividades1a5.Processo C - multi", 4);

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
        Thread processo = new ProcessoParalelo("atividades1a5.Processo Principal", 10);
        processo.start();

        Thread interrupcao = new Thread(() -> {
            try {
                Thread.sleep(3000);
                processo.interrupt();
                System.out.println("atividades1a5.Processo interrompido externamente!");
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

    public static void simularSpoolingDeImpressao() {
        SpoolerImpressao spooler = new SpoolerImpressao();
        spooler.start();

        spooler.adicionarDocumento(new Documento("Doc1", 3, 2));
        spooler.adicionarDocumento(new Documento("Doc2", 2, 1));
        spooler.adicionarDocumento(new Documento("Doc3", 1, 3));

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        spooler.interrupt(); // Encerra a simulação
    }

    public static void simularFuncoesReentrantes() {
        System.out.println("Executando versão NÃO REENTRANTE:");
        Thread t1 = new ThreadNaoReentrante();
        Thread t2 = new ThreadNaoReentrante();
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nExecutando versão REENTRANTE:");
        Thread r1 = new ThreadReentrante();
        Thread r2 = new ThreadReentrante();
        r1.start();
        r2.start();

        try {
            r1.join();
            r2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Documento {
    String nome;
    int tempoProcessamento;
    int prioridade;

    public Documento(String nome, int tempoProcessamento, int prioridade) {
        this.nome = nome;
        this.tempoProcessamento = tempoProcessamento;
        this.prioridade = prioridade;
    }
}

class SpoolerImpressao extends Thread {
    private final PriorityQueue<Documento> fila;

    public SpoolerImpressao() {
        fila = new PriorityQueue<>(Comparator.comparingInt(d -> d.prioridade));
    }

    public synchronized void adicionarDocumento(Documento doc) {
        fila.add(doc);
        notify(); // notifica a thread para continuar, caso esteja esperando
    }

    @Override
    public void run() {
        while (true) {
            Documento doc;
            synchronized (this) {
                while (fila.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                doc = fila.poll();
            }

            System.out.println("Imprimindo: " + doc.nome + " (Prioridade: " + doc.prioridade + ")");
            try {
                Thread.sleep(doc.tempoProcessamento * 1000);
            } catch (InterruptedException e) {
                System.out.println("Impressão interrompida.");
            }
            System.out.println("Finalizado: " + doc.nome);
        }
    }
}

class ContadorNaoReentrante {
    private static int contadorGlobal = 0;

    public static int incrementar() {
        int temp = contadorGlobal;
        try {
            Thread.sleep(50); // Simula operação demorada
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        contadorGlobal = temp + 1;
        return contadorGlobal;
    }
}

class ThreadNaoReentrante extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            int valor = ContadorNaoReentrante.incrementar();
            System.out.println(getName() + " => Contador: " + valor);
        }
    }
}

class ContadorReentrante {
    public static int incrementar(int valorAtual) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return valorAtual + 1;
    }
}

class ThreadReentrante extends Thread {
    public void run() {
        int local = 0;
        for (int i = 0; i < 5; i++) {
            local = ContadorReentrante.incrementar(local);
            System.out.println(getName() + " => Contador: " + local);
        }
    }
}