/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indiceremissivo;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static indiceremissivo.Arquivos.ENTRADA;
import static indiceremissivo.Arquivos.SAIDA;
import static indiceremissivo.Arquivos.STOP_WORDS;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Alessandro Medeiros e Lucas Bieniek
 */
public class IndiceRemissivoMain {

    private final ArrayList<String> livro = new ArrayList<>();
    private final ArrayList<String> stopWordsList = new ArrayList<>();
    private final ArrayList<String> linhaList = new ArrayList<>();
    private final ArrayList<String> paginaList = new ArrayList<>();
    private final LinkedListOfObject linkedPagina = new LinkedListOfObject();
    private final LinkedListOfObject linkedPalavras = new LinkedListOfObject();

    private int numeroDeStopWordsRemovidasDoTexto = 0;
    private int numeroDePalavrasTotalDoTexto = 0;
    
    
    
    public static void main(String[] args) throws IOException {
        
        IndiceRemissivoMain indiceRemissivo = new IndiceRemissivoMain();
        indiceRemissivo.lerArquivos();      //lendo alice.txt e stopwords.txt
        indiceRemissivo.removeStopWords(); //remove stopWords do livro
        
        System.out.println("Ordenando o arquivo livro em ordem alfabética...");
        indiceRemissivo.ordenarSemRepeticao();
        indiceRemissivo.criaPaginas();

        criaMenuJoptionPane(indiceRemissivo);
    }

    private static void criaMenuJoptionPane(IndiceRemissivoMain indiceRemissivo) {
        String valor;
        do {
            valor = JOptionPane.showInputDialog(
                    "Índice Remissivo \n\nMenu(as respostas estarão no terminal da IDE)\n "
                    + "\n0 - SAIR\n"
                    + "\n1 - Exibir todo o índice remissivo (em ordem alfabética)\n"
                    + "\n2 - Exibir o percentual de stopwords do texto (quanto % do texto é formado por stopwords)\n"
                    + "\n3 - Encontrar a palavra mais frequente, isto é, com maior número de ocorrências\n"
                    + "\n4 - Pesquisar palavra (o usuário informa uma palavra; o sistema mostra as páginas em que a palavra ocorre;\n na sequência, o usuário escolhe a página; o sistema exibe a página na tela, circundando a palavra informada com sinais de [ e ]);\n"
                    + "\n5 - Encontrar página complexa (o sistema descobre e informa a página que contém o maior número de linkedPalavras indexadas, informando quantas são).\n\n");
            switch (valor) {
                case "0":
                    System.exit(0);
                    break;
                case "1":
                    indiceRemissivo.indiceRemissivo();
                    break;
                case "2":
                    indiceRemissivo.porcentagemStopWords();
                    break;
                case "3":
                    indiceRemissivo.palavraMaisFrequente();
                    break;
                case "4":
                    String string;
                    string = JOptionPane.showInputDialog("Digite a palavra a ser pesquisada: ");
                    indiceRemissivo.pesquisarPalavra(string);
                    break;
                case "5":
                    indiceRemissivo.paginaComplexa();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "O código informado é inválido.");
                    break;
            }
        } while(!valor.equals("0"));
    }

    private void lerArquivos() {
        try {
            carregarStopWords();
            carregaArquivoEntrada();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void carregarStopWords() throws IOException {
        BufferedReader brStopWords = new BufferedReader(new FileReader(STOP_WORDS)); // Recebe o arquivo stopwords.txt
        System.out.println("Carregando o arquivo: " + STOP_WORDS);
        while (brStopWords.ready()) {
            String stopWords = brStopWords.readLine();
            stopWordsList.add(stopWords);
        }
        System.out.println("Foram lidas "+ stopWordsList.size()+" stopwords...");
        brStopWords.close();
    }

    private void carregaArquivoEntrada() throws IOException {
        BufferedReader brEntrada = new BufferedReader(new FileReader(ENTRADA)); // Recebe o arquivo Alice.txt
        System.out.println("Carregando o arquivo: " + ENTRADA);

        while (brEntrada.ready()) {
            String linha = brEntrada.readLine().toLowerCase();
            linha = linha.replaceAll("--", " ").trim();
            linha = linha.replaceAll("[^a-z ]", "").trim();

            if (!"".equals(linha)) this.linhaList.add(linha);

            String[] palavras = linha.split(" "); //divide a linhaList por linkedPalavras, colocando-as e um array

            for (int i = 0; i < palavras.length; i++) {
                if (!"".equals(palavras[i].trim())) {
                    livro.add(palavras[i]);
                    numeroDePalavrasTotalDoTexto++;
                }
            }
        }
        System.out.println("Foram lidas "+ livro.size()+" palavras...");
        brEntrada.close();
    }

    private void ordenarSemRepeticao() {
        ArrayList<String> lista = livro;
        Collections.sort(lista); //Ordena a lista
        int cont = 0;
        boolean help = false;
        Palavra p = new Palavra("", 0);

        for (int i = 0; i < lista.size(); i++) {    //Percorre a lista com todas as linkedPalavras ordenadas
            for (int j = 0; j < linkedPalavras.size(); j++) { // Percorre a lista das ocorrencias
                if (lista.get(i).equals(linkedPalavras.get(j).toString())) { //compara CADA palavra com a lista de linkedPalavras
                    help = true; //Achei a palavra na lista
                }
            }
            if (help) { //se achou incrementa o número de ocorrências...
                p.setOcorrencias(p.getOcorrencias() + 1);
                cont++;
            } else { //se não achou, adiona a palavra na lista de ocorrências...
                p = new Palavra(lista.get(i), 1);
                linkedPalavras.add(p);
            }
            help = false;
        }
        System.out.println("Foram ordenadas " + cont + " palavras");
    }

    private void removeStopWords() {
        System.out.println("\nRemovendo stopwords...");
        for (int i = 0; i < stopWordsList.size(); i++) {
            for (int j = 0; j < livro.size(); j++) {
                if (livro.get(j).equals(stopWordsList.get(i))) {
                    livro.remove(j);
                    numeroDeStopWordsRemovidasDoTexto++;
                }
            }
        }
        System.out.println("Foram removidos " + numeroDeStopWordsRemovidasDoTexto + " stopwords");
    }

    private void criaPaginas() {
        System.out.println("\nCriando páginas com 40 linhas cada...");
        String aux = "";
        for (int i = 0; i < linhaList.size(); i++) {
            if (i % 40 == 0D && i != 0) {
                paginaList.add(aux);
                aux = "";
            }
            aux += linhaList.get(i) + "\n";
        }
        paginaList.add(aux); // Adicionando a última página... pois a última paginaList não tem 40 linhas, portanto não é divisível por 40.
        for(int i = 0; i< paginaList.size(); i++){
            linkedPagina.add(paginaList.get(i));
        }
        System.out.println("Foram geradas " + paginaList.size() + " páginas...");
    }

    //1
    private void indiceRemissivo() {
        System.out.println("\nCriando um índice remissivo...");
        for (int i = 0; i < linkedPalavras.size(); i++) {
            Palavra p = (Palavra) linkedPalavras.get(i);
            System.out.println(p.getValor() + " ocorrencias: " + p.getOcorrencias());
        }
    }

    //2
    private void porcentagemStopWords() {
        double percentual;
        percentual = (numeroDeStopWordsRemovidasDoTexto * 100) / numeroDePalavrasTotalDoTexto;
        String resultado = String.format("%.2f", percentual);
        System.out.println(resultado + "% porcento do texto era formado por stopwords.");
    }

    //3
    private void palavraMaisFrequente() {
        int aux;
        String string = "";
        Palavra primeira = (Palavra) linkedPalavras.get(0);
        aux = primeira.getOcorrencias();
        for (int i = 0; i < linkedPalavras.size(); i++) {
            Palavra p = (Palavra) linkedPalavras.get(i);
            if (p.getOcorrencias() > aux) {
                aux = p.getOcorrencias();
                string = p.getValor();
            }
        }
        System.out.println(aux +" ocorrências da palavra: "+string);
    }

    //4
    private void pesquisarPalavra(String palavra) {
        String valor;
        int pag=-1;
        String aux="";
        for(int i=0; i<linkedPagina.size(); i++){
            if(String.valueOf(linkedPagina.get(i)).contains(palavra)){
                aux = aux +(i)+",";
            }
        }
        if (aux.length() == 0) {
            System.out.println("A palavra: "+palavra+" não econtra-se no livro.");
        } else {
            System.out.println("A palavra: "+palavra+" aparece na(as) página(as): "+aux.substring(0, aux.length()-1) + "\n");
            valor = JOptionPane.showInputDialog("Digite o número da página para ver a palavra "+palavra+" nas páginas: \n" +aux.substring(0, aux.length()-1));
            if(aux.substring(0, aux.length()-1).contains(valor)){
                pag = Integer.valueOf(valor);
            }else{
                JOptionPane.showMessageDialog(null,"O número que você digitou, não está na lista de páginas mostradas.");
            }
            if (pag!=-1) {
                String string = paginaList.get(pag);
                string = string.replace(palavra, "["+palavra+"]");
                System.out.println(string);
            }
        }
    }

    //5
    private void paginaComplexa() {
        int pagina = 0;
        int numeroDePalavras = 0;
        for (int i = 0; i < linkedPagina.size(); i++) {
            if (((String) linkedPagina.get(i)).split(" ").length > numeroDePalavras) {
                numeroDePalavras = ((String) linkedPagina.get(i)).split(" ").length;
                pagina = i;
            }
        }
        System.out.println("A página " + (pagina + 1) + " possui " + numeroDePalavras + " palavras. Ela é a mais complexa do livro.");
    }
}
