package segunda_va.cap7;

import segunda_va.Processo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Processo> processos = new ArrayList<Processo>();

        processos.add(new Processo("01", "Aguardando", 1, 3000));
        processos.add(new Processo("02", "Aguardando", 2, 20000));
        processos.add(new Processo("03", "Executando", 0,5000));

        processos.sort(Comparator.comparing(Processo::getPrioridade));

        for (Processo processo : processos) {
            processo.run();
        }
    }
}
