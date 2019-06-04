
package controller;

import database.ConexionMysql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Departamento;
import model.Empleado;
import model.Puesto;

/**
 *
 * @author ramir
 */
    public class ControllerEmpleado {
           /**
     * Inserta un registro de Empleado en la base de datos.
     * 
     * @param e Es el objeto de tipo {@link Empleado}, el cual
     *          contiene los datos que seran insertados dentro del nuevo
     *          registro.
     * @return  Devuelve el ID que se genera para el {@link Empleado}, después de su
     *          insercion.
     * @throws Exception 
     */
    public int insert(Empleado e) throws Exception{
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql = "INSERT INTO empleado (numeroUnico, nombre, apellidoPaterno, apellidoMaterno, fechaIngreso, sueldoBase, estatus, idPuestoEmpleado, idDepartamentoEmpleado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? )";
        
        //Aquí guardaremos el ID que se generará
        int idGenerado = -1;
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMysql connMySQL = new ConexionMysql();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.abrir();
        
        //Con este objeto ejecutaremos la sentencia SQL que realiza la
        //inserción en la tabla. Debemos especificarle que queremos que nos
        //devuelva el ID que se genera al realizar la inserción del registro:
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        //En este objeto guardamos el resultado de la consulta, la cual
        //nos devolverá los ID's que se generaron. En este caso, solo se
        //generará un ID:
        ResultSet rs = null;
        
        pstmt.setString(1, e.getNumeroUnico());
        pstmt.setString(2, e.getNombre());
        pstmt.setString(3, e.getApellidoPaterno());
        pstmt.setString(4, e.getApellidoMaterno());
        pstmt.setString(5, e.getFechaIngreso());
        pstmt.setDouble(6, e.getSueldoBase());
        pstmt.setInt(7, 1);
        pstmt.setInt(8, e.getPuesto().getId());
        pstmt.setInt(9, e.getDepartamento().getId());
      
        
        //Ejecutamos la consutla:
        pstmt.executeUpdate();
        
        //Le pedimos al PreparedStatement los valores de las claves generadas,
        //que en este caso, solo es un valor:
        rs = pstmt.getGeneratedKeys();
        
        if(rs.next()){
            idGenerado = rs.getInt(1);
            e.setId(idGenerado);
        }
        
        //Cerramos todos los objetos de conexión con la B.D.:
        rs.close();
        pstmt.close();
        connMySQL.cerrar();
        
        //Devolvemos el ID generado:
        return idGenerado;  
    }
    
     /**
     * Actualiza un registro de {@link Empleado}, previamente existente, 
     * en la base de datos.
     * 
     * @param e Es el objeto de tipo {@link Empleado}, el cual
     *          contiene los datos que seran insertados dentro del nuevo
     *          registro.
     * @throws Exception 
     */
    
    public void update(Empleado e) throws Exception{
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql =    "UPDATE empleado SET numeroUnico = ?, nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, fechaIngreso = ?, sueldoBase = ?, estatus = ?, idPuestoEmpleado = ?, idDepartamentoEmpleado  = ? " + 
                        "WHERE idEmpleado = ?";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMysql connMySQL = new ConexionMysql();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.abrir();
        
        //Con este objeto ejecutaremos la sentencia SQL que realiza la 
        //actualizacion en la tabla.
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Establecemos los valores de los parametros de la consulta, basados
        //en los signos de interrogacion:
        pstmt.setString(1, e.getNumeroUnico());
        pstmt.setString(2, e.getNombre());
        pstmt.setString(3, e.getApellidoPaterno());
        pstmt.setString(4, e.getApellidoMaterno());
        pstmt.setString(5, e.getFechaIngreso());
        pstmt.setDouble(6, e.getSueldoBase());
        pstmt.setInt(7, e.getEstatus());
        pstmt.setInt(8, e.getPuesto().getId());
        pstmt.setInt(9, e.getDepartamento().getId());
        pstmt.setInt(10, e.getId());
        
        //Ejecutamos la consulta:
        pstmt.executeUpdate();
        
        //Cerramos todos los objetos de conexión con la B.D.:
        pstmt.close();
        connMySQL.cerrar();
    }
    
    /**
     * Elimina un registro de {@link Empleado} en la base de datos.
     * 
     * @param id Es el ID de la {@link Empleado} que se desea eliminar.
     * @throws Exception 
     */
    
    public void delete(int id) throws Exception{
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql = "UPDATE empleado SET estatus = 0 WHERE idEmpleado = ?";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMysql connMySQL = new ConexionMysql();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.abrir();
        
        //Con este objeto ejecutaremos la sentencia SQL que realiza la 
        //actualizacion en la tabla.
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Establecemos el valor del ID de la Sucursal a dar de baja:       
        pstmt.setInt(1, id);
        
        //Ejecutamos la consulta:
        pstmt.executeUpdate();
        
        //Cerramos todos los objetos de conexión con la B.D.:
        pstmt.close();
        connMySQL.cerrar();
    }
    
    /**
     * Busca un registro de {@link Empledo} en la base de datos, por su ID.
     * 
     * @param id Es el ID de la {@link Empleado} que se desea buscar.
     * @return  Devuelve la {@link Empleado} que se encuentra en la base de datos,
     *          basado en la coincidencia del ID (id) pasado como parámetro.
     *          Si no es encontrado una {@link Empleado} con el ID especificado,
     *          el método devolvera <code>null</code>.
     * @throws Exception 
     */
    
    public Empleado findById(int id) throws Exception{
        //Definimos la consulta SQL:
       //Definimos la consulta SQL:
        String sql = "SELECT * FROM empleado WHERE idEmpleado = ?";
        
        //Una variable temporal para guardar el Empleado consultado
        //(si es que se encuentra alguno):
        Empleado e = null;
        Puesto p = null;
        Departamento d= null;
        
        ControllerPuesto cp = new ControllerPuesto();
        ControllerDepartamento cd = new ControllerDepartamento();
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMysql connMySQL = new ConexionMysql();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.abrir();
        
        //Con este objeto ejecutaremos la sentencia SQL que realiza la 
        //consulta de empleados:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Aquí guardaremos el resultado de la consulta:
        ResultSet rs = null;
        
        //Establecemos el valor del ID del Empleado:
        pstmt.setInt(1, id);
        
        //Ejecutamos la consulta:
        rs = pstmt.executeQuery();
        
        //Evaluamos si se devolvio algun registro:
        if (rs.next()){
            //Creamos una nueva instancia de Empleado:
            e = new Empleado();
            p = new Puesto();
            d = new Departamento();
//         
            //Llenamos sus propiedades:
            e.setEstatus(rs.getInt("estatus"));
                e.setId(rs.getInt("idEmpleado"));
                e.setNumeroUnico(rs.getString("numeroUnico"));
                e.setNombre(rs.getString("nombre"));
                e.setApellidoPaterno(rs.getString("apellidoPaterno"));
                e.setApellidoMaterno(rs.getString("apellidoMaterno"));
                e.setFechaIngreso(rs.getString("fechaIngreso"));
                e.setSueldoBase(rs.getDouble("sueldoBase"));
                e.setPuesto(cp.findById(rs.getInt("idPuestoEmpleado"))); 
                e.setDepartamento(cd.findById(rs.getInt("idDepartamentoEmpleado")));
           
        }
        
        //Cerramos todos los objetos de conexión con la B.D.:
        rs.close();
        pstmt.close();
        connMySQL.cerrar();
        
        //Devolvemos el Empleado:
        return e;
    }
    
    /**
     * Consulta y devuelve los registros de salas encontradas, basados en
     * las coincidencias parciales del valor del parametro <code>filtro</code>.
     * 
     * Los registros encontrados se devuelven en forma de una lista dinamica
     * (List&lt;{@link Empleado}&rt;) que contiene dentro los objetos de tipo 
     * {@link Empleado}.
     * 
     * @param filtro    Es el termino de coincidencia parcial que condicionara
     *                  la búsqueda solo a aquellos registros coincidentes con
     *                  el valor especificado.
     * @return  Devuelve el listado de salas encontrados 
     *          en la base de datos, en forma de una lista dinamica
     *          <code>List&lt;{@link Empleado}&rt;</code> basado en la coincidencia 
     *          parcial del <code>filtro</code> pasado como parámetro.
     *          Si la base de datos no tiene algun registro de sucursal, se 
     *          devuelve una lista vacia (NO SE DEVUELVE <code>null</code>!).
     * @throws Exception 
     */
    
    public List<Empleado> getAll(String filtro) throws Exception{
        //Definimos la consulta SQL:
        String sql = "SELECT * FROM empleado WHERE estatus = 1";
        
        //Aquí guardaremos objetos de tipo Sala. Una lista es un "contenedor"
        //dinamico de objetos. En este caso, queremos un contenedor de 
        //"empleados". En otras palabras, queremos un contenedor que dentro
        //"contenga" objetos de tipo Empleado:
        List<Empleado> empleados = new ArrayList<>();
        
        //Una variable temporal para crear nuevas instancias de Departamento y Puesto:
        Empleado e = null;
        ControllerPuesto cp = new ControllerPuesto();
        ControllerDepartamento cd = new ControllerDepartamento();
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMysql connMySQL = new ConexionMysql();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.abrir();
        
        //Con este objeto ejecutaremos la sentencia SQL que realiza la 
        //consulta de empleados:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        //Aquí guardaremos el resultado de la consulta:
        ResultSet rs = pstmt.executeQuery();
        
        //Recorremos el ResultSet, comenzando por el primer registro:
        while (rs.next()){
            //Creamos una nueva instancia de Departamento:
            e = new Empleado();
            
            //Llenamos sus propiedades:
            
            e.setId(rs.getInt("idEmpleado"));
            e.setNumeroUnico(rs.getString("numeroUnico"));
            e.setNombre(rs.getString("nombre"));
            e.setApellidoPaterno(rs.getString("apellidoPaterno"));
            e.setApellidoMaterno(rs.getString("apellidoMaterno"));
            e.setFechaIngreso(rs.getString("fechaIngreso"));
            e.setSueldoBase(rs.getDouble("sueldoBase"));
            e.setEstatus(rs.getInt("estatus"));
            e.setPuesto(cp.findById(rs.getInt("idPuestoEmpleado")));
            e.setDepartamento(cd.findById(rs.getInt("idDepartamentoEmpleado")));
     
            //Agregamos el departamento y puesto a la lista:
            empleados.add(e);
        }
        
        //Cerramos todos los objetos de conexión con la B.D.:
        rs.close();
        pstmt.close();
        connMySQL.cerrar();
        
        //Devolvemos la lista dinámica con las sucursales generadas al 
        //realizar la consulta en la Base de Datos.
        return empleados;
    }
}

