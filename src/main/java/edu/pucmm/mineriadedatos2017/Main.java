package edu.pucmm.mineriadedatos2017;

import edu.pucmm.mineriadedatos2017.util.EscenaUtil;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/fotos/pucmm-logo.png"));
        stage.setTitle("OCRFX");
        stage.setResizable(false);
        stage.setScene(new EscenaUtil("/vista/PantallaPrincipal.fxml").getEscena());
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
