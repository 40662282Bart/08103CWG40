FROM openjdk:latest
COPY ./target/Test1-1.0-test-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "Test1-1.0-test-jar-with-dependencies.jar"]

#COPY will copy a file or folder from the source on the local machine to the destination in the Docker image. Here we are copying the folder com from target/classes in the project to the folder /tmp/com. The source is where IntelliJ has been building our classes. The destination is the /tmp folder in the image.
 #WORKDIR states where we want Docker to execute programs from in the container - the working directory. This is /tmp - the same location we copied our classes to.
 #ENTRYPOINT tells Docker what to execute when the container is created. That is, run java with the class com.napier.sem.App.