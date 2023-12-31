# Arquetipo Java para API Rest

Arquetipo Java para API Rest con base de datos relacional

## OnBoarding

Para iniciar el entorno de desarrollo se necesita cumplir los siguientes requisitos:

- JDK: Eclipse Temurin 11 (en caso de querer JDK17: Eclipse Temurin 17)
- Eclipse JEE 2022-06 con plugins:
  - Spring Tools 4
  - m2e-apt
  - Lombok
  - Sonarlint
- Docker

También se debe instalar el entorno de desarrollo acorde con lo explicado en: https://ssl.izertis.com/developer/stack/back/java/environment-configuration

### Instalar Lombok

Para la instalación de Lombok, es preciso descargar la última versión desde [https://projectlombok.org/download](https://projectlombok.org/download). Se descargará un jar que precisa ser ejecutado:

```bash
java -jar lombok.jar
```

Se seleccionará la ubicación en la que se encuentra instalado Eclipse.

En caso de que de problemas a la hora de generar las clases de Mapstruct, es preciso utilizar una versión parcheada de lombok. Para ello, se ha dejado en \\rackstation\Desarrollo\fuentes\Entorno de desarrollo\Eclipses el fichero lombok-patched-1.18.6.jar. Se deberá configurar en el fichero eclipse.ini, sustituyendo el jar que tiene configurado actualmente por el parcheado

```bash
-javaagent:C:\desarrollo\java\install\eclipse-jee-2018-12-R-win32-x86_64\lombok-patched-1.18.6.jar
```

## Inicialización de la base de datos y solr

La inicialización de la base de datos y solr se realiza con docker. En primer lugar es preciso modificar el fichero ```docker-devenv\.env``` y actualizar la variable de entorno ```COMPOSE_PROJECT_NAME```

En el directorio docker-devenv se ha configurado un fichero docker-compose.yml para poder arrancar el entorno de desarrollo. Actualmente contiene los siguientes elementos:

- Postgres 14
- Solr 8.11 (no activo por defecto)

En caso de querer cambiar una versión o añadir un nuevo elemento al docker-compose, se puede buscar la imagen apropiada en https://hub.docker.com/

Se pueden eliminar los elementos que no se vayan a utilizar.

Para arrancar el entorno:

```bash
docker-compose up -d
```

Para pararlo:

```bash
docker-compose down
```

En caso de querer levantar Solr, se precisa descomentar del fichero `docker-compose.yml` la sección correspondiente. Acceder a solr (http://localhost:8983/solr/#/~collections) y crear una colección llamada app con el esquema default

## Metodología de desarrollo

La metodología de desarrollo es Git Flow. Se puede obtener más información en: https://ssl.izertis.com/es/developer/git/git-flow

## Inserción de usuario inicial

La inserción de un usuario administrador inicial, con nombre de usuario `user` y contraseña `secret` se hace mediante el siguiente script:

```sql
INSERT INTO APPLICATION_USER 
	(ID, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED, ADDRESS, CITY, COUNTRY, CREDENTIALS_NON_EXPIRED, EMAIL, ENABLED, LANGUAGE, NAME, PASSWORD, PASSWORD_RECOVERY_HASH, USERNAME, VERSION) 
VALUES 
	('1', true, true, '1', '1', '1', true, 'b@t.com', true, '1', 'user', '{bcrypt}$2a$10$08d0l3aRNN.DQ/CgHFmZNOUmpaWSjWsTRzN/Dcd.1WEhpQ13CEDxK', 'secret', 'user', 1);

INSERT INTO application_user_role (ROLE, USER_ID) VALUES ('ADMINISTRATOR', '1');
```

Se ha dejado el script en `db\02.default-data.sql`

## OAuth

En caso de querer utilizar OAuth con persistencia de sus datos en base de datos, además de configurar la aplicación, es preciso crear las tablas correspondientes. Se ha dejado el script en ```db\01.oauth-schema.sql```. Esto creará también un cliente con el usuario 'acme' y contraseña 'acmesecret'``.

### Ejemplo de configuración de petición con postman

Para poder realizar una petición a un servicio autenticado (por ejemplo ``/user/list``), habrá que configurar los siguientes parámetros en la pestaña 'Authorization' (Ejemplo con la configuración por defecto del arquetipo):

- Type: `OAuth 2.0`
- Grant Type: `Password Credentials`
- Access Token URL: `http://localhost:8080/oauth/token`
- Client ID: `acme`
- Client Secret: `acmesecret`
- Username: `user`
- Password: `secret`

## Java 11

Para descargar JDK 11, se precisa utilizar Eclipse Temurin (aka AdoptOpenJDK), la cual se puede obtener de https://adoptium.net/temurin/releases?version=11

## Java 17

La aplicación está preparada para funcionar con JDK 11. En caso de necesitar trabajar con un JDK superior, es preciso especificar una propiedad en el POM:

```xml
<properties>
	<java.version>17</java.version>
</properties>
```

Para descargar JDK 17, se precisa utilizar Eclipse Temurin (aka AdoptOpenJDK), la cual se puede obtener de https://adoptium.net/temurin/releases?version=17

## Swagger

Se ha añadido la posibilidad de utilizar Swagger, el cual se ha configurado como Starter:

Para acceder a Swagger, se utilizará la siguiente URL:

- http://localhost:8080/swagger-ui/index.html

## Transacciones Spring

Una cosa a tener en cuenta es cómo funcionan las transacciones con spring. Para declarar un método como transaccional, es preciso añadir la anotación @Transactional, la cual se puede añadir a nivel de clase y de método. Lo ideal es configurarlo en los service siguiendo el siguiente patrón:

- Clase
  - readOnly: `true`
  - propagation: `supports`
- Métodos que modifiquen información (save, update, delete, etc.)
  - readOnly: `false`
  - propagation: `required`

Por ejemplo:

```java
@IndexableClass
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public User save(final User entity) {
        User user = new User();
        user.setUsername("user69");
        this.userRepository.save(user);
        
        return save2(entity);
    }
    
    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
```

Es preciso tener en cuenta que en caso de que se produzca una excepción durante la transacción no siempre se realiza el rollback por defecto, las reglas a seguir son las siguientes:

- Excepciones unchecked (runtime): aplica por defecto rollback, por ejemplo todas las excepciones sobre constraints violadas entrarían por esta opción
- Excepciones checked: no aplica rollback por defecto, es preciso configurarlas mediante el parámetro rollbackFor de la anotación `@Transactional`
