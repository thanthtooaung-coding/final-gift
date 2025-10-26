FROM eclipse-temurin:17-jdk-focal AS build

WORKDIR /workspace

COPY mvnw .
COPY pom.xml .

RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-focal

RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

WORKDIR /app

COPY --from=build /workspace/target/shop-management-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 1500

ENTRYPOINT ["java", "-jar", "app.jar"]
