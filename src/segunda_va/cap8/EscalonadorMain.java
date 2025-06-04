package segunda_va.cap8;

import segunda_va.Processo;

import java.util.ArrayList;
import java.util.List;

public class EscalonadorMain {
    public static void main(String [] args) {
        List<Processo> processos1 = new ArrayList<Processo>();
        List<Processo> processos2 = new ArrayList<Processo>();

        processos1.add(new Processo("001", "Aguardando", 0, 1));
        processos1.add(new Processo("002", "Aguardando", 0, 1));
        processos1.add(new Processo("003", "Aguardando", 0, 1));

        processos2.add(new Processo("003", "Aguardando", 1, 1));
        processos2.add(new Processo("004", "Aguardando", 1, 1));
        processos2.add(new Processo("005", "Aguardando", 1, 1));

        List<List<Processo>> todosOsProcessos = new ArrayList<List<Processo>>();
        todosOsProcessos.add(processos2);
        todosOsProcessos.add(processos1);

        Escalonador.gerenciar(todosOsProcessos);
    }
}
