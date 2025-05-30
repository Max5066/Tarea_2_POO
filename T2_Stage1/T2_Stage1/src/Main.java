import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear un botón
        Button btn = new Button();
        btn.setText("¡Haz clic aquí!");
        btn.setOnAction(event -> System.out.println("JavaFX funciona correctamente ✅"));

        // Diseño básico (StackPane)
        StackPane root = new StackPane();
        root.getChildren().add(btn);

        // Configurar la escena y la ventana
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Prueba JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Método que inicia JavaFX
    }
}