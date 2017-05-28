package indiceremissivo;

/**
 *
 * @author Alessandro
 */
public class Palavra {
    private LinkedListOfObject paginas;
    private String valor;
    private int ocorrencias;

    public Palavra(String valor, int ocorrencias) {
        this.valor = valor;
        this.ocorrencias = ocorrencias;
        this.paginas = new LinkedListOfObject();
    }
    
    @Override
    public String toString(){
        return valor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(int ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    public LinkedListOfObject getPaginas() {
        return paginas;
    }

    public void setPaginas(LinkedListOfObject paginas) {
        this.paginas = paginas;
    }

}
