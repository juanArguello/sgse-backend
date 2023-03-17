# SGSE - Sistema de Gestion de Ventas Seguros 
El Sistema SGSE realiza la gestion de ventas y facturacion de seguros  
exequiales para la empresa Futuro, sus principales funciones son:  
> Registrar, editar, borrar y visualizar Usuarios, Roles y permisos.  
> Registrar ventas de seguros.  
> Registrar datos del cliente.  
> Emisión de facturas por cada transacción comercial realizada.  

## Recursos implementado en el sistema

El sistema SGSE utiliza el gestor de contrucción y dependencia [Maven](https://maven.apache.org/), en el back-end  
utiliza el lenguaje Java versión 11 y el framework [Spring](https://github.com/spring-projects/spring-boot/tree/2.7.x) versión 2.7.9.RELEASE. 
El servidor de base de datos es  
[PostgreSQL](https://www.postgresql.org/) y el servidor de aplicación es [Apache Tomcat](http://tomcat.apache.org/). 

## Construcción del proyecto

Ejecute el comando `./mvnw clean` para limpiar el proyecto. Despues ejecute `./mvnw install` para construir el empaquetado  
JAR o WAR en el repositorio local.
  
## Ejecucion del sistema
Ejecute el comando `./mvnw spring-boot:run` para levantar el servidor
  
  
Proyecto del codigo fuente alojado en **GitHub**