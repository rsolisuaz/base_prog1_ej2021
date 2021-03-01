package poo2.progs.interfaces;

import poo2.progs.entidades.*;

import java.util.List;

/**
 * Interface que lista las operaciones a realizar para el manejo
 * de la base de datos controlescolares
 * @author Roberto Solis Robles
 */

public interface DaoEscolares {
        /**
         * Este metodo regresa los estados encontrados en la base de datos en la
         * tabla estado
         *
         * @return Lista de estados
         */
        public List<Estado> obtenEstados();

        /**
         * Este metodo regresa los municipios encontrados en la base de datos en la
         * tabla municipio de un estado en particular
         *
         * @param idEstado ID del estado del que se desean los municipios
         * @return Lista de municipios del estado indicado
         */
        public List<Municipio> obtenMunicipios(long idEstado);

        /**
         * Este metodo regresa las carreras encontradas en la base de datos en la
         * tabla carrera
         *
         * @return Lista de Carreras
         */
        public List<Carrera> obtenCarreras();

        /**
         * Este metodo agrega una carrera  en  la base de datos en la
         * tabla carrera
         *
         * @param c  Informacion de la carrera a agregar
         * @return true si se pudo agregar, false si no
         */
        public boolean agregaCarrera(Carrera c);

        /**
         * Este metodo modifica una carrera  en  la base de datos en la
         * tabla carrera
         *
         * @param c  Informacion de la carrera a modificar
         * @return true si se pudo modificar, false si no
         */
        public boolean modificaCarrera(Carrera c);

        /**
         * Este metodo elimina una carrera  en  la base de datos en la
         * tabla carrera
         *
         * @param clave  Clave de la carrera a eliminar
         * @return true si se pudo eliminar, false si no
         */
        public boolean eliminaCarrera(String clave);

        /**
         * Este metodo regresa las materias encontradas en la base de datos en la
         * tabla materia
         *
         * @return Lista de Materias
         */
        public List<Materia> obtenMaterias();

        /**
         * Este metodo agrega una materia  en  la base de datos en la
         * tabla materia
         *
         * @param m  Informacion de la materia a agregar
         * @return true si se pudo agregar, false si no
         */
        public boolean agregaMateria(Materia m);

        /**
         * Este metodo modifica una materia  en  la base de datos en la
         * tabla materia
         *
         * @param m  Informacion de la materia a modificar
         * @return true si se pudo modificar, false si no
         */
        public boolean modificaMateria(Materia m);

        /**
         * Este metodo elimina una materia  en  la base de datos en la
         * tabla materia
         *
         * @param clave  Clave de la materia a eliminar
         * @return true si se pudo eliminar, false si no
         */
        public boolean eliminaMateria(String clave);

        /**
         * Este metodo regresa los periodos escolares encontrados en la base de datos en la
         * tabla periodoescolar
         *
         * @return Lista de Periodos Escolares
         */
        public List<Periodoescolar> obtenPeriodos();

        /**
         * Este metodo agrega un periodo escolar  en  la base de datos en la
         * tabla periodoescolar
         *
         * @param p  Informacion del periodoescolar a agregar
         * @return true si se pudo agregar, false si no
         */
        public boolean agregaPeriodo(Periodoescolar p);

        /**
         * Este metodo modifica un periodo escolar  en  la base de datos en la
         * tabla periodoescolar
         *
         * @param p  Informacion del periodoescolar a modificar
         * @return true si se pudo modificar, false si no
         */
        public boolean modificaPeriodo(Periodoescolar p);

        /**
         * Este metodo elimina un periodo escolar  en  la base de datos en la
         * tabla periodoescolar
         *
         * @param id  ID del periodo escolar a eliminar
         * @return true si se pudo eliminar, false si no
         */
        public boolean eliminaPeriodo(long id);


        /**
         * Este metodo regresa los profesores encontrados en la base de datos en la
         * tabla profesor
         *
         * @return Lista de profesores
         */
        public List<Profesor> obtenProfesores();

        /**
         * Este metodo agrega un profesor  en  la base de datos en la
         * tabla profesor
         *
         * @param p  Informacion del profesor a agregar
         * @return true si se pudo agregar, false si no
         */
        public boolean agregaProfesor(Profesor p);

        /**
         * Este metodo modifica un profesor  en  la base de datos en la
         * tabla profesor
         *
         * @param p  Informacion del profesor a modificar
         * @return true si se pudo agregar, false si no
         */
        public boolean modificaProfesor(Profesor p);

        /**
         * Este metodo elimina un profesor  en  la base de datos en la
         * tabla profesor
         *
         * @param rfc RFC del profesor a eliminar
         * @return true si se pudo eliminar, false si no
         */
        public boolean eliminaProfesor(String rfc);

        /**
         * Este metodo regresa las cargas del profesor especificado de la base de datos en la
         * tabla cargaprofesor (unida con informacion de la tabla materia)
         *
         * @param rfc  RFC del profesor del cual se desean las cargas
         * @return Lista de Detalles de las Cargas del profesor
         */
        public List<DetalleCargaprofesor> obtenCargaProfesor(String rfc);

        /**
         * Este metodo agrega una carga de profesor  en  la base de datos en la
         * tabla cargaprofesor
         *
         * @param c  Informacion de la carga del profesor a agregar
         * @return true si se pudo agregar, false si no
         */
        public boolean agregaCargaProfesor(Cargaprofesor c);

        /**
         * Este metodo elimina una carga de profesor  en  la base de datos en la
         * tabla cargaprofesor
         *
         * @param idCarga  Identificador de la carga del profesor a eliminar
         * @return true si se pudo eliminar, false si no
         */
        public boolean eliminaCargaProfesor(long idCarga);

        /**
         * Este metodo regresa los estudiantes encontrados en la base de datos en la
         * tabla estudiante
         *
         * @return Lista de estudiantes
         */
        public List<Estudiante> obtenEstudiantes();

        /**
         * Este metodo agrega un estudiante  en  la base de datos en la
         * tabla estudiante
         *
         * @param e  Informacion del estudiante a agregar
         * @return true si se pudo agregar, false si no
         */
        public boolean agregaEstudiante(Estudiante e);

        /**
         * Este metodo modifica un estudiante  en  la base de datos en la
         * tabla estudiante
         *
         * @param e  Informacion del estudiante a modificar
         * @return true si se pudo modificar, false si no
         */
        public boolean modificaEstudiante(Estudiante e);

        /**
         * Este metodo elimina un estudiante  en  la base de datos en la
         * tabla estudiante
         *
         * @param matricula Matricula del estudiante a eliminar
         * @return true si se pudo eliminar, false si no
         */
        public boolean eliminaEstudiante(String matricula);

        /**
         * Este metodo regresa las cargas del estudiante especificado de la base de datos en la
         * tabla cargaestudiante (unida con informacion de la tabla materia)
         *
         * @param matricula  Matricula del estudiante del cual se desean las cargas
         * @return Lista Detalles de las Cargas del estudiante
         */
        public List<DetalleCargaestudiante> obtenCargaEstudiante(String matricula);

        /**
         * Este metodo agrega una carga de estudiante  en  la base de datos en la
         * tabla cargaestudiante
         *
         * @param c  Informacion de la carga del estudiante a agregar
         * @return true si se pudo agregar, false si no
         */
        public boolean agregaCargaEstudiante(Cargaestudiante c);

        /**
         * Este metodo elimina una carga de estudiante  en  la base de datos en la
         * tabla cargaestudiante
         *
         * @param idCarga  Identificador de la carga del estudiante a eliminar
         * @return true si se pudo eliminar, false si no
         */
        public boolean eliminaCargaEstudiante(long idCarga);
}
