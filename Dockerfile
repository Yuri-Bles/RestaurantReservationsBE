FROM eclipse-temurin:25-jre-jammy

WORKDIR /app

COPY artifact/app.jar app.jar


RUN useradd --system --create-home appuser \
    && chown -R appuser:appuser /app

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]