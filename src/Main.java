import models.Pedido;
import models.Producto;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Producto> productos = new ArrayList<>() ;
    private static ArrayList<Pedido> pedidos = new ArrayList<>() ;
    private static Scanner scanner = new Scanner(System.in) ;

    private static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Agregar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar/Actualizar producto");
        System.out.println("4. Eliminar producto");
        System.out.println("5. Crear un pedido");
        System.out.println("6. Listar pedidos");
        System.out.println("7. Salir");
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            System.out.flush();
            String input = scanner.nextLine().trim();

            if (input.isBlank()) {
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Ingrese un número válido.\n");
            }
        }
    }

    private static void agregarProducto() {
        System.out.println("Agregar Producto");
    }

    private static void listarProductos() {
        System.out.println("Listar Productos");
    }

    private static void actualizarProducto() {
        System.out.println("Actualizar Producto");
    }

    private static void eliminarProducto() {
        System.out.println("Eliminar Producto");
    }

    private static void crearPedido() {
        System.out.println("Crear Producto");
    }

    private static void listarPedidos() {
        System.out.println("Listar Producto");
    }



    public static void main(String[] args) {
        int opcion ;

        do {
            mostrarMenu() ;
            opcion = leerEntero("Elegir una opcion : ") ;

            switch (opcion) {
                case 1 :
                    agregarProducto() ;
                    break ;
                case 2 :
                    listarProductos() ;
                    break;
                case 3 :
                    actualizarProducto() ;
                    break;
                case 4 :
                    eliminarProducto() ;
                    break ;
                case 5 :
                    crearPedido() ;
                    break ;
                case 6 :
                    listarPedidos() ;
                    break ;
                case 7 :
                    System.out.println("¡Gracias por usar el sistema! Hasta luego.");
                    break;
                default:
                    System.out.println("Opción inválida. Probá de nuevo.");
            }
        } while ( opcion != 7) ;
    }
}

