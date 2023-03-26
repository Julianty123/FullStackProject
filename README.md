# Empezando con Spring Boot

### Documentacion de referencia
Para mayor referencia, favor considerar las siguientes secciones:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/#build-image)

## Comandos de MySQL

```MySql
# PRIMARY KEY: El valor es unico y no se puede repetir

# Selecciona la base de datos
USE cards;

# Crea una tabla
CREATE TABLE cards (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    numero_validacion INT NOT NULL UNIQUE,
    numero_tarjeta BIGINT NOT NULL UNIQUE,
    titular VARCHAR(255) NOT NULL,
    cedula BIGINT NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    telefono INT NOT NULL,
    enrolada boolean
);

# Inserta datos en la tabla
INSERT INTO cards (numero_validacion, numero_tarjeta, titular, cedula, tipo, telefono, enrolada)
VALUES (99, 1111222233334444, 'Juan Perez', 12345678, 'Debito', 5551234, false);

# Selecciona las filas con el tipo de tarjeta especificado
DELETE FROM cards WHERE tipo = DEFAULT;

# Selecciona todos los datos de la tabla
SELECT * FROM cards;

# Agrega una columna a la tabla
ALTER TABLE cards ADD COLUMN enrolada boolean;

# Actualiza todos los datos de la columna especificada
UPDATE cards SET enrolada = false;

# Limpia la tabla
TRUNCATE TABLE cards;

# Elimina la tabla
DROP TABLE cards;
```
