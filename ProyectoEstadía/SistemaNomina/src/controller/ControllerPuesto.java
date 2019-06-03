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
import model.Puesto;

/**
 *
 * @author Cristian
 */
public class ControllerPuesto {
    

    /**
    * Inserta un registro de Sala en la base de datos.
    * 
     * @param p
           Es el objeto de tipo {@link Sala}, el cual
    *          contiene los datos que seran insertados dentro del nuevo
    *          registro.
    * @return  Devuelve el ID que se genera para la {@link Sala}, después de
    *          su inserción.
    * @throws Exception
    */
    
    public int insert(Puesto p) throws Exception{
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql = "INSERT INTO puesto (nombrePuesto, estatus, idDepartamentoPuesto) "
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
        
        pstmt.setString(1, p.getNombrePuesto());
        pstmt.setInt(2,1);
        pstmt.setInt(3,p.getDepartamento().getId());
      
      
        
        //Ejecutamos la consutla:
        pstmt.executeUpdate();
        
        //Le pedimos al PreparedStatement los valores de las claves generadas,
        //que en este caso, solo es un valor:
        rs = pstmt.getGeneratedKeys();
        
        if(rs.next()){
            idGenerado = rs.getInt(1);
            p.setId(idGenerado);
        }
        
        //Cerramos todos los objetos de conexión con la B.D.:
        rs.close();
        pstmt.close();
        connMySQL.cerrar();
        
        //Devolvemos el ID generado:
        return idGenerado;  
    }
    
     /**
     * Actualiza un registro de {@link Sala}, previamente existente, 
     * en la base de datos.
     * 
     * @param p Es el objeto de tipo {@link Sala}, el cual
     *          contiene los datos que seran insertados dentro del nuevo
     *          registro.
     * @throws Exception 
     */
    
    public void update(Puesto p) throws Exception{
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql =    "UPDATE puesto SET nombrePuesto = ?, estatus = ?, idDepartamentoPuesto  = ? " + 
                        "WHERE idPuesto = ?";
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMysql connMySQL = new ConexionMysql();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.abrir();
        
        //Con este objeto ejecutaremos la sentencia SQL que realiza la 
        //actualizacion en la tabla.
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Establecemos los valores de los parametros de la consulta, basados
        //en los signos de interrogacion:
        pstmt.setString(1, p.getNombrePuesto());
        pstmt.setInt(2, p.getEstatus());
        pstmt.setInt(3, p.getDepartamento().getId());
        pstmt.setInt(4, p.getId());
        
        //Ejecutamos la consulta:
        pstmt.executeUpdate();
        
        //Cerramos todos los objetos de conexión con la B.D.:
        pstmt.close();
        connMySQL.cerrar();
    }
    
    /**
     * Elimina un registro de {@link Sala} en la base de datos.
     * 
     * @param id Es el ID de la {@link Sala} que se desea eliminar.
     * @throws Exception 
     */
    
    public void delete(int id) throws Exception{
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql = "UPDATE puesto SET estatus = 0 WHERE idPuesto = ?";
        
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
     * Busca un registro de {@link Sala} en la base de datos, por su ID.
     * 
     * @param id Es el ID de la {@link Sala} que se desea buscar.
     * @return  Devuelve la {@link Sala} que se encuentra en la base de datos,
     *          basado en la coincidencia del ID (id) pasado como parámetro.
     *          Si no es encontrado una {@link Sala} con el ID especificado,
     *          el método devolvera <code>null</code>.
     * @throws Exception 
     */
    
    public Puesto findById(int id) throws Exception{
        //Definimos la consulta SQL:
       //Definimos la consulta SQL:
        String sql = "SELECT * FROM puesto WHERE idPuesto = ?";
        
        //Una variable temporal para guardar el Producto consultado
        //(si es que se encuentra alguno):
        Puesto p = null;
        Departamento d= null;
        ControllerDepartamento cd = new ControllerDepartamento();
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
            p = new Puesto();
            d = new Departamento();
         
            //Llenamos sus propiedades:
            p.setEstatus(rs.getInt("estatus"));
            p.setId(rs.getInt("idPuesto"));
            p.setNombrePuesto(rs.getString("nombrePuesto"));
            p.setDepartamento(cd.findById(rs.getInt("idDepartamentoPuesto")));
                    
        }
        
        //Cerramos todos los objetos de conexión con la B.D.:
        rs.close();
        pstmt.close();
        connMySQL.cerrar();
        
        //Devolvemos el Producto:
        return p;
    }
    
    /**
     * Consulta y devuelve los registros de salas encontradas, basados en
     * las coincidencias parciales del valor del parametro <code>filtro</code>.
     * 
     * Los registros encontrados se devuelven en forma de una lista dinamica
     * (List&lt;{@link Sala}&rt;) que contiene dentro los objetos de tipo 
     * {@link Sala}.
     * 
     * @param filtro    Es el termino de coincidencia parcial que condicionara
     *                  la búsqueda solo a aquellos registros coincidentes con
     *                  el valor especificado.
     * @return  Devuelve el listado de salas encontrados 
     *          en la base de datos, en forma de una lista dinamica
     *          <code>List&lt;{@link Sala}&rt;</code> basado en la coincidencia 
     *          parcial del <code>filtro</code> pasado como parámetro.
     *          Si la base de datos no tiene algun registro de sucursal, se 
     *          devuelve una lista vacia (NO SE DEVUELVE <code>null</code>!).
     * @throws Exception 
     */
    
    public List<Puesto> getAll(String filtro, int estatus) throws Exception{
        //Definimos la consulta SQL:
        String sql = "SELECT * FROM puesto WHERE estatus = ?";
        
        //Aquí guardaremos objetos de tipo Sala. Una lista es un "contenedor"
        //dinamico de objetos. En este caso, queremos un contenedor de 
        //"salas". En otras palabras, queremos un contenedor que dentro
        //"contenga" objetos de tipo Sala:
        List<Puesto> puestos = new ArrayList<>();
        
        //Una variable temporal para crear nuevas instancias de Sucursal:
        Puesto p = null;
        ControllerDepartamento cd = new ControllerDepartamento();
        
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMysql connMySQL = new ConexionMysql();
        
        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.abrir();
        
        //Con este objeto ejecutaremos la sentencia SQL que realiza la 
        //consulta de sucursales:
        PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, estatus);
        //Aquí guardaremos el resultado de la consulta:
        ResultSet rs = pstmt.executeQuery();
        
        //Recorremos el ResultSet, comenzando por el primer registro:
        while (rs.next()){
            //Creamos una nueva instancia de Sucursal:
            p = new Puesto();
            
            //Llenamos sus propiedades:
            
            p.setId(rs.getInt("idPuesto"));
            p.setNombrePuesto(rs.getString("nombrePuesto"));
            p.setEstatus(rs.getInt("estatus"));
            p.setDepartamento(cd.findById(rs.getInt("idDepartamentoPuesto")));
     
            //Agregamos la sucursal a la lista:
            puestos.add(p);
        }
        
        //Cerramos todos los objetos de conexión con la B.D.:
        rs.close();
        pstmt.close();
        connMySQL.cerrar();
        
        //Devolvemos la lista dinámica con las sucursales generadas al 
        //realizar la consulta en la Base de Datos.
        return puestos;
    }
}

