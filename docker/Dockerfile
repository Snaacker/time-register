FROM openjdk:18-jdk
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring

# TODO: create dockercontext
WORKDIR .
ADD time-register-server-boot.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
