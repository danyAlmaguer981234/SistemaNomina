/*
 * Version:     1.0
 * Date:        18/06/2018 23:16:00
 * Author:      Jocelyn Vanessa Ortega Torres
 * Email:       ortegatorresjocelynvanessa@gmail.com
 * Comments:    Esta clase contiene los métodos necesarios para mantener el
 *              manejo de la interfaz visual.
 */
package gui;


import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.JOptionPane;


public class WindowMain {
    
    
    @FXML Button btnCargarModuloPuesto;
    @FXML Button btnCargarModuloDep;
    @FXML Button btnCargarModuloEmpleados;
   
    
    @FXML BorderPane pnlContenedorPrincipal1;
      @FXML AnchorPane panelRaiz;

 
    FXMLLoader fxmll;
    Scene scene;
    Stage window;
    
    PanelPuesto panelPuesto;
    PanelDepartamento panelDepartamento;
    
   
    public WindowMain(Stage ventanaApp) throws IOException {
        
        window= ventanaApp;
        fxmll = new FXMLLoader (System.class.getResource("/fxml/window_main.fxml"));
        fxmll.setController(this);
          fxmll.load();
    scene = new Scene(fxmll.getRoot());
     
        agregarOyentes();
        //Guardamos la ventana que nos pasa la JVM
        window = ventanaApp;

        //Creamos los mÃ³dulos de la aplicaciÃ³n
        panelPuesto = new PanelPuesto(ventanaApp); 
        panelDepartamento = new PanelDepartamento(ventanaApp);
        
        //Inicializamos los mÃ³dulos que hemos creado
        try{
           
            panelPuesto.inicializar();
            panelDepartamento.inicializar();
          
        }
        catch(Exception e){
            //Si ocurre un error, imprimimos la excepciÃ³n y
            //nos salimos de la aplicaciÃ³n
            
            JOptionPane.showMessageDialog(null,e);
            e.printStackTrace();
            
        }
          
        //Establecemos las propiedades de la ventana       
        window.setScene(scene);
        window.setTitle("SISTEMA DE NÓMIN-HGAL");
        
        //Mostramos la ventama
        window.show();
    }
    
    private void agregarOyentes(){
        //btnCargarModuloEmpleado.setOnAction(evt -> {/* */ });
        btnCargarModuloPuesto.setOnAction(evt -> {cargarModulo(panelPuesto.panelRaiz); });
        btnCargarModuloDep.setOnAction(evt -> {cargarModulo(panelDepartamento.panelRaiz); });
        
    }
    
    private void cargarModulo(Node n){
        pnlContenedorPrincipal1.setCenter(n);
    }

        

    
    
    
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
        
        
        //Agregamos los oyentes de los controles:
       
        
         
    }
    
   
}