package segunda_va.cap9;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class GerenciadorMemoria {
    private List<BlocoMemoria> memoria;
    private int tamanhoTotalMemoria;
    private List<Processo> processosEmDisco; // Simula o disco para swapping

    public GerenciadorMemoria(int tamanhoTotalMemoria) {
        this.tamanhoTotalMemoria = tamanhoTotalMemoria;
        this.memoria = new ArrayList<>();
        // Inicializa a memória como um único bloco livre
        this.memoria.add(new BlocoMemoria(0, tamanhoTotalMemoria));
        this.processosEmDisco = new ArrayList<>();
        System.out.println("Gerenciador de Memória inicializado com " + tamanhoTotalMemoria + " KB de RAM.");
    }

    /**
     * Tenta alocar um processo na memória usando o algoritmo First-Fit.
     * @param processo O processo a ser alocado.
     * @return true se a alocação foi bem-sucedida, false caso contrário.
     */
    public boolean alocarProcesso(Processo processo) {
        System.out.println("\nTentando alocar Processo " + processo.getId() + " (" + processo.getTamanho() + " KB)...");

        // First-Fit: Encontra o primeiro bloco livre que seja grande o suficiente
        for (int i = 0; i < memoria.size(); i++) {
            BlocoMemoria blocoAtual = memoria.get(i);

            if (!blocoAtual.isOcupado() && blocoAtual.getTamanho() >= processo.getTamanho()) {
                // Encontramos um bloco adequado

                // 1. Criar o bloco para o processo
                BlocoMemoria novoBlocoOcupado = new BlocoMemoria(blocoAtual.getInicio(), blocoAtual.getInicio() + processo.getTamanho());
                novoBlocoOcupado.alocar(processo.getId());

                // 2. Atualizar o estado do processo
                processo.setEmMemoria(true);
                processo.setEnderecoInicial(novoBlocoOcupado.getInicio());

                // 3. Atualizar a lista de blocos de memória
                memoria.remove(i); // Remove o bloco livre original

                // Se houver espaço restante no bloco original, cria um novo bloco livre
                if (blocoAtual.getTamanho() > processo.getTamanho()) {
                    BlocoMemoria novoBlocoLivre = new BlocoMemoria(blocoAtual.getInicio() + processo.getTamanho(), blocoAtual.getFim());
                    memoria.add(i, novoBlocoLivre); // Adiciona o novo bloco livre antes (mantendo a ordem)
                }
                memoria.add(i, novoBlocoOcupado); // Adiciona o bloco ocupado no lugar

                System.out.println("Processo " + processo.getId() + " alocado com sucesso em [" + novoBlocoOcupado.getInicio() + "-" + (novoBlocoOcupado.getFim() - 1) + "].");
                consolidarBlocosLivres(); // Tenta juntar blocos livres adjacentes após alocação
                return true;
            }
        }
        System.out.println("Não há espaço contíguo suficiente na memória para o Processo " + processo.getId() + ".");
        return false;
    }

    /**
     * Desaloca um processo da memória.
     * @param processo O processo a ser desalocado.
     * @return true se a desalocação foi bem-sucedida, false caso contrário.
     */
    public boolean desalocarProcesso(Processo processo) {
        System.out.println("\nTentando desalocar Processo " + processo.getId() + "...");

        if (!processo.isEmMemoria()) {
            System.out.println("Erro: Processo " + processo.getId() + " não está na memória para ser desalocado.");
            return false;
        }

        boolean desalocado = false;
        for (int i = 0; i < memoria.size(); i++) {
            BlocoMemoria blocoAtual = memoria.get(i);
            if (blocoAtual.isOcupado() && blocoAtual.getIdProcesso().equals(processo.getId())) {
                blocoAtual.desalocar(); // Marca o bloco como livre
                processo.setEmMemoria(false);
                processo.setEnderecoInicial(-1); // Resetar endereço
                desalocado = true;
                break; // Encontrou e desalocou
            }
        }

        if (desalocado) {
            System.out.println("Processo " + processo.getId() + " desalocado com sucesso.");
            consolidarBlocosLivres(); // Tenta juntar blocos livres adjacentes após desalocação
            return true;
        } else {
            System.out.println("Erro: Não foi possível encontrar o Processo " + processo.getId() + " na memória para desalocação.");
            return false;
        }
    }

    /**
     * Consolida blocos de memória livres adjacentes.
     * Importante para reduzir a fragmentação externa.
     */
    private void consolidarBlocosLivres() {
        memoria.sort((b1, b2) -> Integer.compare(b1.getInicio(), b2.getInicio())); // Garante ordem

        List<BlocoMemoria> novaListaMemoria = new ArrayList<>();
        if (memoria.isEmpty()) {
            return;
        }

        BlocoMemoria blocoAtual = memoria.get(0);
        novaListaMemoria.add(blocoAtual);

        for (int i = 1; i < memoria.size(); i++) {
            BlocoMemoria proximoBloco = memoria.get(i);

            if (!blocoAtual.isOcupado() && !proximoBloco.isOcupado() && blocoAtual.getFim() == proximoBloco.getInicio()) {
                // Blocos livres adjacentes, consolidar
                blocoAtual.fim = proximoBloco.getFim(); // Estende o bloco atual
            } else {
                // Não são adjacentes ou um deles está ocupado, adiciona o próximo como novo bloco atual
                blocoAtual = proximoBloco;
                novaListaMemoria.add(blocoAtual);
            }
        }
        this.memoria = novaListaMemoria; // Atualiza a lista com os blocos consolidados
    }


    /**
     * Realiza o "swapping out" de um processo da memória para o disco.
     * @param processo O processo a ser movido para o disco.
     * @return true se o swap out foi bem-sucedido, false caso contrário.
     */
    public boolean swapOut(Processo processo) {
        System.out.println("\nTentando realizar SWAP OUT do Processo " + processo.getId() + "...");
        if (processo.isEmMemoria()) {
            if (desalocarProcesso(processo)) { // Desaloca da memória RAM
                processosEmDisco.add(processo); // Adiciona à lista de processos em disco
                System.out.println("Processo " + processo.getId() + " movido para o disco (SWAP OUT).");
                return true;
            }
        } else {
            System.out.println("Processo " + processo.getId() + " já está no disco ou nunca esteve na memória.");
        }
        return false;
    }

    /**
     * Realiza o "swapping in" de um processo do disco para a memória.
     * @param processoId O ID do processo a ser movido da memória.
     * @return true se o swap in foi bem-sucedido, false caso contrário.
     */
    public boolean swapIn(String processoId) {
        System.out.println("\nTentando realizar SWAP IN do Processo " + processoId + "...");
        Optional<Processo> processoOpt = processosEmDisco.stream()
                .filter(p -> p.getId().equals(processoId))
                .findFirst();

        if (processoOpt.isPresent()) {
            Processo processo = processoOpt.get();
            if (alocarProcesso(processo)) { // Tenta alocar na memória RAM
                processosEmDisco.remove(processo); // Remove da lista de processos em disco
                System.out.println("Processo " + processoId + " movido para a memória (SWAP IN).");
                return true;
            } else {
                System.out.println("Não há espaço suficiente para realizar SWAP IN do Processo " + processoId + ".");
                return false;
            }
        } else {
            System.out.println("Processo " + processoId + " não encontrado no disco para SWAP IN.");
            return false;
        }
    }


    /**
     * Visualiza o estado atual da memória e dos processos em disco.
     */
    public void visualizarMemoria() {
        System.out.println("\n--- ESTADO ATUAL DA MEMÓRIA (" + tamanhoTotalMemoria + " KB) ---");
        System.out.println("-------------------------------------");

        // Representação gráfica simples
        StringBuilder memoriaGrafica = new StringBuilder();
        int[] mapaBytes = new int[tamanhoTotalMemoria]; // 0 para livre, 1 para P1, 2 para P2, etc. (simplificado)
        // Inicializa com 0 (livre)
        for (int i = 0; i < tamanhoTotalMemoria; i++) {
            mapaBytes[i] = 0;
        }

        // Preenche o mapa de bytes com os processos ocupados
        for (BlocoMemoria bloco : memoria) {
            if (bloco.isOcupado()) {
                // Atribui um 'código' numérico para cada processo para visualização
                // Isso é uma simplificação. Em um SO real, seria o PID ou algo similar.
                int codigoProcesso = bloco.getIdProcesso().hashCode() % 9 + 1; // 1 a 9 para cores/símbolos diferentes
                for (int i = bloco.getInicio(); i < bloco.getFim(); i++) {
                    mapaBytes[i] = codigoProcesso;
                }
            }
        }

        // Desenha a "memória"
        System.out.println("Legenda: ' ' = Livre, '1'-'9' = Processos (ID hash)");
        for (int i = 0; i < tamanhoTotalMemoria; i++) {
            if (i % 10 == 0 && i != 0) { // Nova linha a cada 10 unidades para melhor visualização
                memoriaGrafica.append("\n");
            }
            if (mapaBytes[i] == 0) {
                memoriaGrafica.append(" "); // Espaço livre
            } else {
                memoriaGrafica.append(mapaBytes[i]); // ID do processo (simplificado)
            }
        }
        System.out.println(memoriaGrafica.toString());
        System.out.println("-------------------------------------");


        // Listagem detalhada dos blocos
        System.out.println("LISTA DE PARTIÇÕES:");
        if (memoria.isEmpty()) {
            System.out.println("Memória completamente vazia (erro de estado).");
        } else {
            for (BlocoMemoria bloco : memoria) {
                System.out.println(bloco.toString());
            }
        }

        System.out.println("\n--- PROCESSOS EM DISCO (SWAPPED OUT) ---");
        if (processosEmDisco.isEmpty()) {
            System.out.println("Nenhum processo no disco.");
        } else {
            for (Processo p : processosEmDisco) {
                System.out.println(" - " + p.getId() + " (" + p.getTamanho() + " KB)");
            }
        }
        System.out.println("----------------------------------------\n");
    }
}

