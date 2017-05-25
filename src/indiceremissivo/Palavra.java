/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indiceremissivo;

/**
 *
 * @author Alessandro
 */
public class Palavra {
    LinkedListOfObject paginas = new LinkedListOfObject();
    String valor;
    int ocorrencias;

    public Palavra(String valor, int ocorrencias) {
        this.valor = valor;
        this.ocorrencias = ocorrencias;
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
