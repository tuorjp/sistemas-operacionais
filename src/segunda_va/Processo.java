package segunda_va;

public class Processo implements Runnable {
    @Override
    public void run() {

    }

    private String PID;
    private String estado;
    private Integer prioridade;
    private Integer tempoDeCpu;

    public Processo(String PID, String estado, Integer prioridade, Integer tempoDeCpu) {
        this.PID = PID;
        this.estado = estado;
        this.prioridade = prioridade;
        this.tempoDeCpu = tempoDeCpu;
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

    public Integer getTempoDeCpu() {
        return tempoDeCpu;
    }

    public void setTempoDeCpu(Integer tempoDeCpu) {
        this.tempoDeCpu = tempoDeCpu;
    }

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}

