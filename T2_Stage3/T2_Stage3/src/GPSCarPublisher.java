
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Publicador especializado para simular el movimiento de un vehículo mediante GPS.
 * Lee posiciones desde un archivo y publica coordenadas interpoladas periódicamente.
 */
public class GPSCarPublisher extends Publisher {
    private List<Position> positions = new ArrayList<>();
    private Timeline timeline;
    private double currentTime = 0;

    /**
     * Crea un nuevo publicador GPS.
     * @param name Nombre identificador
     * @param broker Broker central
     * @param topicName Tópico de publicación
     */
    public GPSCarPublisher(String name, Broker broker, String topicName) {
        super(name, broker, topicName);
        loadPositions();
    }

    /**
     * Carga las posiciones desde un archivo seleccionado por el usuario.
     * Formato esperado: tiempo x y (por línea)
     */
    private void loadPositions() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo de posiciones");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));

        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\s+");
                    double time = Double.parseDouble(parts[0]);
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    positions.add(new Position(time, x, y));
                }
            }
            startSimulation();
        } catch (FileNotFoundException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        }
    }

    /**
     * Inicia la simulación de movimiento con actualizaciones cada segundo.
     */
    private void startSimulation() {
        if (positions.isEmpty()) return;

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    currentTime += 1;
                    Position interpolated = interpolatePosition(currentTime);
                    publishNewEvent(interpolated.x + "," + interpolated.y);
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Calcula la posición interpolada para un tiempo dado.
     * @param currentTime Tiempo actual de simulación
     * @return Posición interpolada (x,y)
     */
    private Position interpolatePosition(double currentTime) {
        Position prev = positions.get(0);
        Position next = positions.get(0);

        for (int i = 1; i < positions.size(); i++) {
            if (positions.get(i).time >= currentTime) {
                next = positions.get(i);
                break;
            }
            prev = positions.get(i);
        }

        if (prev.time == next.time) {
            return next;
        }

        double ratio = (currentTime - prev.time) / (next.time - prev.time);
        double x = prev.x + (next.x - prev.x) * ratio;
        double y = prev.y + (next.y - prev.y) * ratio;

        return new Position(currentTime, x, y);
    }

    /**
     * Detiene la simulación del movimiento.
     */
    public void stopSimulation() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}

/**
 * Representa una posición en el tiempo con coordenadas x,y.
 */
class Position {
    double time;
    double x;
    double y;

    /**
     * Crea una nueva posición.
     * @param time Tiempo en segundos
     * @param x Coordenada X
     * @param y Coordenada Y
     */
    public Position(double time, double x, double y) {
        this.time = time;
        this.x = x;
        this.y = y;
    }
}
