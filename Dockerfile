FROM openjdk:20
EXPOSE 8080
ADD target/tb-zadanie-0.0.1-SNAPSHOT.jar tb-zadanie-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "tb-zadanie-0.0.1-SNAPSHOT.jar"]