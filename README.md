# Lab Admin Service

Laboratorio para el desarrollo del servicio de administración de la plataforma de servicios criptográficos y certificados mediante Java y Spring Boot.

El objetivo es construir la solución de forma desacoplada, permitiendo integrar progresivamente la infraestructura de laboratorio y posteriormente componentes reales como HSM, PKI y TSA.

Actualmente este servicio concentra las funciones administrativas, autenticación de usuarios y control de acceso que posteriormente serán utilizadas por los servicios criptográficos y de certificados.

## Requisitos de desarrollo

- Java 21
- Docker Desktop
- Docker Compose
- Git

No es necesario instalar Maven globalmente. El proyecto utiliza **Maven Wrapper**.

Verificar Java:

```powershell
java -version
```

Verificar Docker:

```powershell
docker --version
docker compose version
```

## Clonar el proyecto

Clonar el repositorio:

```powershell
git clone <URL_DEL_REPOSITORIO>
```

Entrar al proyecto:

```powershell
cd lab-admin-service
```

Verificar la rama actual:

```powershell
git branch
git status
```

## Preparación del entorno

El proyecto utiliza variables de entorno para permitir que cada desarrollador tenga configuraciones locales diferentes sin modificar los archivos compartidos del repositorio.

Crear el archivo local de variables de entorno a partir de la plantilla:

### Windows PowerShell

```powershell
Copy-Item .env.example .env
```

El archivo `.env` es local y no debe agregarse al repositorio.

La plantilla `.env.example` contiene las variables necesarias para configurar el entorno.

Ejemplo:

```env
POSTGRES_DB=labdb
POSTGRES_USER=labuser
POSTGRES_PASSWORD=labpassword

DB_HOST=localhost
DB_PORT=5432
DB_NAME=labdb
DB_USER=labuser
DB_PASSWORD=labpassword
```

Cada desarrollador puede modificar su archivo `.env` de acuerdo con su entorno local.

Por ejemplo, si el puerto `5432` ya está ocupado:

```env
DB_PORT=5433
```

Esto permite utilizar configuraciones locales diferentes sin modificar `application.yaml` ni generar conflictos al integrar ramas.

## Configuración de PostgreSQL

La aplicación utiliza variables de entorno para construir la conexión con PostgreSQL.

La configuración de Spring Boot utiliza el siguiente esquema:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:labdb}
    username: ${DB_USER:labuser}
    password: ${DB_PASSWORD:labpassword}
```

Si una variable no está definida, Spring Boot utiliza el valor indicado después de `:` como valor por defecto.

Por ejemplo:

```text
DB_HOST -> localhost
DB_PORT -> 5432
DB_NAME -> labdb
DB_USER -> labuser
```

## Levantar PostgreSQL

Desde la raíz del proyecto ejecutar:

```powershell
docker compose up -d
```

Verificar el estado de los contenedores:

```powershell
docker compose ps
```

También puede verificarse directamente con:

```powershell
docker ps
```

PostgreSQL debe aparecer en estado `Up`.

## Compilar el proyecto

En Windows ejecutar:

```powershell
.\mvnw.cmd clean package
```

Maven descargará las dependencias necesarias, compilará el proyecto y ejecutará las pruebas configuradas.

El resultado esperado es:

```text
BUILD SUCCESS
```

## Ejecutar la aplicación

Iniciar Spring Boot:

```powershell
.\mvnw.cmd spring-boot:run
```

Si el arranque es correcto deberá aparecer un mensaje similar a:

```text
Started LabAdminServiceApplication
```

Por defecto, el servicio queda disponible en:

```text
http://localhost:8080
```

Durante el arranque se valida la conexión con PostgreSQL y se inicializan los componentes de persistencia y seguridad.

## Base de datos y migraciones

El proyecto utiliza:

- PostgreSQL 17
- Spring Data JPA
- Hibernate
- Flyway

Durante el inicio de la aplicación, Flyway verifica y aplica las migraciones correspondientes antes de que Hibernate inicialice las entidades.

Esto permite que un desarrollador pueda clonar el proyecto y generar la estructura necesaria de la base de datos a partir de las migraciones versionadas.

## Autenticación

Actualmente se encuentra implementado el flujo de autenticación de usuarios.

El login permite validar:

- Conectividad con el backend.
- Conectividad con PostgreSQL.
- Existencia y estado del usuario.
- Autenticación mediante Spring Security.
- Generación del token de sesión.

El manejo del token de sesión y su utilización para proteger las demás peticiones se encuentra actualmente en desarrollo.

Por este motivo, durante esta etapa la prueba funcional principal después de levantar el proyecto es el **login**.

## Flujo para levantar el proyecto desde cero

El flujo recomendado para un nuevo desarrollador es:

```text
git clone
    |
    v
crear .env desde .env.example
    |
    v
configurar variables locales
    |
    v
docker compose up -d
    |
    v
.\mvnw.cmd clean package
    |
    v
.\mvnw.cmd spring-boot:run
    |
    v
probar login
```

Si Maven devuelve:

```text
BUILD SUCCESS
```

y Spring Boot muestra:

```text
Started LabAdminServiceApplication
```

el entorno base se encuentra correctamente configurado.

## Trabajo con Git

Cada desarrollador debe trabajar sobre su propia rama de desarrollo.

Ejemplo:

```text
main
 |
 +-- Dev-Oscar
 |
 +-- Dev-Compañero
```

Antes de comenzar nuevos cambios se recomienda actualizar la rama local con los últimos cambios integrados al proyecto.

Los cambios terminados deben integrarse posteriormente a `main` mediante el flujo de merge definido por el equipo.

### Configuraciones locales

Las configuraciones específicas de cada equipo no deben modificarse directamente en los archivos compartidos.

Por ejemplo, si un desarrollador utiliza PostgreSQL en:

```text
localhost:5432
```

y otro necesita:

```text
localhost:5433
```

cada uno debe configurar su propio `.env`:

```env
DB_PORT=5432
```

o:

```env
DB_PORT=5433
```

De esta forma los cambios particulares del entorno no se propagan accidentalmente al realizar merges entre ramas.

## Estado actual del laboratorio

Actualmente se ha validado:

- Clonado limpio del repositorio.
- Configuración local mediante variables de entorno.
- PostgreSQL ejecutándose mediante Docker.
- Compilación mediante Maven Wrapper.
- Ejecución de pruebas durante el build.
- Migraciones de base de datos mediante Flyway.
- Persistencia mediante JPA/Hibernate.
- Configuración de Spring Security.
- Arranque correcto de Spring Boot.
- Tomcat ejecutándose en el puerto `8080`.
- Flujo de login.
- Parametrización del puerto local de PostgreSQL.

Actualmente en desarrollo:

- Manejo del token de sesión.
- Validación del token en las peticiones protegidas.
- Continuación de los endpoints administrativos.