
package gui;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import components.TableAdapterEmpleado;
import controller.ControllerDepartamento;
import controller.ControllerEmpleado;
import controller.ControllerPuesto;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Departamento;
import model.Empleado;
import model.Puesto;

/**
 *
 * @author ramir
 */
public class PanelEmpleado
{
    Stage window;
    @FXML TextField txtBuscar;
    @FXML JFXTextField txtId;
    @FXML JFXTextField txtNumeroUnico;
    @FXML JFXTextField txtNombre;
    @FXML JFXTextField txtApellidoPaterno;
    @FXML JFXTextField txtApellidoMaterno;
    @FXML JFXTextField txtSueldoBase;
    @FXML DatePicker dteFecha;
    @FXML ComboBox<Puesto> cmbPuesto;
    @FXML ComboBox<Departamento> cmbDepartamento;
    @FXML JFXCheckBox chbActivo;

    @FXML Button btnGuardar;
    @FXML Button btnNuevo;
    @FXML Button btnConsultar;
    @FXML Button btnEliminar;
    @FXML Button btnBuscar;
    
    @FXML TableView<Empleado> tblEmpleado;
    
    @FXML AnchorPane panelRaiz;
    
    
    FXMLLoader fxmll;
    
    ControllerEmpleado ce ;
    
    public PanelEmpleado(Stage ventanaApp) {
        window = ventanaApp;
        
        //Le indicamos al FXLLoader donde está el recurso que cargará:
        fxmll = new FXMLLoader(System.class.getResource("/fxml/panel_empleado.fxml"));
        
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
        //Primero instanciamos el controlador de empleado:
        ce = new ControllerEmpleado();
       
        //Después cargamos el archivo fxml:
        fxmll.load();
        agregarOpcionesDepartamento();
        agregarOpcionesPuesto();
         TableAdapterEmpleado.adapt(tblEmpleado);
        //Agregamos oyentes:
        agregarOyentes();
    }
    
    private void guardarEmpleado() {
        
        int idGenerado = 0;
        Empleado e = new Empleado();
        
        try{
            if(chbActivo.isSelected())
                e.setEstatus(1);
            else
                e.setEstatus(0);
            

            //Si la caja de texto NO esta vacía:
            if(!txtId.getText().isEmpty())
                e.setId(Integer.valueOf(txtId.getText()));
                e.setNumeroUnico(txtNumeroUnico.getText());
                e.setNombre(txtNombre.getText());
                e.setApellidoPaterno(txtApellidoPaterno.getText());
                e.setApellidoMaterno(txtApellidoMaterno.getText());
                e.setFechaIngreso(dteFecha.getValue() + "");
                e.setSueldoBase(Double.valueOf(txtSueldoBase.getText()));
                e.setPuesto(cmbPuesto.getValue());
                e.setDepartamento(cmbDepartamento.getValue());
            
            if(e.getId() == 0){
                idGenerado = ce.insert(e);
                txtId.setText("" + idGenerado);
                tblEmpleado.getItems().add(e);
            }else
                ce.update(e);
            consultarEmpleado();
            mostrarMensaje( "Movimiento realizado.", 
                            "Datos del empleado guardados correctamente.", 
                            Alert.AlertType.CONFIRMATION);
            
        }catch(Exception ex){
            ex.printStackTrace();
            mostrarMensaje( "Error al persistir datos.", 
                            "Ocurrió el siguiente error: " + ex.toString(),
                            Alert.AlertType.ERROR);
        }    
    }
    
    
    private void consultarEmpleado() {
        
        List<Empleado> listaEmpleados= null;
        ObservableList<Empleado> listaObservableEmpleados = null;

        try{
            //Consultamos los puestos a través del
            //ControllerEmpleado:
               listaEmpleados = ce.getAll("");
                    
           
            //Convertimos nuestra lista de sucursales en una 
            //lista Observable de empleados:
            listaObservableEmpleados = FXCollections.observableArrayList(listaEmpleados);
            //Ponemos la lista observable dentro del table view
            tblEmpleado.setItems(listaObservableEmpleados);
        }catch(Exception ex){        
            ex.printStackTrace();
            mostrarMensaje( "Error", 
                            "Ocurrio el siguiente error: " + ex.toString(), 
                            Alert.AlertType.ERROR);
        }    
    }
    
    private void mostrarDetalleEmpleado() {
        
        Empleado e = tblEmpleado.getSelectionModel().getSelectedItem();
        
        if(e != null){
            
            txtId.setText("" + e.getId());
            txtNumeroUnico.setText("" + e.getNumeroUnico());
            txtNombre.setText("" + e.getNombre());
            txtApellidoPaterno.setText("" + e.getApellidoPaterno());
            txtApellidoMaterno.setText("" + e.getApellidoMaterno());
            txtSueldoBase.setText("" + e.getSueldoBase());
            cmbPuesto.getSelectionModel().select(e.getPuesto());
            cmbDepartamento.getSelectionModel().select(e.getDepartamento());
            
            if(e.getEstatus() == 1){
                
                chbActivo.setSelected(true);
            }else{
                
                chbActivo.setSelected(false);
            }
        } 
    }
    
    private void eliminarEmpleado() {
        
        Empleado e = new Empleado();
        
        try{
            if(! txtId.getText().isEmpty()){
                e.setId(Integer.valueOf(txtId.getText()));

                
                ce.delete(e.getId());
                e = tblEmpleado.getSelectionModel().getSelectedItem();
                tblEmpleado.getItems().remove(e);
                limpiarCampos();
                mostrarMensaje( "Movimiento realizado.", 
                            "Datos del empleado eliminados correctamente.", 
                            Alert.AlertType.CONFIRMATION);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            mostrarMensaje( "Error al eliminar datos.", 
                            "Ocurrió el siguiente error: " + ex.toString(),
                            Alert.AlertType.ERROR);
        }
    
    }
    
    private void limpiarCampos() {
        
        txtId.setText("");
        txtNumeroUnico.setText("");
        txtNombre.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        dteFecha.getEditor().clear();
        dteFecha.setValue(null);
        txtSueldoBase.setText("");
        chbActivo.setSelected(false);
        cmbPuesto.getSelectionModel().clearSelection();
        cmbPuesto.setValue(null);
        cmbDepartamento.getSelectionModel().clearSelection();
        cmbDepartamento.setValue(null);
    }
    
    private void buscarEmpleado() {
        
        Empleado e = new Empleado();
        ObservableList<Empleado> listaObservableEmpleados = null;
        
        try{
            int i = Integer.valueOf(txtBuscar.getText());
            e = ce.findById(i);
            tblEmpleado.getItems().clear();
            listaObservableEmpleados = FXCollections.observableArrayList(e);
            //Ponemos la lista observable dentro del table view
            tblEmpleado.setItems(listaObservableEmpleados);
            
        }catch(Exception ex){
            ex.printStackTrace();
            mostrarMensaje( "Error al buscar datos.", 
                            "Ocurrió el siguiente error: " + ex.toString(),
                            Alert.AlertType.ERROR);
        }   
    }

    /**
     * Este método es para mostrar un mensaje modal.
     * @param titulo        El título de la ventana.
     * @param mensaje       El contenido del mensaje.
     * @param tipoMensaje   El tipo de mensaje.
     */
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipoMensaje){
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
    
    public void agregarOyentes(){
        btnGuardar.setOnAction(evt -> { guardarEmpleado(); });
        btnEliminar.setOnAction(evt -> { eliminarEmpleado(); });
        btnNuevo.setOnAction(evt -> { limpiarCampos(); });
        btnConsultar.setOnAction(evt -> { consultarEmpleado(); });
        btnBuscar.setOnAction(evt -> { buscarEmpleado(); });
        
        tblEmpleado.getSelectionModel().selectedItemProperty().addListener(evt -> {mostrarDetalleEmpleado();} );
        cmbDepartamento.setOnMouseClicked(evt -> {agregarOpcionesDepartamento();} );
        cmbPuesto.setOnMouseClicked(evt -> {agregarOpcionesPuesto();} );

    }
    
    
       private void agregarOpcionesDepartamento(){
        ControllerDepartamento cd = new ControllerDepartamento();
        ObservableList<Departamento> listaDepartamento = null;
        List<Departamento> listaDe = null;
        
        try{
            
            listaDe = cd.getAll("",1);
            listaDepartamento = FXCollections.observableArrayList(listaDe);
            cmbDepartamento.setItems(listaDepartamento);
            
        }catch(Exception ex){
            ex.printStackTrace();
            mostrarMensaje( "Error al cargar datos.", 
                            "Ocurrió el siguiente error: " + ex.toString(),
                            Alert.AlertType.ERROR);
        }
    }
             
       
       private void agregarOpcionesPuesto(){
        ControllerPuesto cp = new ControllerPuesto();
        ObservableList<Puesto> listaPuestos = null;
        List<Puesto> listaPu = null;
        
        try{
            
            listaPu = cp.getAll("",1);
            listaPuestos = FXCollections.observableArrayList(listaPu);
            cmbPuesto.setItems(listaPuestos);
            
        }catch(Exception ex){
            ex.printStackTrace();
            mostrarMensaje( "Error al cargar datos.", 
                            "Ocurrió el siguiente error: " + ex.toString(),
                            Alert.AlertType.ERROR);
        }
    }
}

    
   
