package segunda_va;

import java.util.List;

public class ExecucaoSimples {
    public Processo processo;

    public ExecucaoSimples( Processo processo ) {
        this.processo = processo;
    }

    public void executarProcesso() {
        this.processo.setEstado("execução");
        Integer tempoDeCpu = processo.getTempoDeCpu();
        String PID = processo.getPID();

        System.out.println("Processo: " + PID + " Executando: " + tempoDeCpu);

        Processo.setTimeout(this.processo, tempoDeCpu * 1000);
    }
}
