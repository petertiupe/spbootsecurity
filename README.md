# spbootsecurity
Spring-Boot-Security Beispiel mit Kommentaren.

Das Beispiel folgt der folgenden [Seite](https://spring.io/guides/gs/securing-web/#scratch) und ist hier nur mit meinen
Kommentaren versehen. Hier geht es zur [Spring-Security-Architektur](https://spring.io/guides/topicals/spring-security-architecture/).


Zunächst hat man ein einfaches Spring-MVC-Projekt, das um Spring-Security ergänzt wird. Dazu sind die folgenden
Schritte erforderlich:
### Abhängigkeiten 
Hinzufügen von Spring-Boot-Security zu den Abhängigkeiten der build.gradle.kts:
````kotlin
// Spring-Boot-Security
implementation("org.springframework.boot:spring-boot-starter-security")
implementation("org.springframework.security:spring-security-test")
````
### Konfiguration
Anschließend ist die nächste Aufgabe darin zu sehen, die Security zu konfigurieren.
Im Beispiel wird dazu noch der folgende Code verwendet, der allerdings mit einem Adapter
arbeitet, der deprecated ist:

````kotlin
@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
   
}
````

Die Annotation `@EnableWebSecurity` sorgt dafür, dass die Security für das MVC-Framework enabled und
integriert wird.

Die Methode [`override fun configure(http: HttpSecurity)`](./src/main/kotlin/de/tiupe/spbootsecurity/config/WebSecurityConfig.kt)
definiert, welche Pfade abgesichert sein sollen und welche nicht.

**Grundsätzlich sichert Spring-Security alle Pfade ab, wenn nichts anderes geschrieben steht.**

Die Methode [`override fun userDetailsService(): UserDetailsService`](./src/main/kotlin/de/tiupe/spbootsecurity/config/WebSecurityConfig.kt)
setzt einen inmemory User-Store für die Benutzer auf. Hier sollte natürlich besser eine andere Anbindung genutzt werden.
Username und Passwort werden direkt in der Klasse genannt. Für die Login-Seite gibt es bereits einen Controller, diesen 
braucht man nicht selbst zu schreiben. Lediglich die Seite [`login.html`](./src/main/resources/templates/login.html)
muss man definieren.


