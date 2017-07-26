FROM frolvlad/alpine-oraclejdk8:slim
ADD target/foodie-recommendation-service-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-Dspring.profiles.active=cloud"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]