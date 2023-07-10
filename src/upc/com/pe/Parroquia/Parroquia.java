package upc.com.pe.Parroquia;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Parroquia {

    static String[] clientes = {"Maria|Rodriguez|Salas|45597220|27/09/1988|Puno","Carlos|Cruz|Ramos|74852101|08/01/1990|Lima","Fernanda|Valverde|Lopez|12345678|01/01/2000|Puno"};
    static String[] servicios = {"BA030Bautizo", "PC030Primera Comunión","CF035Confirmación", "MA800Matrimonio","M1025Misa Fiestas Patronales","M2010Misa por salud",
                                 "M3010Misa por albas","M4010Misa por descanso eterno","M5010Misa Otros", "CO010Copias"};
    static String[] servicioCliente = new String[100];
            //={"0109/07/201645597220BA1|030.00|-", "0209/10/201645597220PC1|030.00|-", "0309/10/202345597220CO2|020.00|constancias de Bautizo y Confirmación"};

    static String[] constancias = new String[100];

    static final char SEPARADOR = '|';
    static final String FECHASISTEMA ="09/07/2023";
    public static void main(String[] args) {
        imprimirMenu();
    }

    static void logo(){
        System.out.println("+-----------------------------------------------------------+");
        System.out.printf("|%59s|\n"," ");
        System.out.println("|               SISTEMA DE GESTIÓN PARROQUIAL               |");
        System.out.printf("|%59s|\n"," ");
        System.out.println("+-----------------------------------------------------------+");
    }
    static void imprimirMenu() {
        Scanner sc= new Scanner(System.in);
        boolean salir = false;
        int opcion;

        do{
            try {
                logo();
                System.out.println("1. Registrar cliente");
                System.out.println("2. Consultar cliente");
                System.out.println("3. Registrar servicio");
                System.out.println("4. Generar recibo");
                System.out.println("5. Salir");
                System.out.println();
                System.out.println("+-----------------------------------------------------------+");
                System.out.print("Indique opción: ");
                opcion = sc.nextInt();
                switch (opcion) {
                    case 1:
                        crearCliente();
                        break;
                    case 2:
                        buscarCliente();
                        break;
                    case 3:
                        registrarServicio();
                        break;
                    case 4:
                        generarRecibo();
                        break;
                    case 5:
                        salir = true;
                        System.out.println("Salió del sistema");
                        break;
                    default:
                        System.out.println("Ingrese opciones del 1 al 5");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número según la opción del menú");
                sc.nextLine();
            }
        }while (!salir);
    }
    static void crearCliente() {
        Scanner sc = new Scanner(System.in);
        int cantidad = 0 , contador = 0 , dni;
        char rpta = 'n';
        String nombre , apellidoPaterno, apellidoMaterno, fechaCumple, lugarNacimiento;

        try {
            System.out.print("Ingrese la cantidad de clientes: ");
            cantidad = sc.nextInt();

            clientes = new String[cantidad];
            do{
                System.out.println("Cliente N° " + (contador + 1) );
                System.out.println("-----------------------------------");
                System.out.print("Ingrese el nombre : ");
                nombre = sc.next();
                System.out.print("Ingrese apellido paterno : ");
                apellidoPaterno = sc.next();
                System.out.print("Ingrese apellido materno : ");
                apellidoMaterno = sc.next();
                System.out.print("Ingrese el nro de dni: ");
                dni = sc.nextInt();
                System.out.print("Ingrese fecha de cumpleaños (dd/mm/yyyy): ");
                fechaCumple = sc.next();
                System.out.println("Ingrese lugar de nacimiento: ");
                lugarNacimiento = sc.next();
                clientes[contador] = nombre + SEPARADOR + apellidoPaterno + SEPARADOR + apellidoMaterno
                        + SEPARADOR + dni + SEPARADOR + fechaCumple + SEPARADOR + lugarNacimiento;
                contador++;
                if(contador < cantidad) {
                    System.out.println("¿Desea ingresar otra cliente? (S/N)");
                    rpta = sc.next().charAt(0);
                }
                sc.nextLine();
            }while(contador < cantidad && (rpta == 'S' || rpta =='s'));

            imprimirMenu();

        }catch (Exception e){
            System.out.println("Ocurrió un error");
            e.printStackTrace();
        }
    }

    static void buscarCliente() {
        int opcion, documento;
        char respuesta;
        String apellidoPaterno;
        String[] detalleCliente;
        boolean regresarMenu = false;

        Scanner sc = new Scanner(System.in);

        do {
            imprimirMenuConsulta();
            System.out.println("+---------------------------------------+");
            System.out.print("Ingrese opcion:");
            opcion = sc.nextInt();
            System.out.println("+---------------------------------------+");

            try {
                switch (opcion) {
                    case 1:
                        //búsqueda por dni
                        System.out.print("Ingresar número de documento (DNI) : ");
                        documento = sc.nextInt();
                        System.out.println();
                        cabeceraConsultacliente();
                        detalleCliente = busquedaDocumento(documento);
                        imprimirInformacionCliente(detalleCliente);
                        break;
                    case 2:
                        //busqueda por apellido paterno
                        System.out.print("Ingresar apellido paterno : ");
                        apellidoPaterno = sc.next();
                        System.out.println();
                        cabeceraConsultacliente();
                        detalleCliente = busquedaApellido(apellidoPaterno);
                        imprimirInformacionCliente(detalleCliente);
                        break;
                    case 3:
                        regresarMenu = true;
                        sc.nextLine();
                        respuesta = 'N';
                        break;
                    default:
                        System.out.println("Ingrese opciones del 1 al 3");
                        break;
                }
                if(!regresarMenu) {
                    System.out.println();
                    System.out.print("Desea continuar en el módulo de consulta de cliente (S/N)? : ");
                    respuesta = sc.next().charAt(0);
                }else{
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número según la opción del submenú");
                sc.nextLine();
                respuesta = 'S';
            }
        }while (respuesta == 'S' || respuesta == 's');

    }
    static void cabeceraConsultacliente(){
        if(clientes.length > 0){
            System.out.println("+----------CONSULTA DE CLIENTE----------+");
            System.out.printf("%-15s | %-18s | %-18s | %-15s | %-15s | %-15s%n",
                    "Nombre", "Apellido Paterno", "Apellido Materno", "Documento (DNI)",
                    "Fecha Nacimiento", "Lugar Nacimiento");
        }
    }
    static void imprimirMenuConsulta() {
        System.out.println();
        System.out.println("+----------CONSULTA DE CLIENTE----------+");
        System.out.println("1. Documento (dni)");
        System.out.println("2. Apellido paterno");
        System.out.println("3. Retornar a menú principal");
        System.out.println();
    }
    static String[] busquedaDocumento(int documento) {
        int clienteDocumento;
        int ubicacion1 = 0,ubicacion2 = 0, contador = 0;
        boolean indicador = false;
        String[] detalleCliente = new String[clientes.length];

        for (int i=0; i< clientes.length;i++){
            ubicacion1 = obtenerUbicacionSeparador(clientes[i],3 );
            ubicacion2 = clientes[i].indexOf(SEPARADOR,ubicacion1 + 1);
            clienteDocumento = Integer.parseInt(clientes[i].substring(ubicacion1+1,ubicacion2));
            if( clienteDocumento == documento){
                detalleCliente[contador] = clientes[i];
                contador ++;
                indicador = true;
            }
        }

        if(!indicador){
            System.out.println();
            System.out.println("Mensaje : No existe cliente!!");
        }
        return detalleCliente;
    }
    static String[] busquedaApellido(String apellidoPaterno) {
        String apellido;
        int ubicacion1 = 0,ubicacion2 = 0, contador = 0;;
        boolean indicador = false;
        String[] detalleCliente = new String[clientes.length];

        for (int i=0; i< clientes.length;i++){
            ubicacion1 = obtenerUbicacionSeparador(clientes[i],1);
            ubicacion2 = clientes[i].indexOf(SEPARADOR,ubicacion1 + 1);
            apellido = clientes[i].substring(ubicacion1+1,ubicacion2);
            if( apellido.equals(apellidoPaterno)){
                detalleCliente[contador] = clientes[i];
                contador ++;
                indicador = true;
            }
        }

        if(!indicador){
            System.out.println();
            System.out.println("Mensaje : No existe cliente!!");
        }
        return detalleCliente;
    }

    static void registrarServicio() {

        String[] detalleCliente;
        String[] busquedaServicio ;
        String tipoServicio, fechaEvento="dd/mm/YYYY", horaEvento="HH:MMPM";
        String nombrePadre = "-", nombreMadre = "-", nombrePadrino="-", nombreMadrina = "-",
                anotaciones="-", cadena , numeroRecibo;
        int documento,contador = 0 , copias = 1 ;
        double precioServicio,precio;
        boolean b = false;
        char respuesta;

        Scanner sc = new Scanner(System.in);
        do {
            System.out.println();
            System.out.print("Ingresar número de documento (DNI) : ");
            documento = sc.nextInt();
            detalleCliente = busquedaDocumento(documento);
            System.out.print("Ingresar tipo de servicio : ");
            tipoServicio = sc.next();
            if (tipoServicio.equals("CO") ) {
                System.out.print("Ingresar cantidad de copias : ");
                copias = sc.nextInt();
            }
            if (!tipoServicio.equals("CO") ) {
                System.out.print("Ingresar fecha de evento (día/mes/año) : ");
                fechaEvento = sc.next();
                System.out.print("Ingresar hora de evento (HH:MMPM) : ");
                sc.nextLine();
                horaEvento = sc.nextLine();
            }
            if (tipoServicio.equals("BA") || tipoServicio.equals("PC") || tipoServicio.equals("CF") ) {
                System.out.print("Ingresar nombre del padre : ");
                nombrePadre = sc.nextLine();
                System.out.print("Ingresar nombre de la madre : ");
                nombreMadre = sc.nextLine();
            }
            if (tipoServicio.equals("BA") || tipoServicio.equals("PC") || tipoServicio.equals("CF") || tipoServicio.equals("MA")  ) {
                System.out.print("Ingresar nombre del padrino : ");
                nombrePadrino = sc.nextLine();
                System.out.print("Ingresar nombre de la madrina : ");
                nombreMadrina = sc.nextLine();
                b = true;
            }
            if (!b) {
                System.out.print("Ingresar anotaciones : ");
                anotaciones = sc.next();
            }
            System.out.println();
            detalleCliente = busquedaDocumento(documento);

            precioServicio = obtenerPrecioServicio(tipoServicio);
            for (int i=0;  i< detalleCliente.length;i++){
                if (detalleCliente[i] !=null) {
                    cadena = tipoServicio+documento+fechaEvento+horaEvento+FECHASISTEMA+SEPARADOR+nombrePadre+SEPARADOR+nombreMadre+
                    SEPARADOR + nombrePadrino + SEPARADOR + nombreMadrina;
                    constancias[contador] = cadena;
                    numeroRecibo = String.format("%02d",contador + 1);
                    precio =  copias * precioServicio ;
                    servicioCliente[contador] =  numeroRecibo + FECHASISTEMA+documento+tipoServicio+copias+SEPARADOR+precio+SEPARADOR+anotaciones;
                    contador++;
                    System.out.println("+-----------------------------------------------------------+");
                    System.out.print("Se registró correctamente!!");
                    System.out.println("+-----------------------------------------------------------+");
                    break;
                }
            }
            System.out.println();
            System.out.print("Desea volver a registrar otro servicio (S/N)? : ");
            respuesta = sc.next().charAt(0);
            sc.nextLine();
        }while(  respuesta =='S'|| respuesta =='s' );
    }

    static double obtenerPrecioServicio(String tipoServicio) {
        double precio=0;
        for (int i=0; i < servicios.length;i++){
            if(servicios[i].substring(0,2).equals(tipoServicio)) {
                precio = Integer.parseInt(servicios[i].substring(2,5));
                break;
            }
        }
        return precio;
    }

    static void generarRecibo() {
        String[] detalleCliente;
        String[] busquedaServicio ;
        String nombre = null, apellidoPaterno = null, apellidoMaterno= null, fechaRegistro,tipoServicio,nota,numeroRecibo;
        int documento,separador1,separador2,separador3,cantidad;
        double precio;
        char respuesta;

        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Ingresar número de documento (DNI) : ");
            documento = sc.nextInt();
            System.out.print("Fecha de registro : ");
            fechaRegistro = sc.next();
            System.out.println();
            detalleCliente = busquedaDocumento(documento);
            for (int i=0;  i< detalleCliente.length;i++){
                if (detalleCliente[i] !=null) {
                    separador1 = detalleCliente[i].indexOf('|');
                    separador2 = detalleCliente[i].indexOf('|', separador1 + 1);
                    separador3 = detalleCliente[i].indexOf('|', separador2 + 1);
                    nombre = detalleCliente[i].substring(0, separador1);
                    apellidoPaterno = detalleCliente[i].substring(separador1 + 1, separador2);
                    apellidoMaterno = detalleCliente[i].substring(separador2 + 1, separador3);
                    busquedaServicio = busquedaServicioCliente(documento,fechaRegistro);
                    for (int j=0;j< busquedaServicio.length;j++){
                        if(busquedaServicio[j] != null){
                            numeroRecibo = busquedaServicio[j].substring(0,2);
                            tipoServicio = busquedaServicio[j].substring(20,22);
                            cantidad = Integer.parseInt(busquedaServicio[j].substring(22,23));
                            separador1 = busquedaServicio[j].indexOf('|');
                            separador2 = busquedaServicio[j].indexOf('|', separador1 + 1);
                            precio =  Double.parseDouble(busquedaServicio[j].substring(separador1+1,separador2));
                            nota =  busquedaServicio[j].substring(separador2 + 1);
                            imprimirRecibo(numeroRecibo,nombre, apellidoPaterno, apellidoMaterno, fechaRegistro, tipoServicio, cantidad, precio, nota);

                        }else{
                            break;
                        }
                    }
                    break;
                }
            }
            System.out.println();
            System.out.print("Desea volver a generar otro recibo (S/N)? : ");
            respuesta = sc.next().charAt(0);
        }while(  documento  != -1 && (respuesta =='S'|| respuesta =='s'));
    }

    static String[] busquedaServicioCliente(int documento, String fechaRegistro) {
        String[] busqueda = new String[servicioCliente.length];
        String fecha ;
        int documentoCliente, contador = 0;
        for (int i=0;  i< servicioCliente.length;i++){
             fecha = servicioCliente[i].substring(2,12);
             documentoCliente = Integer.parseInt(servicioCliente[i].substring(12,20));
             if(fecha.equals(fechaRegistro) && documentoCliente == documento) {
                 busqueda[contador] = servicioCliente[i];
                 contador++;
                 break;
             }
        }
        if(contador==0){
            System.out.println();
            System.out.println("Mensaje : El cliente no tiene registro de algún servicio!!");
        }
        return busqueda;
    }

    static void imprimirRecibo(String numeroRecibo,String nombre, String apellidoPaterno, String apellidoMaterno,
                               String fecha, String tipoServicio, int cantidad, double precio, String nota) {
    String detalleServicio = null;
    detalleServicio = obtenerDescripcionServicio(tipoServicio);

    System.out.println("+-----------------------------------------------------------+");
    System.out.println("|                     RECIBO DE INGRESOS                    |");
    System.out.println("|                PARROQUIA SAN JUAN BAUTISTA                |");
    System.out.println("+-----------------------------------------------------------+");
    System.out.printf("|                                             N° Recibo: %3s|\n",numeroRecibo);
    System.out.printf("| Recibimos de: %-44s|\n", nombre + " " + apellidoPaterno + " " + apellidoMaterno);
    System.out.printf("| En concepto de : %-40s |\n", detalleServicio);
    System.out.printf("| %-57s |\n", nota);
    System.out.printf("| %-58s|\n","");
    System.out.printf("| Por el valor de: S/.%-38s|\n", precio);
    System.out.printf("| %-58s|\n","");
    System.out.printf("| Fecha:%-52s|\n",fecha);
    System.out.println("+-----------------------------------------------------------+");
    System.out.println();
    }

    static String obtenerDescripcionServicio(String tipoServicio) {
        String descripcion =null; //BA030|Bautizo
        for (int i=0; i < servicios.length;i++){
            if(servicios[i].substring(0,2).equals(tipoServicio)) {
                descripcion = servicios[i].substring(5);
                break;
            }
        }
        return descripcion;
    }

    static void imprimirInformacionCliente(String[] detalleCliente) {
        String nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, lugarNacimiento;
        int documento,separador1,separador2,separador3,separador4,separador5;

        for (int i=0; i< detalleCliente.length;i++) {
            if(detalleCliente[i]  !=null) {
                separador1 = detalleCliente[i].indexOf('|');
                separador2 = detalleCliente[i].indexOf('|', separador1 + 1);
                separador3 = detalleCliente[i].indexOf('|', separador2 + 1);
                separador4 = detalleCliente[i].indexOf('|', separador3 + 1);
                separador5 = detalleCliente[i].indexOf('|', separador4 + 1);

                nombre = detalleCliente[i].substring(0, separador1);
                apellidoPaterno = detalleCliente[i].substring(separador1 + 1, separador2);
                apellidoMaterno = detalleCliente[i].substring(separador2 + 1, separador3);
                documento = Integer.parseInt(detalleCliente[i].substring(separador3 + 1, separador4));
                fechaNacimiento = detalleCliente[i].substring(separador4 + 1, separador5);
                lugarNacimiento = detalleCliente[i].substring(separador5 + 1);

                System.out.printf("%-15s | %-18s | %-18s | %-15s | %-15s | %-15s%n",
                        nombre, apellidoPaterno, apellidoMaterno, documento, fechaNacimiento, lugarNacimiento);
            }else {
             break;
            }
        }
    }
    static int obtenerUbicacionSeparador(String cadena, int numeroUbicacion) {
        int ubicacion = -1;
        int contador = 0;
        int inicio = 0;
        int fin;

        while ((fin = cadena.indexOf(SEPARADOR, inicio)) != -1) {
            contador++;

            if (contador == numeroUbicacion) {
                ubicacion = fin;
                break;
            }

            inicio = fin + 1;
        }

        return ubicacion;
    }
}
