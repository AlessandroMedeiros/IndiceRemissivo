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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Alessandro
 */
public class DesdeOzero {

    String entrada = "C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/Alice.txt";
    String saida = "C:/Users/IBM_ADMIN/Desktop/T2 - AED/Saida.txt";
    String stopwords = "C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/stopwords.txt";

    File arquivo1 = new File("C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/Saida.txt");

    ArrayList<String> livro = new ArrayList<>();
    ArrayList<String> listaStopWords = new ArrayList<>();
    ArrayList<String> linha = new ArrayList<>();
    ArrayList<String> pagina = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        DesdeOzero doz = new DesdeOzero();
        doz.lendoArquivos();
        doz.removeStopWords();
        doz.paginas();

        String valor = JOptionPane.showInputDialog("INDICE REMISSIVO \n\n Menu\n "
                + "\n1 - Exibir todo o índice remissivo (em ordem alfabética)\n"
                + "\n2 - Exibir o percentual de stopwords do texto (quanto % do texto é formado por stopwords)\n"
                + "\n3 - Encontrar a palavra mais frequente, isto é, com maior número de ocorrências\n"
                + "\n4 - Pesquisar palavra (o usuário informa uma palavra; o sistema mostra as páginas em que a palavra ocorre;\n na sequência, o usuário escolhe a página; o sistema exibe a página na tela, circundando a palavra informada com sinais de [ e ]);\n"
                + "\n5 - Encontrar página complexa (o sistema descobre e informa a página que contém o maior número de palavras indexadas, informando quantas são).");
        switch (valor) {
            case "1":
                doz.indiceRemissivo();
                break;
            case "2":
                doz.porcentagemStopWords();
                break;
            case "3":
                doz.palavraMaisFrequente();
                break;
            case "4":
                doz.pesquisarPalavra("teste");
                break;
            case "5":
                doz.paginaComplexa();
                break;

            default:
                JOptionPane.showMessageDialog(null, "O código informado é inválido.");
                break;
        }
    }

    private void lendoArquivos() {

        try {

            BufferedReader br = new BufferedReader(new FileReader(entrada)); // Recebe o arquivo Alice.txt
            BufferedReader brStopWords = new BufferedReader(new FileReader(stopwords));
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(saida));

            System.out.println("Carregando o arquivo: stopwords.txt ...");
            while (brStopWords.ready()) {
                String stopWords = brStopWords.readLine();
                listaStopWords.add(stopWords);
            }
            System.out.println("Carregando o arquivo: Alice.txt ...");
            while (br.ready()) {

                String linha = br.readLine().toLowerCase();
                linha = linha.replaceAll("--", " ").trim();
                linha = linha.replaceAll("[^a-z ]", "").trim();

                if (!"".equals(linha)) {
                    this.linha.add(linha);
                }

                String[] palavras = linha.split(" ");

                for (int i = 0; i < palavras.length; i++) {
                    if (!"".equals(palavras[i].trim())) {
                        livro.add(palavras[i]);
                    }
                }
            }

            System.out.println("Ordenando o arquivo livro em ordem alfabética...");
            livro = this.ordenarSemRepeticao(livro);

            /*for (String s : livro) {
                System.out.println(s);
            }*/

            buffWrite.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private ArrayList<String> ordenarSemRepeticao(ArrayList<String> lista) {
        Collections.sort(lista);
        ArrayList<String> listaAux = new ArrayList<>();
        boolean help = false;

        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < listaAux.size(); j++) {
                if (lista.get(i).equals(listaAux.get(j))) {
                    help = true;
                }
            }
            if (!help) {
                listaAux.add(lista.get(i));
            }
            help=false;
        }
        return listaAux;
    }

    private void removeStopWords() {
    System.out.println("Removendo 319 stopwords....");
        for (int i = 0; i < listaStopWords.size(); i++) {
            for (int j = 0; j < livro.size(); j++) {
                if (livro.get(j).equals(listaStopWords.get(i))) {
                    livro.remove(j);
                }
            }
        }
    }

    private void paginas() {
        System.out.println("Criando páginas com 40 linhas cada...");
        String aux = "";
        for (int i = 0; i < linha.size(); i++) {

            if (i % 40 == 0D && i != 0) {
                pagina.add(aux);
                aux = "";
            }
            aux = aux + linha.get(i) + "\n";
        }
    }

    //1
    private void indiceRemissivo() {
        System.out.println("Tentando... criar um indice remissivo...");
        int contador = 0;

        for (int i = 0; i < livro.size(); i++) {
            for (int j = 0; j < pagina.size(); j++) {
                if (livro.get(i).contains(pagina.get(j))) {
                    contador++;
                }
            }
            if (contador != 0) {
                System.out.println(livro.get(i) + " " + contador + " ocorrencias.");
            }

        }
        System.out.println("BreakPoint");
    }

    //2
    private void porcentagemStopWords() {
    }

    //3
    private void palavraMaisFrequente() {
    }

    //4
    private void pesquisarPalavra(String palavra) {
    }

    //5
    private void paginaComplexa() {
    }
}
