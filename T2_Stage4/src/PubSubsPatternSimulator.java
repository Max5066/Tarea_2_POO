
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.util.Optional;

public class PubSubsPatternSimulator extends Application {
    private VBox vBoxLeft, vBoxRight;
    private Broker broker;

    public void start(Stage primaryStage) {
        broker = new Broker();

        // Configuración de la interfaz gráfica
        MenuBar menuBar = new MenuBar();
        Menu menuPublisher = new Menu("Publisher");
        Menu menuSubscriber = new Menu("Subscriber");

        MenuItem menuItemVideoPub = new MenuItem("Video");
        MenuItem menuItemGPSPub = new MenuItem("Car's GPS");
        menuPublisher.getItems().addAll(menuItemVideoPub, menuItemGPSPub);

        MenuItem menuItemVideoSubs = new MenuItem("Video");
        MenuItem menuItemGPSSubs = new MenuItem("Car's GPS");
        menuSubscriber.getItems().addAll(menuItemVideoSubs, menuItemGPSSubs);

        menuBar.getMenus().addAll(menuPublisher, menuSubscriber);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        vBoxLeft = new VBox(5);
        vBoxLeft.setAlignment(Pos.TOP_CENTER);
        vBoxLeft.setStyle("-fx-padding: 10;");
        borderPane.setLeft(vBoxLeft);

        vBoxRight = new VBox(5);
        vBoxRight.setAlignment(Pos.TOP_CENTER);
        vBoxRight.setStyle("-fx-padding: 10;");
        borderPane.setRight(vBoxRight);

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("Publisher-Subscriber Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Manejo de eventos
        menuItemVideoPub.setOnAction(e -> addVideoPub());
        menuItemVideoSubs.setOnAction(e -> addVideoSubs());

        menuItemGPSPub.setOnAction(e -> addGPSPublisher());
        menuItemGPSSubs.setOnAction(e -> addGPSSubscriber());
    }

    private String getInputString(String prompt) {
        TextInputDialog dialog = new TextInputDialog("default");
        dialog.setTitle("Input Required");
        dialog.setHeaderText("Please enter " + prompt);
        dialog.setContentText(prompt + ":");

        Optional<String> result = dialog.showAndWait();
        return result.orElse("default");
    }

    private void addVideoPub() {
        String name = getInputString("Publisher Name");
        String topic = getInputString("Topic Name");
        vBoxLeft.getChildren().add(new VideoPublisher(name, broker, topic).getView());
    }

    private void addVideoSubs() {
        String name = getInputString("Subscriber Name");
        String topic = getInputString("Topic to Follow");
        VideoFollower follower = new VideoFollower(name, topic);

        if (broker.subscribe(follower)) {
            vBoxRight.getChildren().add(follower.getView());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Subscription Failed");
            alert.setContentText("Topic '" + topic + "' does not exist!");
            alert.showAndWait();
        }
    }

    // Nuevos métodos para manejar GPS
    private void addGPSPublisher() {
        String name = getInputString("GPS Publisher Name");
        String topic = getInputString("GPS Topic Name");
        GPSCarPublisher gpsPublisher = new GPSCarPublisher(name, broker, topic);
        vBoxLeft.getChildren().add(new Label("GPS Publisher: " + name));
    }

    private void addGPSSubscriber() {
        String name = getInputString("GPS Subscriber Name");
        String topic = getInputString("GPS Topic to Follow");
        PositionFollower follower = new PositionFollower(name, topic);

        if (broker.subscribe(follower)) {
            new Alert(Alert.AlertType.INFORMATION,
                    "Suscriptor GPS creado en ventana separada").show();
        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Error: El tópico no existe").show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}