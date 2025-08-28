package exploradorFicherosTxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vrand
 *
 * Haz un programa que lea un archivo de texto (por ejemplo texto.txt) y
 * muestre:
 *
 * Número total de líneas.
 *
 * Número total de palabras (separadas por espacios).
 *
 * Las 5 palabras más frecuentes con su contador.
 *
 */
public class AnalizadorTexto {

    public static void main(String[] args) {

        // Contar número de líneas. Versión con Scanner
        try {
            File f = new File("texto.txt"); //File sólo representa el archivo
            Scanner sc = new Scanner(f); //para leer se necesita clase Scanner o BufferedReader
            int numLineasSc = 0;
            while (sc.hasNextLine()) {
                sc.nextLine();
                numLineasSc++;
            }
            sc.close();
            System.out.println("Número de líneas: " + numLineasSc);

        } catch (Exception ex) {
            System.out.println("No se ha podido acceder al fichero");
        }

        //Contar total de palabras (Versión con BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader("texto.txt"))) {
            int numLineas = 0;
            int numPalabras = 0;

            String linea;
            while ((linea = br.readLine()) != null) {
                numLineas++;

                // separar por espacios (y posibles tabuladores o múltiples espacios)
                String[] palabras = linea.trim().split("\\s+");

                // cuidado: si la línea está vacía, split devuelve un array con un elemento vacío
                if (palabras.length == 1 && palabras[0].isEmpty()) {
                    continue; // ignoramos líneas vacías
                }

                numPalabras += palabras.length;
            }

            System.out.println("Número de líneas: " + numLineas);
            System.out.println("Número de palabras: " + numPalabras);

        } catch (Exception ex) {
            System.out.println("No se ha podido acceder al fichero");
        }
        
        // Las 5 palabras más frecuentes con su contador
        
        Map<String, Integer> topPalabras = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("texto.txt"))) {
            String linea;
            while((linea = br.readLine()) != null) {
                String[] palabras = linea.trim().split("\\s+");
                
                if(palabras.length == 1 || palabras[0].isEmpty()) continue; //linea vacia
                
                for(String p : palabras) {
                    p.toLowerCase(); //normalizar
                    topPalabras.put(p, topPalabras.getOrDefault(p, 0)+1);
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnalizadorTexto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnalizadorTexto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // ordenar las entradas por valor (descendente)
        List<Map.Entry<String,Integer>> lista = new ArrayList<>(topPalabras.entrySet());
        lista.sort((a,b) -> b.getValue().compareTo(a.getValue()));
        
        //mostrar las 5 primeras
        
        System.out.println("Top de la 5 palabras más frecuentes: ");
        for(int i = 0; i < Math.min(5, lista.size()); i++) {
            Map.Entry<String, Integer> entry = lista.get(i);
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
