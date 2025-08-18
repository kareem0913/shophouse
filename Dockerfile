FROM eclipse-temurin:21-jre

# set working directory
WORKDIR /app

# copy the JAR file
COPY target/shophouse-0.0.1-SNAPSHOT.jar app.jar

# create uploads directory
RUN mkdir -p /app/uploads

# Expose port
EXPOSE 8080

# set JVM options
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]