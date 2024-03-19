import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscene.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setMainWindow(primaryStage);
        
        Scene scene = new Scene(root, Color.web("#cf2323"));

        Text title = new Text();
        title.setText("Help Luffy reach the One Piece!");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(Font.font("Montserrat", 25));

        primaryStage.setTitle("Help Luffy reach the One Piece!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}