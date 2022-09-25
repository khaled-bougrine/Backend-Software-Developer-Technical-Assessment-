FROM eclipse-temurin:11
RUN mkdir /opt/app
COPY target/eclipse-temurin-service-0.0.1-SNAPSHOT.jar /opt/app
EXPOSE 9443
CMD ["java", "-jar", "/opt/app/eclipse-temurin-service-0.0.1-SNAPSHOT.jar"]