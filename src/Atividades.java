import java.util.Scanner;

public class Atividades {
    public static void main(String[] args) {
        int simulacao;

        System.out.println("Selecione uma simulação: \n");
        System.out.println(
                """
                1 - Execução monoprogramada\s
                2 - Execução multiprogramada\s
                3 - Simulação de interrupções de um processo\s
                """
        );

        Scanner scan = new Scanner(System.in);

        simulacao = -1;

        while (simulacao < 1 || simulacao > 3) {
            System.out.print("Digite o número da simulação (1, 2 ou 3): ");
            if (scan.hasNextInt()) {
                simulacao = scan.nextInt();

                if (simulacao < 1 || simulacao > 3) {
                    System.out.println("Número inválido. Por favor, escolha entre 1, 2 ou 3.");
                }
            } else {
                System.out.println("Por favor, insira um número inteiro válido.");
                scan.next();
            }
        }

        switch (simulacao) {
            case 1:
                Simulador.simularMonoProgramavel();
                break;
            case 2:
                Simulador.simularMultiProgramavel();
                break;
            case 3:
                Simulador.simularInterrupcoesDeUmProcesso();
                break;
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

    }

    public static void simularMultiProgramavel() {

    }

    public static void simularInterrupcoesDeUmProcesso() {

    }
}