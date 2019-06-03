/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.ResizeFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Departamento;

/**
 *
 * @author Cristian
 */
public class TableAdapterDepartamento {

    /**
     * En este método creamos y configuramos las columnas de la tabla que
     * mostrará datos de los productos objetos de tipo
     * {@link org.solsistema.myspa.model.Producto}.
     *
     * @param tbl
     */
    public static void adapt(TableView<Departamento> tbl) {

        //Creamos las columnas y estbalecemos el texto de cabecera que mostrará:
        TableColumn<Departamento, Integer> tcDepartamentoId = new TableColumn<>("Clave del Departamento");
        TableColumn<Departamento, String> tcDepartamentoNombre = new TableColumn<>("Nombre Departamento");
        TableColumn<Departamento, Integer> tcDepartamentoCantidad = new TableColumn<>("Cantidad de Empleados");
        TableColumn<Departamento, Integer> tcDepartamentoEstatus = new TableColumn<>("Estatus");

        //Ahora, le diremos a cada columna como deberá mostrarse y cual será
        //el atributo que mostrará de un objeto de tipo Producto, a través de
        //un CellFactory y un CallBack.
        //Para propiedades directas del objeto podemos utilizar:
        tcDepartamentoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcDepartamentoNombre.setCellValueFactory(new PropertyValueFactory<>("nombreDepartamento"));
        tcDepartamentoCantidad.setCellValueFactory(new PropertyValueFactory<>("numeroEmpleados"));
        tcDepartamentoEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));

        //Una vez configuradas las columnas, las pondremos en la tabla,
        //pero antes, quítaremos cualquier columna que pudiera tener:
        tbl.getColumns().clear();
        
        //Ajustamos las columnas al tamaño de la tabla

        tbl.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
            @Override
            public Boolean call(ResizeFeatures p) {
                return true;
            }
        });

        tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //Agregamos las columnas a la tabla, en el orden
        //que deseamos que aparezcan:
        tbl.getColumns().addAll(tcDepartamentoId, tcDepartamentoNombre, tcDepartamentoCantidad, tcDepartamentoEstatus);
    }
}
