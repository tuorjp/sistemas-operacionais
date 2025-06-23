package segunda_va.cap9;

public class SimulacaoGerenciaMemoria {
    public static void main(String[] args) {
        // Define o tamanho total da memória RAM para a simulação
        int tamanhoMemoriaRAM = 100; // KB (unidades abstratas)
        GerenciadorMemoria gm = new GerenciadorMemoria(tamanhoMemoriaRAM);

        // --- Demonstração de Alocação de Memória (Dinâmica) ---
        // Criando alguns processos
        Processo p1 = new Processo("P1", 20);
        Processo p2 = new Processo("P2", 30);
        Processo p3 = new Processo("P3", 10);
        Processo p4 = new Processo("P4", 40);
        Processo p5 = new Processo("P5", 15);
        Processo p6 = new Processo("P6", 25); // Processo que pode exigir swap

        gm.visualizarMemoria();

        // 1. Alocando P1
        gm.alocarProcesso(p1);
        gm.visualizarMemoria();

        // 2. Alocando P2
        gm.alocarProcesso(p2);
        gm.visualizarMemoria();

        // 3. Alocando P3
        gm.alocarProcesso(p3);
        gm.visualizarMemoria();

        // 4. Tentando alocar P4 (deve caber)
        gm.alocarProcesso(p4);
        gm.visualizarMemoria();

        // 5. Tentando alocar P5 (não deve caber, sem fragmentação suficiente)
        gm.alocarProcesso(p5);
        gm.visualizarMemoria();

        // --- Demonstração de Desalocação e Fragmentação ---
        // Desalocando P2 para criar um buraco no meio
        gm.desalocarProcesso(p2);
        gm.visualizarMemoria(); // Agora deve haver um buraco onde P2 estava

        // 6. Tentando alocar P5 novamente (agora deve caber no buraco de P2 ou outro)
        gm.alocarProcesso(p5);
        gm.visualizarMemoria();

        // --- Demonstração de Swapping ---
        System.out.println("\n--- INICIANDO DEMONSTRAÇÃO DE SWAPPING ---");

        // Tentando alocar P6 (pode não caber, forçando um swap)
        // Se P6 não couber, precisaremos liberar espaço
        if (!gm.alocarProcesso(p6)) {
            System.out.println("Memória cheia! Necessário liberar espaço ou realizar SWAP OUT.");
            // Exemplo: Swapping out P3 para liberar espaço para P6
            gm.swapOut(p3);
            gm.visualizarMemoria();
            // Tenta alocar P6 novamente
            if (gm.alocarProcesso(p6)) {
                System.out.println("P6 alocado após SWAP OUT de P3.");
            } else {
                System.out.println("Ainda não foi possível alocar P6 mesmo após SWAP OUT de P3.");
            }
        }
        gm.visualizarMemoria();

        // Realizando SWAP IN de P3 (trazendo de volta do disco)
        gm.swapIn("P3"); // Tenta trazer P3 de volta. Pode não caber se não houver espaço.
        gm.visualizarMemoria();

        // Desalocando todos para limpar a memória
        System.out.println("\n--- DESALOCANDO TODOS OS PROCESSOS ---");
        gm.desalocarProcesso(p1);
        gm.desalocarProcesso(p4);
        gm.desalocarProcesso(p5);
        gm.desalocarProcesso(p6); // Se P6 foi alocado
        gm.desalocarProcesso(p3); // Se P3 foi feito swap in e ainda está na memória

        gm.visualizarMemoria();

        System.out.println("Simulação de Gerência de Memória concluída.");
    }
}
