FROM maven:3.8.5-openjdk-11 AS maven_build

WORKDIR /tmp/

COPY pom.xml /tmp/

RUN mvn verify clean --fail-never

COPY src /tmp/src/

RUN mvn package -DskipTests=true

FROM adoptopenjdk:11-jre-hotspot

COPY --from=maven_build /tmp/target/filestorage-*.jar /data/filestorage.jar

CMD java -jar /data/filestorage.jar

EXPOSE 8080