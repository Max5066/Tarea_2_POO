
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VideoPublisher extends Publisher {
    private HBox view;
    private TextField message;

    public VideoPublisher(String name, Broker broker, String topicName) {
        super(name, broker, topicName);
        setupUI();
    }

    private void setupUI() {
        // Crear elementos de la UI
        Label titleLabel = new Label("Video Publisher: " + getName());
        message = new TextField();
        message.setPromptText("Enter video URL");

        // Configurar acción al presionar Enter
        message.setOnAction(e -> {
            publishNewEvent(message.getText());
            message.clear();
        });

        // Organizar en layout
        VBox container = new VBox(5, titleLabel, message);
        view = new HBox(container);
        view.setStyle("-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-radius: 5;");
    }

    public HBox getView() {
        return view;
    }
}