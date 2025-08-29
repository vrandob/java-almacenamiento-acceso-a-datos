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
 *
 * Ccrear un programa de consola que guarde una lista en lista.txt. Requisitos:
 *
 * Añadir ítem (una línea por ítem, modo append).
 *
 * Listar ítems (mostrar todas las líneas).
 *
 * Eliminar ítem exacto (borra la línea que coincida exactamente con el texto).
 *
 * Salir.
 */
public class ListaCompra {

    private static final String FICHERO = "lista.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        do {
            mostrarMenu();
            System.out.println("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); //limpio salto

            switch (opcion) {
                case 1 ->
                    aniadir(sc);
                case 2 ->
                    listar();
                case 3 ->
                    eliminar(sc);
                case 4 ->
                    System.out.println("Saliendo...");
                case 5 ->
                    System.out.println("Opción no válida.");

            }

        } while (opcion != 4);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n---Lista de la compra---");
        System.out.println("1) Añadir item");
        System.out.println("2) Listar items");
        System.out.println("3) Eliminar item");
        System.out.println("4) Salir");
    }

    private static void aniadir(Scanner sc) {
        // TODO: pedir texto; si está en blanco, avisar y return
        // TODO: abrir BufferedWriter(new FileWriter(FICHERO, true))
        // TODO: escribir la línea + newLine(); imprimir "Guardado"

        System.out.println("Introduce el nuevo artículo: ");
        String articuloNuevo = sc.nextLine();
        if (articuloNuevo.isBlank()) {
            System.out.println("No se ha añadido nada.");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("lista.txt", true))) {
            bw.write(articuloNuevo);
            bw.newLine();
            System.out.println("Artículo guardado correctamente");
        } catch (IOException ex) {
            Logger.getLogger(ListaCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void listar() {
        // TODO: abrir BufferedReader(new FileReader(FICHERO))
        // TODO: leer readLine() en bucle e imprimir
        // TIP: FileNotFoundException -> "Aún no hay lista"
        try (BufferedReader br = new BufferedReader(new FileReader("lista.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error, aún no hay lista creada " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("No se ha podido acceder al archivo " + ex.getMessage());
        }
    }

    private static void eliminar(Scanner sc) {
        // TODO: pedir texto a eliminar (coincidencia exacta)
        // TODO: leer todas las líneas a List<String>
        // TODO: crear nueva lista filtrada (sin la que coincide)
        // TODO: reescribir el archivo SIN append (FileWriter(FICHERO))
        // TODO: informar cuántas líneas se eliminaron
        
        System.out.println("Introduce el artículo a eliminar: ");
        String aEliminar = sc.nextLine().trim();

        List<String> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("lista.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.equalsIgnoreCase(aEliminar)) {
                    lista.add(linea);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer: " + ex.getMessage());
            return;
        }

        //Reescribimos el archivo sin append a partir de la lista creada filtrada
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("lista.txt"))) {
            for (String art : lista) {
                bw.write(art);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error al escribir: " + ex.getMessage());
        }
        
        System.out.println("Se eliminó " + aEliminar + "si es que existía");
    }
}
