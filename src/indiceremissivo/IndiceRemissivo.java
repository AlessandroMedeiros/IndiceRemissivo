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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Alessandro Medeiros e Lucas Bieniek
 */
public class IndiceRemissivo {

    String entrada = "C:/Users/IBM_ADMIN/Desktop/T2 - AED/alice.txt";
    String saida = "C:/Users/IBM_ADMIN/Desktop/T2 - AED/Saida.txt";
    String stopwords = "C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/stopwords.txt";

    File arquivo1 = new File("C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/Saida.txt");

    ArrayList<String> livro = new ArrayList<>();
    ArrayList<String> listaStopWords = new ArrayList<>();
    ArrayList<String> linha = new ArrayList<>();
    ArrayList<String> pagina = new ArrayList<>();
    LinkedListOfObject linkedLivro = new LinkedListOfObject();
    LinkedListOfObject linkedLinha = new LinkedListOfObject();
    LinkedListOfObject linkedPagina = new LinkedListOfObject();
    LinkedListOfObject palavras = new LinkedListOfObject();

    double numeroDeStopWorsRemivodasDoTexto = 0;
    double numeroDePalavrasTotalDoTexto = 0;

    public static void main(String[] args) throws IOException {
        IndiceRemissivo doz = new IndiceRemissivo();
        doz.lendoArquivos();
        doz.removeStopWords();
        System.out.println("\nOrdenando o arquivo livro em ordem alfabética...");
        doz.ordenarSemRepeticao();
        doz.paginas();

        String valor = JOptionPane.showInputDialog("INDICE REMISSIVO \n\n Menu\n "
                + "\n0 - SAIR\n"
                + "\n1 - Exibir todo o índice remissivo (em ordem alfabética)\n"
                + "\n2 - Exibir o percentual de stopwords do texto (quanto % do texto é formado por stopwords)\n"
                + "\n3 - Encontrar a palavra mais frequente, isto é, com maior número de ocorrências\n"
                + "\n4 - Pesquisar palavra (o usuário informa uma palavra; o sistema mostra as páginas em que a palavra ocorre;\n na sequência, o usuário escolhe a página; o sistema exibe a página na tela, circundando a palavra informada com sinais de [ e ]);\n"
                + "\n5 - Encontrar página complexa (o sistema descobre e informa a página que contém o maior número de palavras indexadas, informando quantas são).\n\n");
        switch (valor) {
            case "0":
                break;
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

            System.out.println("Carregando o arquivo: " + stopwords);
            while (brStopWords.ready()) {
                String stopWords = brStopWords.readLine();
                listaStopWords.add(stopWords);
            }
            System.out.println("Carregando o arquivo: " + entrada);
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
                        numeroDePalavrasTotalDoTexto++;
                    }
                }
            }

            br.close();
            brStopWords.close();
            buffWrite.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void ordenarSemRepeticao() {
        ArrayList<String> lista = livro;
        Collections.sort(lista);    //Ordena a lista
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
        linkedLivro = palavras;
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
        int aux = 0;
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

    //4
    private void pesquisarPalavra(String palavra) {
    }

    //5
    private void paginaComplexa() {
    }
}
