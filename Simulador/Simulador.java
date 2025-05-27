import java.io.*;
import java.util.*;

public class Simulador {
    private Map<String, Publisher> publishers = new HashMap<>();
    private Broker broker = new Broker();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Simulador <configFile.txt>");
            System.exit(-1);
        }

        Simulador simulator = new Simulador();
        simulator.loadConfiguration(args[0]);
        simulator.runSimulation();
    }

    private void loadConfiguration(String configFile) {
        try (Scanner fileScanner = new Scanner(new File(configFile))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                Scanner lineScanner = new Scanner(line);
                String componentType = lineScanner.next();

                if (componentType.equals("publicador")) {
                    String name = lineScanner.next();
                    String topic = lineScanner.next();
                    publishers.put(name, new Publisher(name, broker, topic));
                }
                else if (componentType.equals("suscriptor")) {
                    String subscriberType = lineScanner.next();
                    String name = lineScanner.next();
                    String topic = lineScanner.next();
                    String outputFile = lineScanner.next();

                    Subscriber subscriber = createSubscriber(subscriberType, name, topic, outputFile);
                    if (subscriber != null) {
                        broker.subscribe(subscriber);
                    }
                }
                lineScanner.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo de configuración no encontrado");
            System.exit(-1);
        }
    }

    private Subscriber createSubscriber(String type, String name, String topic, String outputFile) {
        try {
            PrintStream out = new PrintStream(outputFile);
            switch (type) {
                case "Seguidor":
                    return new Follower(name, topic, out);
                case "Registrador":
                    return new Recorder(name, topic, out);
                case "Monitor":
                    return new Monitor(name, topic, out);
                default:
                    System.err.println("Tipo de suscriptor desconocido: " + type);
                    return null;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al crear archivo de salida: " + outputFile);
            return null;
        }
    }

    private void runSimulation() {
        Scanner input = new Scanner(System.in);
        System.out.println("Sistema listo. Ingrese mensajes:");

        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.isEmpty()) continue;

            int firstSpace = line.indexOf(' ');
            if (firstSpace == -1) {
                System.out.println("Formato incorrecto. Use: <publicador> <mensaje>");
                continue;
            }

            String publisherName = line.substring(0, firstSpace);
            String message = line.substring(firstSpace + 1);

            Publisher publisher = publishers.get(publisherName);
            if (publisher == null) {
                System.out.println("Unknown Publisher: " + publisherName);
                continue;
            }

            publisher.publishNewEvent(message);
        }
    }
}