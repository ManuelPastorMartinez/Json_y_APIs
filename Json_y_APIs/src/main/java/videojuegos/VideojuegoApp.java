package videojuegos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VideojuegoApp {
    static Scanner teclado = new Scanner(System.in);
    static ArrayList<Videojuego> listaVideojuegos = new ArrayList<>();
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        for (int i = 0; i < 3; i++) {
            listaVideojuegos.add(crearVideojuegos());
        }
        String json = gson.toJson(listaVideojuegos);
        System.out.println("JSON: " + json);

        try (FileWriter writer = new FileWriter("src/main/java/videojuegos/videojuegos.json")) {
            gson.toJson(listaVideojuegos, writer);
            System.out.println("JSON guardado en videojuegos.json");
        } catch (Exception e) {
            System.out.println("Algo ha ido mal.");
            e.printStackTrace();
        }

        try {
            FileReader reader = new FileReader("src/main/java/videojuegos/videojuegos.json");
            Videojuego videojuegos[] = gson.fromJson(reader, Videojuego[].class);

            List<Videojuego> listaVideojuegosReconstruida = Arrays.asList(videojuegos);

            listaVideojuegosReconstruida.add(crearVideojuegos());

            for (Videojuego videojuego : listaVideojuegosReconstruida){
                System.out.println("Videojuegos de menos de 30€");

                if (videojuego.getPrecio() < 30) {
                    System.out.println("Nombre: " + videojuego.getNombre());
                    System.out.println("Plataforma: " + videojuego.getPlataforma());
                    System.out.println("Precio: " + videojuego.getPrecio());
                }
            }

            try (FileWriter writer = new FileWriter("src/main/resources/videojuegos.json")) {
                gson.toJson(listaVideojuegosReconstruida, writer);
                System.out.println("JSON guardado en persona.json");
            } catch (Exception e) {
                System.out.println("Algo ha ido mal.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static Videojuego crearVideojuegos(){
        System.out.println("*** BIENVENIDO A LA CREACIÓN DE VIDEOJUEGOS ***");

        System.out.println("Nombre del videojuego:");
        String nombre=teclado.nextLine();

        System.out.println("Plataforma del videojuego:");
        String plataforma=teclado.nextLine();

        System.out.println("Precio del videojuego:");
        double precio = teclado.nextDouble();

        System.out.println("El videojuego está disponible? (S/N)");

        char disponible = teclado.next().toUpperCase().charAt(0);

        Videojuego videojuego = null;
        if (disponible=='S'){
            videojuego = new Videojuego(nombre,plataforma,precio,true);
        }else {
            videojuego = new Videojuego(nombre,plataforma,precio,false);
        }

        System.out.println("Añade los géneros del videojuego (para salir no introduzcas nada)");
        String genero = teclado.nextLine();
        do {
            videojuego.anyadirGenero(genero);
            genero=teclado.nextLine();
        }while (!genero.isEmpty());

        return videojuego;
    }


}
