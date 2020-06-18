# App Android en Kotlin YourConcierge
Parte del proyecto de YourConcierge movil en applicacin android en nativo Kotlin.

#### Tecnologías usadas:
* [Android Studio 3.6](https://developer.android.com/studio)
* [Kotlin](https://kotlinlang.org/)
* [Dagger](https://dagger.dev/)
* [ButterKnife](https://jakewharton.github.io/butterknife/)

***

#### Entorno necesario para el uso del api rest:
* El ide desde el cual instalar la aplicación
* O usar el apk de la app que se encuentra en `/app/release` el archivo `app-release.apk`
* El api rest de la app [Ver](https://github.com/DanielSantanoF/YourConcierge/tree/master/API/YourConcierge) de manera local o de manera remota desde el despliegue de [Heroku](https://www.heroku.com/) ambas url se encuentran en el fochero de `Constant.kt` comentar la que no se desea usar y dejar la otra variable `API_BASE_URL`

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

#### Documentación
* Proyecto enteramente desarrollado en Kotlin
* Uso de ViewModel implementando Android Jetpack
* Uso de Singleton con Dagger
* Uso de ButterKnife en toda a aplicación
