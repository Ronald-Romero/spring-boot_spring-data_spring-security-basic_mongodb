FROM openjdk:23-rc-jdk

EXPOSE 8080

WORKDIR /app-mongo

COPY ./pom.xml /app-mongo
COPY ./.mvn /app-mongo/.mvn
COPY ./mvnw /app-mongo

RUN ./mvnw dependency:go-offline

COPY ./src /app-mongo/src

RUN ./mvnw clean install -DskipTests

ENTRYPOINT ["java", "-jar", "/app-mongo/target/demo-0.0.1-SNAPSHOT.jar"]