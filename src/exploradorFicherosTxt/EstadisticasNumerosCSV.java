package exploradorFicherosTxt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vrand
 *
 * 3) Estadísticas de números (texto → parseo) Enunciado. Dado un archivo
 * datos.csv con una columna de enteros por línea, calcula mínimo, máximo, media
 * y mediana. Pistas suaves. •	Files.lines(Path) → mapToInt(Integer::parseInt) •
 * Mediana: ordena; si par, media de los dos centrales. Comprobación. Verifica
 * con un set pequeño (p. ej., 1,2,3,4,100).
 *
 */
public class EstadisticasNumerosCSV {

    public static void main(String[] args) {

        List<Integer> numeros = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("datos.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if(!linea.isEmpty()) {
                    numeros.add(Integer.parseInt(linea));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EstadisticasNumerosCSV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EstadisticasNumerosCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Integer numero : numeros) {
            System.out.println(numero);
        }
        
        //Calcular min, max y media (una sola pasada)
        if(numeros.isEmpty()) {
            System.out.println("La lista está vacía");
            return;
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int suma = 0;
        
        for (int x : numeros) {
            if (x < min) min = x;
            if (x  >max) max = x;
            suma += x;
        }
        double media = suma / numeros.size();
        
        //Ordenar la lista
        List<Integer> ordenados = new ArrayList<>(numeros);
        Collections.sort(ordenados);
        
        int tamanio = ordenados.size();
        double mediana = (tamanio % 2 == 1) 
                ? ordenados.get(tamanio/2)
                : (ordenados.get(tamanio/2-1) + ordenados.get(tamanio/2))/2;
        
        System.out.println("Resultados: ");
        System.out.printf("Mínimo %d, Máximo %d, Media %.2f, Mediana %.2f", min, max, media, mediana);
    }
}
