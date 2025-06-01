package segunda_va;

public class Processo implements Runnable {
    @Override
    public void run() {
        try {
            this.estado = "executando";
            System.out.println("Processo " + getPID() + "; prioridade: " + getPrioridade() + " está em execução...");
            Thread.sleep(this.tempoDeExecucao);

            Thread.sleep(this.tempoDeExecucao);
            this.estado = "Finalizado";
            System.out.println("Processo " + getPID() + " finalizado.");
        } catch (InterruptedException e) {
            System.err.println("Processo " + getPID() + " foi interrompido.");
        }
    }

    private String PID;
    private String estado;
    private Integer prioridade;
    private final Integer tempoDeExecucao;

    public Processo(String PID, String estado, Integer prioridade, Integer tempoDeExecucao) {
        this.PID = PID;
        this.estado = estado;
        this.prioridade = prioridade;
        this.tempoDeExecucao = tempoDeExecucao;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public Integer getTempoDeExecucao() {
        return tempoDeExecucao;
    }
}

