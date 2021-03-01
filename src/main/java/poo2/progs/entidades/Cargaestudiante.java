package poo2.progs.entidades;


public class Cargaestudiante {

  private long idCargaEstudiante;
  private String matricula;
  private String claveMateria;
  private long idPeriodo;


  public long getIdCargaEstudiante() {
    return idCargaEstudiante;
  }

  public void setIdCargaEstudiante(long idCargaEstudiante) {
    this.idCargaEstudiante = idCargaEstudiante;
  }


  public String getMatricula() {
    return matricula;
  }

  public void setMatricula(String matricula) {
    this.matricula = matricula;
  }


  public String getClaveMateria() {
    return claveMateria;
  }

  public void setClaveMateria(String claveMateria) {
    this.claveMateria = claveMateria;
  }


  public long getIdPeriodo() {
    return idPeriodo;
  }

  public void setIdPeriodo(long idPeriodo) {
    this.idPeriodo = idPeriodo;
  }

}
