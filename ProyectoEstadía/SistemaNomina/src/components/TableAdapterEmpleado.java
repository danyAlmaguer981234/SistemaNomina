
package components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Empleado;

/**
 *
 * @author ramir
 */
public class TableAdapterEmpleado {
     /**
     * En este método creamos y configuramos las columnas de la
     * tabla que mostrará datos de los puestos objetos de 
     * tipo {@link model.Empleado}.
     * @param tbl 
     */
    public static void adapt(TableView<Empleado> tbl){
        
        //Creamos las columnas y estbalecemos el texto de cabecera que mostrará:
        TableColumn<Empleado, Integer> tcId = new TableColumn<>("Id Empleado");
        TableColumn<Empleado, String>  tcNumeroUnico = new TableColumn<>("Número Único");
        TableColumn<Empleado, String>  tcNombre = new TableColumn<>("Nombre");
        TableColumn<Empleado, String>  tcApellidoP = new TableColumn<>("Apellido Paterno");
        TableColumn<Empleado, String>  tcApellidoM = new TableColumn<>("Apellido Materno");
        TableColumn<Empleado, String> tcFechaIngreso = new TableColumn<>("Fecha Ingreso");
        TableColumn<Empleado, Double> tcSueldoBase = new TableColumn<>("Sueldo Base");
        TableColumn<Empleado, Integer> tcEstatus = new TableColumn<>("Estatus");
        TableColumn<Empleado, Integer> tcPuesto = new TableColumn<>("Puesto");
        TableColumn<Empleado, Integer> tcDepartamento = new TableColumn<>("Departamento");

        
        //Ahora, le diremos a cada columna como deberá mostrarse y cual será
        //el atributo que mostrará de un objeto de tipo Empleado, a través de
        //un CellFactory y un CallBack.
        
        //Para propiedades directas del objeto podemos utilizar:
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNumeroUnico.setCellValueFactory(new PropertyValueFactory<>("numeroUnico"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcApellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        tcApellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        tcFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));
        tcSueldoBase.setCellValueFactory(new PropertyValueFactory<>("sueldoBase"));
        tcEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));
        tcPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        tcDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));

        
        //Una vez configuradas las columnas, las pondremos en la tabla,
        //pero antes, quítaremos cualquier columna que pudiera tener:
        tbl.getColumns().clear();
        
        
        tbl.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
            @Override
            public Boolean call(TableView.ResizeFeatures p) {
                return true;
            }
        });

        tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //Agregamos las columnas a la tabla, en el orden
        //que deseamos que aparezcan:
        tbl.getColumns().addAll(tcId, tcNumeroUnico, tcNombre, tcApellidoP, tcApellidoM, tcFechaIngreso, tcSueldoBase, tcEstatus, tcPuesto, tcDepartamento);
    }
}
