package edu.pucmm.mineriadedatos2017.util;

import javafx.fxml.FXMLLoader;

public class FXMLUtil {
    private String path;

    public FXMLUtil(String path) {
        this.path = path;
    }

    public FXMLLoader getArchivoFXML() {
        return new FXMLLoader(getClass().getResource(this.path));
    }
}
