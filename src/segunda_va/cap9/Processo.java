package segunda_va.cap9;

public class Processo {
    private String id;
    private int tamanho;
    private boolean emMemoria;
    private int enderecoInicial;

    public Processo(String id, int tamanho) {
        this.id = id;
        this.tamanho = tamanho;
        this.emMemoria = false;
        this.enderecoInicial = -1;
    }

    public String getId() {
        return id;
    }
    public int getTamanho() {
        return tamanho;
    }
    public boolean isEmMemoria() {
        return emMemoria;
    }
    public int getEnderecoInicial() {
        return enderecoInicial;
    }

    public void setEnderecoInicial(int enderecoInicial) {
        this.enderecoInicial = enderecoInicial;
    }
    public void setEmMemoria(boolean emMemoria) {
        this.emMemoria = emMemoria;
    }

    @Override
    public String toString() {
        return "Processo{" +
                "id='" + id + '\'' +
                ", tamanho=" + tamanho +
                ", emMemoria=" + emMemoria +
                (emMemoria ? ", enderecoInicial=" + enderecoInicial : "") +
                '}';
    }
}
