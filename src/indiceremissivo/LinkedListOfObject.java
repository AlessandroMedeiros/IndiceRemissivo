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
public class LinkedListOfObject {

    // Classe interna Node
    private class Node {

        public Object element;
        public Node next;

        public Node(Object element) {
            this.element = element;
            next = null;
        }
    }

    // Referência para o primeiro elemento da lista encadeada.
    private Node head;
    // Referência para o último elemento da lista encadeada.
    private Node tail;
    // Contador para a quantidade de elementos que a lista contem.
    private int count;

    /**
     * Construtor da lista
     */
    public LinkedListOfObject() {
        head = null;
        tail = null;
        count = 0;
    }

    /**
     * Adiciona um elemento ao final da lista
     *
     * @param element elemento a ser adicionado ao final da lista
     */
    public void add(Object element) {
        Node aux = new Node(element);
        if (head == null) { // Se a lista estiver vazia, head recebe o elemento.
            head = aux;
        } else {            // Se a lista estiver com 1 elemento, adiciona no final.
            tail.next = aux;
        }
        tail = aux;
        count++;
    }

    /**
     * Insere um elemento em uma determinada posicao da lista
     *
     * @param index a posicao da lista onde o elemento sera inserido
     * @param element elemento a ser inserido
     * @throws IndexOutOfBoundsException se (index < 0 || index > size())
     */
    public void add(int index, Object element) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }

        Node n = new Node(element);
        if (index == 0) { //insere no inicio
            n.next = head;
            head = n;
            if (tail == null) {
                tail = n;
            }
        } else if (index == count) { // insere no final
            tail.next = n;
            tail = n;
        } else { // insere no meio
            Node aux = head;
            for (int i = 0; i < index - 1; i++) {
                aux = aux.next;
            }
            n.next = aux.next;
            aux.next = n;
        }
        count++;
    }

    /**
     * Retorna o elemento de uma determinada posicao da lista
     *
     * @param index a posição da lista
     * @return o elemento da posicao especificada
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    public Object get(int index) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException();
        }
        Node aux = head;
        int c = 0;
        while (c < index) {
            aux = aux.next;
            c++;
        }
        return (aux.element);
    }

    /**
     * Substitui o elemento armanzenado em uma determinada posicao da lista pelo
     * elemento indicado
     *
     * @param index a posicao da lista
     * @param element o elemento a ser armazenado na lista
     * @return o elemento armazenado anteriormente na posicao da lista
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    public Object set(int index, Object element) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException();
        }
        Node aux = head;
        for (int i = 0; i < index; i++) {
            aux = aux.next;
        }
        Object tmp = aux.element;
        aux.element = element;
        return tmp;

    }

    /**
     * Remove a primeira ocorrencia do elemento na lista, se estiver presente
     *
     * @param element o elemento a ser removido
     * @return true se a lista contem o elemento especificado
     */
    public boolean remove(Object element) {
        if (element == null) {
            return false;
        }
        if (count == 0) {
            return false;
        }

        if (head.element.equals(element)) { // remocao do primeiro
            head = head.next;
            if (count == 1) { // se havia so um elemento na lista
                tail = null;
            }
            count--;
            return true;
        }

        Node ant = head;
        Node aux = head.next;

        for (int i = 1; i < count; i++) {
            if (aux.element.equals(element)) {
                if (aux == tail) { // remocao do ultimo
                    tail = ant;
                    tail.next = null;
                } else { // remocao do meio
                    ant.next = aux.next;
                }
                count--;
                return true;
            }
            ant = ant.next;
            aux = aux.next;
        }

        return false;
    }

    /**
     * Retorna true se a lista nao contem elementos
     *
     * @return true se a lista nao contem elementos
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Retorna o numero de elementos da lista
     *
     * @return o numero de elementos da lista
     */
    public int size() {
        return count;
    }

    /**
     * Esvazia a lista
     */
    public void clear() {
        head = null;
        tail = null;
        count = 0;
    }

    /**
     * Remove o elemento de uma determinada posicao da lista
     *
     * @param index a posicao da lista
     * @return o elemento que foi removido da lista
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    public Object removeByIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException();
        }

        Node aux = head;
        if (index == 0) {
            if (tail == head) // se tiver apenas um elemento
            {
                tail = null;
            }
            head = head.next;
            count--;
            return aux.element;
        }
        int c = 0;
        while (c < index - 1) {
            aux = aux.next;
            c++;
        }
        Object element = aux.next.element;
        if (tail == aux.next) {
            tail = aux;
        }
        aux.next = aux.next.next;
        count--;
        return element;
    }

    /**
     * Retorna o indice da primeira ocorrencia do elemento na lista, ou -1 se a
     * lista nao contem o elemento
     *
     * @param element o elemento a ser buscado
     * @return o indice da primeira ocorrencia do elemento na lista, ou -1 se a
     * lista nao contem o elemento
     */
    public int indexOf(Object element) {
        int index = 0;
        Node aux = head;
        while (aux != null) {
            if (aux.element.toString().equals(element.toString())) {
                return (index);
            }
            aux = aux.next;
            index++;
        }
        return -1;
    }

    /**
     * Retorna true se a lista contem o elemento especificado
     *
     * @param element o elemento a ser testado
     * @return true se a lista contem o elemento especificado
     */
    public boolean contains(Object element) {
        Node aux = head;
        while (aux != null) {
            if (aux.element.equals(element)) {
                return (true);
            }
            aux = aux.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        Node aux = head;

        while (aux != null) {
            s.append(aux.element.toString());
            s.append(" ");
            aux = aux.next;
        }

        return s.toString();
    }
    
    /*
     public void reverse(){
        if(count > 1){
            this.add(head.element);
            this.remove(head.element);
            int i=count-1;
            while(i>=2){
                this.add(i,head.element);
                this.remove(head.element);
                i--;
            }
        }
    }*/
    
    public LinkedListOfObject subList(int start, int end){
        
        LinkedListOfObject lista = new LinkedListOfObject();
        Node aux = head;
        for(int i=0; i<end; i++){
            if(i>=start){
                lista.add(aux.element);
            }
            aux=aux.next;
        }
        
        return lista;
    }
    
    /*1) Acrescentar na classe LinkedListOfObject um método que faça uma inserção ordenada dos elementos 
        na lista (do menor para o maior). A assinatura deste método deve ser: public void addIncreasingOrder(String element). */
        
    public void addIncreasingOrder(String element){
        if(count==0){ // Adiciona com o elemento vazio
            this.add(element);
        }else{
                for(int i=0; i<count; i++){
                    
             }
        }
    }
 
    
    /*2) Acrescentar na classe LinkedListOfObject um método não recursivo que imprima 
        o conteúdo da lista de trás para frente.*/
    
    public void printaInvertido(){
        
    }
    
    /*3) Acrescentar na classe LinkedListOfObject um método recursivo que imprima 
        o conteúdo da lista de trás para frente.*/
    
    public void recursivoPrintaInvertido(){
        
    }
    
    /*4) Acrescentar na classe LinkedListOfObject o seguinte método: 
        public void reverse(); Esse método inverte o conteúdo da lista*/
    
    public void reverse(){
        if(count > 1){
            this.add(head.element);
            this.remove(head.element);
        
            for(int i=count-1; i>=1; i--){
                this.add(i,head.element);
                this.remove(head.element);
            }
        }
    }
    
    /*5) Crie duas instâncias de LinkedListOfObject na classe App e as inicialize com 100 valores 
        aleatórios entre (0 e 30). Depois implemente um algoritmo para descobrir e mostrar qual é o 
        maior valor que está armazenado nas duas listas simultaneamente. 
        Para gerar números aleatórios use a classe Random r = new Random(); r.nextInt(30);
    
        No exemplo abaixo:, com listas de 5 elementos, o maior valor armazenado nas duas é 07.
        L1: 04,12,07,30,20
        L2: 50,07,04,21,33*/
    
    public void fatorComumLista(){
        
    }
    
    /**6. Implemente um método que percorre a lista e retira elementos repetidos, deixando apenas uma
            ocorrência de cada elemento. A assinatura deste método deve ser: public void unique().Teste a
            sua implementação para este método na classe App e analise a caracterização O do seu tempo de
            execução*/
   
    public LinkedListOfObject union(LinkedListOfObject lista2){ //On²
        LinkedListOfObject lista1 = new LinkedListOfObject();
        boolean aux = false;
        
        for(int i=0; i<lista2.size(); i++){
            for(int j=0; j<this.size(); j++){
                if(i==0){                   //Quando estiver na posição zero, copia a lista
                    lista1.add(this.get(j)); 
                }
                 if(lista2.get(i)==this.get(j)){
                     aux = true;
                 }
            }
            if(!aux){
                lista1.add(lista2.get(i));    
            }
            aux = false;
        }
        return lista1;
    }
    
    public LinkedListOfObject intersect(LinkedListOfObject lista2){ //On²
        LinkedListOfObject intersect = new LinkedListOfObject();
        LinkedListOfObject lista1 = new LinkedListOfObject();
        lista1 = this;
        
        for(int i=0; i<lista2.size(); i++){
            for(int j=0; j<lista1.size(); j++){
                if(lista2.get(i)==lista1.get(j)){
                    intersect.add(lista2.get(i));
                }
            }
        }
        return intersect;
    }
    
}

