/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indiceremissivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/**
 *
 * @author Alessandro
 */
public class IndiceRemissivo {

    String entrada = "C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/AliceTeste.txt";
    String saida = "C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/Saida.txt";
    String stopwords = "C:/Users/IBM_ADMIN/Desktop/Alessandro Pessoal/T2 - AED/stopwords.txt";
    
    LinkedListOfString livro = new LinkedListOfString(); // Estrutura de Dados LISTA DUPLAMENTE ENCADEADA;
    LinkedListOfString listaStopWords = new LinkedListOfString();
    
    public static void main(String[] args) throws IOException {
        IndiceRemissivo ir = new IndiceRemissivo();
        ir.lendoEarmazenandoOlivro();
        //ir.removeStopWords();
        
        
        String valor = JOptionPane.showInputDialog("INDICE REMISSIVO \n\n Menu \n"
                + "\n1 - Exibir todo o índice remissivo (em ordem alfabética)\n"
                + "\n2 - Exibir o percentual de stopwords do texto (quanto % do texto é formado por stopwords)\n"
                + "\n3 - Encontrar a palavra mais frequente, isto é, com maior número de ocorrências\n"
                + "\n4 - Pesquisar palavra (o usuário informa uma palavra; o sistema mostra as páginas em que a palavra ocorre;\n na sequência, o usuário escolhe a página; o sistema exibe a página na tela, circundando a palavra informada com sinais de [ e ]);\n"
                + "\n5 - Encontrar página complexa (o sistema descobre e informa a página que contém o maior número de palavras indexadas, informando quantas são).");
        switch (valor){
            case "1":
                ir.indiceRemissivo();
                break;
            case "2":
                ir.porcentagemStopWords();
                break;
            case "3":
                ir.palavraMaisFrequente();
                break;
            case "4":
                ir.pesquisarPalavra("teste");
                break;
            case "5":
                ir.paginaComplexa();
                break;
                
                default:
                     JOptionPane.showMessageDialog(null, "O código informado é inválido.");
                     break;
        }
    }

    private void lendoEarmazenandoOlivro() throws FileNotFoundException, IOException {

        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(entrada)); // Recebe o arquivo Alice.txt
            PrintWriter arquivo;
            arquivo = new PrintWriter(new File(saida));

            while (br.ready()) {    //Lendo o ARQUIVO Alive.txt por LINAS.
                String linha = br.readLine(); //Armazane a linha do arquivo na variável "linha";
                count++;
                int aux = 0;    //Varivável auxiliar para ajudar a remover a pontuação do arquivo.

                for (int i = 0; i < linha.length(); i++) {  //Varre cada CARACTER da linha
                    if (linha.charAt(0) == ' ') {   //Esse if remove os espaços do começo da frase. Exemplo: linha: 239 até 242 do arquivo Alice.txt
                        linha = linha.trim();   //Remove os espaços do começo da frase.
                    }

                    if (linha.charAt(i) == ' ') {   //Procura por espaços e adiciona a palavra antes do espaço.

                        if (linha.contains("*")) {  //Esse if verifica se tem ASTERISCO(*) da linha. Exemplo: linha: 158 até 162 do arquivo Alice.txt
                            break;  //Pula a linha.
                        }

                        //Esse bloco de if/else verifica a pontuação na primeira palavra e nas plavaras do meio da FRASE.
                        if (linha.charAt(i - 1) == '.' || linha.charAt(i - 1) == ',' || linha.charAt(i - 1) == ';' || linha.charAt(i - 1) == ':' || linha.charAt(i - 1) == '!' || linha.charAt(i - 1) == '?' || linha.charAt(i - 1) == '’' || linha.charAt(i - 1) == '-' || linha.charAt(i - 1) == '(' || linha.charAt(i - 1) == ')') { //Remove os seguintes carácteres: .  ,  ;  :
                            livro.add(linha.substring(aux, i - 1).toLowerCase());   //Se tiver pontuação, remove o ÚLTIMO caracter e adiciona a palavra. E deixa a palavra minúscula.
                        } else {
                            livro.add(linha.substring(aux, i).toLowerCase());   //Caso não tenha a pontuação, adiciona a palavra normalmente. E deixa a palavra minúscula.
                        }

                        aux = i;    //Variável para auxiliar a remover a pontuação

                    } else if (i == linha.length() - 1) {   //Verifica a pontuação SOMENTE NO FINAL DA FRASE.

                        //Verifica se tem pontuação no final da FRASE.
                        if (linha.charAt(i) == '.' || linha.charAt(i) == ',' || linha.charAt(i - 1) == ';' || linha.charAt(i - 1) == ':' || linha.charAt(i - 1) == '!' || linha.charAt(i - 1) == '?' || linha.charAt(i - 1) == '’' || linha.charAt(i - 1) == '-' || linha.charAt(i - 1) == '(' || linha.charAt(i - 1) == ')') {
                            livro.add(linha.substring(aux, linha.length() - 1).toLowerCase());  // Adiciona a ÚLTIMA PALAVRA da frase/linha quando a mesma estiver com algum tipo de pontuação no final.
                        } else {
                            livro.add(linha.substring(aux, linha.length()).toLowerCase());  // Adiciona a ÚLTIMA PALAVRA da frase/linha quando ela não tiver algum tipo de pontuação final.
                        }

                    }
                }
            }
            System.out.println("Quantas palavras tem: " + livro.size());
            System.out.println("ISSO É UM TESTE...  Onde estoura: " + count);
            System.out.println("SE count = três mil trezentos e trina e nova, PASSOU NO TESTE, sem erro na implementação\n");
            System.out.println("Fim da leitura do livro!");
        } catch (Exception e) {
            System.out.println(count);
            e.printStackTrace();
        }
    }

    private void removeStopWords() throws FileNotFoundException, IOException {
        
        BufferedReader sw = new BufferedReader(new FileReader(stopwords)); // Recebe o arquivo stopwords.txt
        while(sw.ready()){
            String stopwords = sw.readLine();
            listaStopWords.add(stopwords);
        }
        
        for(int i=0; i<livro.size(); i++){
            for(int j=0; j<listaStopWords.size(); j++){
                if(livro.get(i).equals(listaStopWords.get(j))){
                    System.out.println(livro.removeByIndex(i));
                }
            }
        } 
        System.out.println("ok");
    }
    
    
    //1
    private void indiceRemissivo(){
    }
    
    //2
    private void porcentagemStopWords(){
    }
    
    //3
    private void palavraMaisFrequente(){
    }
    
    //4
    private void pesquisarPalavra(String palavra){
    }
    
    //5
    private void paginaComplexa(){
    }
    
}
