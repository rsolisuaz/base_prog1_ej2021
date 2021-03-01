package poo2.progs.basedatos;

import poo2.progs.entidades.*;
import poo2.progs.interfaces.DaoEscolares;
import poo2.progs.interfaces.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoEscolaresImpl implements DaoEscolares {
    private final Connection conn;
    
    /**
     * Constructor que recibe los datos necesarios para establecer una conexion
     * a la base de datos usando JDBC
     * @param ubicacionServidor  Ubicacion del servidor (direccion IP o nombre de dominio)
     * @param nomBD Nombre de la base de datos a usar
     * @param usuario  Usuario que se utilizara para conectarse
     * @param clave  Clave que se utilizara para conectarse
     * @throws DaoException  En caso de no poder establecer la conexion
     */
    public DaoEscolaresImpl(String ubicacionServidor, String nomBD,
                            String usuario, String clave)
            throws DaoException {
        String url=String.format("jdbc:mysql://%s/%s",
                ubicacionServidor, nomBD);
        try {
            conn= DriverManager.getConnection(url, usuario, clave);
        }
        catch (SQLException exsql) {
            throw new DaoException(exsql.getMessage(), exsql.getCause());
        }
        inicializaDaos(conn);
    }

    /**
     * Constructor vacio que creara una conexion en base
     * a los datos por default
     * @throws DaoException  Si no se puede crear la conexion
     */
    public DaoEscolaresImpl() throws DaoException {
        this("localhost","controlescolares",
                "IngSW2021","UAZsw2021");
    }

    /**
     * Constructor que recibe el objeto conexion desde fuera
     * y con el cual se establecera la comunicacion con MySQL
     * @param con  Objeto conexion creado desde fuera
     * @throws DaoException  En caso de que el objeto conexion no sea valido
     */
    public DaoEscolaresImpl(Connection con) throws DaoException {
        if (con==null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn=con;
        inicializaDaos(conn);
    }


    /**
     * Este metodo inicializara los Dao necesarios
     * pasandoles el objeto de conexion
     * @param conn Objeto de conexion a MySQL
     */
    private void inicializaDaos(Connection conn) {
        // TODO Inicializar todos los Daos a user asi como los statement que se requieran
    }

    /* TODO Completar la implementacion de todos los metodos de la interface
     Eliminando los return null o return false que estan por el momento
     en cada metodo */

    @Override
    public List<Estado> obtenEstados() {
        return null;
    }

    @Override
    public List<Municipio> obtenMunicipios(long idEstado) {
        return null;
    }

    @Override
    public List<Carrera> obtenCarreras() {
        return null;
    }

    @Override
    public boolean agregaCarrera(Carrera c) {
        return false;
    }

    @Override
    public boolean modificaCarrera(Carrera c) {
        return false;
    }

    @Override
    public boolean eliminaCarrera(String clave) {
        return false;
    }

    @Override
    public List<Materia> obtenMaterias() {
        return null;
    }

    @Override
    public boolean agregaMateria(Materia m) {
        return false;
    }

    @Override
    public boolean modificaMateria(Materia m) {
        return false;
    }

    @Override
    public boolean eliminaMateria(String clave) {
        return false;
    }

    @Override
    public List<Periodoescolar> obtenPeriodos() {
        return null;
    }

    @Override
    public boolean agregaPeriodo(Periodoescolar p) {
        return false;
    }

    @Override
    public boolean modificaPeriodo(Periodoescolar p) {
        return false;
    }

    @Override
    public boolean eliminaPeriodo(long id) {
        return false;
    }

    @Override
    public List<Profesor> obtenProfesores() {
        return null;
    }

    @Override
    public boolean agregaProfesor(Profesor p) {
        return false;
    }

    @Override
    public boolean modificaProfesor(Profesor p) {
        return false;
    }

    @Override
    public boolean eliminaProfesor(String rfc) {
        return false;
    }

    @Override
    public List<DetalleCargaprofesor> obtenCargaProfesor(String rfc) {
        return null;
    }

    @Override
    public boolean agregaCargaProfesor(Cargaprofesor c) {
        return false;
    }

    @Override
    public boolean eliminaCargaProfesor(long idCarga) {
        return false;
    }

    @Override
    public List<Estudiante> obtenEstudiantes() {
        return null;
    }

    @Override
    public boolean agregaEstudiante(Estudiante e) {
        return false;
    }

    @Override
    public boolean modificaEstudiante(Estudiante e) {
        return false;
    }

    @Override
    public boolean eliminaEstudiante(String matricula) {
        return false;
    }

    @Override
    public List<DetalleCargaestudiante> obtenCargaEstudiante(String matricula) {
        return null;
    }

    @Override
    public boolean agregaCargaEstudiante(Cargaestudiante c) {
        return false;
    }

    @Override
    public boolean eliminaCargaEstudiante(long idCarga) {
        return false;
    }
}
