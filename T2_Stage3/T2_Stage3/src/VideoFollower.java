
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VideoFollower extends Subscriber {
   private HBox view;
   private Button videoButton;
   private String currentVideoUrl;

   public VideoFollower(String name, String topicName) {
      super(name, topicName);
      setupUI();
   }

   private void setupUI() {
      Label titleLabel = new Label("Follower: " + getName());
      videoButton = new Button("No video available");
      videoButton.setDisable(true);

      // Configurar acción del botón
      videoButton.setOnAction(e -> playVideo());

      VBox container = new VBox(5, titleLabel, videoButton);
      view = new HBox(container);
      view.setStyle("-fx-padding: 10; -fx-border-style: solid inside; " +
              "-fx-border-width: 2; -fx-border-radius: 5;");
   }

   @Override
   public void update(String message) {
      Platform.runLater(() -> {
         currentVideoUrl = message;
         videoButton.setText("Play: " + shortenUrl(message));
         videoButton.setDisable(false);
      });
   }

   private void playVideo() {
      if (currentVideoUrl == null || currentVideoUrl.isEmpty()) return;

      try {
         Media media = new Media(currentVideoUrl);
         MediaPlayer mediaPlayer = new MediaPlayer(media);
         MediaView mediaView = new MediaView(mediaPlayer);

         Stage videoStage = new Stage();
         videoStage.setTitle("Video Player - " + getName());
         videoStage.setScene(new Scene(new HBox(mediaView), 640, 480));

         // Configurar comportamiento al cerrar
         videoStage.setOnCloseRequest(event -> mediaPlayer.dispose());

         mediaPlayer.setOnError(() -> {
            System.err.println("MediaPlayer Error: " + mediaPlayer.getError());
            //new Alert(Alert.AlertType.ERROR,
                    //"Error playing video: " + mediaPlayer.getError().getMessage()).show();
         });

         videoStage.show();
         mediaPlayer.play();
      } catch (Exception e) {
         System.err.println("Error loading media: " + e.getMessage());
         //new Alert(Alert.AlertType.ERROR,
                 //"Invalid video URL or format").show();
      }
   }

   private String shortenUrl(String url) {
      return url.length() > 20 ? url.substring(0, 20) + "..." : url;
   }

   public HBox getView() {
      return view;
   }
}