package ficherosTxt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vrand
 */
public class BlocNotas {

    private final static String FICHERO = "notas.txt";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        do {
            mostrarMenu();
            System.out.println("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); //limpiar salto

            switch (opcion) {
                case 1 ->
                    aniadirNota(sc);
                case 2 ->
                    verUltimasNNotas(sc);
                case 3 ->
                    buscarTexto(sc);
                case 4 ->
                    System.out.println("Saliendo...");
                default ->
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
    }

    private static void mostrarMenu() {
        System.out.println("----------- BLOC DE NOTAS -----------");
        System.out.println("1. Añadir nota");
        System.out.println("2. Ver últimas N notas");
        System.out.println("3. Buscar palabra en notas");
        System.out.println("4. Salir");
    }

    private static void aniadirNota(Scanner sc) {
        System.out.println("Escribe tu nota (línea): ");
        String nota = sc.nextLine();

        if (nota.isBlank()) {
            System.out.println("No se añadió, la nota está vacía.");
            return;
        }
        //abrimos en modo append
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHERO, true))) {
            //escribir texto y salto de línea
            bw.write(nota);
            bw.newLine();
            System.out.println("Nota guardada en " + FICHERO);
        } catch (IOException ex) {
            System.out.println("Error al escribir en el fichero: " + ex.getMessage());
        }

    }

    private static void verUltimasNNotas(Scanner sc) {
        System.out.println("¿Cuántas notas quieres ver? ");
        //while (!sc.hasNextInt()) { sc.next(); }
        int nNotas = sc.nextInt();
        sc.nextLine(); //limpiar buffer

        //Leer todas las líneas en una lista
        List<String> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FICHERO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Aún no existe " + FICHERO + ". Añade alguna nota primero.");
            return;
        } catch (IOException ex) {
            System.out.println("Error al leer el fichero " + ex.getMessage());
            return;
        }

        //comprobar que la lista no esté vacía. Y que nNotas líneas sea positivo
        if (lineas.isEmpty()) {
            System.out.println("El fichero está vacío");
            return;
        }
        if (nNotas <= 0) {
            System.out.println("El número debe ser mayor que cero.");
            return;
        }

        int total = lineas.size();
        int desde = Math.max(0, total - nNotas);
        System.out.println("Últimas " + Math.min(nNotas, total) + " línea(s): ");
        for (int i = desde; i < total; i++) {
            System.out.println(lineas.get(i));
        }
    }

    private static void buscarTexto(Scanner sc) {
        System.out.println("Introduce el texto a buscar: ");
        String aBuscar = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(FICHERO))) {
            String linea;
            int contador = 0;
            while ((linea = br.readLine()) != null) {
                contador++;
                if(linea.contains(aBuscar)) {
                    System.out.println("Texto encontrado en la línea: " +contador +": " + linea);
                } else {
                    System.out.println("No se ha encontrado el texto en la línea " + contador);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BlocNotas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BlocNotas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
