/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import components.TableAdapterDepartamento;
import components.TableAdapterPuesto;
import controller.ControllerDepartamento;
import controller.ControllerPuesto;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Departamento;
import model.Puesto;

/**
 *
 * @author Cristian
 */
public class PanelDepartamento {

    Stage window;

    //El Panel Raiz:
    @FXML
    AnchorPane panelRaiz;
    @FXML
    TextField txtRef;
    @FXML JFXTextField txtIdDepartamento;
    @FXML JFXTextField txtNombreDep;
    @FXML JFXTextField txtCantidadE;

    @FXML
    JFXCheckBox chbActivo;

    @FXML
    Button btnGuardar;
    @FXML
    Button btnNuevo;
    @FXML
    Button btnConsultar;
    @FXML
    Button btnEliminar;
    @FXML
    Button btnBuscar;

    @FXML
    TableView tblDepartamentos;
    ControllerDepartamento cd;

    FXMLLoader fxmll;

    public PanelDepartamento(Stage ventanaApp) {
        window = ventanaApp;

        //Le indicamos al FXLLoader donde está el recurso que cargará:
        fxmll = new FXMLLoader(System.class.getResource("/fxml/panel_departamento.fxml"));

        //Le indicamos al FXMLLoader que esta clase será su clase controladora:
        fxmll.setController(this);
    }

    /**
     * Devuelve el panel raiz de este componente.
     *
     * El panel raiz es el componente visual que contiene todos los controles
     * visuales que se requieren para el módulo de control de clientes.
     *
     * @return
     */
    public AnchorPane getPanelRaiz() {
        return panelRaiz;
    }

    /**
     * Este método inicializa el módulo de empleados.
     *
     * @throws Exception
     */
    public void inicializar() throws Exception {
        //Cargamos el archivo FXML:
        fxmll.load();

        //Instanciamos el controlador:
        cd = new ControllerDepartamento();
        TableAdapterDepartamento.adapt(tblDepartamentos);
        //Agregamos los oyentes de los controles:
        agregarOyentes();

    }

    private void agregarOyentes() {
        //Agregamos el oyente a los botones:

        btnGuardar.setOnAction(evt -> {
            guardarDepartamentos();
        });
        btnConsultar.setOnAction(evt -> {
            consultarDepartamentos();
        });
        btnBuscar.setOnAction(evt -> {
            buscarDepartamentos();
        });
        btnEliminar.setOnAction(evt -> {
            eliminarDepartamentos();
        });
        btnNuevo.setOnAction(evt -> {
            limpiarPanel();
        });
        tblDepartamentos.getSelectionModel().selectedItemProperty().addListener(evt -> {
            mostrarDetalleDepartamento();
        });

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

    private void mostrarDetalleDepartamento() {

        Departamento d = (Departamento) tblDepartamentos.getSelectionModel().getSelectedItem();

        if (d != null) {

            txtIdDepartamento.setText("" + d.getId());
            txtNombreDep.setText("" + d.getNombreDepartamento());
            txtCantidadE.setText("" + d.getNumeroEmpleados());

            if (d.getEstatus() == 1) {

                chbActivo.setSelected(true);
            } else {

                chbActivo.setSelected(false);
            }
        }
    }

    private void eliminarDepartamentos() {
        Departamento d = new Departamento();

        try {
            if (!txtIdDepartamento.getText().isEmpty()) {
                d.setId(Integer.valueOf(txtIdDepartamento.getText()));
                d.setNombreDepartamento(txtNombreDep.getText());
                d.setNombreDepartamento(txtCantidadE.getText());

                if (chbActivo.isSelected()) {
                    d.setEstatus(1);
                } else {
                    d.setEstatus(0);
                }

                cd.delete(d.getId());
                d = (Departamento) tblDepartamentos.getSelectionModel().getSelectedItem();
                tblDepartamentos.getItems().remove(d);
                limpiarPanel();
                mostrarMensaje("Movimiento realizado.",
                        "Datos deL departamento eliminados correctamente.",
                        Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al eliminar datos.",
                    "Ocurrió el siguiente error: " + e.toString(),
                    Alert.AlertType.ERROR);
        }
        consultarDepartamentos();

    }

    private void limpiarPanel() {

        txtIdDepartamento.setText("");

        txtNombreDep.setText("");
        txtCantidadE.setText("");

        chbActivo.setSelected(false);

    }

    private void guardarDepartamentos() {
        //Creamos un nuevo objeto de tipo Departamento:
        Departamento d = new Departamento();

        int idGenerado = 0;

        try {
            //p.setEstatus(chbActivo.isSelected() ? 1 : 0);
            if (chbActivo.isSelected()) {
                d.setEstatus(1);
            } else {
                d.setEstatus(0);
            }

            //Si la caja de texto NO esta vacía:
            if (!txtIdDepartamento.getText().isEmpty()) {
                d.setId(Integer.valueOf(txtIdDepartamento.getText()));
            }
           
            d.setNombreDepartamento(txtNombreDep.getText());
            d.setNumeroEmpleados(Integer.parseInt(txtCantidadE.getText()));

            if (d.getId() == 0) {
                idGenerado = cd.insert(d);
                txtIdDepartamento.setText("" + idGenerado);
                //tblSalas.getItems().add(p);
            } else {
                cd.update(d);
            }
            mostrarMensaje("Movimiento realizado.",
                    "Datos del departamento guardados correctamente.",
                    Alert.AlertType.CONFIRMATION);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al persistir datos en la base de datos.",
                    "Ocurrió el siguiente error: " + e.toString(),
                    Alert.AlertType.ERROR);
        }
consultarDepartamentos();
limpiarPanel();
    }

    private void consultarDepartamentos() {

        List<Departamento> listaDepartamentos = null;
        ObservableList<Departamento> listaObservableDepartamentos = null;

        try {
            //Consultamos las sucursales a través del
            //ControllerSucursal:
            if (chbActivo.isSelected()) {
                listaDepartamentos = cd.getAll("", 1);
            } else {
                listaDepartamentos = cd.getAll("", 0);
            }

            //Convertimos nuestra lista de sucursales en una 
            //lista Observable de sucursales:
            listaObservableDepartamentos = FXCollections.observableArrayList(listaDepartamentos);
            //Ponemos la lista observable dentro del table view
            tblDepartamentos.setItems(listaObservableDepartamentos);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error",
                    "Ocurrio el siguiente error: " + e.toString(),
                    Alert.AlertType.ERROR);
        }
    }

    private void buscarDepartamentos() {

        Departamento d = new Departamento();
        ObservableList<Departamento> listaObservableDepartamentos = null;

        try {
            int i = Integer.valueOf(txtRef.getText());
            d = cd.findById(i);
            tblDepartamentos.getItems().clear();
            listaObservableDepartamentos = FXCollections.observableArrayList(d);
            //Ponemos la lista observable dentro del table view
            tblDepartamentos.setItems(listaObservableDepartamentos);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al buscar datos.",
                    "Ocurrió el siguiente error: " + e.toString(),
                    Alert.AlertType.ERROR);
        }
    }

   

}
