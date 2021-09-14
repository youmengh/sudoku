package sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Youmeng Hin
 * @version 21.09.14
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets up window and implements interface from FXML.fxml
     *
     * @param stage starting stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sudoku (v21.09.14) by Youmeng Hin");
        stage.show();
    }
}
