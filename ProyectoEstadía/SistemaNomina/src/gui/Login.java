/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import static java.time.Clock.system;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.JOptionPane;

/**
 *
 * @author Daniel Almaguer
 */
public class Login extends Application {

    FXMLLoader fxmll;
    Scene scene;
    Stage window;
    @FXML ImageView imgSalir;
    @FXML
    JFXPasswordField txtContrasenia;
    @FXML
    JFXTextField txtUsuario;
    @FXML
    JFXButton btnIngresar;

    WindowMain windowMain;
    @FXML
    BorderPane pnlContenedorPrincipal;

    public Login() {
        fxmll = new FXMLLoader(System.class.getResource("/fxml/login.fxml"));
        fxmll.setController(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmll.load();
        //Creamos una nueva escena utilizando como contenedor principal la raiz (El panel de mas alto nivel) de la GUI
        scene = new Scene(fxmll.getRoot());

        agregarOyentes();
        //Guardamos la ventana que nos pasa la JVM
        window = primaryStage;
        window.getIcons().add(new Image("/resources/logo.png"));

        //Creamos los mÃ³dulos de la aplicaciÃ³n
        windowMain = new WindowMain(primaryStage);

        //Inicializamos los mÃ³dulos que hemos creado
        try {

            windowMain.inicializar();
        } catch (Exception e) {
            //Si ocurre un error, imprimimos la excepciÃ³n y
            //nos salimos de la aplicaciÃ³n

            e.printStackTrace();

        }

        //Establecemos las propiedades de la ventana       
        window.setScene(scene);{
        
    }
        window.setTitle("SISTEMA-HGAL");
        //Mostramos la ventama
        window.show();
    }

    private void agregarOyentes() {
        btnIngresar.setOnAction(evt -> {
            logear();
        });
    imgSalir.setOnMouseClicked(evt -> {
        mostrarMensaje("SALIR.",
                    "Saliendo del sistema", Alert.AlertType.WARNING);
            System.exit(0);
        });
    }

    public void logear() {
        if ("1".equals(txtContrasenia.getText()) && "1".equals(txtUsuario.getText())) {

            mostrarMensaje("BIENVENIDO.",
                    "Cargando módulos", Alert.AlertType.INFORMATION);
            cargarModulo(windowMain.panelRaiz);
        } else {
            mostrarMensaje("Error al iniciar Sesión.",
                    "Usuario y/o Contraseñas incorrectos.",
                    Alert.AlertType.ERROR);

        }
    }

    private void cargarModulo(Node n) {
        pnlContenedorPrincipal.setCenter(n);
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipoMensaje) {
        //Creamos una nueva alerta:
        Alert msg = new Alert(tipoMensaje);

        //Establecemos el título de la ventana del mensaje:
        msg.setTitle(titulo);

        //Establecemos el contenido de la ventana del mensaje:
        msg.setContentText(mensaje);

        //Establecemos el tipo de la ventana del mensaje como MODAL.
        //Esto significa que el programa no avanza hasta que el usuario realiza
        //una acción que cierra la ventana:
        msg.initModality(Modality.WINDOW_MODAL);

        //Establecemos la ventana "Padre" de la ventana del mensaje:
        msg.initOwner(window);

        //Mostramos la ventana del mensaje y esperamos:
        msg.showAndWait();
    }
}
