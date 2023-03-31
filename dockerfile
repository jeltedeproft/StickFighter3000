FROM openjdk:11-jdk-slim
WORKDIR /app
COPY . /app
RUN ./gradlew desktop:dist
CMD ["java", "-jar", "desktop/build/libs/desktop-1.0.jar"]
