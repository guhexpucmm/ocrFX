package edu.pucmm.mineriadedatos2017;

import edu.pucmm.mineriadedatos2017.util.EscenaUtil;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.getIcons().add(new Image("/fotos/ocrfx_icon.png"));
        stage.setTitle("OCRFX");
        stage.setScene(new EscenaUtil("/vista/PantallaPrincipal.fxml").getEscena());
        stage.setMinWidth(1300);
        stage.setMinHeight(700);
        stage.setMaximized(true);
        stage.show();
    }
}
