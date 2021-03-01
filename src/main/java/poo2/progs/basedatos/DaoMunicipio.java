package poo2.progs.basedatos;

import poo2.progs.entidades.Municipio;
import poo2.progs.interfaces.Dao;
import poo2.progs.interfaces.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoMunicipio implements Dao<Municipio> {
    private final Connection conn;
    // TODO Declarar atributos para los statement necesarios (si es que se requieren)

    /**
     * Constructor para inicializar la conexion
     * la cual es recibida desde fuera
     * @param con Objeto conexion a MySQL a usar
     * @throws DaoException En caso de que el objeto de conexion no sea valido
     */
    public DaoMunicipio(Connection con) throws DaoException {
        if (con==null) {
            throw new DaoException("Conector recibido es nulo");
        }
        conn=con;
        inicializaStmts();
    }

    /**
     * Constructor que recibe los datos necesarios para establecer una conexion
     * a la base de datos usando JDBC
     * @param ubicacionServidor  Ubicacion del servidor (direccion IP o nombre de dominio)
     * @param nomBD Nombre de la base de datos a usar
     * @param usuario  Usuario que se utilizara para conectarse
     * @param clave  Clave que se utilizara para conectarse
     * @throws DaoException  En caso de no poder establecer la conexion
     */
    public DaoMunicipio(String ubicacionServidor, String nomBD,
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
        inicializaStmts();
    }

    /**
     * Constructor vacio que creara una conexion en base
     * a los datos por default
     * @throws DaoException  Si no se puede crear la conexion
     */
    public DaoMunicipio() throws DaoException {
        this("localhost","controlescolar",
                "IngSW","UAZsw2021");
    }

    /**
     * Este metodo inicializa los statement a usar para las consultas necesarias
     * @throws DaoException En caso de que no se puedan crear
     */
    private void inicializaStmts() throws DaoException {
        // TODO Completar implementacion

    }

    @Override
    public Optional<Municipio> get(Object id) {
        // TODO Completar implementacion eliminando el return que esta actualmente
        return Optional.empty();
    }

    @Override
    public List<Municipio> getAll() {
        // TODO Completar implementacion eliminando el return que esta actualmente
        return null;
    }

    @Override
    public boolean save(Municipio dato) {
        return false;
    }

    @Override
    public boolean update(Municipio dato) {
        return false;
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }
}
