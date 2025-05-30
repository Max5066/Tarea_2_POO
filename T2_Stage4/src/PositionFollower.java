
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class PositionFollower extends Subscriber {
    private Circle positionDot;
    private Label positionLabel;
    private Stage displayStage;
    private Pane drawingPane;
    private final long creationTime;

    public PositionFollower(String name, String topicName) {
        super(name, topicName);
        this.creationTime = System.currentTimeMillis();
        setupUI();
    }

    private void setupUI() {
        // Configurar el Stage para mostrar la posición
        displayStage = new Stage();
        displayStage.setTitle("GPS Tracker: " + getName());

        drawingPane = new Pane();
        drawingPane.setPrefSize(500, 500);

        positionDot = new Circle(0, 0, 10, Color.RED);
        positionLabel = new Label("Esperando datos...");

        VBox root = new VBox(10, drawingPane, positionLabel);
        displayStage.setScene(new Scene(root, 500, 550));
        displayStage.show();
    }

    @Override
    public void update(String message) {
        Platform.runLater(() -> {
            String[] coords = message.split(",");
            double x = Double.parseDouble(coords[0]);
            double y = Double.parseDouble(coords[1]);

            // Ajustar coordenadas para el sistema gráfico (invertir Y)
            double displayX = x;
            double displayY = 500 - y; // Asumiendo un área de 500x500

            positionDot.setCenterX(displayX);
            positionDot.setCenterY(displayY);

            if (!drawingPane.getChildren().contains(positionDot)) {
                drawingPane.getChildren().add(positionDot);
            }

            positionLabel.setText(String.format("Tiempo: %.1fs | Posición: (%.1f, %.1f)",
                    getCurrentTime(), x, y));
        });
    }

    private double getCurrentTime() {
        // Lógica para obtener el tiempo actual (puede ser mejorada)
        return (System.currentTimeMillis() - creationTime) / 1000.0;
    }
}