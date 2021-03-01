package poo2.progs.entidades;


import java.io.Serializable;
import java.util.Objects;

public class Municipio implements Serializable {

  private long idMunicipio;
  private String nombreMunicipio;

  // TODO Colocar los 3 constructores necesarios (en esta y todas las clases de entidad)

  public long getIdMunicipio() {
    return idMunicipio;
  }

  public void setIdMunicipio(long idMunicipio) {
    this.idMunicipio = idMunicipio;
  }


  public String getNombreMunicipio() {
    return nombreMunicipio;
  }

  public void setNombreMunicipio(String nombreMunicipio) {
    this.nombreMunicipio = nombreMunicipio;
  }

  // TODO Crear el equals, hashCode para esta y todas las clases de entidad

  // TODO Crear el toString para esta y todas las clases de entidad donde se solicite
}
