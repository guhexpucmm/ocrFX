package edu.pucmm.mineriadedatos2017.controladora;

import edu.pucmm.mineriadedatos2017.alerta.Alerta;
import edu.pucmm.mineriadedatos2017.componentes.Seccion;
import edu.pucmm.mineriadedatos2017.data.LeerEscribirArchivos;
import edu.pucmm.mineriadedatos2017.util.EscenaUtil;
import edu.pucmm.mineriadedatos2017.util.LetraUtil;
import edu.pucmm.mineriadedatos2017.weka.AlgoritmoJ48;
import edu.pucmm.mineriadedatos2017.weka.AlgoritmoNaiveBayes;
import edu.pucmm.mineriadedatos2017.weka.AlgoritmoSMO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PrefixSelectionComboBox;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PantallaPrincipalControladora implements Initializable {

    private final String ENTRENAR_COMO = "Entrenar como letra ";

    private final KeyCombination combinacionPredecir = new KeyCodeCombination(KeyCode.SPACE, KeyCombination.CONTROL_DOWN);
    private final KeyCombination combinacionEntrenarComo = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN);
    private final KeyCombination combinacionBorrar = new KeyCodeCombination(KeyCode.BACK_SPACE, KeyCombination.CONTROL_DOWN);

    private ArrayList<Seccion> secciones;
    private Rectangle[][] rec;

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox vBox;

    @FXML
    private ImageView imageView;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Pane panelDibujo;

    @FXML
    private HBox hBox;

    @FXML
    private Button btnPredecir;

    @FXML
    private Button btnBorrar;

    @FXML
    private PrefixSelectionComboBox<String> comboBoxLetra;

    @FXML
    private Button btnEntrenarComo;

    @FXML
    private Button btnEstadisticas;

    public PantallaPrincipalControladora() {
        secciones = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCampos();
        setRectangulo();
        setEventosRectangulo();
        setShortcuts();
        setFocus();
    }

    @FXML
    void btnBorrarClick(ActionEvent event) {
        borrar();
    }

    @FXML
    void btnEntrenarComoLetraClick(ActionEvent event) {
        entrenarComoLetra();
    }

    @FXML
    void btnPredecirClick(ActionEvent event) {
        predecir();
    }

    @FXML
    void comboBoxEntrenarComoLetraClick(ActionEvent event) {
        comboBoxSeleccion();
    }

    @FXML
    void btnVerEstadisticasClick(ActionEvent event) {
        verEstadisticas();
    }

    //Funcion para configurar los accesos directos con teclas del teclado
    private void setShortcuts() {
        stackPane.setOnKeyPressed(event -> {
            if (combinacionEntrenarComo.match(event)) {
                btnEntrenarComo.fire();
            } else if (combinacionPredecir.match(event)) {
                btnPredecir.fire();
            } else if (combinacionBorrar.match(event)) {
                btnBorrar.fire();
            }
        });
    }

    //Funcion para hacer que el vbox coja focus desde que inicie el programa
    private void setFocus() {
        vBox.requestFocus();
    }

    //Funcion para que la ventana tenga los campos por defectos definidos
    private void setCampos() {
        imageView.setImage(new Image("/fotos/ocrfx.png"));
        comboBoxLetra.setItems(LetraUtil.letras);
        comboBoxLetra.getSelectionModel().select(0);
        btnEntrenarComo.setText(ENTRENAR_COMO + comboBoxLetra.getValue());
    }

    //Funcion para hacer el grid en el panel de dibujo, los cuadrados
    private void setRectangulo() {
        double width = 20;
        int n = 20;

        rec = new Rectangle[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rec[i][j] = new Rectangle();
                rec[i][j].setX(i * width);
                rec[i][j].setY(j * width);
                rec[i][j].setWidth(width);
                rec[i][j].setHeight(width);
                rec[i][j].setFill(null);
                rec[i][j].setStroke(Color.BLACK);
                panelDibujo.getChildren().add(rec[i][j]);
                secciones.add(new Seccion((int) rec[i][j].getX(), (int) rec[i][j].getY(), (int) rec[i][j].getWidth(), (int) rec[i][j].getHeight()));
            }
        }
    }

    //Funcion para hacer los eventos del rectangulo, cuando se da click y cuando se arrastra el click seleccionado
    private void setEventosRectangulo() {
        panelDibujo.setOnMouseClicked(event -> {
            if (event.isPrimaryButtonDown() && isInside(event)) {
                double posX = event.getX();
                double posY = event.getY();

                int colX = (int) (posX / 20);
                int colY = (int) (posY / 20);

                rec[colX][colY].setFill(Color.BLACK);

                for (Seccion s : secciones) {
                    if (event.getX() > s.getX() && event.getX() < s.getX() + s.getW() && event.getY() > s.getY() && event.getY() < s.getY() + s.getH()) {
                        s.setActivo(true);
                    }
                }
            }
            if (event.isSecondaryButtonDown() && isInside(event)) {
                double posX = event.getX();
                double posY = event.getY();

                int colX = (int) (posX / 20);
                int colY = (int) (posY / 20);

                rec[colX][colY].setFill(null);

                for (Seccion s : secciones) {
                    if (event.getX() > s.getX() && event.getX() < s.getX() + s.getW() && event.getY() > s.getY() && event.getY() < s.getY() + s.getH()) {
                        s.setActivo(false);
                    }
                }
            }
        });

        panelDibujo.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown() && isInside(event)) {
                double posX = event.getX();
                double posY = event.getY();

                int colX = (int) (posX / 20);
                int colY = (int) (posY / 20);

                rec[colX][colY].setFill(Color.BLACK);

                for (Seccion s : secciones) {
                    if (event.getX() > s.getX() && event.getX() < s.getX() + s.getW() && event.getY() > s.getY() && event.getY() < s.getY() + s.getH()) {
                        s.setActivo(true);
                    }
                }
            }
            if (event.isSecondaryButtonDown() && isInside(event)) {
                double posX = event.getX();
                double posY = event.getY();

                int colX = (int) (posX / 20);
                int colY = (int) (posY / 20);

                rec[colX][colY].setFill(null);

                for (Seccion s : secciones) {
                    if (event.getX() > s.getX() && event.getX() < s.getX() + s.getW() && event.getY() > s.getY() && event.getY() < s.getY() + s.getH()) {
                        s.setActivo(false);
                    }
                }
            }
        });
    }

    //Funcion para asegurarse que el click o arrastre que se haga sea dentro del panel y no fuera
    private boolean isInside(MouseEvent event) {
        if (event.getX() >= 0 && event.getX() <= panelDibujo.getWidth() && event.getY() >= 0 && event.getY() <= panelDibujo.getHeight())
            return true;
        else
            return false;
    }

    //Funcion para cojer los pixeles o los cuadros negros que fueron dibujados para formar el arreglo de 0s y 1s
    private ArrayList<Integer> getPixeles() {
        ArrayList<Integer> pixeles = new ArrayList<>();

        for (Seccion seccion : secciones) {
            if (seccion.isActivo())
                pixeles.add(1);
            else
                pixeles.add(0);
        }

        return pixeles;
    }

    //Funcion para borrar todos los cuadros
    private void limpiar() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                rec[i][j].setFill(null);
            }
        }

        for (Seccion seccion : secciones) {
            seccion.setActivo(false);
        }
    }

    //Funcion para verificar si el panel de dibujo esta vacio completamente
    private boolean isVacio() {
        int cont = 0;

        for (Seccion seccion : secciones) {
            if (seccion.isActivo() == false)
                cont++;
        }

        if (cont == secciones.size())
            return true;
        else
            return false;
    }

    //Funcion para borrar el panel de dibujo
    private void borrar() {
        limpiar();
    }

    //Funcion para entrenar una letra
    private void entrenarComoLetra() {
        if (!isVacio()) {
            String letra = comboBoxLetra.getValue();

            LeerEscribirArchivos.getInstancia().guardarEnArchivo(getPixeles(), letra);

            new Alerta("Letra entrenada", "La letra fue entrenada como " + comboBoxLetra.getValue(), Alert.AlertType.CONFIRMATION).showAndWait();
        } else
            new Alerta("Error!", "No hay nada dibujado.", Alert.AlertType.ERROR).showAndWait();

        limpiar();
    }

    //Funcion para predecir una letra
    private void predecir() {
        if (!isVacio()) {
            stackPane.setCursor(Cursor.WAIT);
            AlgoritmoNaiveBayes.getInstancia().ejecutarAlgortimo(getPixeles());
            stackPane.setCursor(Cursor.DEFAULT);
        } else
            new Alerta("Error!", "No hay nada dibujado.", Alert.AlertType.ERROR).showAndWait();
    }

    //Funcion para cojer la seleccion del combobox
    private void comboBoxSeleccion() {
        btnEntrenarComo.setText(ENTRENAR_COMO + comboBoxLetra.getValue());
    }

    //Funcion para poner una imagen de background en el stackpane
    private void setBackground() {
        Image image = new Image("/fotos/fondo_principal.png");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        stackPane.setBackground(new Background(backgroundImage));
    }

    //Funcion para ver estadisticas del modelo predecido
    private void verEstadisticas() {
        Stage stage = new Stage();
        stage.setScene(new EscenaUtil("/vista/Estadisticas.fxml").getEscena());
        stage.setTitle("Visualizacion de estadisticas");
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMaximized(true);
        stage.getIcons().add(new Image("/fotos/ocrfx_icon.png"));
        stage.toFront();
        stage.show();
    }
}
