FROM openjdk:17
EXPOSE 9000
COPY --from=build /app/target/centranord-api.jar /centranord.jar
CMD ["java", "-jar", "/centranord.jar"]




