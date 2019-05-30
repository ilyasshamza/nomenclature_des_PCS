# nomenclature des PCS
la nomenclature des professions et catégories socio-professionnelles.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

I checked the Maven Central Repository as well as the MvnRepository for the rs2xml JAR file and could not find it. A Google search for a POM corresponding to this dependency also came up empty. However, you can still manually install the JAR into your own local repository and then use it as if it were any other dependency.

```
mvn install:install-file -Dfile=lib/rs2xml.jar -DgroupId=net.proteanit.sql -DartifactId=rs2xml -Dversion=1.0 -Dpackaging=jar

```

 Import the database to your computer and modify the properties of dbinfo.properties


### Build instructions

nomenclature des PCS is a Maven project. It can be built using the following command:

```
mvn install
```
### Running the Application

```
java -jar target/pcs.jar
```

## Deployment

Add additional notes about how to deploy this on a live system


## Authors

* **ilyass hamza** - *Initial work* - [ilyassHamza](https://github.com/ilyasshamza)

## Framed by
* **Stéphane Lopes**   - [Stéphane Lopes](https://github.com/hal91190)
* **Sandrine Vial**  
