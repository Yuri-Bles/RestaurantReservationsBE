FROM eclipse-temurin:25-jdk-jammy AS build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew # Makes gradle wrapper an executable

COPY src src

RUN ./gradlew bootJar -x test --no-daemon # Creates the docker image


FROM eclipse-temurin:25-jre-jammy

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

RUN useradd --system --create-home appuser \
    && chown -R appuser:appuser /app # Creates user and makes them the only user with permissions.
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]