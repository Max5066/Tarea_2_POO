import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class VideoFollower extends Subscriber {
    private HBox view;
    private Button videoButton;

    public VideoFollower(String name, String topicName) {
        super(name, topicName);
        setupUI();
    }

    private void setupUI() {
        // Crear elementos de la UI
        Label titleLabel = new Label("Follower: " + getName());
        videoButton = new Button("No video available");
        videoButton.setDisable(true);

        // Organizar en layout
        VBox container = new VBox(5, titleLabel, videoButton);
        view = new HBox(container);
        view.setStyle("-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-radius: 5;");
    }

    @Override
    public void update(String message) {
        Platform.runLater(() -> {
            videoButton.setText("Play: " + shortenUrl(message));
            videoButton.setDisable(false);
        });
    }

    private String shortenUrl(String url) {
        return url.length() > 20 ? url.substring(0, 20) + "..." : url;
    }

    public HBox getView() {
        return view;
    }
}