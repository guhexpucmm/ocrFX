package edu.pucmm.mineriadedatos2017.controladora;

import edu.pucmm.mineriadedatos2017.weka.AlgoritmoNaiveBayes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EstadisticasControladora implements Initializable {

    @FXML
    private VBox vBoxEstadisticas;

    @FXML
    private Label lblEstadisticas;

    @FXML
    private TextArea textArea;

    @FXML
    private HBox hBox;

    @FXML
    private Button btnCerrar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textArea.appendText(AlgoritmoNaiveBayes.getInstancia().estadistica);
    }


    @FXML
    void btnCerrarClick(ActionEvent event) {
        cerrar();
    }

    private void cerrar() {
        Stage stage = ((Stage)btnCerrar.getScene().getWindow());
        stage.close();
    }
}
