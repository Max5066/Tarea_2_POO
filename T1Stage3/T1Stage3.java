import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class T1Stage3 {
    private Map<String, Publisher> publishers = new HashMap<>();
    private Broker broker = new Broker();

    public static void main(String args[]) {
        if (args.length != 0) {
            System.out.println("Usage: java T1Stage3");
            System.exit(-1);
        }

        T1Stage3 stage = new T1Stage3();
        stage.setupSimulator();
        stage.runSimulation();
    }

    public void setupSimulator() {
        // Configuración según los requisitos de la etapa 3
        Publisher streamer = new Publisher("Streamer_1", broker, "Notificaciones_1");
        publishers.put("Streamer_1", streamer);//Se guarda en tabla hash

        try {
            // Crear dos seguidores diferentes
            Follower follower1 = new Follower("Seguidor_1", "Notificaciones_1", new PrintStream("seguidor1.txt"));
            Follower follower2 = new Follower("Seguidor_2", "Notificaciones_1", new PrintStream("seguidor2.txt"));

            broker.subscribe(follower1);
            broker.subscribe(follower2);

        } catch (FileNotFoundException e) {
            System.err.println("Error al crear archivos de salida para seguidores");
            System.exit(-1);
        }
    }

    public void runSimulation() {
        Scanner inputEvent = new Scanner(System.in);
        System.out.println("Sistema listo. Ingrese mensajes (<nombre_publicador> <mensaje>):");

        while (inputEvent.hasNextLine()) {
            String line = inputEvent.nextLine();
            String[] parts = line.split(" ", 2); // Dividir en nombre y mensaje

            if (parts.length < 2) {
                System.out.println("Formato incorrecto. Use: <nombre_publicador> <mensaje>");
                continue;
            }

            String publisherName = parts[0];
            String message = parts[1];

            Publisher publisher = publishers.get(publisherName);
            if (publisher == null) {
                System.out.println("Unknown Publisher");
                continue;
            }

            publisher.publishNewEvent(message);
        }
    }
}