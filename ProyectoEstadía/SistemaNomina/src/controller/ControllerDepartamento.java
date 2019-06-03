/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.ConexionMysql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Departamento;

/**
 *
 * @author Cristian
 */
public class ControllerDepartamento {
    /**
    * Inserta un registro de Producto en la base de datos.
    * 
    * @param d Es el objeto de tipo {@link Producto}, el cual
    *          contiene los datos que seran insertados dentro del nuevo
    *          registro.
    * @return  Devuelve el ID que se genera para el {@link Producto}, después de
    *          su inserción.
    * @throws Exception
    */
    
    public int insert(Departamento d) throws Exception{
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql = "INSERT INTO departamento(nombreDepartamento, numeroEmpleados, estatus) "
                + "VALUES (?, ?, ?)";
        
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
        
        pstmt.setString(1, d.getNombreDepartamento());
        pstmt.setInt(2, d.getNumeroEmpleados());
        pstmt.setInt(3,1);
     
       
        
        //Ejecutamos la consutla:
        pstmt.executeUpdate();
        
        //Le pedimos al PreparedStatement los valores de las claves generadas,
        //que en este caso, solo es un valor:
        rs = pstmt.getGeneratedKeys();
        
        //Operaciones basicas con un ResultSet
        //rs.beforeFirst();
        //rs.last();
        //rs.first();
        //rs.afterLast();
        
        if(rs.next()){
            idGenerado = rs.getInt(1);
            d.setId(idGenerado);
        }
        
        //Cerramos todos los objetos de conexión con la B.D.:
        rs.close();
        pstmt.close();
        connMySQL.cerrar();
        
        //Devolvemos el ID generado:
        return idGenerado;  
    }
    
     /**
     * Actualiza un registro de {@link Producto}, previamente existente, 
     * en la base de datos.
     * 
     * @param d Es el objeto de tipo {@link Producto}, el cual
     *          contiene los datos que seran insertados dentro del nuevo
     *          registro.
     * @throws Exception 
     */
    
    public void update(Departamento d) throws Exception{
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql =    "UPDATE departamento SET nombreDepartamento = ?, numeroEmpleados = ?, estatus = ?" + 
                        " WHERE idDepartamento = ?";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMysql connMySQL = new ConexionMysql();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.abrir();
        
        //Con este objeto ejecutaremos la sentencia SQL que realiza la 
        //actualizacion en la tabla.
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Establecemos los valores de los parametros de la consulta, basados
        //en los signos de interrogacion:
        pstmt.setString(1, d.getNombreDepartamento());
        pstmt.setInt(2, d.getNumeroEmpleados());
        pstmt.setInt(3, d.getEstatus());
        pstmt.setInt(4, d.getId());
        
        //Ejecutamos la consulta:
        pstmt.executeUpdate();
        
        //Cerramos todos los objetos de conexión con la B.D.:
        pstmt.close();
        connMySQL.cerrar();
    }
    
    /**
     * Elimina un registro de {@link Producto} en la base de datos.
     * 
     * @param id Es el ID del {@link Producto} que se desea eliminar.
     * @throws Exception 
     */
    
    public void delete(int id) throws Exception{
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql =    "UPDATE departamento SET estatus = 0 WHERE idDepartamento = ?";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMysql connMySQL = new ConexionMysql();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.abrir();
        
        //Con este objeto ejecutaremos la sentencia SQL que realiza la 
        //actualizacion en la tabla.
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Establecemos el valor del ID del Producto a dar de baja:       
        pstmt.setInt(1, id);
        
        //Ejecutamos la consulta:
        pstmt.executeUpdate();
        
        //Cerramos todos los objetos de conexión con la B.D.:
        pstmt.close();
        connMySQL.cerrar();
    }
    
    /**
     * Busca un registro de {@link Producto} en la base de datos, por su ID.
     * 
     * @param id Es el ID del {@link Producto} que se desea buscar.
     * @return  Devuelve el {@link Producto} que se encuentra en la base de datos,
     *          basado en la coincidencia del ID (id) pasado como parámetro.
     *          Si no es encontrado un {@link Producto} con el ID especificado,
     *          el método devolvera <code>null</code>.
     * @throws Exception 
     */
    
    public Departamento findById(int id) throws Exception{
        //Definimos la consulta SQL:
        String sql = "SELECT * FROM departamento WHERE idDepartamento = ?";
        
        //Una variable temporal para guardar el Producto consultado
        //(si es que se encuentra alguno):
        Departamento d = null;
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMysql connMySQL = new ConexionMysql();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.abrir();
        
        //Con este objeto ejecutaremos la sentencia SQL que realiza la 
        //consulta de productos:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Aquí guardaremos el resultado de la consulta:
        ResultSet rs = null;
        
        //Establecemos el valor del ID del Producto:
        pstmt.setInt(1, id);
        
        //Ejecutamos la consulta:
        rs = pstmt.executeQuery();
        
        //Evaluamos si se devolvio algun registro:
        if (rs.next()){
            //Creamos una nueva instancia de Producto:
            d = new Departamento();
            
            //Llenamos sus propiedades:
            d.setEstatus(rs.getInt("estatus"));
            d.setId(rs.getInt("idDepartamento"));
            d.setNombreDepartamento(rs.getString("nombreDepartamento"));
            d.setNumeroEmpleados(rs.getInt("numeroEmpleados"));
                  
        }
        
        //Cerramos todos los objetos de conexión con la B.D.:
        rs.close();
        pstmt.close();
        connMySQL.cerrar();
        
        //Devolvemos el Producto:
        return d;
    }
    
    /**
     * Consulta y devuelve los registros de productos encontrados, basados en
     * las coincidencias parciales del valor del parametro <code>filtro</code>.
     * 
     * Los registros encontrados se devuelven en forma de una lista dinamica
     * (List&lt;{@link Producto}&rt;) que contiene dentro los objetos de tipo 
     * {@link Producto}.
     * 
     * @param filtro    Es el termino de coincidencia parcial que condicionara
     *                  la búsqueda solo a aquellos registros coincidentes con
     *                  el valor especificado.
     * @return  Devuelve el listado de productos encontrados 
     *          en la base de datos, en forma de una lista dinamica
     *          <code>List&lt;{@link Producto}&rt;</code> basado en la coincidencia 
     *          parcial del <code>filtro</code> pasado como parámetro.
     *          Si la base de datos no tiene algun registro de empleado, se 
     *          devuelve una lista vacia (NO SE DEVUELVE <code>null</code>!).
     * @throws Exception 
     */
    
    public List<Departamento> getAll(String filtro, int estatus) throws Exception{
        //Definimos la consulta SQL:
        String sql = "SELECT * FROM departamento WHERE estatus = ?";
        
        //Aquí guardaremos objetos de tipo Producto. Una lista es un "contenedor"
        //dinamico de objetos. En este caso, queremos un contenedor de 
        //"productos". En otras palabras, queremos un contenedor que dentro
        //"contenga" objetos de tipo Producto:
        List<Departamento> departamentos = new ArrayList<>();
        
        //Una variable temporal para crear nuevas instancias de Producto:
        Departamento d = null;
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMysql connMySQL = new ConexionMysql();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.abrir();
        
        //Con este objeto ejecutaremos la sentencia SQL que realiza la 
        //consulta de productos:
        PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, estatus);
        //Aquí guardaremos el resultado de la consulta:
        ResultSet rs = pstmt.executeQuery();
        
        //Recorremos el ResultSet, comenzando por el primer registro:
        while (rs.next()){
            //Creamos una nueva instancia de Departamento:
            d = new Departamento();
            
            //Llenamos sus propiedades:
            d.setEstatus(rs.getInt("estatus"));
            d.setId(rs.getInt("idDepartamento"));
            d.setNombreDepartamento(rs.getString("nombreDepartamento"));
            d.setNumeroEmpleados(rs.getInt("numeroEmpleados"));
           
            
            //Agregamos el producto a la lista:
            departamentos.add(d);
        }
        
        //Cerramos todos los objetos de conexión con la B.D.:
        rs.close();
        pstmt.close();
        connMySQL.cerrar();
        
        //Devolvemos la lista dinámica con los productos generados al 
        //realizar la consulta en la Base de Datos.
        return departamentos;
    }
}
