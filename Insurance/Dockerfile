# Start with a base image containing Java runtime
FROM java:8

# Make port 8030 available to the world outside this container
EXPOSE 8030

ADD target/Insurance_Service.jar Insurance_Service.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","Insurance_Service.jar"]