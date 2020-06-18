# ApiRest YourConcierge
Api Rest en Spring con Kotlin.
* En la raíz del api rest se encuentra una colección de postman para el uso de esta ApiRest en formato .json  que puedes importar a postman para el uso del api rest desde [Postman](https://www.postman.com/)
***

#### Tecnologías usadas:
* [Spring](https://spring.io/)
* [Kotlin](https://kotlinlang.org/)
* [Maven](https://maven.apache.org/)
* IDE: [IntelliJ IDEA](https://www.jetbrains.com/es-es/idea/) necesario para arrancar la api rest (se debe arrancar desde el IDE o terminal)

***

#### Entorno necesario para el uso del api rest:
* `JDK` esta api rest requiere del JDK 11 de java
* Maven

***

#### Datos de acceso:
* role USER:
```
username: username
password: 123456
```
* role ADMIN: 
```
username: admin
password: 123456
```

***

#### Usar la Api Rest:
El archivo `application.properties` es diferente dependiendo si usas h2 o postgresql como base de datos en local.
 
* En local con h2 de la siguiente manera: 

    Abrir desde el ide de IntelliJ Idea el proyecto y arrancarlo desde la opción del ide, o bien ir a la ruta del api rest y ejecutar el comando de maven -> `mvn spring-boot:run`, de manera local con h2 puedes acceder a la base de datos desde la url `localhost:9000/h2-console` con usuario `sa` y sin contraseña como esta indicado en el application.properties del api rest

* En local con Postgresql de la siguiente manera:

    Abrir desde el ide de IntelliJ Idea el proyecto y arrancarlo desde la opción del ide, o bien ir a la ruta del api rest y ejecutar el comando de maven -> `mvn spring-boot:run`, y debes ejecutar el comando `docker-compose up -d` para levantar el contenedor de docker de postgresql con el fichero docker-compose.yml que se encuentra en la raiz del api rest.

* De manera remota:

    El api rest se encuentra desplegada en [Heroku](https://www.heroku.com/) en la url `https://yourconcierge.herokuapp.com/`