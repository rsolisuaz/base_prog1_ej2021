package poo2.prac08.main;

import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dbunit.*;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.*;
import poo2.ConfigAccesoBaseDatos;
import poo2.progs.basedatos.DaoEscolaresImpl;
import poo2.progs.entidades.Estudiante;
import poo2.progs.entidades.Municipio;
import poo2.progs.interfaces.DaoException;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DaoEscolaresImplTest extends TestCase {
    private static IDatabaseTester databaseTester;
    private static IDatabaseConnection conndbunit;
    private static double calificacion;
    private static int calif_estudiante;
    private static int calif_municipio;

    private final static int CALIF_OBTENER=5;
    private final static int CALIF_AGREGAR_VALIDO=8;
    private final static int CALIF_AGREGAR_INVALIDO=2;
    private final static int CALIF_UPDATE_VALIDO=8;
    private final static int CALIF_UPDATE_INVALIDO=2;
    private final static int CALIF_DELETE_VALIDO=8;
    private final static int CALIF_DELETE_INVALIDO=2;

    private final static int MAX_CALIF_MUNICIPIO=5;
    private final static int MAX_CALIF_ESTUDIANTE=35;
    private final static double PORCENTAJE_MUNICIPIO=20;
    private final static double PORCENTAJE_ESTUDIANTE=10;
    private final static double MAX_CALIF=30.0;

    @BeforeAll
    public static void inicializa() throws Exception {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);
        databaseTester=new JdbcDatabaseTester(ConfigAccesoBaseDatos.driverName,
                ConfigAccesoBaseDatos.url,
                ConfigAccesoBaseDatos.usuario,ConfigAccesoBaseDatos.clave);
        databaseTester.setOperationListener(new ConfigAccesoBaseDatos.CustomConfigurationOperationListener());
        conndbunit=databaseTester.getConnection();
        DatabaseConfig config=conndbunit.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS,true);
        IDataSet dataSet=new FlatXmlDataSetBuilder().build(new FileInputStream("datosescolarv2.xml"));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @AfterAll
    public static void termina() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.REFRESH);
        databaseTester.onTearDown();
        if (calif_estudiante>0) {
            //System.out.printf("Puntos obtenidos en DAOEstudiante %d\n",calif_estudiante);
            double temp_est= calif_estudiante * PORCENTAJE_ESTUDIANTE / MAX_CALIF_ESTUDIANTE;
            calificacion += temp_est;
            System.out.printf("Puntos para Pruebas DAOEscolaresImpl (Estudiante): %.2f/%.2f\n",temp_est , PORCENTAJE_ESTUDIANTE);
        }
        if (calif_municipio>0) {
            //System.out.printf("Puntos obtenidos en DAOMunicipio %d\n",calif_municipio);
            double temp_mun= calif_municipio * PORCENTAJE_MUNICIPIO / MAX_CALIF_MUNICIPIO;
            calificacion += temp_mun;
            System.out.printf("Puntos para Pruebas DAOEscolaresImpl (obtenMunicipios): %.2f/%.2f\n",temp_mun , PORCENTAJE_MUNICIPIO);
        }
        System.out.printf("Puntos para Pruebas DAOEscolaresImpl (Total): %.2f/%.2f\n",calificacion, MAX_CALIF);
    }

    /// MUNICIPIO

    private void comparaMunicipio(Municipio actual, ITable expected, int numrow) {
        try {
            assertEquals(String.valueOf(actual.getIdMunicipio()), expected.getValue(numrow, "id_municipio").toString());
            assertEquals(actual.getNombreMunicipio(), expected.getValue(numrow, "nombre_municipio"));
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            assertNull("No deberia generar excepcion comparar los municipios",ex);
        }
    }

    // PRUEBA METODO obtenMunicipio
    @Test
    @Order(1)
    public void testMunicipioObten() throws Exception {
        DaoEscolaresImpl dao= new DaoEscolaresImpl(conndbunit.getConnection());
        List<Municipio> actual;
        long id=32;
        try {
            actual=dao.obtenMunicipios(id);
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            assertNull("No deberia generar excepcion el metodo obtenMunicipios de DaoEscolaresImpl",ex);
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("municipio32.xml"));
        ITable expectedTable=expectedDataSet.getTable("municipio");

        assertEquals(actual.size(),expectedTable.getRowCount());
        for (int i=0; i<actual.size(); i++) {
            comparaMunicipio(actual.get(i), expectedTable,i);
        }

        id=1;
        try {
            actual=dao.obtenMunicipios(id);
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            assertNull("No deberia generar excepcion el metodo obtenMunicipios de DaoEscolaresImpl",ex);
        }
        expectedDataSet=new FlatXmlDataSetBuilder().build(new File("municipio1.xml"));
        expectedTable=expectedDataSet.getTable("municipio");

        assertEquals(actual.size(),expectedTable.getRowCount());
        for (int i=0; i<actual.size(); i++) {
            comparaMunicipio(actual.get(i), expectedTable,i);
        }
        calif_municipio += CALIF_OBTENER;
    }

    // ESTUDIANTE

    private void comparaEstudiante(Estudiante actual, ITable expected, int numrow) {
        try {
            assertEquals(actual.getMatricula(), expected.getValue(numrow, "matricula"));
            assertEquals(actual.getNombre(), expected.getValue(numrow, "nombre"));
            assertEquals(actual.getApPaterno(), expected.getValue(numrow, "ap_paterno"));
            assertEquals(actual.getApMaterno(), expected.getValue(numrow, "ap_materno"));
            assertEquals(actual.getCalle(), expected.getValue(numrow, "calle"));
            assertEquals(actual.getColonia(), expected.getValue(numrow, "colonia"));
            assertEquals(actual.getCodPostal(), expected.getValue(numrow, "cod_postal"));
            assertEquals(actual.getTelefono(), expected.getValue(numrow, "telefono"));
            assertEquals(actual.getEmail(), expected.getValue(numrow, "email"));
            assertEquals(String.valueOf(actual.getIdEstado()), expected.getValue(numrow, "id_estado").toString());
            assertEquals(String.valueOf(actual.getIdMunicipio()), expected.getValue(numrow, "id_municipio").toString());
        }
        catch (Exception ex) {
            //ex.printStackTrace();
            assertNull("No deberia generar excepcion comparar los estudiantes",ex);
        }
    }

    @Test
    @Order(2)
    public void testEstudianteObten() throws Exception {
        DaoEscolaresImpl dao= new DaoEscolaresImpl(conndbunit.getConnection());
        List<Estudiante> actual;
        try {
            actual=dao.obtenEstudiantes();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            assertNull("No deberia generar excepcion el metodo obtenEstudiantes de DaoEscolaresImpl",ex);
        }
        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("datosescolarv2.xml"));
        ITable expectedTable=expectedDataSet.getTable("estudiante");

        assertEquals(actual.size(),expectedTable.getRowCount());
        for (int i=0; i<actual.size(); i++) {
            comparaEstudiante(actual.get(i), expectedTable,i);
        }
        calif_estudiante += CALIF_OBTENER;
    }

    @Test
    @Order(3)
    public void testEstudianteAgregar() throws Exception {
        String matricula="31081514";
        String nombre="Miguel";
        String apPaterno="Salas";
        String apMaterno="Guzman";
        String calle="Herreros 25";
        String colonia="Centro";
        String codpostal="99104";
        String telefono="4331234567";
        String email="misalas@uaz.edu.mx";
        long idest=32L;
        long idmun=32042L;

        Estudiante est = new Estudiante(matricula,nombre,apPaterno,email);
        est.setApMaterno(apMaterno);
        est.setCalle(calle);
        est.setColonia(colonia);
        est.setCodPostal(codpostal);
        est.setTelefono(telefono);
        est.setIdEstado(idest);
        est.setIdMunicipio(idmun);

        DaoEscolaresImpl dao= new DaoEscolaresImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = dao.agregaEstudiante(est);
        }
        catch (DaoException ex) {
            resultado=false;
            assertNull("No deberia generar excepcion el metodo agregaEstudiante de DaoEscolaresImpl",ex);
        }
        assertTrue(resultado);


        ITable actualTable=conndbunit.createTable("estudiante");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("estudiante_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("estudiante");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_estudiante += CALIF_AGREGAR_VALIDO;
    }

    @Test
    @Order(3)
    public void testEstudianteAgregarDuplicado() throws Exception {
        String matricula="31081514";
        String nombre="Miguel";
        String apPaterno="Salas";
        String apMaterno="Guzman";
        String calle="Herreros 25";
        String colonia="Centro";
        String codpostal="99104";
        String telefono="4331234567";
        String email="misalas@uaz.edu.mx";
        long idest=32L;
        long idmun=32042L;

        Estudiante est = new Estudiante(matricula,nombre,apPaterno,email);
        est.setApMaterno(apMaterno);
        est.setCalle(calle);
        est.setColonia(colonia);
        est.setCodPostal(codpostal);
        est.setTelefono(telefono);
        est.setIdEstado(idest);
        est.setIdMunicipio(idmun);

        DaoEscolaresImpl dao= new DaoEscolaresImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = dao.agregaEstudiante(est);
        }
        catch (DaoException ex) {
            resultado=false;
            assertNull("No deberia generar excepcion el metodo agregaEstudiante de DaoEscolaresImpl",ex);
        }
        assertFalse(resultado);


        ITable actualTable=conndbunit.createTable("estudiante");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("estudiante_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("estudiante");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_estudiante += CALIF_AGREGAR_INVALIDO;
    }

    @Test
    @Order(5)
    public void testEstudianteModificar() throws Exception {
        String matricula="31081514";
        String nombre="Luis";
        String apPaterno="Padilla";
        String apMaterno="Medina";
        String calle="Av. Torreon 901";
        String colonia="Las Lomas";
        String codpostal="99108";
        String telefono="4331234578";
        String email="lpadilla@uaz.edu.mx";
        long idest=1L;
        long idmun=1001L;

        Estudiante est = new Estudiante(matricula,nombre,apPaterno,email);
        est.setApMaterno(apMaterno);
        est.setCalle(calle);
        est.setColonia(colonia);
        est.setCodPostal(codpostal);
        est.setTelefono(telefono);
        est.setIdEstado(idest);
        est.setIdMunicipio(idmun);

        DaoEscolaresImpl dao= new DaoEscolaresImpl(conndbunit.getConnection());
        boolean resultado;
        try {
            resultado = dao.modificaEstudiante(est);
        }
        catch (DaoException ex) {
            resultado=false;
            assertNull("No deberia generar excepcion el metodo modificaEstudiante de DaoEscolaresImpl",ex);
        }
        assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("estudiante",
                String.format("SELECT * FROM estudiante WHERE matricula='%s'", matricula));

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("estudiante_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("estudiante");

        Assertion.assertEquals(expectedTable,actualTable);
        calif_estudiante += CALIF_UPDATE_VALIDO;
    }

    @Test
    @Order(6)
    public void testEstudianteModificarInexistente() throws Exception {
        String matricula="31081510";
        String nombre="Luis";
        String apPaterno="Padilla";
        String apMaterno="Medina";
        String calle="Av. Torreon 901";
        String colonia="Las Lomas";
        String codpostal="99108";
        String telefono="4331234578";
        String email="lpadilla@uaz.edu.mx";
        long idest=1L;
        long idmun=1001L;

        Estudiante est = new Estudiante(matricula,nombre,apPaterno,email);
        est.setApMaterno(apMaterno);
        est.setCalle(calle);
        est.setColonia(colonia);
        est.setCodPostal(codpostal);
        est.setTelefono(telefono);
        est.setIdEstado(idest);
        est.setIdMunicipio(idmun);

        DaoEscolaresImpl dao= new DaoEscolaresImpl(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado = dao.modificaEstudiante(est);
        }
        catch (DaoException ex) {
            assertNull("No deberia generar excepcion el metodo modificaEstudiante de DaoEscolaresImpl",ex);
        }
        assertFalse(resultado);

        calif_estudiante += CALIF_UPDATE_INVALIDO;
    }

    @Test
    @Order(7)
    public void testEstudianteEliminar() throws Exception {
        String id="31081514";
        DaoEscolaresImpl dao= new DaoEscolaresImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaEstudiante(id);
            assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            assertNull("No deberia generar excepcion el metodo eliminaEstudiante de DaoEscolaresImpl",ex);
        }

        ITable actualTable=conndbunit.createTable("estudiante");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("estudiante.xml"));
        ITable expectedTable=expectedDataSet.getTable("estudiante");
        Assertion.assertEquals(expectedTable,actualTable);
        calif_estudiante += CALIF_DELETE_VALIDO;
    }


    @Test
    @Order(8)
    public void testEstudianteEliminarInexistente() throws Exception {
        String id="31081510";
        DaoEscolaresImpl dao= new DaoEscolaresImpl(conndbunit.getConnection());
        try {
            boolean resultado=dao.eliminaEstudiante(id);
            assertFalse(resultado);
        }
        catch (Exception ex) {
            assertNull("No deberia generar excepcion el metodo eliminaEstudiante de DaoEscolaresImpl",ex);
        }
        calif_estudiante += CALIF_DELETE_INVALIDO;
    }
}
