package segunda_va.cap9;

public class BlocoMemoria {
    private int inicio;
    int fim;
    private boolean ocupado;
    private String idProcesso;

    public BlocoMemoria(int inicio, int fim) {
        this.inicio = inicio;
        this.fim = fim;
        this.ocupado = false;
        this.idProcesso = null;
    }

    public int getInicio() {
        return inicio;
    }
    public int getFim() {
        return fim;
    }
    public int getTamanho() {
        return fim - inicio;
    }
    public boolean isOcupado() {
        return ocupado;
    }
    public String getIdProcesso() {
        return idProcesso;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
    public void setIdProcesso(String idProcesso) {
        this.idProcesso = idProcesso;
    }
    public void alocar(String idProcesso) {
        this.ocupado = true;
        this.idProcesso = idProcesso;
    }
    public void desalocar() {
        this.ocupado = false;
        this.idProcesso = null;
    }

    @Override
    public String toString() {
        return "[" + inicio + "-" + (fim - 1) + "]: " +
                (ocupado ? "Ocupado por " + idProcesso : "Livre (" + getTamanho() + " KB)");
    }
}
