package segunda_va;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Processo> processos = new ArrayList<Processo>();

        processos.add(
                new Processo("01", "aguardando", 0, 2)
        );
    }
}
