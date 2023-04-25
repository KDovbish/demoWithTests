FROM eclipse-temurin:11.0.18_10-jre-focal
ADD target/demo-with-tests.jar /app/demo-with-tests.jar
ENTRYPOINT ["java","-jar","/app/demo-with-tests.jar"]