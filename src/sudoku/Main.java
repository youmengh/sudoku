package sudoku;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Youmeng Hin
 * @version 21.08.19
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Sudoku (v21.08.19) by Youmeng Hin");
        stage.show();
    }
}

//    Puzzle p = new Puzzle();
//    System.out.println(p);
//    System.out.println(GameLogic.checkCompletion(p.getPuzzleArray()));
