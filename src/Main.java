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
        System.out.print("Nombre del producto: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio: $ ");
        String precioInput = scanner.nextLine();

        System.out.print("Stock: ");
        String stockInput = scanner.nextLine();

        try {
            double precio = Double.parseDouble(precioInput);
            int stock = Integer.parseInt(stockInput);

            Producto nuevo = new Producto(nombre, precio, stock);
            productos.add(nuevo);
            System.out.println("✅ Producto agregado con éxito.");
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: El precio o el stock ingresado no es válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error al agregar producto: " + e.getMessage());
        }
    }

    private static void listarProductos() {
        System.out.println("\n=== LISTA DE PRODUCTOS ===");

        if( productos.isEmpty()){
            System.out.println("No hay productos cargados");
            return ;
        }
        System.out.printf("%-5s %-20s %-10s %-10s\n", "ID", "Nombre", "Precio", "Stock");
        System.out.println("-----------------------------------------------------");

        for (Producto p : productos) {
            System.out.printf("%-5d %-20s $%-9.2f %-10d\n",
                    p.getId(), p.getNombre(), p.getPrecio(), p.getStock());
        }
    }

    private static Producto buscarProductoPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private static void actualizarProducto() {
        int id;

        while (true) {
            System.out.print("Ingrese el ID del producto a actualizar: ");
            String input = scanner.nextLine();
            if (input.isBlank()) {
                System.out.println("❌ El ID no puede estar vacío. Intente nuevamente.");
                continue;
            }
            try {
                id = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("❌ Debe ingresar un número válido. Intente nuevamente.");
            }
        }

        Producto producto = buscarProductoPorId(id);
        if (producto == null) {
            System.out.println("❌ Producto no encontrado.");
            return;
        }

        System.out.println("✅ Producto encontrado: \n" + producto );


        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.isBlank()) {
            producto.setNombre(nuevoNombre);
        }


        while (true) {
            System.out.print("Nuevo precio: ");
            String input = scanner.nextLine();
            if (input.isBlank()) {
                System.out.println("Debe ingresar un número");
                continue;
            }
            try {
                double nuevoPrecio = Double.parseDouble(input);
                if (nuevoPrecio >= 0) {
                    producto.setPrecio(nuevoPrecio);
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("❌ Precio inválido. Intente nuevamente.");
            }
        }


        while (true) {
            System.out.print("Nuevo stock : ");
            String input = scanner.nextLine();
            if (input.isBlank()) {
                System.out.println("Debe ingresar un número.");
                continue;
            }
            try {
                int nuevoStock = Integer.parseInt(input);
                if (nuevoStock >= 0) {
                    producto.setStock(nuevoStock);
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("❌ Stock inválido. Intente nuevamente.");
            }
        }
        System.out.println("✅ Producto actualizado con éxito.");
    }

    private static void eliminarProducto() {
       int id ;

       while ( true ) {
           System.out.println("Ingresar el ID del producto a eliminar : ");
           String input = scanner.nextLine() ;
           if(input.isBlank()) {
               System.out.println("El ID no puede estar vacío. Intente nuevamente.");
               continue;
           }
           try {
               id = Integer.parseInt(input);
               break;
           }catch (NumberFormatException e) {
               System.out.println("Debe ingresar un número válido.");
           }
       }
        Producto producto = buscarProductoPorId(id);

        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        String nombreEliminado = producto.getNombre() ;
        productos.remove(producto);
        System.out.println("✅ " + nombreEliminado + " eliminado con éxito.");
    }

    private static void crearPedido() {
       Pedido nuevoPedido = new Pedido() ;

       while (true) {
           int id = leerEntero("Ingrese el ID del producto ( -1 para terminar ) : ") ;
           if ( id == -1 ) break ;

           Producto producto = buscarProductoPorId(id) ;
           if( producto == null ) {
               System.out.println("Producto no encontrado.\n");
               continue;
           }

           int cantidad = leerEntero("Ingrese cantidad : ") ;
           if( cantidad <= 0) {
               System.out.println("La cantidad debe ser mayor a 0 .\n");
               continue;
           }

           try{
               nuevoPedido.agregarProducto(producto,cantidad) ;
               System.out.println("Producto agregado al pedido.\n");
           }catch (IllegalArgumentException e ) {
               System.out.println("Error : " + e.getMessage() + "\n.");
           }
       }

       if( nuevoPedido.estaVacio() ) {
           System.out.println("No se creo el pedido, no se agregaron productos");
       }else{
           nuevoPedido.confirmarPedido();
           pedidos.add(nuevoPedido) ;
           System.out.println("\n Pedido confirmado con exito : ");
           nuevoPedido.mostrarDetalles();
       }
    }

    private static void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }

        int contador = 1;
        for (Pedido p : pedidos) {
            System.out.println("📦 Pedido #" + contador++ );
            p.mostrarDetalles();
            System.out.println("---------------------------");
        }
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

