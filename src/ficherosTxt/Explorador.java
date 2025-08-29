package ficherosTxt;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author vrand
 *
 *
 * Crea una app de consola que, dado un directorio, muestre:
 *
 * nº de archivos y carpetas
 *
 * tamaño total (solo archivos)
 *
 * los 5 archivos más grandes (nombre y tamaño)
 *
 */
public class Explorador {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Ruta del directorio: ");
        String ruta = sc.nextLine().trim();
        File dir = new File(ruta);

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("La ruta no existe o no es un directorio.");
            return;
        }

        File[] items = dir.listFiles();
        if (items == null) {
            System.out.println("No se pudo listar el directorio.");
            return;
        }

        // TODO 1: contar nº de archivos y nº de carpetas (solo nivel actual)
        long numArchivos = Arrays.stream(items) // Stream<File>
                .filter(File::isFile) // me quedo con archivos
                .count();                          // cuento cuántos hay

        long numCarpetas = Arrays.stream(items) // Stream<File>
                .filter(File::isDirectory) // me quedo con carpetas
                .count();                          // cuento cuántas hay

        System.out.println("Archivos: " + numArchivos + " | Carpetas: " + numCarpetas);

        // TODO 2: calcular tamaño total (solo archivos del nivel actual, sin recursión)
        long tamTotal = 0L; // suma de lengths de los archivos
        tamTotal = Arrays.stream(items)
                .filter(File::isFile)
                .mapToLong(File::length)
                .sum();

        System.out.println("Tamaño total (bytes): " + tamTotal);

        // TODO 3: mostrar top 5 por tamaño (nombre y tamaño)
        System.out.println("Top 5 archivos más grandes:");
        Arrays.stream(items)
                .filter(File::isFile)
                .sorted(Comparator.comparingLong(File::length).reversed())
                .limit(5)
                .forEach(f -> System.out.println(f.getName() + " -> " + f.length() + " bytes"));
        // pista: filtra isFile(), ordena por length desc, limita a 5 e imprime
    }
}
