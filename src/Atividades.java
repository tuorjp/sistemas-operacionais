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
                """
        );

        Scanner scan = new Scanner(System.in);

        while(true) {
            while (simulacao < 0 || simulacao > 3) {
                System.out.print("Digite o número da simulação (0, 1, 2 ou 3): ");
                if (scan.hasNextInt()) {
                    simulacao = scan.nextInt();

                    if (simulacao < 0 || simulacao > 3) {
                        System.out.println("Número inválido. Por favor, escolha entre 0, 1, 2 ou 3.");
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
                default:
                    throw new IllegalStateException("Unexpected value: " + simulacao);
            }

            simulacao = -1;
        }
    }

    public static void setTimeoutSync(Runnable runnable, int delay) {
        try {
            // Converte para milissegundos, que é o tipo de argumento da função
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

    public Processo(String nome) {
        this.nome = nome;
    }

    public Processo(String nome, Integer seconds) {
        this.nome = nome;
        this.seconds = seconds;
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
            Atividades.setTimeoutSync(
                    () -> System.out.println(processo.nome),
                    processo.seconds
            );
        });
    }

    public static void simularMultiProgramavel() {

    }

    public static void simularInterrupcoesDeUmProcesso() {

    }
}