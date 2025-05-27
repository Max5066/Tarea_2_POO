import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class T1Stage2 {
   public static void main(String args[]) {
      if (args.length != 1) {
         System.out.println("Usage: java T1Stage2 <configurationFile.txt>");
         System.exit(-1);
      }

      try {
         Scanner configFile = new Scanner(new File(args[0]));
         T1Stage2 stage = new T1Stage2();
         stage.setupSimulator(configFile);
         stage.runSimulation();
      } catch (FileNotFoundException e) {
         System.err.println("Error: No se pudo encontrar el archivo de configuración: " + args[0]);
         System.exit(-1);
      }
   }

   public void setupSimulator(Scanner configFile) {
      Broker broker = new Broker();

      // Leer configuración del publicador (GPS)
      if (!configFile.hasNext()) {
         System.err.println("Error: Archivo de configuración incompleto - falta publicador");
         System.exit(-1);
      }

      String componentType = configFile.next();
      if (!componentType.equals("publicador")) {
         System.err.println("Error: Primera línea debe ser un publicador");
         System.exit(-1);
      }

      String publisherName = configFile.next();
      String topicName = configFile.next();
      gps = new Publisher(publisherName, broker, topicName);

      // Leer configuración del suscriptor (Registrador)
      if (!configFile.hasNext()) {
         System.err.println("Error: Archivo de configuración incompleto - falta suscriptor");
         System.exit(-1);
      }

      componentType = configFile.next();
      if (!componentType.equals("suscriptor")) {
         System.err.println("Error: Segunda línea debe ser un suscriptor");
         System.exit(-1);
      }

      String subscriberType = configFile.next();
      String subscriberName = configFile.next();
      String subscriberTopic = configFile.next();
      String outputFileName = configFile.next();

      if (!subscriberType.equals("Registrador")) {
         System.err.println("Error: En esta etapa solo se soporta suscriptor tipo Registrador");
         System.exit(-1);
      }

      try {
         Recorder recorder = new Recorder(subscriberName, subscriberTopic, new PrintStream(outputFileName));
         if (!broker.subscribe(recorder)) {
            System.err.println("Error: No se pudo suscribir el Registrador al tópico");
            System.exit(-1);
         }
      } catch (FileNotFoundException e) {
         System.err.println("Error: No se pudo crear el archivo de salida: " + outputFileName);
         System.exit(-1);
      }
   }

   public void runSimulation() {
      Scanner inputEvent = new Scanner(System.in);
      System.out.println("Sistema listo. Ingrese coordenadas (x y):");

      while (inputEvent.hasNextLine()) {
         String message = inputEvent.nextLine();
         gps.publishNewEvent(message);
      }
   }

   private Publisher gps;
}