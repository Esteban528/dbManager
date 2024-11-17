![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

# JavaDBManager

### Description
This is a small tool for interacting with databases, mainly focused on the database development stage.

### Dependencies 
- [Apache Maven](https://maven.apache.org/)
- [Java 17](https://openjdk.org/projects/jdk/17/)

## Database Support
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

## Use

```bash
# Clone this repo
git clone https://github.com/Esteban528/dbManager.git

# Packaging project
mvn clean package

java -jar target/dbmanager-1.0-SNAPSHOT.jar
```

## Development
This project has three layer architecture with a business delegate for more decoupling.
