package principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ListaNombres {
    private static List<String> nombres = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger(ListaNombres.class.getName());

    public static void main(String[] args) {
    	configurarFicheroLogs();
        nombres = leerNombresFichero("nombres.txt");
        if (nombres.isEmpty()) {
            System.out.println("Error al leer el archivo nombres.txt o el archivo está vacío.");
            return;
        }
        int opcion = 0;
        while (opcion != 5) {
            opcion = mostrarMenu();           
            switch (opcion) {
                case 1:
                    visualizarNombres();
                    break;
                case 2:
                    ordenarNombres();
                    break;
                case 3:
                    System.out.print("Introduce el nombre que quieres añadir: ");
                    String nombre = scanner.nextLine();
                    if (!nombres.contains(nombre)) {
                        nombres.add(nombre);
                        System.out.println(nombre + " ha sido añadido a la lista.");
                    } else {
                        System.out.println(nombre + " ya está en la lista.");
                    }
                    break;
                case 4:
                    int numNombres = contarNombres(nombres);
                    System.out.println("En la lista hay " + numNombres + " nombres");
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, inténtalo de nuevo.");
            }
        }
    }

    public static List<String> leerNombresFichero(String fichero) {
    	List<String> nombresLeidos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fichero))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	nombresLeidos.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
        }
        return nombresLeidos;
    }

    private static int mostrarMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Mostrar nombres");
        System.out.println("2. Ordenar nombres");
        System.out.println("3. Añadir un nombre a la lista");
        System.out.println("4. Contar combres en la lista");
        System.out.println("5. Salir");
        System.out.print("Introduce tu opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    private static void visualizarNombres() {
        System.out.println("Lista de nombres:");
        for (String name : nombres) {
            System.out.println(name);
        }
    }

    private static void ordenarNombres() {
        Collections.sort(nombres);
        System.out.println("Los nombres han sido ordenados.");
    }
    
    public static int contarNombres(List<String> nombresAcontar) {
        return nombresAcontar.size();
    }
    
    private static void configurarFicheroLogs() {
        Handler fileHandler  = null;
        try{
        	//Eliminar handler por defecto (salida consola)
        	LOGGER.setUseParentHandlers(false);
            fileHandler = new FileHandler("ficheroLog.txt", true);
            LOGGER.addHandler(fileHandler);
            fileHandler.setLevel(Level.ALL);
            LOGGER.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
        }catch(IOException exception){
        	System.out.println("Error en la configuración de los logs. " + exception);
        }
    }

}
