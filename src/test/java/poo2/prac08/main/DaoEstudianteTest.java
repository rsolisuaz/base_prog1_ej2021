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
import poo2.progs.basedatos.DaoEstudiante;
import poo2.progs.entidades.Estudiante;
import poo2.progs.interfaces.DaoException;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DaoEstudianteTest extends TestCase{
    private static IDatabaseTester databaseTester;
    private static IDatabaseConnection conndbunit;
    private static List<Estudiante> datosEsestados;
    private static double calificacion;
    public static IDataSet expectedDataSet;

    private final static double CALIF_OBTENER=3;
    private final static double CALIF_OBTENER_ALL=8;
    private final static double CALIF_AGREGAR=4;
    private final static double CALIF_UPDATE=4;
    private final static double CALIF_DELETE=4;
    private final static double MAX_CALIF=50;

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
        expectedDataSet=new FlatXmlDataSetBuilder().build(
                new FileInputStream("estudiante.xml"));
        databaseTester.setDataSet(expectedDataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
        ITable expectedTable=expectedDataSet.getTable("estudiante");
        datosEsestados=new ArrayList<>();
        int numRegistros=expectedTable.getRowCount();
        for (int i=0; i<numRegistros;i++) {
            String matricula = (String) expectedTable.getValue(i,"matricula");
            String nom = (String) expectedTable.getValue(i,"nombre");
            String apPaterno = (String) expectedTable.getValue(i,"ap_paterno");
            String apMaterno = (String) expectedTable.getValue(i,"ap_materno");
            String calle = (String) expectedTable.getValue(i,"calle");
            String colonia = (String) expectedTable.getValue(i,"colonia");
            String codPostal = (String) expectedTable.getValue(i,"cod_postal");
            String telefono = (String) expectedTable.getValue(i,"telefono");
            String email = (String) expectedTable.getValue(i,"email");
            long idest = Long.parseLong(expectedTable.getValue(i,"id_estado").toString());
            long idmun = Long.parseLong(expectedTable.getValue(i,"id_municipio").toString());
            Estudiante est = new Estudiante(matricula,nom,apPaterno,email);
            est.setApMaterno(apMaterno);
            est.setCalle(calle);
            est.setColonia(colonia);
            est.setCodPostal(codPostal);
            est.setTelefono(telefono);
            est.setIdEstado(idest);
            est.setIdMunicipio(idmun);
            datosEsestados.add(est);
        }
    }

    @AfterAll
    public static void termina() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.REFRESH);
        databaseTester.onTearDown();
        System.out.printf("Puntos para Pruebas DAOEstudiante: %.2f/%.2f\n",calificacion, MAX_CALIF);
    }

    private void comparaEstudiante(Estudiante expected,
                                   Estudiante actual) {
        assertEquals(expected.getMatricula(),
                actual.getMatricula());
        assertEquals(expected.getNombre(),
                actual.getNombre());
        assertEquals(expected.getApPaterno(),
                actual.getApPaterno());
        assertEquals(expected.getApMaterno(),
                actual.getApMaterno());
        assertEquals(expected.getCalle(),
                actual.getCalle());
        assertEquals(expected.getColonia(),
                actual.getColonia());
        assertEquals(expected.getCodPostal(),
                actual.getCodPostal());
        assertEquals(expected.getTelefono(),
                actual.getTelefono());
        assertEquals(expected.getEmail(),
                actual.getEmail());
        assertEquals(expected.getIdEstado(),
                actual.getIdEstado());
        assertEquals(expected.getIdMunicipio(),
                actual.getIdMunicipio());
    }

    @Test
    @Order(2)
    public void testGetEstudianteExistente() throws Exception {
        DaoEstudiante daoEstudiante=new DaoEstudiante(conndbunit.getConnection());
        String matriculaABuscar="29807050";
        Optional<Estudiante> actual = Optional.empty();
        try {
            actual = daoEstudiante.get(matriculaABuscar);
        }
        catch (DaoException ex) {
            assertNull("No deberia generar excepcion el metodo get de DaoEstudiante",ex);
        }
        assertTrue(actual.isPresent());
        comparaEstudiante(datosEsestados.get(0),actual.get());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(3)
    public void testGetEstudianteInexistente() throws Exception {
        DaoEstudiante daoEstudiante=new DaoEstudiante(conndbunit.getConnection());
        String matriculaABuscar="0000000";

        Optional<Estudiante> actual = Optional.empty();
        try {
            actual = daoEstudiante.get(matriculaABuscar);
            assertFalse(actual.isPresent());
            actual = daoEstudiante.get(80L);
        }
        catch (DaoException ex) {
            assertNull("No deberia generar excepcion el metodo get de DaoEstudiante",ex);
        }

        assertFalse(actual.isPresent());
        calificacion += CALIF_OBTENER;
    }

    @Test
    @Order(4)
    public void testGetAllEstudiante() throws Exception {
        DaoEstudiante daoEstudiante=new DaoEstudiante(conndbunit.getConnection());
        List<Estudiante> actual= null;
        try {
            actual=daoEstudiante.getAll();
        }
        catch (DaoException ex) {
            actual=new ArrayList<>();
            assertNull("No deberia generar excepcion el metodo getAll de DaoEstudiante",ex);
        }
        assertEquals(actual.size(),datosEsestados.size());
        for (int i=0; i<actual.size(); i++) {
            comparaEstudiante(datosEsestados.get(i), actual.get(i));
        }
        calificacion += CALIF_OBTENER_ALL;
    }

    @Test
    @Order(5)
    public void testSaveEstudianteValido() throws Exception {
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


        DaoEstudiante daoEstudiante=new DaoEstudiante(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado = daoEstudiante.save(est);
        }
        catch (DaoException ex) {
            resultado=false;
            assertNull("No deberia generar excepcion el metodo save de Estudiante",ex);
        }
        assertTrue(resultado);

        ITable actualTable=conndbunit.createTable("estudiante");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("estudiante_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("estudiante");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(6)
    public void testSaveEstudianteDuplicado() throws Exception {
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


        DaoEstudiante daoEstudiante=new DaoEstudiante(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado = daoEstudiante.save(est);
        }
        catch (DaoException ex) {
            resultado=false;
            assertNull("No deberia generar excepcion el metodo save de Estudiante",ex);
        }
        assertFalse(resultado);

        ITable actualTable=conndbunit.createTable("estudiante");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("estudiante_add.xml"));
        ITable expectedTable=expectedDataSet.getTable("estudiante");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_AGREGAR;
    }

    private void validaNull_o_Vacio(DaoEstudiante daoEstudiante,
                                    String valor, String id, boolean nuevo) {
        String matricula=id;
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
        
        boolean resultado=true;
        String valprev;

        Estudiante est = new Estudiante(valor,nombre,apPaterno,email);
        est.setApMaterno(apMaterno);
        est.setCalle(calle);
        est.setColonia(colonia);
        est.setCodPostal(codpostal);
        est.setTelefono(telefono);
        est.setIdEstado(idest);
        est.setIdMunicipio(idmun);

        resultado=nuevo?daoEstudiante.save(est):daoEstudiante.update(est);
        assertFalse(resultado);

        est.setMatricula(matricula);
        valprev=est.getNombre();
        est.setNombre(valor);
        resultado=nuevo?daoEstudiante.save(est):daoEstudiante.update(est);
        assertFalse(resultado);

        est.setNombre(valprev);
        valprev=est.getApPaterno();
        est.setApPaterno(valor);
        resultado=nuevo?daoEstudiante.save(est):daoEstudiante.update(est);
        assertFalse(resultado);

        est.setApPaterno(valprev);
        valprev=est.getEmail();
        est.setEmail(valor);
        resultado=nuevo?daoEstudiante.save(est):daoEstudiante.update(est);
        assertFalse(resultado);

        est.setEmail(valprev);
        valprev=est.getApMaterno();
        est.setApMaterno(valor);
        resultado=nuevo?daoEstudiante.save(est):daoEstudiante.update(est);
        assertTrue(resultado);

        Connection conn;
        Statement stmt=null;
        String sql=String.format("DELETE FROM estudiante WHERE matricula='%s'",matricula);
        try {
            if (nuevo) {
                conn = conndbunit.getConnection();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            assertNull("Problema al eliminar el estudiante que se agrego en la prueba",exsql);
        }


        est.setApMaterno(valprev);
        valprev=est.getCalle();
        est.setCalle(valor);
        resultado=nuevo?daoEstudiante.save(est):daoEstudiante.update(est);
        assertTrue(resultado);
        try {
            if (nuevo) {
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            assertNull("Problema al eliminar el estudiante que se agrego en la prueba",exsql);
        }

        est.setCalle(valprev);
        valprev=est.getColonia();
        est.setColonia(valor);
        resultado=nuevo?daoEstudiante.save(est):daoEstudiante.update(est);
        assertTrue(resultado);
        try {
            if (nuevo) {
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            assertNull("Problema al eliminar el estudiante que se agrego en la prueba",exsql);
        }

        est.setColonia(valprev);
        valprev=est.getCodPostal();
        est.setCodPostal(valor);
        resultado=nuevo?daoEstudiante.save(est):daoEstudiante.update(est);
        assertTrue(resultado);
        try {
            if (nuevo) {
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            assertNull("Problema al eliminar el estudiante que se agrego en la prueba",exsql);
        }

        est.setCodPostal(valprev);
        est.setTelefono(valor);
        resultado=nuevo?daoEstudiante.save(est):daoEstudiante.update(est);
        assertTrue(resultado);
        try {
            if (nuevo) {
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException exsql) {
            assertNull("Problema al eliminar el estudiante que se agrego en la prueba",exsql);
        }
    }

    @Test
    @Order(7)
    public void testSaveEstudianteInvalido() throws Exception {
        String[] matricula={"33081010","MATRICUL"};
        String[] nom={"Mario","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] apPat={"Ramirez","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] apMat={"Juarez","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String calle1="Carretera Zacatecas, Cd Cuauhtemoc km. 5";
        calle1 += calle1;
        calle1 += calle1;
        String[] calle={"Calle Tolosa 25",calle1};
        String colonia1="Ejido Cieneguitas";
        colonia1 += colonia1; colonia1 += colonia1; colonia1 += colonia1; colonia1 += colonia1; colonia1 += colonia1;
        String[] colonia={"Centro",colonia1};
        String[] codpostal={"98000","5AG2B"};
        String[] telefono={"4921234567","49292762AA"};
        String[] email={"xxtat@yho.com","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        long[] idest={32,35};
        long[] idmun={32056,32100};
        

        Estudiante est = new Estudiante(matricula[1],nom[0],apPat[0],email[0]);
        est.setApMaterno(apMat[0]);
        est.setCalle(calle[0]);
        est.setColonia(colonia[0]);
        est.setCodPostal(codpostal[0]);
        est.setTelefono(telefono[0]);
        est.setIdEstado(idest[0]);
        est.setIdMunicipio(idmun[0]);


        DaoEstudiante daoEstudiante=new DaoEstudiante(conndbunit.getConnection());
        boolean resultado;
        //resultado = true;
        try {
            validaNull_o_Vacio(daoEstudiante,null,matricula[0],true);
            validaNull_o_Vacio(daoEstudiante,"",matricula[0],true);

            resultado=daoEstudiante.save(est);
            assertFalse(resultado);

            est.setMatricula(matricula[0]);
            est.setNombre(nom[1]);
            resultado=daoEstudiante.save(est);
            assertFalse(resultado);

            est.setNombre(nom[0]);
            est.setApPaterno(apPat[1]);
            resultado=daoEstudiante.save(est);
            assertFalse(resultado);

            est.setApPaterno(apPat[0]);
            est.setApMaterno(apMat[1]);
            resultado=daoEstudiante.save(est);
            assertFalse(resultado);

            est.setApMaterno(apMat[0]);
            est.setCalle(calle[1]);
            resultado=daoEstudiante.save(est);
            assertFalse(resultado);

            est.setCalle(calle[0]);
            est.setColonia(colonia[1]);
            resultado=daoEstudiante.save(est);
            assertFalse(resultado);

            est.setColonia(colonia[0]);
            est.setCodPostal(codpostal[1]);
            resultado=daoEstudiante.save(est);
            assertFalse(resultado);

            est.setCodPostal(codpostal[0]);
            est.setTelefono(telefono[1]);
            resultado=daoEstudiante.save(est);
            assertFalse(resultado);

            est.setTelefono(telefono[0]);
            est.setEmail(email[1]);
            resultado=daoEstudiante.save(est);
            assertFalse(resultado);

            est.setEmail(email[0]);
            est.setIdEstado(idest[1]);
            resultado=daoEstudiante.save(est);
            assertFalse(resultado);

            est.setIdEstado(idest[0]);
            est.setIdMunicipio(idmun[1]);
            resultado=daoEstudiante.save(est);
            assertFalse(resultado);

        }
        catch (Exception ex) {
            assertNull("No deberia generar excepcion el metodo save de DaoEstudiante",ex);
            System.err.println(ex.getMessage());
            resultado=true;
        }
        assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(8)
    public void testUpdateEstudianteValido() throws Exception {
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


        DaoEstudiante daoEstudiante=new DaoEstudiante(conndbunit.getConnection());
        boolean resultado=false;
        try {
            resultado = daoEstudiante.update(est);
        }
        catch (DaoException ex) {
            resultado=false;
            assertNull("No deberia generar excepcion el metodo save de Estudiante",ex);
        }
        assertTrue(resultado);

        ITable actualTable=conndbunit.createQueryTable("estudiante",
                String.format("SELECT * FROM estudiante WHERE matricula='%s'", matricula));

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("estudiante_upd.xml"));
        ITable expectedTable=expectedDataSet.getTable("estudiante");

        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(9)
    public void testUpdateEstudianteInexistente() throws Exception {
        String matricula="31081522";
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


        DaoEstudiante daoEstudiante=new DaoEstudiante(conndbunit.getConnection());
        boolean resultado=true;
        try {
            resultado = daoEstudiante.update(est);
        }
        catch (DaoException ex) {
            assertNull("No deberia generar excepcion el metodo save de Estudiante",ex);
        }
        assertFalse(resultado);
        calificacion += CALIF_UPDATE;
    }

    @Test
    @Order(10)
    public void testUpdateEstudianteInvalido() throws Exception {
        String[] matricula={"31081514","MATRICUL"};
        String[] nom={"Mario","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] apPat={"Ramirez","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String[] apMat={"Juarez","Nombre Extremadamente Largo para el Limite que tiene el campo y que por tanto no deberia de pasar Tecnologica Estado de Zacatecas"};
        String calle1="Carretera Zacatecas, Cd Cuauhtemoc km. 5";
        calle1 += calle1;
        calle1 += calle1;
        String[] calle={"Calle Tolosa 25",calle1};
        String colonia1="Ejido Cieneguitas";
        colonia1 += colonia1; colonia1 += colonia1; colonia1 += colonia1; colonia1 += colonia1; colonia1 += colonia1;
        String[] colonia={"Centro",colonia1};
        String[] codpostal={"98000","5AG2B"};
        String[] telefono={"4921234567","49292762AA"};
        String[] email={"xxtat@yho.com","correoultralarguisimoquenodeberiadeseraceptado@masalla.delmasalla.delmasalla.com"};
        long[] idest={32,35};
        long[] idmun={32056,32100};


        Estudiante est = new Estudiante(matricula[1],nom[0],apPat[0],email[0]);
        est.setApMaterno(apMat[0]);
        est.setCalle(calle[0]);
        est.setColonia(colonia[0]);
        est.setCodPostal(codpostal[0]);
        est.setTelefono(telefono[0]);
        est.setIdEstado(idest[0]);
        est.setIdMunicipio(idmun[0]);


        DaoEstudiante daoEstudiante=new DaoEstudiante(conndbunit.getConnection());
        boolean resultado;
        //resultado = true;
        try {
            validaNull_o_Vacio(daoEstudiante,null,matricula[0],false);
            validaNull_o_Vacio(daoEstudiante,"",matricula[0],false);

            resultado=daoEstudiante.update(est);
            assertFalse(resultado);

            est.setMatricula(matricula[0]);
            est.setNombre(nom[1]);
            resultado=daoEstudiante.update(est);
            assertFalse(resultado);

            est.setNombre(nom[0]);
            est.setApPaterno(apPat[1]);
            resultado=daoEstudiante.update(est);
            assertFalse(resultado);

            est.setApPaterno(apPat[0]);
            est.setApMaterno(apMat[1]);
            resultado=daoEstudiante.update(est);
            assertFalse(resultado);

            est.setApMaterno(apMat[0]);
            est.setCalle(calle[1]);
            resultado=daoEstudiante.update(est);
            assertFalse(resultado);

            est.setCalle(calle[0]);
            est.setColonia(colonia[1]);
            resultado=daoEstudiante.update(est);
            assertFalse(resultado);

            est.setColonia(colonia[0]);
            est.setCodPostal(codpostal[1]);
            resultado=daoEstudiante.update(est);
            assertFalse(resultado);

            est.setCodPostal(codpostal[0]);
            est.setTelefono(telefono[1]);
            resultado=daoEstudiante.update(est);
            assertFalse(resultado);

            est.setTelefono(telefono[0]);
            est.setEmail(email[1]);
            resultado=daoEstudiante.update(est);
            assertFalse(resultado);

            est.setEmail(email[0]);
            est.setIdEstado(idest[1]);
            resultado=daoEstudiante.update(est);
            assertFalse(resultado);

            est.setIdEstado(idest[0]);
            est.setIdMunicipio(idmun[1]);
            resultado=daoEstudiante.update(est);
            assertFalse(resultado);

        }
        catch (Exception ex) {
            assertNull("No deberia generar excepcion el metodo update de DaoEstudiante",ex);
            System.err.println(ex.getMessage());
            resultado=true;
        }

        assertFalse(resultado);
        calificacion += CALIF_AGREGAR;
    }

    @Test
    @Order(11)
    public void testDeleteEstudianteInexistente() throws Exception {
        String id="SHY$$gq@gaga";
        DaoEstudiante daoEstudiante=new DaoEstudiante(conndbunit.getConnection());
        try {
            boolean resultado=daoEstudiante.delete(id);
            assertFalse(resultado);
        }
        catch (Exception ex) {
            assertNull("No deberia generar excepcion el metodo delete de DaoEstudiante",ex);
        }
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(12)
    public void testDeleteEstudianteExistente() throws Exception {
        String id="31081514";
        DaoEstudiante daoEstudiante=new DaoEstudiante(conndbunit.getConnection());
        try {
            boolean resultado=daoEstudiante.delete(id);
            assertTrue(resultado);
        }
        catch (Exception ex) {
            //System.err.println(ex.getMessage());
            assertNull("No deberia generar excepcion el metodo delete de DaoEstudiante",ex);
        }

        //ITable actualTable=conndbunit.createTable("institucion");
        ITable actualTable=conndbunit.createTable("estudiante");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("estudiante.xml"));
        ITable expectedTable=expectedDataSet.getTable("estudiante");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_DELETE;
    }

    @Test
    @Order(13)
    public void testDeleteEstudianteInvalido() throws Exception {
        String id="29807050";
        DaoEstudiante daoEstudiante=new DaoEstudiante(conndbunit.getConnection());
        try {
            boolean resultado=daoEstudiante.delete(id);
            assertFalse(resultado);
            resultado=daoEstudiante.delete(80L);
            assertFalse(resultado);
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
            assertNull("No deberia generar excepcion el metodo delete de DaoEstudiante",ex);
        }
        ITable actualTable=conndbunit.createTable("estudiante");

        IDataSet expectedDataSet=new FlatXmlDataSetBuilder().build(new File("estudiante.xml"));
        ITable expectedTable=expectedDataSet.getTable("estudiante");
        Assertion.assertEquals(expectedTable,actualTable);
        calificacion += CALIF_DELETE;
    }



}
