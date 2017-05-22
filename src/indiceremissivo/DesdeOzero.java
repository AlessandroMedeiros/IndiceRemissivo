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
import java.util.Scanner;

/**
 *
 * @author Alessandro
 */
public class DesdeOzero {

    String entrada = "C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/Alice.txt";
    String saida = "C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/Saida.txt";
    String stopwords = "C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/stopwords.txt";

    File arquivo1 = new File("C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/Saida.txt");

    ArrayList<String> livro = new ArrayList<>(); // Estrutura de Dados LISTA DUPLAMENTE ENCADEADA;
    ArrayList<String> listaStopWords = new ArrayList<>();

    public static void main(String[] args) {
        DesdeOzero doz = new DesdeOzero();
        doz.lendoArquivos();
        doz.removeStopWords();
    }

    private void lendoArquivos() {

        try {

            BufferedReader br = new BufferedReader(new FileReader(entrada)); // Recebe o arquivo Alice.txt
            BufferedReader brStopWords = new BufferedReader(new FileReader(stopwords));
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(arquivo1));

            while (brStopWords.ready()) {
                String stopWords = brStopWords.readLine();
                listaStopWords.add(stopWords);
            }
            while (br.ready()) {

                String linha = br.readLine();

                int aux = 0;
                for (int i = 0; i < linha.length(); i++) {
                    String string = linha.toLowerCase();
                    if (string.contains("*")) {
                        break;
                    }

                    if (linha.charAt(0) == ' ') {   //Esse if remove os espaços do começo da frase. Exemplo: linha: 239 até 242 do arquivo Alice.txt
                        linha = linha.toLowerCase();
                        linha = linha.replaceAll("[^a-z]", " ").trim();
                        string = linha.toLowerCase();
                        string.replaceAll("[^a-z]", " ").trim();
                    }

                    if (linha.charAt(i) == ' ') {
                        string = string.replaceAll("[^a-z]", " ").trim();
                        string = string.replaceAll("'", "").trim();
                        if (!(string.substring(aux, i).equals(""))) {
                            livro.add(string.substring(aux, i).trim());
                            buffWrite.append(string.substring(aux, i).trim() + "\n");
                        }
                        aux = i + 1;
                    } else if (i == string.length() - 1) {
                        if (linha.charAt(i) == '.' || linha.charAt(i) == ',' || linha.charAt(i) == ';' || linha.charAt(i) == ':' || linha.charAt(i) == '!' || linha.charAt(i) == '?' || linha.charAt(i) == '’' || linha.charAt(i) == '-' || linha.charAt(i) == '(' || linha.charAt(i) == ')') {
                            livro.add(string.substring(aux, linha.length() - 1).toLowerCase());  // Adiciona a ÚLTIMA PALAVRA da frase/linha quando a mesma estiver com algum tipo de pontuação no final.
                            buffWrite.append(string.substring(aux, linha.length() - 1).toLowerCase() + "\n");
                        } else {
                            livro.add(string.substring(aux, i + 1));
                            buffWrite.append(string.substring(aux, i + 1) + "\n");
                        }
                    }
                }
            }

            buffWrite.close();
            System.out.println("PÁÃÀH!!!");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void removeStopWords() {

        for (int i = 0; i < listaStopWords.size(); i++) {
            for (int j = 0; j < livro.size(); j++) {
                if (livro.get(j).equals(listaStopWords.get(i))) {
                    livro.remove(j);
                }
            }
        }
        System.out.println("PAH2");
    }
}
