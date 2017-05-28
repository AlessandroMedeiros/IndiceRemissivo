/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indiceremissivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 *
 * @author Alessandro Medeiros e Lucas Bieniek
 */
public class IndiceRemissivo {

    private String entrada = "C:/Users/IBM_ADMIN/Desktop/T2 - AED/alice.txt";
    private String saida = "C:/Users/IBM_ADMIN/Desktop/T2 - AED/Saida.txt";
    private String stopwords = "C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/stopwords.txt";

    private ArrayList<String> livro = new ArrayList<>();
    private ArrayList<String> listaStopWords = new ArrayList<>();
    private ArrayList<String> linha = new ArrayList<>();
    private ArrayList<String> pagina = new ArrayList<>();
    private LinkedListOfObject linkedPagina = new LinkedListOfObject();
    private LinkedListOfObject palavras = new LinkedListOfObject();

    private int numeroDeStopWorsRemivodasDoTexto = 0;
    private int numeroDePalavrasTotalDoTexto = 0;

    public static void main(String[] args) throws IOException {
        IndiceRemissivo indiceRemissivo = new IndiceRemissivo();
        indiceRemissivo.lendoArquivos();
        indiceRemissivo.removeStopWords(); //remove stopWords do livro

        System.out.println("\nOrdenando o arquivo livro em ordem alfabética...");

        indiceRemissivo.ordenarSemRepeticao();
        indiceRemissivo.paginas();

        String valor;
        do {
            valor = JOptionPane.showInputDialog("Índice Remissivo \n\nMenu(as respostas estarão no terminal da IDE)\n "
                    + "\n0 - SAIR\n"
                    + "\n1 - Exibir todo o índice remissivo (em ordem alfabética)\n"
                    + "\n2 - Exibir o percentual de stopwords do texto (quanto % do texto é formado por stopwords)\n"
                    + "\n3 - Encontrar a palavra mais frequente, isto é, com maior número de ocorrências\n"
                    + "\n4 - Pesquisar palavra (o usuário informa uma palavra; o sistema mostra as páginas em que a palavra ocorre;\n na sequência, o usuário escolhe a página; o sistema exibe a página na tela, circundando a palavra informada com sinais de [ e ]);\n"
                    + "\n5 - Encontrar página complexa (o sistema descobre e informa a página que contém o maior número de palavras indexadas, informando quantas são).\n\n");
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

    private void carregarStopWords() throws IOException {
        BufferedReader brStopWords = new BufferedReader(new FileReader(stopwords)); // Recebe o arquivo stopwords.txt
        System.out.println("Carregando o arquivo: " + stopwords);
        while (brStopWords.ready()) {
            String stopWords = brStopWords.readLine();
            listaStopWords.add(stopWords);
        }
        brStopWords.close();
    }

    private void carregaArquivoEntrada() throws IOException {
        BufferedReader brEntrada = new BufferedReader(new FileReader(entrada)); // Recebe o arquivo Alice.txt
        System.out.println("Carregando o arquivo: " + entrada);
        while (brEntrada.ready()) {

            String linha = brEntrada.readLine().toLowerCase();
            linha = linha.replaceAll("--", " ").trim();
            linha = linha.replaceAll("[^a-z ]", "").trim();

            if (!"".equals(linha)) this.linha.add(linha);

            String[] palavras = linha.split(" ");

            for (int i = 0; i < palavras.length; i++) {
                if (!"".equals(palavras[i].trim())) {
                    livro.add(palavras[i]);
                    numeroDePalavrasTotalDoTexto++;
                }
            }
        }
        brEntrada.close();
    }

    private void lendoArquivos() {
        try {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(saida));

            carregarStopWords();
            carregaArquivoEntrada();

            buffWrite.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void ordenarSemRepeticao() {
        ArrayList<String> lista = livro;
        Collections.sort(lista); //Ordena a lista
        int contador = 0;
        boolean help = false;
        Palavra p = new Palavra("", 0);
        for (int i = 0; i < lista.size(); i++) {    //Percorre a lista com todas as palavras ordenadas
            for (int j = 0; j < palavras.size(); j++) { // Percorre a lista das ocorrencias
                if (lista.get(i).equals(palavras.get(j).toString())) { //compara CADA palavra com a lista de palavras
                    help = true; //Achei a palavra na lista
                }
            }
            if (!help) { //se não achou, adiona a palavra na lista de ocorrências...
                p = new Palavra(lista.get(i), 1);
                palavras.add(p);
            } else { //se achou incrementa o número de ocorrências...
                p.setOcorrencias(p.getOcorrencias() + 1);
                contador++;
            }
            help = false;
        }
        System.out.println("Foram ordenadas " + contador + " palavras");
    }

    private void removeStopWords() {
        System.out.println("\nRemovendo stopwords...");
        for (int i = 0; i < listaStopWords.size(); i++) {
            for (int j = 0; j < livro.size(); j++) {
                if (livro.get(j).equals(listaStopWords.get(i))) {
                    livro.remove(j);
                    numeroDeStopWorsRemivodasDoTexto++;
                }
            }
        }
        System.out.println("Foram removidos " + numeroDeStopWorsRemivodasDoTexto + " stopwords");
    }

    private void paginas() {
        System.out.println("\nCriando páginas com 40 linhas cada...");
        String aux = "";
        for (int i = 0; i < linha.size(); i++) {
            if (i % 40 == 0D && i != 0) {
                pagina.add(aux);
                aux = "";
            }
            aux = aux + linha.get(i) + "\n";
        }
        pagina.add(aux); // Adicionando a última página... pois a última pagina não tem 40 linhas, portanto não é divisível por 40.
        for(int i=0; i<pagina.size(); i++){
            linkedPagina.add(pagina.get(i));
        }
        System.out.println("Foram geradas " + pagina.size() + " páginas...");
    }

    //1
    private void indiceRemissivo() {
        System.out.println("\nCriando um indice remissivo...");
        for (int i = 0; i < palavras.size(); i++) {
            Palavra p = (Palavra) palavras.get(i);
            System.out.println(p.getValor() + " ocorrencias: " + p.getOcorrencias());
        }
    }

    //2
    private void porcentagemStopWords() {
        double percentual;
        percentual = (numeroDeStopWorsRemivodasDoTexto * 100) / numeroDePalavrasTotalDoTexto;
        String resultado = String.format("%.2f", percentual);
        System.out.println(resultado + "%");
    }

    //3
    private void palavraMaisFrequente() {
        int aux;
        String string = "";
        Palavra primeira = (Palavra) palavras.get(0);
        aux = primeira.getOcorrencias();
        for (int i = 0; i < palavras.size(); i++) {
            Palavra p = (Palavra) palavras.get(i);
            if (p.getOcorrencias() > aux) {
                aux = p.getOcorrencias();
                string = p.getValor();
            }
        }
        System.out.println(aux +" ocorrências da palavra: "+string);
    }

    /*4.Pesquisar palavra (o usuário informa uma palavra; o sistema mostra as
            páginas em que a palavra ocorre; na sequência, o usuário escolhe a página;
            o sistema exibe a página na tela, circundando a palavra informada com sinais de [ e ]);
         */
    private void pesquisarPalavra(String palavra) {
        String valor;
        int pag;
        String aux="";
        for(int i=0; i<linkedPagina.size(); i++){
            if(String.valueOf(linkedPagina.get(i)).contains(palavra)){
                aux = aux +(i)+",";
            }
        }
        if (aux.length() == 0) {
            System.out.println("A palavra: "+palavra+" não econtra-se no livro");
        } else {
            System.out.println("A palavra: "+palavra+" aparece na(as) página(as): "+aux.substring(0, aux.length()-1) + "\n");
            valor = JOptionPane.showInputDialog("Digite o número da página para ver a palavra "+palavra+" na pagina:");
            pag = Integer.valueOf(valor);
            if (pagina.get(pag) != null) {
                String string = pagina.get(pag);
                string = string.replace(palavra, "["+palavra+"]");
                System.out.println(string);
            }
        }
    }

    /*5. Encontrar página complexa (o sistema descobre e informa a página que contém
    o maior número de palavras indexadas, informando quantas são).*/
    private void paginaComplexa() {
        int pagina = 0;
        int numeroDePalavras = 0;
        for (int i = 0; i < linkedPagina.size(); i++) {
            if (((String) linkedPagina.get(i)).split(" ").length > numeroDePalavras) {
                numeroDePalavras = ((String) linkedPagina.get(i)).split(" ").length;
                pagina = i;
            }
        }
        System.out.println("A página " + (pagina + 1) + " possui " + numeroDePalavras + " palavras. Ela é a mais complexa do livro");
    }
}
