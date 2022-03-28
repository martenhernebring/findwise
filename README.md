# Elastic search
Author: Mårten Hernebring
----------------------------------------------------
###Technologies:
* Maven
* JPA
* Spring Boot
* H2 in memory DB
----------------------------------------------------
###To run locally (development environment):
1. Open terminal
2. `mvn package`
3. a) `java -jar target/search-0.0.1-SNAPSHOT.jar default` for default values OR
 b) `java -jar target/search-0.0.1-SNAPSHOT.jar "custom1" "custom2" ...` for custom
4. a) `fox` to search for documents containing fox OR
b) Enter to quit the app.

Requirements:
* Maven
* Java (language level 17, but probably 11+ and maybe 8 will work as well)