# Back-end del sistema CodexRM
 Encargado de establecer las conexiones entre los clientes (escritorio, web, móvil), con la base de datos, mediante la utilización de Spring Boot, framework que facilita el 
 desarrollo y dspliegue los servicios REST. Utiliza el patrón de Lectura/Escritura de datos llamado "La última escritura gana" para la sincronización de los datos. Es posible
 almacenar la información de forma segura y compacta mediante la utilización de JWT token, estandar para la autenticación y el intercambio de infomación. 
 Cuenta con los siguientes servicios:
 - Obtener el listado de todas las referencias de la base de datos, listado de todas las referencias de un usuario y el listado de los usuarios. Estos listados son paginados,
 ordenados e incluso filtrados bajo determinados criterios.
 - Realizar la sincronización de las referencias con la base de datos y obtener el listado de referencias resultantes mediante patrón de Lectura/Escritura de datos.
 - Se realiza la exportación e importación de un conjunto de referencias en los formatos Ris o BibTeX.
