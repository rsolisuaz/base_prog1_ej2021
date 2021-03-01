# PROGRAMA 1. CLASES DE ENTIDAD Y DAO PARA LA BASE DE DATOS controlescolar.

Este proyecto será usado tanto para el Programa 1 como para la Práctica 8. En la práctica 8, bajo la guía del maestro se implementará el Dao para las tablas municipio y estudiante, las cuales son un subconjunto de lo que se hará para el programa 1.

## COPIA DEL REPOSITORIO REMOTO EN SU COMPUTADORA LOCAL

Como primer paso, será necesario crear una copia local del repositorio remoto creado en Github al aceptar la tarea. Para ello, es necesario hacer los siguientes pasos:
1. Entrar a la página cuyo URL les fue proporcionado al aceptar la tarea, en tal página dé click en el botón Code y copie el URL que aparece en el cuadro de texto de nombre **Clone with HTTPS** (comienza con *https://*)

2. Si usarás IntellijIDEA entonces haz los siguientes pasos:
   - Abre IntelliJ IDEA e indica que harás un clon local de tu repositorio:
   - Si no tienes ningún repositorio abierto selecciona la opción **Get From Version Control** de la Ventana de Bienvenida, o si tienes un proyecto abierto, puedes entrar al menú **VCS**  y seleccionar la opción **Get From VCS**
   - En el cuadro de diálogo que aparece:
     - Selecciona Git
     - Pega el URL que copiaste en el paso 1  en el cuadro de texto **URL**
     - Selecciona en Directory la carpeta donde lo colocarás, es importante que crees una nueva carpeta o se colocará (da click en el icono de carpeta , navega a donde lo quieres colocar y da click en el icono de New Folder para crear una nueva carpeta)
     - Da click en **Clone**
     - Si te pide usuario y clave de Github proporciona esos datos
     - Después de unos segundos tendrás listo tu un clon de tu repositorio listo para trabajar en Intellij IDEA

3. Si NO usarás IntellijIDEA y harás todo desde la ventana de comandos usando un editor de texto tal como Sublime haz los siguientes pasos:

   - En una consola de Git Bash en Windows (o en una terminal en Linux o Mac), cree una carpeta donde quiera que se contengan sus prácticas del semestre (si es que aún no la has creado) y colócate en tal carpeta. La carpeta la puedes crear desde el Git Bash o terminal Linux/Mac usando el comando `mkdir` (o con el explorador de archivos de su sistema operativo) y en la consola de Git Bash o terminal de Linux/Mac te puedes cambiar a la carpeta mencionada usando el comando `cd`
   - Clone el repositorio privado dando el comando `git clone URL programa_01`
   (donde **URL** es el URL que copió en el paso 1)\
   Este comando creará dentro de la carpeta creada en el paso 2) una subcarpeta de nombre **programa_01** donde estará una copia local del repositorio remoto.\
   Los comandos posteriores de git tendrán que darse desde tal carpeta, por tanto, cámbiate a la carpeta usando `cd programa_01`

## EJECUCIÓN DE SCRIPTS PARA CREAR LA BASE DE DATOS NECESARIA Y COLOCARLE DATOS

Deberá ejecutar los scripts **creaescolar.sql** y **llenaescolar.sql** que se encuentran en la carpeta raíz del proyecto que acaba de clonar. La ejecución de estos scripts debe ser realizado  de la siguiente manera desde la terminal de comandos, **habiéndose colocado primero en la carpeta raíz del proyecto**:
```
mysql -u root -p  --default-character-set=utf8 < creaescolar.sql  
mysql -u root -p  --default-character-set=utf8 < llenaescolar.sql
```

Para cada uno de estos se le pedirá la clave de root, tecléela y presione Enter. El segundo comando puede tomar algunos segundos pues coloca varios registros en las tablas.

## MODIFICACIÓN DEL CÓDIGO PROPORCIONADO CON RESPECTO A LA PRÁCTICA 08

Una vez que tengas el repositorio local, el trabajo consiste en completar las clases de entidad para la base de datos **controlescolar** asociadas con las tablas **municipio** y **estudiante**,  implementar la interface **Dao<T>** para hacer las operaciones CRUD en tales tablas y los métodos asociados con municipio y estudiante de la interface  **DAOEscolares**, la implementación de la interface **Dao<T>** se hará en las clases **DaoMunicipio** y **DaoEstudiante** y la implementación de la interface **DaoEscolares** en la clase **DaoEscolaresImpl**, todas a colocarse en el paquete **poo2.progrs.basedatos** .

En el paquete **poo2.progs.main** se te recomienda (aunque no es obligatorio ni es algo que se vaya a calificar) crear clases para que pruebes que tus clases **DaoMunicipio**, **DaoEstudiante** y **DaoEscolaresImpl** funcionan correctamente.


## CALIFICACIÓN PARA LA PRÁTICA 08
Cada uno de los métodos que debes implementar de la de la interface **Dao<T>** y **DaoEscolares**, además de las clases de entidad de **municipio** y **estudiante** aportan parte de la calificación:

1. La clases de entidad aportan  **6 puntos**
2. La implementación de Dao<Municipio> en la clase DaoMunicipio aporta **14 puntos**
3. La implementación de Dao<Estudiante> en la clase DaoEstudiante aporta **50 puntos**
4. La implementación del método obtenMunicipios() en DaoEscolaresImpl aporta **20 puntos**
5. La implementación de los métodos que tienen que ver con la clase entidad Estudiante en DaoEscolaresImpl aportan **10 puntos**

Para ejecutar las pruebas de tu programa selecciona el archivo correspondiente a lo que quieres probar de entre  los archivos del paquete **poo2.prac08.main** en la sección **test** del proyecto, dale click con el botón derecho y selecciona **Run** (la opción tendrá un triángulo verde ), te mostrará el resultado de las pruebas y la calificación obtenida

**RECUERDA QUE NO DEBES TOCAR EL CÓDIGO DE LAS PRUEBAS, A MENOS QUE EL INSTRUCTOR TE LO INDIQUE** 

## NOTAS IMPORTANTES PARA LA PRACTICA 08
1. Tanto la clase que implemente la interface DAO<T> como la clase que implemente la interface DAOEscolares deben tener 3 constructores:
   - uno que no reciba argumentos, el cual creara un objeto Connection con los valores por default (servidor en localhost, usando la base de datos controlescolares y conectandose con el usuario IngSW2021 con clave UAZsw2021) ayudándose de DriverManager
   - otro que reciba como argumentos la ubicacion del servidor, nombre de la base de datos a usar, usuario y clave con el que se conectará (todos String) y a partir de ellos crear la conexión ayudándose de DriverManager
   - otro que reciba como argumento el objeto Connection a usar, en cuyo caso simplemente lo asigna a su atributo Connection, generando una excepción en caso de que el objeto recibido sea null

2. En cada uno de los constructores de las interfaces DAO<T> y DAOEscolares que implementarás, debes capturar las posibles excepciones que se den al conectarse a MySQL (si es que aplica) y en su lugar emitirás una excepción de tipo DaoException con los mismos mensajes y causas que la excepción original, esto para que en caso de que quien use el constructor tenga la opción de determinar las causas del error.

3. La clase que implemente DAOEscolares debe apoyarse en las clases que implementen DAO<T> para realizar su trabajo, por lo cual deberá contener objetos de tales clases los cuales serán inicializados al construirse el objeto en un método de nombre inicializaDaos.

4. Al agregar o actualizar es necesario asegurarse que los campos tienen un rango válido de valores, en caso contrario debería devolver false el método correspondiente. En el caso de los id de municipio o estado, verificar que existen en la tabla municipio y entidad respectivamente (en las pruebas del repositorio remoto solo se puden usar las entidades 1 a 5 y la 32 con sus respectivos municipios)

5. Al eliminar un estudiante es necesario verificar primero que no haya registros asociados con el estudiante a eliminar, es decir, primero hay que ver que ningun registro de cargaestudiante esté asociado al estudiante a eliminar, si existe algun registro asociado, no se deberá eliminar el estudiante y se regresará false.

6. La clases de entidad de estudiante Y municipio deberán cumplir lo siguiente:
   - deberán tener 3 constructores: uno vacío, uno que reciba como argumento solo la llave primaria y otro que reciba como argumentos los valores obligatorios (marcados como NOT NULL en la creación de la tabla), los cuales deberán ser recibidos en el orden en que están declarados en la tabla
   - deben implementar la interface Serializable
   - para la clase Municipio el metodo toString deberá regresar el nombre del municipio, y los métodos equals y hashCode solo deben considerar el atributo idMunicipio
   - para la clase Estudiante el metodo toString deberá regresar el valor de nombre seguido de un espacio, del valor de apPaterno, de un espacio y del valor de apMaterno (el espacio y valor de apMaterno solo se incluye en lo que se regresa si apMaterno no es null y tiene por lo menos un carácter, y los métodos equals y hashCode solo deben considerar el atributo matricula

7. Cada vez que completes un método ejecuta las pruebas para verificar que las pruebas del método completado son exitosas

8. Una vez vez que completes un método y verifiques que pasa las pruebas haz un commit usando el comando que corresponda de acuerdo a la forma en que estés trabajando en el proyecto (desde Intellij IDEA o desde la ventana de comandos). Asegúrate de incluir en el commit el archivo involucrado (usando git add en la terminal de comandos o seleccionandolo en IntelliJ) y de teclear un mensaje que describa los cambios realizados de manera clara y concisa (debe ser un mensaje que permita a otras personas darse cuenta de lo realizado)

9. Después de haber hecho todos los commits que completan tu programa, súbelo al repositorio remoto haciendo un git push

10. Cada vez que subas tu proyecto al repositorio privado con un push, se aplicarán automáticamente las pruebas sobre tu código para verificar si funciona correctamente. Recuerda que en la página de tu repositorio en la sección **Pull Requests**, se encuentra una subsección de nombre **Feedback**, donde podrás encontrar los resultados de las pruebas en la pestaña denominada **Check** (expandiendo la parte que dice **Run education/autograding@v1**), y cualquier comentario general que el profesor tenga sobre tu código en la pestaña **Conversation**. 


## REQUERIMIENTOS DEL PROGRAMA 1

La lista de requerimientos completa que deben cumplirse para el programa 01 los puede encontrar en el archivo **POO2_Progr1EneJun2021.pdf**


## CALIFICACIÓN PARA EL PROGRAMA 1

La calificación para esta se calculará de manera automatizada al hacer push, recuerde que es necesario ir actualizando las pruebas conforme el instructor se lo indique. En el documento PDF están los puntos asignados a cada componente del programa 1.

## NOTAS IMPORTANTES PARA EL PROGRAMA 1.

1. Recuerda que el proceso que debes estar haciendo es:
   - Crea la clase DAO correspondiente y/o implementa alguno de los métodos que debe tener.
   - Una vez que verifiques que el código realizado pasa las pruebas haz un commit usando el comando que corresponda de acuerdo a la forma en que estés trabajando en el proyecto (desde Intellij IDEA o desde la ventana de comandos). Asegúrate de incluir en el commit el archivo involucrado (usando git add en la terminal de comandos o seleccionandolo en IntelliJ) y de teclear un mensaje que describa los cambios realizados de manera clara y concisa (debe ser un mensaje que permita a otras personas darse cuenta de lo realizado)
  
2. Ve integrando y ejecutando las pruebas que se te van a ir entregando durante la semana para verificar que tu programa cumple con los requerimientos. 

3. Si la parte que ya tienes pasa las pruebas correspondientes, súbelo al repositorio remoto dando `git push`. De otra manera, corrige los errores (haciendo las pruebas, git add y git commit correspondientes)

4. Recuerda que cada vez que hagas `git push` se realizarán automáticamente pruebas sobre tu código para verificar si funciona correctamente. Para esta práctica en particular no se te proporcionará una calificación de manera inmediata pues debe ser revisada con mayor detalle. Recuerda que en la página de tu repositorio en la sección **Pull Requests**, se encuentra una subsección de nombre **Feedback**, donde podrás encontrar los resultados de las pruebas en la pestaña denominada **Check** (expandiendo la parte que dice **Run education/autograding@v1**), y cualquier comentario general que el profesor tenga sobre tu código en la pestaña **Conversation**. 
