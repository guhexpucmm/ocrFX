package edu.pucmm.mineriadedatos2017.alerta;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class Alerta extends Alert {
    public Alerta(String titulo, String contenido, AlertType alertType) {
        super(alertType);
        setTitle(titulo);
        setHeaderText("");
        setContentText(contenido);

        getDialogPane().getStylesheets().add("/css/main.css");

        ButtonType buttonTypeCancel = new ButtonType("Cerrar", ButtonBar.ButtonData.CANCEL_CLOSE);

        getButtonTypes().setAll(buttonTypeCancel);
    }
}
