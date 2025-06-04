package segunda_va.cap8;

import segunda_va.Processo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Escalonador {
    public static void gerenciar(List<List<Processo>> processosMatriz) {
        List<Processo> processosArray = new ArrayList<Processo>();
        processosMatriz.forEach(processos -> {
            processosArray.addAll(processos);
        });

        processosArray.sort(Comparator.comparing(Processo::getPrioridade));

        processosArray.forEach(Processo::run);
    }
}
