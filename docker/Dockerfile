FROM adoptopenjdk/openjdk15
ARG JAR_FILE=*.jar
COPY /backend-0.0.1-SNAPSHOT.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]