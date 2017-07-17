package edu.pucmm.mineriadedatos2017.util;

import javafx.scene.Scene;

import java.io.IOException;

public class EscenaUtil extends FXMLUtil{

    public EscenaUtil(String path) {
        super(path);
    }

    public Scene getEscena() {
        try {
            Scene scene = new Scene(getArchivoFXML().load());

            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
