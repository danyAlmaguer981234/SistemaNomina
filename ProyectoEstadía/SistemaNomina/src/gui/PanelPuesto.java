/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
 * @author lider
 */
public class PanelPuesto {
    Stage window;
    
    //El Panel Raiz:
    @FXML AnchorPane panelRaiz;
    @FXML TextField txtRef;
    @FXML JFXTextField txtIdPuesto;
    @FXML JFXTextField txtNombrePuesto;
    @FXML JFXComboBox cmbDepartamento;
   @FXML JFXCheckBox chbActivo;
   
    
    @FXML Button btnGuardar;
    @FXML Button btnNuevo;
    @FXML Button btnConsultar;
    @FXML Button btnEliminar;
    @FXML Button btnBuscar;
    
    
    @FXML TableView tblPuestos;
    ControllerPuesto cp;
  
    
    
    
    FXMLLoader fxmll;
    public PanelPuesto(Stage ventanaApp) {
        window = ventanaApp;
        
        //Le indicamos al FXLLoader donde está el recurso que cargará:
        fxmll = new FXMLLoader(System.class.getResource("/fxml/panel_puesto.fxml"));
        
        //Le indicamos al FXMLLoader que esta clase será su clase controladora:
        fxmll.setController(this);
    }
    /**
     * Devuelve el panel raiz de este componente.
     * 
     * El panel raiz es el componente visual que contiene todos los controles
     * visuales que se requieren para el módulo de control de clientes.
     * @return 
     */
    public AnchorPane getPanelRaiz() {
        return panelRaiz;
    }
    
    /**
     * Este método inicializa el módulo de empleados.
     * @throws Exception 
     */
    public void inicializar() throws Exception {       
        //Cargamos el archivo FXML:
        fxmll.load();
        
        //Instanciamos el controlador:
        cp = new ControllerPuesto();       
         TableAdapterPuesto.adapt(tblPuestos);
        //Agregamos los oyentes de los controles:
           agregarOyentes();
           agregarOpcionesDepartamento();
           
        
         
    }
     private void agregarOyentes() {
        //Agregamos el oyente a los botones:
        
     
        btnGuardar.setOnAction(evt -> { guardarPuesto(); });
        btnConsultar.setOnAction(evt -> { consultarPuestos(); });
        btnBuscar.setOnAction(evt -> { buscarPuesto(); });
        btnEliminar.setOnAction(evt -> { eliminarPuesto(); });
        btnNuevo.setOnAction(evt -> { limpiarPanel(); });
        tblPuestos.getSelectionModel().selectedItemProperty().addListener(evt -> {mostrarDetallePuesto();} );
       cmbDepartamento.setOnMouseClicked(evt -> { agregarOpcionesDepartamento(); } );
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
     private void mostrarDetallePuesto() {
        
        Puesto p = (Puesto) tblPuestos.getSelectionModel().getSelectedItem();
        
        if(p != null){
            
            txtIdPuesto.setText("" + p.getId());
            txtNombrePuesto.setText("" + p.getNombrePuesto());
           
            cmbDepartamento.getSelectionModel().select(p.getDepartamento());
            if(p.getEstatus() == 1){
                
                chbActivo.setSelected(true);
            }else{
                
                chbActivo.setSelected(false);
            }
        } 
    }
     private void eliminarPuesto() {
        
        Puesto p = new Puesto();
        
        try{
            if(! txtIdPuesto.getText().isEmpty()){
                p.setId(Integer.valueOf(txtIdPuesto.getText()));
                p.setNombrePuesto(txtNombrePuesto.getText());
               
             
                
                //s.setSucursal;
                
                if(chbActivo.isSelected()){
                    p.setEstatus(1);
                }else{
                    p.setEstatus(0);
                }
                
                cp.delete(p.getId());
                p = (Puesto) tblPuestos.getSelectionModel().getSelectedItem();
                tblPuestos.getItems().remove(p);
                limpiarPanel();
                mostrarMensaje( "Movimiento realizado.", 
                            "Datos de Puesto eliminados correctamente.", 
                            Alert.AlertType.CONFIRMATION);
                consultarPuestos();
            
            }
        }catch(Exception e){
            e.printStackTrace();
            mostrarMensaje( "Error al eliminar datos.", 
                            "Ocurrió el siguiente error: " + e.toString(),
                            Alert.AlertType.ERROR);
        }
    consultarPuestos();
    }
      private void limpiarPanel() {
        
        txtNombrePuesto.setText("");
     
        txtIdPuesto.setText("");
       
        chbActivo.setSelected(false);
        cmbDepartamento.getSelectionModel().clearSelection();
    }
    
    private void guardarPuesto() {
        //Creamos un nuevo objeto de tipo Persona:
        Puesto p = new Puesto();
        //Creamos un nuevo objeto de tipo Usuario:
        Departamento d = new Departamento();
        //Creamos un nuevo objeto de tipo Cliente:
       int idGenerado = 0;
       
        
        try{
            //p.setEstatus(chbActivo.isSelected() ? 1 : 0);
            if(chbActivo.isSelected())
                p.setEstatus(1);
            else
                p.setEstatus(0);
            

            //Si la caja de texto NO esta vacía:
            if(!txtIdPuesto.getText().isEmpty())
                p.setId(Integer.valueOf(txtIdPuesto.getText()));
            

            p.setNombrePuesto(txtNombrePuesto.getText());
          
            p.setDepartamento((Departamento) cmbDepartamento.getValue());
            
            if(p.getId() == 0){
                idGenerado = cp.insert(p);
                txtIdPuesto.setText("" + idGenerado);
                //tblSalas.getItems().add(p);
            }else
                cp.update(p);
            mostrarMensaje( "Movimiento realizado.", 
                            "Datos del puesto guardados correctamente.", 
                            Alert.AlertType.CONFIRMATION);
            
        }catch(Exception e){
            e.printStackTrace();
            mostrarMensaje( "Error al persistir datos en la base de datos.", 
                            "Ocurrió el siguiente error: " + e.toString(),
                            Alert.AlertType.ERROR);
        }    
    
     consultarPuestos();
     limpiarPanel();
    
    }
    
     private void consultarPuestos() {
        
        List<Puesto> listaPuestos = null;
        ObservableList<Puesto> listaObservablePuestos = null;

        try{
            //Consultamos las sucursales a través del
            //ControllerSucursal:
             if(chbActivo.isSelected()){
                      listaPuestos = cp.getAll("",1);
                    }else{
                    listaPuestos = cp.getAll("",0);
                    }    
           
            //Convertimos nuestra lista de sucursales en una 
            //lista Observable de sucursales:
            listaObservablePuestos = FXCollections.observableArrayList(listaPuestos);
            //Ponemos la lista observable dentro del table view
            tblPuestos.setItems(listaObservablePuestos);
        }catch(Exception e){        
            e.printStackTrace();
            mostrarMensaje( "Error", 
                            "Ocurrio el siguiente error: " + e.toString(), 
                            Alert.AlertType.ERROR);
        }    
    }
      private void buscarPuesto() {
        
        Puesto p = new Puesto();
        ObservableList<Puesto> listaObservablePuestos = null;
        
        try{
            int i = Integer.valueOf(txtRef.getText());
            p = cp.findById(i);
            tblPuestos.getItems().clear();
            listaObservablePuestos = FXCollections.observableArrayList(p);
            //Ponemos la lista observable dentro del table view
            tblPuestos.setItems(listaObservablePuestos);
            
        }catch(Exception e){
            e.printStackTrace();
            mostrarMensaje( "Error al buscar datos.", 
                            "Ocurrió el siguiente error: " + e.toString(),
                            Alert.AlertType.ERROR);
        }   
    }
     private void agregarOpcionesDepartamento(){
        ControllerDepartamento cd = new ControllerDepartamento();
        ObservableList<Departamento> listaDepartamento = null;
        List<Departamento> listaDep = null;
        
        try{
            
            listaDep = cd.getAll("",1);
            listaDepartamento = FXCollections.observableArrayList(listaDep);
            cmbDepartamento.setItems(listaDepartamento);
            
        }catch(Exception e){
            e.printStackTrace();
            mostrarMensaje( "Error al cargar datos.", 
                            "Ocurrió el siguiente error: " + e.toString(),
                            Alert.AlertType.ERROR);
        }
    }
    
    
    
}
    
   
