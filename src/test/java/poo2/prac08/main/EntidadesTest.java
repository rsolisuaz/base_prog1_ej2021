package poo2.prac08.main;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import poo2.progs.entidades.Estudiante;
import poo2.progs.entidades.Municipio;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntidadesTest {

    private static double calificacion;
    private final static double CALIF_CLASE_ESTUDIANTE=3.5;
    private final static double CALIF_CLASE_MUNICIPIO=2.5;
    private final static double MAX_CALIF=6;

    @BeforeAll
    static void beforeAll() {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);
        calificacion=0;
    }

    @AfterAll
    static void afterAll() {
        System.out.printf("Puntos para Pruebas de las Clases de Entidad: %.2f/%.2f\n",calificacion,MAX_CALIF);
    }

    @Test
    void testMunicipio() {
        long idmun=30012;
        String nombre="Mi nuevo municipio";
        String otroNombre="Otro municipio";

        Municipio mun = new Municipio(idmun,nombre);
        assertEquals(idmun,mun.getIdMunicipio());
        assertEquals(nombre,mun.getNombreMunicipio());
        assertEquals(nombre,mun.toString());

        Municipio otro = new Municipio(idmun);
        otro.setNombreMunicipio(otroNombre);
        assertEquals(mun,otro,"La clase Municipio deberia comparar dos objetos en base a idMunicipio");

        otro.setIdMunicipio(idmun+1);
        otro.setNombreMunicipio(nombre);
        assertNotEquals(otro, mun);
        boolean esSerializable=mun instanceof Serializable;
        assertTrue(esSerializable,"La clase Municipio deberia implementar Serializable");
        calificacion += CALIF_CLASE_MUNICIPIO;
    }

    @Test
    void testEstudiante() {
        String matricula="36177218";
        String nombre="Roberto";
        String apPaterno="Solis";
        String apMaterno="Robles";
        String nomcompleto=String.format("%s %s %s",nombre,apPaterno,apMaterno);
        String calle="Hidalgo 100";
        String colonia="Centro";
        String codpostal="98000";
        String telefono="4921234567";
        String email="rsolis@uaz.edu.mx";
        long idest=32L;
        long idmun=32056L;



        Estudiante est = new Estudiante(matricula,nombre,apPaterno,email);
        est.setApMaterno(apMaterno);
        est.setCalle(calle);
        est.setColonia(colonia);
        est.setCodPostal(codpostal);
        est.setTelefono(telefono);
        est.setIdEstado(idest);
        est.setIdMunicipio(idmun);
        assertEquals(matricula,est.getMatricula());
        assertEquals(nombre,est.getNombre());
        assertEquals(apPaterno,est.getApPaterno());
        assertEquals(apMaterno,est.getApMaterno());
        assertEquals(calle,est.getCalle());
        assertEquals(colonia,est.getColonia());
        assertEquals(codpostal,est.getCodPostal());
        assertEquals(telefono,est.getTelefono());
        assertEquals(email,est.getEmail());
        assertEquals(idest,est.getIdEstado());
        assertEquals(idmun,est.getIdMunicipio());
        assertEquals(nomcompleto,est.toString());

        String otroNombre="Mario";
        String otroApPaterno="Gonzalez";
        String otraMatricula="36177290";
        String otroNomcompleto=String.format("%s %s",otroNombre,otroApPaterno);

        Estudiante otro = new Estudiante(matricula);
        otro.setNombre(otroNombre);
        otro.setApPaterno(otroApPaterno);
        assertEquals(est,otro,"La clase Estudiante deberia comparar dos objetos en base a la matricula solamente");
        assertEquals(otroNomcompleto,otro.toString());


        otro.setMatricula(otraMatricula);
        otro.setNombre(nombre);
        otro.setApPaterno(apPaterno);
        otro.setApMaterno(apMaterno);
        assertNotEquals(otro, est, "La clase Estudiante deberia comparar dos objetos en base a la matricula solamente");
        boolean esSerializable=est instanceof Serializable;
        assertTrue(esSerializable, "La clase Estudiante deberia implementar Serializable");
        calificacion += CALIF_CLASE_ESTUDIANTE;
    }

}
