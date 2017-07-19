package edu.pucmm.mineriadedatos2017;

import edu.pucmm.mineriadedatos2017.weka.AlgoritmoNaiveBayes;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class MainPreloader extends Preloader{

    private Stage stage;
    private ProgressBar progressBar;
    private Scene scene;
    private Task task;

    public MainPreloader() {
        super();

        task = new Task() {
            @Override
            protected Object call() throws Exception {
                AlgoritmoNaiveBayes.getInstancia();

                return null;
            }
        };

        task.run();
    }

    @Override
    public void init() throws Exception {
        super.init();
        Platform.runLater(() -> {
            scene = getEscena();
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void handleProgressNotification(ProgressNotification info) {
        super.handleProgressNotification(info);

        if(info instanceof ProgressNotification)
            progressBar.setProgress(info.getProgress());
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        super.handleStateChangeNotification(info);
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_LOAD:

                break;
            case BEFORE_INIT:

                break;
            case BEFORE_START:
                if (task.isDone())
                    stage.hide();
                break;
        }
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        super.handleApplicationNotification(info);
    }

    @Override
    public boolean handleErrorNotification(ErrorNotification info) {
        return super.handleErrorNotification(info);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setScene(scene);
        stage.setTitle("Cargando...");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setMinWidth(300);
        stage.setMinHeight(170);
        stage.toFront();
        stage.show();
    }

    private Scene getEscena() {
        VBox vBox = new VBox();
        vBox.setMinWidth(300);
        vBox.setMinHeight(170);
        vBox.setPrefWidth(300);
        vBox.setPrefHeight(170);
        vBox.setStyle("-fx-background-color: transparent");

        ImageView imageView = new ImageView(new Image("/fotos/ocrfx.png"));
        imageView.setFitWidth(300);
        imageView.setFitHeight(150);

        progressBar = new ProgressBar(0);
        progressBar.setMinWidth(300);
        progressBar.setMinHeight(20);
        progressBar.setPrefWidth(300);
        progressBar.setPrefHeight(20);

        vBox.getChildren().addAll(imageView, progressBar);

        Parent root = vBox;

        Scene scene = new Scene(root);

        return scene;
    }

    private void cargarAlgoritmos() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
